package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import cn.cf.common.Constant;
import cn.cf.common.RestCode;
import cn.cf.constant.Block;
import cn.cf.dto.B2bAddressDtoEx;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bContractGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.dto.B2bOrderDtoEx;
import cn.cf.dto.B2bOrderGoodsDtoEx;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bTokenDto;
import cn.cf.entity.B2bContractDtoEx;
import cn.cf.entity.B2bOrderGoodsUpdateOrderShiped;
import cn.cf.entity.B2bOrderGoodsUpdateOrderStatus;
import cn.cf.entity.B2bOrderGoodsUpdateWeight;
import cn.cf.entity.B2bOrderUpdateOrderShiped;
import cn.cf.entity.B2bOrderUpdateOrderStatus;
import cn.cf.entity.B2bOrderUpdateWeight;
import cn.cf.entity.ContractSyncToMongo;
import cn.cf.entity.ErpContractSync;
import cn.cf.entity.ErpGoodsSync;
import cn.cf.entity.OrderSync;
import cn.cf.entity.OrderSyncToMongo;
import cn.cf.jedis.JedisUtils;
import cn.cf.json.JsonUtils;
import cn.cf.service.B2bCompanyService;
import cn.cf.service.B2bContractGoodsService;
import cn.cf.service.B2bContractService;
import cn.cf.service.B2bOrderGoodsService;
import cn.cf.service.B2bOrderService;
import cn.cf.service.B2bStoreService;
import cn.cf.service.CrmShService;
import cn.cf.service.CrmXfmService;
import cn.cf.service.HuaxianhuiCrmService;
import cn.cf.service.HuaxianhuiService;
import cn.cf.service.TokenService;
import cn.cf.task.CrmShServiceCallable;
import cn.cf.task.CrmXfmServiceCallable;
import cn.cf.util.DateUtil;
import cn.cf.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class HuaxianhuiCrmServiceImpl implements HuaxianhuiCrmService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TokenService tokenService;

	@Autowired
	private CrmShService crmShService;

	@Autowired
	private CrmXfmService crmXfmService;

	@Autowired
	private HuaxianhuiService huaxianhuiService;

	@Autowired
	private B2bOrderService b2bOrderService;

	@Autowired
	private B2bContractService b2bContractService;

	@Autowired
	private TokenService b2bTokenService;

	@Autowired
	private B2bOrderGoodsService b2bOrderGoodsService;

	@Autowired
	private B2bContractGoodsService b2bContractGoodsService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bCompanyService b2bCompanyService;

	@Autowired
	private B2bStoreService b2bStoreService;

	/**
	 * 存同步标识
	 * 
	 * @param storePk
	 * @param token
	 */
	private void addStore(String storePk, B2bTokenDto token) {
		JedisUtils.set("erp." + storePk, token, 3600);
	}

	/**
	 * 删除同步标识
	 * 
	 * @param storePk
	 */
	private void delStore(String storePk) {
		if (JedisUtils.existsObject("erp." + storePk)) {
			JedisUtils.del("erp." + storePk);
		}
	}

	/**
	 * 同步所有商品信息
	 */
	@Override
	public String syncGoods(String storePk, String jsonData) {
		String result = RestCode.CODE_0000.toJson();
		// 正在更新中
		if (JedisUtils.existsObject("erp." + storePk)) {
			result = RestCode.CODE_I001.toJson();
		} else {
			B2bTokenDto token = tokenService.getB2bTokenByStorePk(storePk);
			if (null != token) {
				try {
					ErpGoodsSync eGoods = null;// 新增编辑数据封装
					addStore(storePk, token);
					B2bStoreDto store = b2bStoreService.storeIsOpen(storePk);
					Boolean openFlag = false;// 关店
					if (null != store) {
						openFlag = true;// 开店
					}
					Boolean showFlag = true;//商品全部显示
					switch (token.getAccType()) {
					// 盛虹
					case 1:
						jsonData = crmShService.syncGoods();// 取盛虹数据
						eGoods = crmShService.getGoodsList(storePk, StringUtils.replaceBlank(jsonData),openFlag);
						showFlag = false;//商品全部隐藏 
						break;
					// 新凤鸣
					case 2:
						eGoods = crmXfmService.getGoodsList(storePk, StringUtils.replaceBlank(jsonData),openFlag);
						break;
					default:
						break;
					}
					// 回调化纤汇业务
					if (null != eGoods && null != eGoods.getInsertGoodsList()&& null != eGoods.getUpdateGoodsList()) {
						logger.info("insertGoodsList---------------------:start"+ eGoods.getInsertGoodsList().size());
						logger.info("updateGoodsList---------------------:start"+ eGoods.getUpdateGoodsList().size());
						huaxianhuiService.syncGoods(storePk, eGoods, openFlag,showFlag);
						logger.info("updateGoodsList---------------------:end");
					}
				} catch (Exception e) {
					logger.error("------syncGoods-jsonData-----------------:",
							e);
					result = RestCode.CODE_S999.toJson();
				} finally {
					delStore(storePk);
				}
			} else {
				result = RestCode.CODE_S001.toJson();
			}
		}
		return result;
	}

	/**
	 * 更新商品库存
	 */
	@Override
	public String syncGoodsStock(String storePk, String jsonData) {
		String result = RestCode.CODE_0000.toJson();
		// 正在更新中
		if (JedisUtils.existsObject("erp." + storePk)) {
			result = RestCode.CODE_I001.toJson();
		} else {
			B2bTokenDto token = tokenService.getB2bTokenByStorePk(storePk);
			// token不存在
			if (null != token) {
				List<B2bGoodsDtoEx> goodslist = new ArrayList<B2bGoodsDtoEx>();
				try {
					addStore(storePk, token);
					Boolean openFlag = false;// 关店
					B2bStoreDto store = b2bStoreService.storeIsOpen(storePk);
					if (null != store) {
						openFlag = true;// 开店
					}
					switch (token.getAccType()) {
					// 盛虹
					case 1:
						jsonData = crmShService.syncGoodsStock();// 取盛虹数据
						goodslist = crmShService.getGoodsStockList(storePk,jsonData, openFlag);
						break;
					// 新凤鸣
					case 2:
						goodslist = crmXfmService.getGoodsStockList(storePk,jsonData, openFlag);
						break;
					default:
						break;
					}
					// 回调化纤汇业务
					if (null != goodslist && goodslist.size() > 0) {
						logger.info("updateGoodsWeightList---------------------:start"+ goodslist.size());
						huaxianhuiService.syncGoodsStock(storePk, goodslist,openFlag);
						logger.info("updateGoodsWeightList---------------------:end");
					}
				} catch (Exception e) {
					logger.error("errorSyncStock------------------:", e);
					result = RestCode.CODE_S999.toJson();
				} finally {
					delStore(storePk);
				}
			} else {
				result = RestCode.CODE_S001.toJson();
			}
		}
		return result;
	}

	/**
	 * 同步商品价格
	 */
	@Override
	public String syncGoodsPrice(String storePk, String jsonData) {
		String result = RestCode.CODE_0000.toJson();
		// 正在更新中
		if (JedisUtils.existsObject("erp." + storePk)) {
			result = RestCode.CODE_I001.toJson();
		} else {
			B2bTokenDto token = tokenService.getB2bTokenByStorePk(storePk);
			// token存在
			if (null != token) {
				List<B2bGoodsDtoEx> goodslist = new ArrayList<B2bGoodsDtoEx>();
				try {
					addStore(storePk, token);
					Boolean openFlag = false;// 关店
					B2bStoreDto store = b2bStoreService.storeIsOpen(storePk);
					if (null != store) {
						openFlag = true;// 开店
					}
					switch (token.getAccType()) {
					// 盛虹
					case 1:
						jsonData = crmShService.syncGoodsPrice();// 取盛虹商品价格数据
						goodslist = crmShService.getGoodsPriceList(storePk,jsonData, openFlag);// 处理盛虹数据
						break;
					// 新凤鸣
					case 2:
						goodslist = crmXfmService.getGoodsPriceList(storePk,jsonData, openFlag);// 处理新风鸣数据
						break;
					default:
						break;
					}
					// 回调化纤汇业务
					if (null != goodslist && goodslist.size() > 0) {
						logger.info("updateGoodsPriceList---------------------:start"+ goodslist.size());
						huaxianhuiService.syncGoodsPrice(storePk, goodslist,openFlag);
						logger.info("updateGoodsPriceList---------------------:end");
					}
				} catch (Exception e) {
					logger.error("errorSyncPrice------------------:", e);
					result = RestCode.CODE_S999.toJson();
				} finally {
					delStore(storePk);
				}
			} else {
				result = RestCode.CODE_S001.toJson();
			}
		}
		return result;
	}

	/**
	 * 同步所有产品隐藏状态
	 */
	@Override
	public String syncGoodsShow(String storePk, String jsonData) {
		String result = RestCode.CODE_0000.toJson();
		// 正在更新中
		if (JedisUtils.existsObject("erp." + storePk)) {
			result = RestCode.CODE_I001.toJson();
		} else {
			B2bTokenDto token = tokenService.getB2bTokenByStorePk(storePk);
			// token不存在
			if (null != token) {
				JSONArray array = new JSONArray();
				try {
					addStore(storePk, token);
					// 如果没有提供数据 则取crm端获取
					if (null == jsonData) {
						switch (token.getAccType()) {
						// 盛虹
						case 1:
							jsonData = crmShService.syncGoodsShow();// 取盛虹数据
							array = parseSHJSONString(jsonData);// 解析盛虹数据
							break;
						default:
							break;
						}
					}
					// 回调化纤汇业务
					if (null != array && array.size() > 0) {
						logger.error("parseAllGoodsShowSize------------------:start"+ array.size());
						huaxianhuiService.syncGoodsShow(storePk, array);
						logger.error("parseAllGoodsShowSize------------------:end");
					}
				} catch (Exception e) {
					logger.error("errorSyncShow------------------:", e);
					result = RestCode.CODE_S999.toJson();
				} finally {
					delStore(storePk);
				}
			} else {
				result = RestCode.CODE_S001.toJson();
			}
		}
		return result;
	}

	/**
	 * 同步所有竞价商品
	 */
	@Override
	public String syncAuctionGoods(String storePk, String jsonData) {
		String result = RestCode.CODE_0000.toJson();
		// 正在更新中
		if (JedisUtils.existsObject("erp." + storePk)) {
			result = RestCode.CODE_I001.toJson();
		} else {
			B2bTokenDto token = tokenService.getB2bTokenByStorePk(storePk);
			// token不存在
			if (null != token) {
				JSONArray array = new JSONArray();
				try {
					addStore(storePk, token);
					// 如果没有提供数据 则取crm端获取
					if (null == jsonData) {
						switch (token.getAccType()) {
						// 盛虹
						case 1:
							jsonData = crmShService.syncAuctionGoods();// 取盛虹数据
							array = parseSHJSONString(jsonData);// 解析盛虹数据
							break;
						default:
							break;
						}
					}
					// 回调化纤汇业务
					if (null != array && array.size() > 0) {
						logger.error("syncAuctionGoods------------------:start"+ array.size());
						huaxianhuiService.syncAuctionGoods(storePk, array);
						logger.error("syncAuctionGoods------------------:end");
					}
				} catch (Exception e) {
					result = RestCode.CODE_S999.toJson();
				} finally {
					delStore(storePk);
				}
			} else {
				result = RestCode.CODE_S001.toJson();
			}
		}
		return result;
	}

	/**
	 * 同步所有捆绑商品
	 */
	@Override
	public String syncBindGoods(String storePk, String jsonData) {
		String result = RestCode.CODE_0000.toJson();
		// 正在更新中
		if (JedisUtils.existsObject("erp." + storePk)) {
			result = RestCode.CODE_I001.toJson();
		} else {
			B2bTokenDto token = tokenService.getB2bTokenByStorePk(storePk);
			// token不存在
			if (null != token) {
				JSONArray array = new JSONArray();
				try {
					addStore(storePk, token);
					// 如果没有提供数据 则取crm端获取
					if (null == jsonData) {
						switch (token.getAccType()) {
						// 盛虹
						case 1:
							jsonData = crmShService.syncBindGoods();// 取盛虹数据
							array = parseSHJSONString(jsonData);// 解析盛虹数据
							break;
						default:
							break;
						}
					}
					if (null != array && array.size() > 0) {
						// 回调化纤汇业务
						logger.error("syncBindGoods------------------:start"+ array.size());
						huaxianhuiService.syncBindGoods(storePk, array);
						logger.error("syncBindGoods------------------:end");
					}
				} catch (Exception e) {
					result = RestCode.CODE_S999.toJson();
				} finally {
					delStore(storePk);
				}
			} else {
				result = RestCode.CODE_S001.toJson();
			}
		}
		return result;
	}

	@Override
	public String syncOrder(String orderNumber) {
		String result = null;
		B2bOrderDtoEx order = b2bOrderService.getByOrderNumber(orderNumber);
		if (null != order) {
			B2bTokenDto b2bTokenDto = b2bTokenService.getB2bTokenByStorePk(order.getStorePk());
			if (null != b2bTokenDto) {
				List<B2bOrderGoodsDtoEx> orderGoodsList = b2bOrderGoodsService.getByOrderNumber(orderNumber);
				OrderSync orderSync = new OrderSync(order, orderGoodsList,b2bTokenDto);
				String jsonData = JSONObject.toJSONString(orderSync);
				logger.error("+++++++++++++++++++++orderSync:" + jsonData+ ",orderNumber:" + orderNumber);
				try {
					switch (b2bTokenDto.getAccType()) {
					case 1:// 盛虹
						jsonData = crmShService.syncOrder(jsonData);
						break;
					case 2:// 新凤鸣
						jsonData = crmXfmService.syncOrder(jsonData);
						break;
					default:
						break;
					}
					logger.info("+++++++++++++++++++++data:" + jsonData+ ",orderNumber:" + orderNumber);
					JSONObject obj = JSON.parseObject(jsonData);
					// 成功
					if (Constant.RESPONSE_STATUS_SUCCESS.equals(obj.getString("status"))
							|| ("非当日定单不能创建!".equals(obj.getString("message")))
							|| ("该定单号已存在".equals(obj.getString("message")))) {
						result = RestCode.CODE_0000.toJson();
						// 如果返回同步失败保存到mongo
					} else {
						insertOrderContractToMongo(orderNumber,order.getStorePk(), obj.getString("message"), 1);
						result = "{\"msg\": \"" + obj.getString("message")+ " \",\"code\": \"C000\"}";
					}
				} catch (Exception e) {
					logger.error("errorSyncOrder", e);
					result = RestCode.CODE_S999.toJson();
					// 报错保存到mongo，定时器定时同步
					insertOrderContractToMongo(orderNumber, order.getStorePk(),e.toString(), 1);
				}
			} else {
				result = RestCode.CODE_S001.toJson();
			}
		} else {
			result = RestCode.CODE_O001.toJson();
		}
		return result;
	}

	@Override
	public String syncContract(String contractNo) {
		String result = null;
		B2bContractDtoEx b2bContract = b2bContractService
				.getContractDtoEx(contractNo);
		if (null != b2bContract) {
			B2bTokenDto b2bTokenDto = b2bTokenService
					.getB2bTokenByStorePk(b2bContract.getStorePk());
			if (null != b2bTokenDto) {
				List<B2bContractGoodsDto> orderGoodsList = b2bContractGoodsService.getB2bContractGoods(contractNo);
				ErpContractSync erpContractSync = new ErpContractSync(b2bContract, orderGoodsList);
				String jsonData = JSONObject.toJSONString(erpContractSync);
				logger.error("+++++++++++++++++++++contractSync:" + jsonData
						+ ",contractNo:" + contractNo);
				try {
					switch (b2bTokenDto.getAccType()) {
					case 1:// 盛虹
						jsonData = crmShService.syncContract(jsonData);
						break;
					case 2:// 新凤鸣
						jsonData = crmXfmService.syncContract(jsonData);
						break;
					default:
						break;
					}
					logger.info("+++++++++++++++++++++data:" + jsonData
							+ ",orderNumber:" + contractNo);
					JSONObject obj = JSON.parseObject(jsonData);
					// 成功
					if (Constant.RESPONSE_STATUS_SUCCESS.equals(obj
							.getString("status"))) {
						result = RestCode.CODE_0000.toJson();
						// 如果返回同步失败保存到mongo
					} else {
						insertOrderContractToMongo(contractNo,
								b2bContract.getStorePk(),
								obj.getString("message"), 2);
						result = "{\"msg\": \"" + obj.getString("message")
								+ " \",\"code\": \"C000\"}";
					}
				} catch (Exception e) {
					logger.error("errorSyncOrder", e);
					result = RestCode.CODE_S999.toJson();
					// 报错保存到mongo，定时器定时同步
					insertOrderContractToMongo(contractNo,
							b2bContract.getStorePk(), e.toString(), 2);
				}
			} else {
				result = RestCode.CODE_S001.toJson();
			}
		} else {
			result = RestCode.CODE_O001.toJson();
		}
		return result;
	}

	private void insertOrderContractToMongo(String number, String storePk,
			String details, int type) {
		// 1存订单
		if (type == 1) {
			OrderSyncToMongo syncToMongo = new OrderSyncToMongo();
			if (null != storePk) {
				syncToMongo.setStorePk(storePk);
			}
			syncToMongo.setId(number);
			syncToMongo.setIsPush(2);
			syncToMongo.setInsertTime(DateUtil.formatYearMonthDay(new Date()));
			syncToMongo.setOrderNumber(number);
			syncToMongo.setDetail(details);
			mongoTemplate.save(syncToMongo);
			// 存合同
		} else {
			ContractSyncToMongo syncToMongo = new ContractSyncToMongo();
			syncToMongo.setId(number);
			syncToMongo.setCompanyPk(storePk);
			syncToMongo.setIsPush(2);
			syncToMongo.setStorePk(storePk);
			syncToMongo.setInsertTime(DateUtil.formatYearMonthDay(new Date()));
			syncToMongo.setContractNumber(number);
			syncToMongo.setDetail(details);
			mongoTemplate.save(syncToMongo);
		}

	}

	/**
	 * 同步客户地址
	 */
	@Override
	public String syncAddress(String companyPk, String storePk, String jsonData) {
		String result = RestCode.CODE_0000.toJson();
		// 客户
		B2bCompanyDto companyDto = b2bCompanyService.getByPk(companyPk);
		if (null == companyDto || null == companyDto.getPk()) {
			return RestCode.CODE_C005.toJson();
		}
		List<B2bAddressDtoEx> addressList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("storePk", storePk);
		B2bTokenDto b2btoken = b2bTokenService.searchToken(map);
		if (null != b2btoken && null != b2btoken.getPk()) {
			try {
				LinkedHashMap<String, Object> params = new LinkedHashMap<>();
				params.put("companyName", companyDto.getName());
				params.put("organizationCode", companyDto.getOrganizationCode());
				if (null == jsonData) {
					switch (b2btoken.getAccType()) {
					// 盛虹
					case 1:
						jsonData = crmShService.syncAddress(params);// 取盛虹数据
						addressList = crmShService.getAddressList(jsonData);
						break;
					default:
						break;
					}
				}
				if (null != addressList && addressList.size() > 0) {
					// 回调化纤汇业务
					huaxianhuiService.syncAddress(companyPk, addressList);
				}
			} catch (Exception e) {
				result = RestCode.CODE_S999.toJson();
			}
		} else {
			result = RestCode.CODE_W001.toJson();
		}
		return result;
	}

	@Override
	public String syncAddressOne(String jsonData) {
		String rest = RestCode.CODE_0000.toJson();
		try {
			B2bAddressDtoEx address = JSON.parseObject(jsonData,
					B2bAddressDtoEx.class);
			if (null != address) {
				B2bCompanyDto company = b2bCompanyService.getByName(address
						.getCompanyName());
				if (null != company) {
					List<B2bAddressDtoEx> addressList = new ArrayList<B2bAddressDtoEx>();
					addressList.add(address);
					huaxianhuiService.syncAddress(company.getPk(), addressList);
				}
			}
		} catch (Exception e) {
			rest = RestCode.CODE_S999.toJson();
			logger.error("syncAddressOne============", e);
		}
		return rest;
	}

	/**
	 * 解析盛虹返回数据
	 * 
	 * @param jsonData
	 * @return JSONArray
	 */
	private JSONArray parseSHJSONString(String jsonData) {
		JSONArray array = new JSONArray();
		if (null != jsonData && !"".equals(jsonData)) {
			JSONObject jsonObject = JSONObject.parseObject(jsonData);
			if (null != jsonObject
					&& !"".equals(jsonObject.get("status"))
					&& Constant.RESPONSE_STATUS_SUCCESS.equals(jsonObject
							.get("status"))) {
				if (jsonObject.get("message") != null
						|| !"".equals(jsonObject.get("message").toString())) {
					array = JSONArray.parseArray(jsonObject.get("message")
							.toString());
				}
			}
		}
		return array;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String updateOrders(Integer orderStatus, String jsonData) {
		String rest = RestCode.CODE_0000.toJson();
		if (null == orderStatus || null == jsonData || "".equals(jsonData)) {
			rest = RestCode.CODE_A001.toJson();
		} else {
			Map<String, Class> map = new HashMap<String, Class>();
			try {
				switch (orderStatus) {
				case 1:// 分货
					map.put("items", B2bOrderGoodsUpdateWeight.class);
					B2bOrderUpdateWeight b2bOrderWeightDto = JsonUtils.toJSONMapBean(jsonData,B2bOrderUpdateWeight.class, map);
					huaxianhuiService.updateDivision(b2bOrderWeightDto.getOrderNumber(),b2bOrderWeightDto.getItems());
					break;
				case 3:// 确认收款
					map.put("items", B2bOrderGoodsUpdateOrderStatus.class);
					B2bOrderUpdateOrderStatus b2bOrderStatusDto = JsonUtils.toJSONMapBean(jsonData,B2bOrderUpdateOrderStatus.class, map);
					huaxianhuiService.updateOrderStatus(b2bOrderStatusDto.getOrderNumber(),b2bOrderStatusDto.getItems());
					break;
				case -1:// 关闭订单
					map.put("items", B2bOrderGoodsUpdateOrderStatus.class);
					B2bOrderUpdateOrderStatus b2bOrderStatusDtos = JsonUtils.toJSONMapBean(jsonData,B2bOrderUpdateOrderStatus.class, map);
					huaxianhuiService.updateOrderStatus(b2bOrderStatusDtos.getOrderNumber(),b2bOrderStatusDtos.getItems());
					break;
				case 4:// 发货
					map.put("items", B2bOrderGoodsUpdateOrderShiped.class);
					B2bOrderUpdateOrderShiped b2bOrderShipedDto = JsonUtils.toJSONMapBean(jsonData,B2bOrderUpdateOrderShiped.class, map);
					huaxianhuiService.updateOrderShiped(b2bOrderShipedDto.getOrderNumber(),b2bOrderShipedDto.getItems());
					break;
				default:
					break;
				}
			} catch (Exception e) {
				rest = RestCode.CODE_S999.toJson();
				logger.error("updateOrders", e);
			}
		}
		return rest;
	}

	/**
	 * 物流模板同步
	 */
	@Override
	public String syncLogistics(String storePk, String jsonData) {
		String result = RestCode.CODE_0000.toJson();
		if (JedisUtils.existsObject("erp." + storePk)) {
			result = RestCode.CODE_I001.toJson();
		} else {
			B2bTokenDto b2bTokenDto = b2bTokenService
					.getB2bTokenByStorePk(storePk);
			if (null != b2bTokenDto) {
				String block = Block.CF.getValue();
				try {
					addStore(storePk, b2bTokenDto);
					switch (b2bTokenDto.getAccType()) {
					case 1:// 盛虹
						jsonData = crmShService.syncLogistics();// 取盛虹运费模板
						break;
					case 2:// 新凤鸣
						break;
					default:
						break;
					}
					huaxianhuiService.updateAllLogistics(storePk, jsonData,block);// 处理盛虹返回的物流模板数据
				} catch (Exception e) {
					result = RestCode.CODE_S999.toJson();
				} finally {
					delStore(storePk);
				}
			} else {
				result = RestCode.CODE_S001.toJson();
			}
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void syncNoPushOrder(List<OrderSyncToMongo> olist) {
		Map<String, Future> futuremap = new HashMap<String, Future>();
		ExecutorService pool = Executors.newFixedThreadPool(olist.size());
		String jsonData = null;
		Future future = null;
		for (OrderSyncToMongo om : olist) {
			B2bOrderDtoEx order = b2bOrderService.getByOrderNumber(om
					.getOrderNumber());
			if (null != order) {
				B2bTokenDto b2bTokenDto = b2bTokenService
						.getB2bTokenByStorePk(order.getStorePk());
				if (null != b2bTokenDto) {
					List<B2bOrderGoodsDtoEx> orderGoodsList = b2bOrderGoodsService
							.getByOrderNumber(om.getOrderNumber());
					OrderSync orderSync = new OrderSync(order, orderGoodsList,
							b2bTokenDto);
					jsonData = JSONObject.toJSONString(orderSync);
					logger.error("+++++++++++++++++++++orderSync:" + jsonData
							+ ",orderNumber:" + om.getOrderNumber());
					try {
						switch (b2bTokenDto.getAccType()) {
						case 1:// 盛虹
							future = pool.submit(new CrmShServiceCallable(
									jsonData));
								futuremap.put(
										om.getOrderNumber() + "-"
												+ order.getStorePk()+"-"+om.getIsPush(), future);
							break;
						case 2:// 新凤鸣
							future = pool.submit(new CrmXfmServiceCallable(
									jsonData));
							// jsonData= (String) xfm.get(5, TimeUnit.SECONDS);
								futuremap.put(
										om.getOrderNumber() + "-"
												+ order.getStorePk()+"-"+om.getIsPush(), future);
							break;
						default:
							break;
						}
					} catch (Exception e) {
						logger.error("errorSyncOrder", e);
						// result = RestCode.CODE_S999.toJson();
						// 报错保存到mongo，定时器定时同步
						insertOrderContractToMongo(order.getOrderNumber(),
								order.getStorePk(), e.toString(), 1);
					}
				}
			}
		}
		for (String key : futuremap.keySet()) {
			future = futuremap.get(key);
			String[] keys = key.split("-");
			try {
//				if("1".equals(keys[3])){
//					jsonData = (String) future.get(1,TimeUnit.SECONDS);
//				}else{
				jsonData = (String) future.get();
//				}
				logger.info("+++++++++++++++++++++data:" + jsonData
						+ ",orderNumber:" + key);
				JSONObject obj = JSON.parseObject(jsonData);
				// 成功
				if (Constant.RESPONSE_STATUS_SUCCESS.equals(obj
						.getString("status"))
						|| ("非当日定单不能创建!".equals(obj.getString("message")))
						|| ("该定单号已存在".equals(obj.getString("message")))) {
					Query querys = Query.query(Criteria.where("orderNumber")
							.is(keys[0]));
					mongoTemplate.remove(querys, OrderSyncToMongo.class);
					// 如果返回同步失败保存到mongo
				}else if(Constant.RESPONSE_STATUS_FAIL.equals(obj
						.getString("status")) ) {
					insertOrderContractToMongo(keys[0], keys[1],
							obj.getString("message"), 1);

				}else{
					insertOrderContractToMongo(keys[0], keys[1],
							obj.getString("message"), 1);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("errorSyncOrder", e);
				// result = RestCode.CODE_S999.toJson();
				// 报错保存到mongo，定时器定时同步
				insertOrderContractToMongo(keys[0], keys[1], e.toString(), 1);
			}
		}
		// 关闭线程池
		pool.shutdown();

	}

	
	/**
	 * 盛虹合同提交审批
	 */
	@Override
	public String submitAudit(String contractNo) {
		String result = null;
		B2bContractDtoEx b2bContract = b2bContractService.getContractDtoEx(contractNo);
		if (null != b2bContract) {
			B2bTokenDto b2bTokenDto = b2bTokenService.getB2bTokenByStorePk(b2bContract.getStorePk());
			if (null != b2bTokenDto) {
				String jsonData = "";
				try {
					switch (b2bTokenDto.getAccType()) {
					case 1:// 盛虹
						jsonData = crmShService.submitAudit(contractNo);
						break;
					case 2:// 新凤鸣
						result = RestCode.CODE_W001.toJson();
						break;
					default:
						break;
					}
				} catch (Exception e) {
					logger.error("submitAudit", e);
					result = RestCode.CODE_W002.toJson();
				}
				logger.info("+++++++++++++submitAudit++++++++data:" + jsonData+ ",contractNo:" + contractNo);
				if (null!=jsonData && !"".equals(jsonData)) {
					JSONObject obj = JSON.parseObject(jsonData);
					// 成功
					if (Constant.RESPONSE_STATUS_SUCCESS.equals(obj.getString("status"))) {
						result = RestCode.CODE_0000.toJson();
					}else {
						//失败
						RestCode.CODE_B005.setMsg(obj.getString("message"));
						result = RestCode.CODE_B005.toJson();
					}
				}
			} else {
				result = RestCode.CODE_W001.toJson();
			}
		} else {
			result = RestCode.CODE_O001.toJson();
		}
		return result;
	}
	
	
	

}
