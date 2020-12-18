package cn.cf.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ch.qos.logback.classic.Logger;
import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bContractDto;
import cn.cf.dto.B2bOrderDtoEx;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bTokenDto;
import cn.cf.entity.ContractSyncToMongo;
import cn.cf.entity.OrderSyncToMongo;
import cn.cf.service.B2bContractService;
import cn.cf.service.B2bContractSyncToMongoService;
import cn.cf.service.B2bOrderService;
import cn.cf.service.B2bOrderSyncToMongoService;
import cn.cf.service.HuaxianhuiCrmService;
import cn.cf.service.TokenService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

@RestController
@RequestMapping("/shop2erp")
public class Shop2ErpController extends BaseController {
	private final static Logger logger = (Logger) LoggerFactory.getLogger(Shop2ErpController.class);

	@Autowired
	private B2bOrderService b2bOrderService;

	@Autowired
	private TokenService b2bTokenService;
	
	@Autowired
	private B2bContractService b2bContractService;
	
	@Autowired
	private HuaxianhuiCrmService huaxianhuiCrmService;
	
	@Autowired
	private B2bContractSyncToMongoService b2bContractSyncToMongoService;
	
	@Autowired
	private  B2bOrderSyncToMongoService b2bOrderSyncToMongoService;

	/**
	 * 同步所有商品，上下价
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "syncGoods", method = RequestMethod.POST)
	public String syncGoods(HttpServletRequest request) throws Exception {

		String result = RestCode.CODE_0000.toJson();
		B2bCompanyDto b2bCompayDtoEx = this.getCompanyBysessionsId(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		if (null == b2bCompayDtoEx) {
			result = RestCode.CODE_M008.toJson();
		} else {
			if (null == store) {
				result = RestCode.CODE_A006.toJson();
			} else {
				result = huaxianhuiCrmService.syncGoods(store.getPk(), null);
			}
		}
		return result;
	}

	/**
	 * 同步所有产品库存接口
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "syncGoodsAvailableWeight", method = RequestMethod.POST)
	public String syncGoodsAvailableWeight(HttpServletRequest request) throws Exception {
		String result = RestCode.CODE_0000.toJson();
		B2bCompanyDto b2bCompayDtoEx = this.getCompanyBysessionsId(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		if (null == b2bCompayDtoEx) {
			result = RestCode.CODE_M008.toJson();
		} else {
			if (null == store) {
				result = RestCode.CODE_A006.toJson();
			} else {
				result = huaxianhuiCrmService.syncGoodsStock(store.getPk(), null);
			}
		}
		return result;
	}

	/**
	 * 同步所有产品价格接口
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "syncGoodsPrice", method = RequestMethod.POST)
	public String syncGoodsPrice(HttpServletRequest request) throws Exception {
		String result = RestCode.CODE_0000.toJson();
		B2bCompanyDto b2bCompayDtoEx = this.getCompanyBysessionsId(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		if (null == b2bCompayDtoEx) {
			result = RestCode.CODE_M008.toJson();
		} else {
			if (null == store) {
				result = RestCode.CODE_A006.toJson();
			} else {
				result = huaxianhuiCrmService.syncGoodsPrice(store.getPk(), null);
			}
		}
		return result;
	}

	/**
	 * 同步所有产品隐藏状态接口
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "syncGoodsShow", method = RequestMethod.POST)
	public String syncGoodsShow(HttpServletRequest request) throws Exception {
		String result = RestCode.CODE_0000.toJson();
		B2bCompanyDto b2bCompayDtoEx = this.getCompanyBysessionsId(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		if (null == b2bCompayDtoEx) {
			result = RestCode.CODE_M008.toJson();
		} else {
			if (null == store) {
				result = RestCode.CODE_A006.toJson();
			} else {
				result =  huaxianhuiCrmService.syncGoodsShow(store.getPk(),null);
			}
		}
		return result;
	}

	/**
	 * 同步所有竞价产品接口
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "syncAuctionGoods", method = RequestMethod.POST)
	public String syncAuctionGoods(HttpServletRequest request) throws Exception {
		String result = RestCode.CODE_0000.toJson();
		B2bCompanyDto b2bCompayDtoEx = this.getCompanyBysessionsId(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		if (null == b2bCompayDtoEx) {
			result = RestCode.CODE_M008.toJson();
		} else {
			if (null == store) {
				result = RestCode.CODE_A006.toJson();
			} else {
				result = huaxianhuiCrmService.syncAuctionGoods(store.getPk(), null);
			}
		}
		return result;
	}
	

	/**
	 * 捆绑产品
	 * 
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "syncBindGoods", method = RequestMethod.POST)
	public String syncBindGoods(HttpServletRequest request) throws Exception {
		String result = RestCode.CODE_0000.toJson();
		B2bCompanyDto b2bCompayDtoEx = this.getCompanyBysessionsId(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		if (null == b2bCompayDtoEx) {
			result = RestCode.CODE_M008.toJson();
		} else {
			if (null == store) {
				result = RestCode.CODE_A006.toJson();
			} else {
				result = huaxianhuiCrmService.syncBindGoods(store.getPk(), null);
			}
		}
		return result;
	}

	/**
	 * 物流模板抓取接口 运费规则同步(接口方ERP)
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "syncLogistics", method = RequestMethod.POST)
	public String syncLogistics(HttpServletRequest request) throws Exception {
		String result = RestCode.CODE_0000.toJson();
		B2bCompanyDto b2bCompayDtoEx = this.getCompanyBysessionsId(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		if (null == b2bCompayDtoEx) {
			result = RestCode.CODE_M008.toJson();
		} else {
			if (null == store) {
				result = RestCode.CODE_A006.toJson();
			} else {
				result = huaxianhuiCrmService.syncLogistics(store.getPk(),null);
			}
		}
		return result;
	}

	/**
	 * 合同同步crm
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "syncContract", method = RequestMethod.POST)
	public String syncContract(HttpServletRequest request) throws Exception {
		String result = RestCode.CODE_0000.toJson();
		String contractNo = request.getParameter("contractNo");
		if(contractNo != null && !"".equals(contractNo)){
			B2bContractDto contractDto = b2bContractService.getByContractNo(contractNo);
			if(contractDto != null){
				B2bStoreDto b2bStoreDto = b2bTokenService.getByStorePk(contractDto.getStorePk());
				B2bTokenDto b2bTokenDto = b2bTokenService.getB2bTokenByStorePk(contractDto.getStorePk());
				if(b2bTokenDto != null){
					try {
						result = huaxianhuiCrmService.syncContract(contractNo);
					} catch (Exception e) {
						b2bContractSyncToMongoService.syncContractToMongo(contractNo, b2bStoreDto, e.toString());
						result = RestCode.CODE_S999.toJson();
					}
				}else{
					result = RestCode.CODE_S001.toJson();
				}
			}
		}else{
			result = RestCode.CODE_T000.toJson();	
		}
		return result;
	}



	/**
	 * 订单同步接口(接口方ERP)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "syncOrder", method = RequestMethod.POST)
	public String orderSync(HttpServletRequest request) throws Exception {
		String result = RestCode.CODE_0000.toJson();
		String orderNumber = request.getParameter("orderNumber");
		if (orderNumber != null && !"".equals(orderNumber)) {
			B2bOrderDtoEx dto = b2bOrderService.getByOrderNumber(orderNumber);
			if (dto != null) {
				B2bStoreDto b2bStoreDto = b2bTokenService.getByStorePk(dto.getStorePk());
				B2bTokenDto b2bTokenDto = b2bTokenService.getB2bTokenByStorePk(dto.getStorePk());
				if (b2bTokenDto == null) {
					result = RestCode.CODE_S001.toJson();
				} else {
					try {
						result = huaxianhuiCrmService.syncOrder(orderNumber);
					} catch (Exception e) {
						logger.error("errorSyncOrder", e);
						result = RestCode.CODE_S999.toJson();
						// 报错保存到mongo，定时器定时同步
						b2bOrderSyncToMongoService.syncOrderNumberToMongo(orderNumber, b2bStoreDto, e.toString());
					}
				}
			}
		} else {
			result = RestCode.CODE_T000.toJson();
		}
		return result;
	}


	
	
	
	/**
	 * 同步采购商收货地址
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "syncAddress", method = RequestMethod.POST)
	public String syncAddress(HttpServletRequest request) throws Exception {
		String result = RestCode.CODE_0000.toJson();
		String companyPk = request.getParameter("companyPk");
		if (null==companyPk || "".equals(companyPk)) {
			return RestCode.CODE_A001.toJson();
		}
		B2bCompanyDto b2bCompayDtoEx = this.getCompanyBysessionsId(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		String storePk=null;
		if (null == b2bCompayDtoEx) {
			result = RestCode.CODE_M008.toJson();
		} else {
			if (null == store) {
				result = RestCode.CODE_A006.toJson();
			} else {
				storePk = store.getPk();
			}
		}
		result = huaxianhuiCrmService.syncAddress(companyPk, storePk, null);
		return result;

	}
	/**
	 * 查询订单合同
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/searchB2bContractSyncToMongo", method = RequestMethod.POST)
	public String searchVoucherList(HttpServletRequest req, HttpServletResponse resp){
		Map<String,Object> map = new HashMap<String,Object>();
		PageModel<ContractSyncToMongo> pageModel =null;
		PageModel<OrderSyncToMongo> pageModel2 =null;
		B2bStoreDto store = this.getStoreBysessionsId(req);
		map= paramsToMap(req);
		//company.bind(req);
		String searchType = ServletUtils.getStringParameter(req, "searchType","");
		String contractNumber = ServletUtils.getStringParameter(req, "contractNumber","");
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		String accountedTimeBegin = ServletUtils.getStringParameter(req, "accountedTimeBegin","");
		String accountedTimeEnd = ServletUtils.getStringParameter(req, "accountedTimeEnd","");
		map.put("searchType", ServletUtils.getIntParameterr(req, "searchType", 2));
		//map.put("searchType",searchType);
		map.put("contractNumber",contractNumber);
		map.put("orderNumber",orderNumber);
		map.put("accountedTimeBegin",accountedTimeBegin);
		map.put("accountedTimeEnd",accountedTimeEnd);
		map.put("isPush",ServletUtils.getStringParameter(req, "isPush","2"));
		map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
		if (!"".equals(searchType)&&searchType.equals("1")) {
			pageModel = b2bContractSyncToMongoService.searchContractSyncToMongoList(map, null==store?null:store.getPk());
			return RestCode.CODE_0000.toJson(pageModel);
		}else if (!"".equals(searchType)&&searchType.equals("2")){
			pageModel2 =b2bOrderSyncToMongoService.searchOrderSyncToMongoList(map,null==store?null:store.getPk());
			return RestCode.CODE_0000.toJson(pageModel2);
		}
		return  null;
	}
	
		
	
	/**
	 *(盛虹)合同提交审批
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "submitAudit", method = RequestMethod.POST)
	public String submitAudit(HttpServletRequest request) throws Exception {
		String result = RestCode.CODE_0000.toJson();
		String contractNo = request.getParameter("contractNo");
		logger.info("==========erp==submitAudit===============:" + contractNo);
		if (contractNo != null && !"".equals(contractNo)) {
			B2bContractDto dto = b2bContractService.getByContractNo(contractNo);
			if (dto != null) {
				B2bTokenDto b2bTokenDto = b2bTokenService.getB2bTokenByStorePk(dto.getStorePk());
				if (b2bTokenDto == null) {
					result = RestCode.CODE_W001.toJson();
				} else {
					try {
						result = huaxianhuiCrmService.submitAudit(contractNo);
					} catch (Exception e) {
						logger.error("errorSyncOrder", e);
						result = RestCode.CODE_S999.toJson();
					}
				}
			}
		} else {
			result = RestCode.CODE_T000.toJson();
		}
		return result;
	}
	
	
	
}
