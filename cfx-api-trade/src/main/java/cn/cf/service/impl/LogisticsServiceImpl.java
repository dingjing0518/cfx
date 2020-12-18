package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.constant.Block;
import cn.cf.dao.B2bAddressDaoEx;
import cn.cf.dao.B2bGoodsDaoEx;
import cn.cf.dao.B2bPlantDaoEx;
import cn.cf.dao.LogisticsErpBubbleDaoEx;
import cn.cf.dao.LogisticsErpBubbleStepInfoDaoEx;
import cn.cf.dao.LogisticsErpDaoEx;
import cn.cf.dao.LogisticsErpMemberDaoEx;
import cn.cf.dao.LogisticsErpStepInfoDaoEx;
import cn.cf.dao.LogisticsModelDao;
import cn.cf.dto.B2bAddressDto;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bPlantDto;
import cn.cf.dto.LogisticsErpBubbleDto;
import cn.cf.dto.LogisticsErpBubbleDtoEx;
import cn.cf.dto.LogisticsErpBubbleStepInfoDto;
import cn.cf.dto.LogisticsErpBubbleStepInfoDtoEx;
import cn.cf.dto.LogisticsErpDto;
import cn.cf.dto.LogisticsErpDtoEx;
import cn.cf.dto.LogisticsErpMemberDto;
import cn.cf.dto.LogisticsErpMemberDtoEx;
import cn.cf.dto.LogisticsErpStepInfoDto;
import cn.cf.dto.LogisticsErpStepInfoDtoEx;
import cn.cf.dto.LogisticsModelDto;
import cn.cf.model.LogisticsErp;
import cn.cf.model.LogisticsErpBubble;
import cn.cf.model.LogisticsErpBubbleStepInfo;
import cn.cf.model.LogisticsErpStepInfo;
import cn.cf.service.B2bCustomerSaleManService;
import cn.cf.service.CommonService;
import cn.cf.service.LogisticsService;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;

@Service
public class LogisticsServiceImpl implements LogisticsService {

	@Autowired
	private LogisticsModelDao logisticsModelDao;

	@Autowired
	private B2bAddressDaoEx addressDao;

	@Autowired
	private B2bGoodsDaoEx b2bGoodsDaoEx;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bPlantDaoEx plantDaoEx;

 
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private LogisticsErpDaoEx logisticsErpDaoEx;

	@Autowired
	private LogisticsErpStepInfoDaoEx logisticsErpStepInfoDaoEx;

	@Autowired
	private LogisticsErpMemberDaoEx logisticsErpMemberDaoEx;

	@Autowired
	private LogisticsErpBubbleDaoEx erpBubbleDaoEx;

	@Autowired
	private LogisticsErpBubbleStepInfoDaoEx erpBubbleStepInfoDaoEx;

	@Autowired
	private B2bCustomerSaleManService customerSaleManService;

	@Autowired
	private LogisticsErpBubbleStepInfoDaoEx bubbleStepInfoDaoEx;

	@Override
	public LogisticsModelDto getLogisticModel(String pk) {
		return logisticsModelDao.getByPk(pk);
	}

	/*
	 * public RestCode getLogisticsSetpinfos(String logisticsModelPk, String
	 * addressPk, String cartPks, String goodsPk, Integer boxes, String goods) {
	 * RestCode code = RestCode.CODE_0000; List<LogisticsCart> list = new
	 * ArrayList<LogisticsCart>(); LogisticsModelDto dto =
	 * logisticsModelDao.getByPk(logisticsModelPk); // 购物车 if (null != cartPks
	 * && !"".equals(cartPks)) { String[] cartPk = cartPks.split(","); for (int
	 * i = 0; i < cartPk.length; i++) { B2bCartDto cdto =
	 * b2bCartDaoEx.getByPk(cartPk[i]); if (null != cdto) { LogisticsCart lc =
	 * new LogisticsCart(); lc.setCartPk(cartPk[i]);
	 * lc.setBoxes(cdto.getBoxes()); lc.setGoodsPk(cdto.getGoodsPk());
	 * lc.setLogisticsPk(""); lc.setLogisticsStepInfoPk(""); lc.setPrice(0d);
	 * list.add(lc); } } } // 单个商品 if (null != goodsPk && null != boxes &&
	 * !"".equals(goodsPk) && boxes > 0) { LogisticsCart lc = new
	 * LogisticsCart(); lc.setBoxes(boxes); lc.setGoodsPk(goodsPk);
	 * lc.setLogisticsPk(""); lc.setLogisticsStepInfoPk(""); lc.setPrice(0d);
	 * list.add(lc); } // 多个商品 if (null != goods && !"".equals(goods)) {
	 * JSONArray array = JSONArray.fromObject(goods); for (int i = 0; i <
	 * array.size(); i++) { LogisticsCart lc = new LogisticsCart();
	 * lc.setBoxes(array.getJSONObject(i).getInt("boxes"));
	 * lc.setGoodsPk(array.getJSONObject(i).getString("goodsPk"));
	 * lc.setLogisticsPk(""); lc.setLogisticsStepInfoPk(""); lc.setPrice(0d);
	 * list.add(lc); } } // TODO B2bAddressDto address =
	 * addressDao.getByPk(addressPk); // // 平台承运 if (dto.getType() == 1) { code
	 * = searchLgLogisticsPirce(address, list,0); } // 商家承运 if (dto.getType() ==
	 * 2) { code = searchB2bLogisticsPirce(address, list); }
	 * code.setRestValue(list); return code; }
	 */
	/**
	 * 
	 * @param address
	 *            地址
	 * @param list
	 * @param type
	 *            合同下单的标识，不取最低运费 1：合同下单
	 * @return
	 */
	private RestCode searchLgLogisticsPirce(B2bAddressDto address,
			List<LogisticsCart> list, Integer type) {
		RestCode code = RestCode.CODE_0000;
		LgSearchPriceForB2BDto lgPrice = new LgSearchPriceForB2BDto();
		// 收货地址
		lgPrice.setToProvicePk(address.getProvince());
		lgPrice.setToCityPk(address.getCity());
		lgPrice.setToAreaPk(address.getArea());
		lgPrice.setToTownPk(address.getTown());
		for (LogisticsCart lc : list) {
			B2bGoodsDtoMa good = new B2bGoodsDtoMa();
			B2bGoodsDto gdto = b2bGoodsDaoEx.getByPk(lc.getGoodsPk());
			good.UpdateMine(gdto);
			B2bPlantDto plant = plantDaoEx.getByPk(null!=good.getCfGoods()?good.getCfGoods().getPlantPk():
				good.getSxGoods().getPlantPk());
			Double weight = 0d;
			if (null != lc.getWeight() && lc.getWeight() > 0) {
				weight = lc.getWeight();
			} else {
				weight = ArithUtil.div(
						ArithUtil.mul(good.getTareWeight(), lc.getBoxes()),
						1000);
			}
			/*
			 * Double weight = ArithUtil.div(good.getTareWeight() *
			 * lc.getBoxes(),1000);
			 */
			lgPrice.setFromProvicePk(plant.getProvince());
			lgPrice.setFromCityPk(plant.getCity());
			lgPrice.setFromAreaPk(plant.getArea());
			lgPrice.setFromTownPk(plant.getTown());
			lgPrice.setProductPk(null!=good.getCfGoods()?good.getCfGoods().getProductPk():null);
			lgPrice.setGradePk(null!=good.getCfGoods()?good.getCfGoods().getGradePk():null);
			lgPrice.setWeight(weight);
			lgPrice.setType(type);
			ForB2BLgPriceDto priceDto = commonService
					.searchLgPriceForB2B(lgPrice);
			if (null != priceDto) {
				lc.setLogisticsPk(priceDto.getLinePk());
				lc.setLogisticsStepInfoPk(priceDto.getLogisticsStepInfoPk() == null ? ""
						: priceDto.getLogisticsStepInfoPk());
				lc.setPrice(priceDto.getPrice() == null ? 0.0 : priceDto
						.getPrice());
				lc.setLowPrice(priceDto.getLowPrice() == null ? 0.0 : priceDto
						.getLowPrice());
			} else {
				code = RestCode.CODE_S999;
			}
		}
		return code;
	}

	/*
	 * private RestCode searchB2bLogisticsPirce(B2bAddressDto address,
	 * List<LogisticsCart> list) { RestCode code = RestCode.CODE_0000; // 收货地址
	 * String town = address.getTown() == null ? "" : address.getTown(); String
	 * area = address.getArea() == null ? "" : address.getArea(); String city =
	 * address.getCity() == null ? "" : address.getCity(); String province =
	 * address.getProvince(); Map<String, String> map = null; LogisticsDto ldto
	 * = null; Double weight = 0d; for (LogisticsCart lc : list) { B2bGoodsDto
	 * goods = b2bGoodsDaoEx.getByPk(lc.getGoodsPk()); if (null != goods) {
	 * weight = ArithUtil.div( ArithUtil.mul(goods.getTareWeight(),
	 * lc.getBoxes()), 1000); map = new HashMap<String, String>();
	 * map.put("province", province); map.put("city", city); map.put("area",
	 * area); map.put("town", town); map.put("storePk", goods.getStorePk());
	 * map.put("productPk", goods.getProductPk()); map.put("gradePk",
	 * goods.getGradePk()); map.put("plantPk", goods.getPlantPk());
	 * mapw.putAll(map); ldto = queryArea(map, weight); } if (null != ldto &&
	 * weight > 0) { Criteria stepInfo = new Criteria(); stepInfo.andOperator(
	 * Criteria.where("logisticsPk").is(ldto.getPk()),
	 * Criteria.where("startWeight").lte(weight), Criteria
	 * .where("endWeight").gt(weight)); Query query = new Query(stepInfo);
	 * List<LogisticsStepInfoDto> stepInfos = mongoTemplate.find( query,
	 * LogisticsStepInfoDto.class); if (stepInfos.size() > 0) {
	 * LogisticsStepInfoDto info = stepInfos.get(0);
	 * lc.setLogisticsPk(ldto.getPk()); lc.setLogisticsStepInfoPk(info.getPk());
	 * lc.setPrice(info.getPrice()); } else { code = RestCode.CODE_A001; } } }
	 * return code; }
	 * 
	 * private LogisticsDto queryArea(Map<String, String> map, double weight) {
	 * LogisticsDto dto = this.queryBussines(map, weight); if (null == dto) { if
	 * (!"".equals(map.get("town"))) { map.putAll(mapw); map.put("town", "");
	 * return queryArea(map, weight); } if (!"".equals(map.get("area"))) {
	 * map.putAll(mapw); map.put("town", ""); map.put("area", ""); return
	 * queryArea(map, weight); } if (!"".equals(map.get("city"))) {
	 * map.putAll(mapw); map.put("town", ""); map.put("area", "");
	 * map.put("city", ""); return queryArea(map, weight); } } else { //
	 * 查询物流线路在承运商线路管理中是否存在，不存在不可选择商家承运 // List<B2bCarrierLineDtoEx> lineDto =
	 * this.searchCarrierLine(dto); // if (null == lineDto || lineDto.size() ==
	 * 0) { // dto = new LogisticsDto(); // } } return dto; }
	 * 
	 * // 查找线路管理
	 * 
	 * private LogisticsDto queryBussines(Map<String, String> map, double
	 * weight) { LogisticsDto dto = null; // 匹配所有条件 Map<String, String> qmap =
	 * new HashMap<String, String>(); qmap.putAll(map); List<LogisticsDto>
	 * logistics = queryMongo(map); if (null == logistics || logistics.size() ==
	 * 0) { map.put("plantPk", ""); logistics = queryMongo(map); if (null ==
	 * logistics || logistics.size() == 0) { map.put("plantPk",
	 * qmap.get("plantPk")); map.put("gradePk", ""); logistics =
	 * queryMongo(map); if (null == logistics || logistics.size() == 0) {
	 * map.put("plantPk", ""); map.put("gradePk", ""); logistics =
	 * queryMongo(map); if (null == logistics || logistics.size() == 0) {
	 * map.put("productPk", ""); map.put("gradePk", qmap.get("gradePk"));
	 * map.put("plantPk", qmap.get("plantPk")); logistics = queryMongo(map); if
	 * (null == logistics || logistics.size() == 0) { map.put("gradePk",
	 * qmap.get("gradePk")); map.put("plantPk", ""); logistics =
	 * queryMongo(map); if (null == logistics || logistics.size() == 0) {
	 * map.put("gradePk", ""); map.put("plantPk", qmap.get("plantPk"));
	 * logistics = queryMongo(map); } if (null == logistics || logistics.size()
	 * == 0) { map.put("gradePk", ""); map.put("plantPk", ""); logistics =
	 * queryMongo(map); } } } } } } if (null != logistics && logistics.size() >
	 * 0) { if (logistics.size() == 1) { dto = logistics.get(0); } else { for
	 * (LogisticsDto dto1 : logistics) { Criteria stepInfo = new Criteria();
	 * stepInfo.andOperator( Criteria.where("logisticsPk").is(dto1.getPk()),
	 * Criteria.where("startWeight").lte(weight), Criteria
	 * .where("endWeight").gt(weight)); Query query = new Query(stepInfo);
	 * List<LogisticsStepInfoDto> stepInfos = mongoTemplate.find( query,
	 * LogisticsStepInfoDto.class); if (null != stepInfos && stepInfos.size() >
	 * 0) { dto = dto1; break; } } } } return dto; }
	 */

	@Override
	public List<LogisticsModelDto> searchLogisticModelList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isVisable", 1);
		return logisticsModelDao.searchGrid(map);
	}

	@Override
	public RestCode updateLogisticsErp(LogisticsErpDtoEx dto) {
		RestCode code = RestCode.CODE_0000;
		dto.setMemberType(0);
		if (null != dto.getMembers() && !"".equals(dto.getMembers())) {
			JSONArray array = JSONArray.parseArray(dto.getMembers());
			if (array.size() > 0) {
				dto.setMemberType(1);
			}
		}
		int nameCount = logisticsErpDaoEx.searchExistenceByName(dto);
		int count = logisticsErpDaoEx.searchExistence(dto);// 是否存在模板

		// 物流模板已添加
		if (count > 0 || nameCount > 0) {
			code = RestCode.CODE_L002;
		} else {
			LogisticsErp l = new LogisticsErp();
			l.UpdateDTO(dto);
			if (null == dto.getPk() || "".equals(dto.getPk())) {
				String pk = KeyUtils.getUUID();
				l.setPk(pk);
				dto.setPk(pk);
				dto.setIsDelete(1);
				dto.setStatus(1);
				logisticsErpDaoEx.insert(l);
				updateLogisticErpStepInfo(dto);
				updateLogisticsErpMember(dto);
			} else {
				logisticsErpDaoEx.update(l);
				// 如果只是更新状态不做阶梯价格处理
				if (null == dto.getStatus()) {
					updateLogisticErpStepInfo(dto);
					updateLogisticsErpMember(dto);
				}
			}
			saveLogisticsErpMongo(dto);
		}
		return code;
	}

	private void saveLogisticsErpMongo(LogisticsErpDtoEx dto) {
		Query query = Query.query(Criteria.where("pk").is(dto.getPk()));
		LogisticsErpDtoEx mogo = mongoTemplate.findOne(query,
				LogisticsErpDtoEx.class);
		mongoTemplate.remove(query, LogisticsErpDtoEx.class);
		Query queryByLogisticsPK = Query.query(Criteria.where("logisticsPk")
				.is(dto.getPk()));
		mongoTemplate.remove(queryByLogisticsPK, LogisticsErpStepInfoDto.class);
		if (dto.getIsDelete() == null || dto.getIsDelete() == 1) {
			List<LogisticsErpStepInfoDto> stepInfo = dto.getStepInfos();
			if (stepInfo.size() > 0) {
				for (LogisticsErpStepInfoDto si : stepInfo) {
					mongoTemplate.insert(si);
				}
			}
			dto.setStepInfos(null);
			dto.setStepPrices(null);
			dto.setMembers(null);
			dto.setMemberType(null);
			if (dto.getStatus() == null) {
				dto.setStatus(null == mogo ? 2 : mogo.getStatus());
				dto.setIsDelete(null == mogo ? 1 : mogo.getIsDelete());
			}
			mongoTemplate.insert(dto);
		}

	}

	private void updateLogisticErpStepInfo(LogisticsErpDtoEx dto) {
		// 先删除对应的阶梯运费价格
		logisticsErpStepInfoDaoEx.deleteByLogisticsPk(dto.getPk());
		// 重新新增物流阶梯运费
		if (null != dto.getStepPrices() && !"".equals(dto.getStepPrices())) {
			JSONArray array = JSONArray.parseArray(dto.getStepPrices());
			for (int i = 0; i < array.size(); i++) {
				LogisticsErpStepInfoDtoEx lsDto = new LogisticsErpStepInfoDtoEx(array.getJSONObject(i));
				LogisticsErpStepInfo ls = new LogisticsErpStepInfo();
				ls.UpdateDTO(lsDto);
				ls.setPk(KeyUtils.getUUID());
				ls.setLogisticsPk(dto.getPk());
				ls.setBlock(lsDto.getBlock());
				logisticsErpStepInfoDaoEx.insert(ls);
				LogisticsErpStepInfoDto esidto = new LogisticsErpStepInfoDto();
				esidto.UpdateModel(ls);
				dto.getStepInfos().add(esidto);
			}
		}

	}

	private void updateLogisticsErpMember(LogisticsErpDtoEx dto) {
		// 先删除对应的业务员
		logisticsErpMemberDaoEx.deleteByLogisticsPk(dto.getPk());
		JSONArray array = new JSONArray();
		if (null != dto.getMembers() && !"".equals(dto.getMembers())) {
			array = JSONArray.parseArray(dto.getMembers());
			for (int i = 0; i < array.size(); i++) {
				LogisticsErpMemberDtoEx lsDto = new LogisticsErpMemberDtoEx(
						array.getJSONObject(i));
				LogisticsErpMemberDto ls = new LogisticsErpMemberDto();
				ls.UpdateMine(lsDto);
				ls.setPk(KeyUtils.getUUID());
				ls.setLogisticsPk(dto.getPk());
				logisticsErpMemberDaoEx.insert(ls);
				dto.getLogisticsErpMembers().add(ls);
			}
		}
		if (null == dto.getMembers() || "".equals(dto.getMembers())
				|| array.size() == 0) {
			LogisticsErpMemberDto ls = new LogisticsErpMemberDto();
			ls.setPk(KeyUtils.getUUID());
			ls.setEmployeeNumber("-1");
			dto.getLogisticsErpMembers().add(ls);
		}

	}

	@Override
	public PageModel<LogisticsErpDto> searchLogisticsErpList(
			Map<String, Object> map) {
		PageModel<LogisticsErpDto> pm = new PageModel<LogisticsErpDto>();
		map.put("isDelete", 1);
		List<LogisticsErpDto> list = logisticsErpDaoEx
				.searchLogisticsErpList(map);
		/*
		 * if (null != list && list.size() > 0) { for (LogisticsErpDto dto :
		 * list) { dto.setStepInfoList(logisticsErpStepInfoDaoEx.
		 * searchLogisticsErpStepInfoList(dto.getPk())); } }
		 */
		int count = logisticsErpDaoEx.searchLogisticsErpCount(map);
		pm.setTotalCount(count);
		pm.setDataList(list);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}

	@Override
	public RestCode delLogisticsErp(LogisticsErpDtoEx dto) {
		LogisticsErp l = new LogisticsErp();
		l.setPk(dto.getPk());
		l.setIsDelete(2);
		logisticsErpDaoEx.update(l);
		logisticsErpStepInfoDaoEx.deleteByLogisticsPk(dto.getPk());
		saveLogisticsErpMongo(dto);
		return RestCode.CODE_0000;
	}

	@Override
	public RestCode updateLogisticsErpBubble(LogisticsErpBubbleDtoEx dto) {
		RestCode code = RestCode.CODE_0000;
		int count = erpBubbleDaoEx.searchExistence(dto);// 是否存在
		// 已添加
		if (count > 0) {
			code = RestCode.CODE_L003;
		} else {
			LogisticsErpBubble l = new LogisticsErpBubble();
			l.UpdateDTO(dto);
			if (null == dto.getPk() || "".equals(dto.getPk())) {
				String pk = KeyUtils.getUUID();
				l.setPk(pk);
				dto.setPk(pk);
				l.setIsDelete(1);
				erpBubbleDaoEx.insert(l);
				updateLogisticErpBubbleStepInfo(dto);
			} else {
				erpBubbleDaoEx.update(l);
				// 如果只是更新状态不做阶梯价格处理
				if (null == dto.getIsVisable()) {
					updateLogisticErpBubbleStepInfo(dto);
				}
			}
		}
		return code;
	}

	private void updateLogisticErpBubbleStepInfo(LogisticsErpBubbleDtoEx dto) {
		// 先删除对应的阶梯运费价格
		erpBubbleStepInfoDaoEx.deleteByBubblePk(dto.getPk());
		// 重新新增物流阶梯运费
		if (null != dto.getBubbleStepInfos()
				&& !"".equals(dto.getBubbleStepInfos())) {
			JSONArray array = JSONArray.parseArray(dto.getBubbleStepInfos());
			for (int i = 0; i < array.size(); i++) {
				LogisticsErpBubbleStepInfoDtoEx lsdto = new LogisticsErpBubbleStepInfoDtoEx(
						array.getJSONObject(i));
				LogisticsErpBubbleStepInfo ls = new LogisticsErpBubbleStepInfo();
				ls.UpdateDTO(lsdto);
				ls.setPk(KeyUtils.getUUID());
				ls.setBubblePk(dto.getPk());
				erpBubbleStepInfoDaoEx.insert(ls);
			}
		}

	}

	@Override
	public RestCode delLogisticsErpBubble(LogisticsErpBubbleDtoEx dto) {
		LogisticsErpBubble l = new LogisticsErpBubble();
		l.setPk(dto.getPk());
		l.setIsDelete(2);
		erpBubbleDaoEx.update(l);
		erpBubbleStepInfoDaoEx.deleteByBubblePk(dto.getPk());
		return RestCode.CODE_0000;
	}

	@Override
	public PageModel<LogisticsErpBubbleDto> searchBubbleList(
			Map<String, Object> map) {
		PageModel<LogisticsErpBubbleDto> pm = new PageModel<LogisticsErpBubbleDto>();
		map.put("isDelete", 1);
		List<LogisticsErpBubbleDto> list = erpBubbleDaoEx.searchBubbleList(map);
		int count = erpBubbleDaoEx.searchBubbleCount(map);
		pm.setTotalCount(count);
		pm.setDataList(list);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}

	@Override
	public BackLogistics getLogisticsErpPrice(String logisticsModelPk,
			String addressPk, String goods, String purchaserPk, Integer type) {
		BackLogistics bl = new BackLogistics(RestCode.CODE_0000);
		List<LogisticsCart> list = new ArrayList<LogisticsCart>();
		LogisticsModelDto dto = logisticsModelDao.getByPk(logisticsModelPk);
		B2bAddressDto address = addressDao.getByPk(addressPk);
		if (null != dto && null != address) {
			// 多个商品
			if (null != goods && !"".equals(goods)) {
				JSONArray array = JSONArray.parseArray(goods);
				for (int i = 0; i < array.size(); i++) {
					LogisticsCart lc = new LogisticsCart();
					if (null != array.getJSONObject(i).getString("weight")
							&& !"".endsWith(array.getJSONObject(i).getString(
									"weight"))) {
						lc.setWeight(array.getJSONObject(i).getDouble("weight"));
					}
					lc.setBoxes(array.getJSONObject(i).getInteger("boxes"));
					lc.setGoodsPk(array.getJSONObject(i).getString("goodsPk"));
					lc.setLogisticsPk("");
					lc.setLogisticsStepInfoPk("");
					lc.setPrice(0d);
					lc.setLowPrice(0d);
					list.add(lc);
				}
			}
			// 平台承运
			if (dto.getType() == 1) {
				bl.setRestCode(searchLgLogisticsPirce(address, list, type)) ;
			}
			// 商家承运
			if (dto.getType() == 2) {
				bl.setRestCode(searchB2bLogisticsErpPirce(address, list, purchaserPk,
						type));
			}
			bl.setList(list);
		} else {
			bl.setRestCode(RestCode.CODE_A001);
		}
		return bl;
	}

	private RestCode searchB2bLogisticsErpPirce(B2bAddressDto address,
			List<LogisticsCart> list, String purchaserPk, Integer type) {
		RestCode code = RestCode.CODE_0000;
		// 收货地址
		String town = address.getTown() == null ? "" : address.getTown();
		String area = address.getArea() == null ? "" : address.getArea();
		String city = address.getCity() == null ? "" : address.getCity();
		String province = address.getProvince();
		Map<String, String> map = null;
		LogisticsErpDtoEx ldto = null;
		Double weight = 0d;
		
		for (LogisticsCart lc : list) {
			B2bGoodsDtoMa goods = new B2bGoodsDtoMa();
			B2bGoodsDto gdto = b2bGoodsDaoEx.getByPk(lc.getGoodsPk());
			goods.UpdateMine(gdto);
			B2bMemberDto dto = customerSaleManService.getSaleMan(
					goods.getCompanyPk(), purchaserPk, null!=goods.getCfGoods()?
							goods.getCfGoods().getProductPk():null,goods.getStorePk());
			if(null == dto){
				code = RestCode.CODE_M010;
				break;
			}
			if (null != goods) {
				if (null != lc.getWeight() && lc.getWeight() > 0) {
					weight = lc.getWeight();
					//纱线的换算成吨
					if(Block.SX.getValue().equals(goods.getBlock())){
						weight = ArithUtil
								.div(lc.getWeight(), 1000);
					}
				} else {
					weight = ArithUtil
							.div(ArithUtil.mul(goods.getTareWeight(),
									lc.getBoxes()), 1000);
				}
				map = new HashMap<String, String>();
				map.put("province", province);
				map.put("city", city);
				map.put("area", area);
				map.put("town", town);
				map.put("storePk", goods.getStorePk());
				map.put("plantPk", null!=goods.getCfGoods()?
						goods.getCfGoods().getPlantPk():
							goods.getSxGoods().getPlantPk());
				ldto = this.searchLogistic(map, dto);
			}
			if (null != ldto && weight > 0) {
				LogisticsErpStepInfoDto stepInfo = this.searchErpStepInfo(
						goods, ldto.getPk(), weight);
				if (null != stepInfo ) {
					Double coefficient = 0d;
					if(Block.CF.getValue().equals(goods.getBlock())){
						Map<String,Object> bmap = new HashMap<String,Object>();
						bmap.put("productPk", goods.getCfGoods().getProductPk());
						bmap.put("storePk", goods.getStorePk());
						bmap.put("tareWeight", goods.getTareWeight());
						List<LogisticsErpBubbleStepInfoDto> bubb = bubbleStepInfoDaoEx
								.find(bmap);
						
						if (bubb.size() > 0) {
							LogisticsErpBubbleStepInfoDto info = bubb.get(0);
							coefficient = info.getCoefficient();
 					/*	System.out.println("有系数-=-=-=-----------------------------------"+ coefficient);*/
						}
					}
					lc.setLogisticsPk(ldto.getPk());

					Double price = 0.0;
					if (coefficient > 0) {
						price = ArithUtil.mul(stepInfo.getPrice(), coefficient);
					} else {
						price = stepInfo.getPrice();
					}
					Double lowPrice = ldto.getLowPrice() == null ? 0.0 : ldto
							.getLowPrice();
					if (type == 1) {
						lc.setLogisticsStepInfoPk(stepInfo.getPk());
						lc.setPrice(price);
					} else if (ArithUtil.sub(ArithUtil.mul(price, weight),
							lowPrice) >= 0) {
//						System.out.println("最终区间价------------" + price);
						lc.setLogisticsStepInfoPk(stepInfo.getPk());
						lc.setPrice(price);
					} else {
						lc.setPrice(ArithUtil.round(
								ArithUtil.div(lowPrice, weight), 0));
					}
					lc.setLowPrice(lowPrice);
				} else {
					if (ldto.getLowPrice() > 0) {
						lc.setLogisticsPk(ldto.getPk());
						lc.setLogisticsStepInfoPk("");
						lc.setPrice(ArithUtil.round(
								ArithUtil.div(ldto.getLowPrice(), weight), 0));
						lc.setLowPrice(ldto.getLowPrice());
					}
				}
//				 System.err
//				 .println("----------------------------logicticsPk:---"
//				 + lc.getLogisticsPk() + "---StepInfoPk:--"
//				 + lc.getLogisticsStepInfoPk() + "--price:--"
//				 + lc.getPrice() + "--lowPirce:--"
//				 + lc.getPrice());
				//匹配到线路 运费单价为0 不给提交
				if (lc.getPrice() <= 0) {
					lc.setLogisticsPk("");
					lc.setLogisticsStepInfoPk("");
					lc.setPrice(0d);
					lc.setLowPrice(0d);
				}
			}

		}
		return code;
	}

	@Override
	public Map<String, Object> searchLogisticsErp(String logisticsPk,
			String stepInfoPk, B2bGoodsDtoMa goods, Double weight) {
		Map<String, Object> map = new HashMap<String, Object>();
		Double coefficient = 0.0;
		Double price = 0.0;
		map.put("price", price);
		// 查系数
		if(Block.CF.getValue().equals(goods.getBlock())){
			Map<String,Object> bmap = new HashMap<String,Object>();
			bmap.put("productPk", goods.getCfGoods().getProductPk());
			bmap.put("storePk", goods.getStorePk());
			bmap.put("tareWeight", goods.getTareWeight());
			List<LogisticsErpBubbleStepInfoDto> bubb = bubbleStepInfoDaoEx
					.find(bmap);
			if (bubb.size() > 0) {
				LogisticsErpBubbleStepInfoDto info = bubb.get(0);
				coefficient = info.getCoefficient();
			}
		}
		// 阶梯价
		LogisticsErpStepInfoDto stepInfo = logisticsErpStepInfoDaoEx
				.getByPk(stepInfoPk);
		if (null != stepInfo) {
			if (coefficient > 0) {
				price = ArithUtil.mul(stepInfo.getPrice(), coefficient);
			} else {
				price = stepInfo.getPrice();
			}
		}

		// 物流模板
		LogisticsErpDto ldto = logisticsErpDaoEx.getByPk(logisticsPk);

		Double lowPrice = ldto.getLowPrice() == null ? 0.0 : ldto.getLowPrice();
		if (ArithUtil.sub(ArithUtil.mul(price, weight), lowPrice) >= 0) {
			map.put("price", price);

		} else {
			map.put("price",
					ArithUtil.round(ArithUtil.div(lowPrice, Block.CF.getValue().equals(goods.getBlock())?weight:ArithUtil.div(weight, 1000)), 0));
		}
		map.put("lowPrice", lowPrice);
		map.put("lgCompanyPk", ldto.getLgCompanyPk());
		map.put("lgCompanyName", ldto.getLgCompanyName());
		return map;
	}

	private LogisticsErpStepInfoDto searchErpStepInfo(B2bGoodsDtoMa goods,
			String logisticsPk, Double weight) {
		LogisticsErpStepInfoDto step = null;
		LogisticsErpStepInfoList lsi = new LogisticsErpStepInfoList(goods);
//		 System.out.println("------------------------stepInfo------------------");
		for (Map<String, Object> m : lsi.getList()) {

			Query query = new Query();
			for (String key : m.keySet()) {
				query.addCriteria(Criteria.where(key.toString()).is(m.get(key)));
			}
			query.addCriteria(Criteria.where("logisticsPk").is(logisticsPk));
			query.addCriteria(Criteria.where("startWeight").lt(weight));
			query.addCriteria(Criteria.where("endWeight").gte(weight));
			query.addCriteria(Criteria.where("block").is(goods.getBlock()));
			query.addCriteria(Criteria.where("isDelete").is(1));
// 		System.out.println("stepinfo--=-=-=-=-=" + query);
			step = mongoTemplate.findOne(query, LogisticsErpStepInfoDto.class);
			if (null != step) {
				break;
			}
		}
		return step;
	}

	private LogisticsErpDtoEx searchLogistic(Map<String, String> map,
			B2bMemberDto dto) {
		LogisticsErpDtoEx ldto = null;
		LogisticsErpList lel = new LogisticsErpList(map, dto);
		for (Map<String, String> m : lel.getList()) {
			Query query = new Query();
			for (String key : m.keySet()) {

				if ("employeeNumber".equals(key)) {
					query.addCriteria(Criteria.where(
							"logisticsErpMembers.employeeNumber").is(
							m.get(key) == null ? "" : m.get(key)));
				} else {
					query.addCriteria(Criteria.where(key.toString()).is(
							m.get(key) == null ? "" : m.get(key)));
				}
			}
			if (!m.containsKey("employeeNumber") && !m.containsKey("memberPk")) {
				query.addCriteria(Criteria.where(
						"logisticsErpMembers.employeeNumber").is("-1"));
			}
			query.addCriteria(Criteria.where("isDelete").is(1));
			query.addCriteria(Criteria.where("status").is(1));
// 		System.out.println("-=-=-=-=-=-=-=-=-=-=" + query);
			ldto = mongoTemplate.findOne(query, LogisticsErpDtoEx.class);
			if (null != ldto) {
				break;
			}
		}

		return ldto;
	}

	@Override
	public LogisticsErpDto getLogisticsErp(String pk) {
		LogisticsErpDtoEx lerp = logisticsErpDaoEx.getLogisticsByPk(pk);
		if (null != lerp) {
			List<LogisticsErpStepInfoDto> step = logisticsErpStepInfoDaoEx
					.searchLogisticsErpStepInfoList(lerp.getPk());
			lerp.setStepInfos(step);
			List<LogisticsErpMemberDto> members = logisticsErpMemberDaoEx
					.searchLogisticsErpMemberList(lerp.getPk());
			lerp.setLogisticsErpMembers(members);
		}
		return lerp;
	}

	@Override
	public LogisticsErpBubbleDto getBubbleDetail(String pk) {
		LogisticsErpBubbleDto bubbleDto = erpBubbleDaoEx.getByPk(pk);
		if (null != bubbleDto) {
			List<LogisticsErpBubbleStepInfoDto> bstep = bubbleStepInfoDaoEx
					.searchbubbleStepInfoList(bubbleDto.getPk());
			bubbleDto.setBubbleStepInfo(bstep);
		}
		return bubbleDto;
	}

	@Override
	public Map<String, Object> searchLogisticsErpCounts(Map<String, Object> map) {
		// TODO Auto-generated method stub
		map.put("isDelete", 1);
		return logisticsErpDaoEx.searchLogisticsErpCounts(map);
	}

	@Override
	public Map<String, Object> searchBubbleCounts(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return erpBubbleDaoEx.searchBubbleCounts(map);
	}

	@Override
	public RestCode updateBubble(LogisticsErpBubbleDtoEx dto) {
		LogisticsErpBubble l = new LogisticsErpBubble();
		l.setIsVisable(dto.getIsVisable());
		l.setPk(dto.getPk());
		erpBubbleDaoEx.update(l);
		return RestCode.CODE_0000;
	}

	@Override
	public RestCode updateLogisticsErpStatus(LogisticsErpDtoEx dto) {
		LogisticsErp l = new LogisticsErp();
		l.setPk(dto.getPk());
		l.setStatus(dto.getStatus());
		logisticsErpDaoEx.update(l);
		Query query = new Query();
		query.addCriteria(Criteria.where("pk").is(dto.getPk()));
		Update update = Update.update("status", dto.getStatus());
		mongoTemplate.upsert(query, update, LogisticsErpDtoEx.class);
		return RestCode.CODE_0000;
	}

	@Override
	public LogisticsCart getLogisticsErpPriceByGoods(String logisticsModelPk,
			String addressPk, String goodsPk, Integer boxes, Double weight,
			String purchaserPk, Integer type) {

		List<LogisticsCart> list = new ArrayList<LogisticsCart>();
		LogisticsModelDto dto = logisticsModelDao.getByPk(logisticsModelPk);
		B2bAddressDto address = addressDao.getByPk(addressPk);
		if (null != dto && null != address) {
			// 多个商品
			if (null != goodsPk && !"".equals(goodsPk)) {
				LogisticsCart lc = new LogisticsCart();
				lc.setWeight(weight);
				lc.setBoxes(boxes);
				lc.setGoodsPk(goodsPk);
				lc.setLogisticsPk("");
				lc.setLogisticsStepInfoPk("");
				lc.setPrice(0d);
				lc.setLowPrice(0d);
				list.add(lc);
			}
			// 平台承运
			if (dto.getType() == 1) {
				searchLgLogisticsPirce(address, list,type);
			}
			// 商家承运
			if (dto.getType() == 2) {
				searchB2bLogisticsErpPirce(address, list, purchaserPk,type
						);
			}
		}
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	
	}

 
}
