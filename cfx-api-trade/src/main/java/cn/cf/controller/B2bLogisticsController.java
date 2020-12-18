package cn.cf.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.LogisticsErpBubbleDtoEx;
import cn.cf.dto.LogisticsErpDtoEx;
import cn.cf.entity.BackLogistics;
import cn.cf.service.LogisticsService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/**
 * 物流模板管理
 * 
 * @description:
 * @author FJM
 * @date 2018-4-24 下午8:05:20
 */
@RestController
@RequestMapping("trade")
public class B2bLogisticsController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LogisticsService logisticsService;

	/**
	 * 物流模板详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchLogisticsModel", method = RequestMethod.POST)
	public String searchLogisticsModel(HttpServletRequest request) {
		return RestCode.CODE_0000.toJson(logisticsService
				.searchLogisticModelList());
	}

	/**
	 * 新增/编辑/删除线路管理
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateLogisticsErp", method = RequestMethod.POST)
	public String updateLogisticsErp(HttpServletRequest request) {
		RestCode code = RestCode.CODE_0000;
		LogisticsErpDtoEx dto = new LogisticsErpDtoEx();
		B2bCompanyDto company = this.getCompanyBysessionsId(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		if (company == null) {
			code = RestCode.CODE_M008;
		} else {
			dto.bind(request);
			dto.setStorePk(store.getPk());
			try {
				if (null != dto.getIsDelete() && dto.getIsDelete() == 2) {
					code = logisticsService.delLogisticsErp(dto);
				} else if (null != dto.getStatus()) {
					code = logisticsService.updateLogisticsErpStatus(dto);
				} else {
					code = logisticsService.updateLogisticsErp(dto);
				}
			} catch (Exception e) {
				logger.error("errorUpdateLogistics", e);
				code = RestCode.CODE_S999;
			}
		}

		return code.toJson();
	}

	/**
	 * 线路管理分页查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchLogisticsErpList", method = RequestMethod.POST)
	public String searchLogisticsErpList(HttpServletRequest request) {
		Map<String, Object> map = this.paramsToMap(request);
		map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
		B2bCompanyDto company = this.getCompanyBysessionsId(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		String rest = null;
		if (null == company || null == store.getPk()
				|| "".equals(store.getPk())) {
			rest = RestCode.CODE_0000.toJson();
		} else {
			map.put("storePk", store.getPk());
			rest = RestCode.CODE_0000.toJson(logisticsService
					.searchLogisticsErpList(map));
		}
		return rest;
	}

	/**
	 * 线路管理标签数量
	 * 
	 */
	@RequestMapping(value = "searchLogisticsErpCounts", method = RequestMethod.POST)
	public String searchLogisticsErpCounts(HttpServletRequest req) {
		String result = "";
		try {
			Map<String, Object> map = this.paramsToMap(req);
			B2bStoreDto store = this.getStoreBysessionsId(req);
			map.put("storePk", store.getPk());
			result = RestCode.CODE_0000.toJson(logisticsService
					.searchLogisticsErpCounts(map));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("searchLogisticsErpCounts exception:", e);
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}

	/**
	 * 新增/编辑/删除 泡货
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateLogisticsErpBubble", method = RequestMethod.POST)
	public String updateLogisticsErpBubble(HttpServletRequest request) {
		RestCode code = RestCode.CODE_0000;
		LogisticsErpBubbleDtoEx dto = new LogisticsErpBubbleDtoEx();
		B2bCompanyDto company = this.getCompanyBysessionsId(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		if (company == null) {
			code = RestCode.CODE_M008;
		} else {
			dto.bind(request);
			dto.setStorePk(store.getPk());
			try {
				if (null != dto.getIsDelete() && dto.getIsDelete() == 2) {
					code = logisticsService.delLogisticsErpBubble(dto);
				} else if (null != dto.getIsVisable()) {
					code = logisticsService.updateBubble(dto);
				} else {
					code = logisticsService.updateLogisticsErpBubble(dto);
				}
			} catch (Exception e) {
				logger.error("errorupdateLogisticsErpBubble", e);
				code = RestCode.CODE_S999;
			}
		}

		return code.toJson();
	}

	/**
	 * 泡货分页查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchBubbleList", method = RequestMethod.POST)
	public String searchBubbleList(HttpServletRequest request) {
		Map<String, Object> map = this.paramsToMap(request);
		map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
		B2bCompanyDto company = this.getCompanyBysessionsId(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		String rest = null;
		if (null == company || null == store.getPk()
				|| "".equals(store.getPk())) {
			rest = RestCode.CODE_0000.toJson();
		} else {
			map.put("storePk", store.getPk());
			rest = RestCode.CODE_0000.toJson(logisticsService
					.searchBubbleList(map));
		}
		return rest;
	}

	/**
	 * 泡货管理标签数量
	 * 
	 */
	@RequestMapping(value = "searchBubbleCounts", method = RequestMethod.POST)
	public String searchBubbleCounts(HttpServletRequest req) {
		String result = "";
		try {
			Map<String, Object> map = this.paramsToMap(req);
			B2bStoreDto store = this.getStoreBysessionsId(req);
			map.put("storePk", store.getPk());
			result = RestCode.CODE_0000.toJson(logisticsService
					.searchBubbleCounts(map));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("searchBubbleCounts exception:", e);
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}

	/**
	 * 根据商品重量调取运费模板
	 * 
	 * @param request
	 * @return 返回运费模板
	 */
	@RequestMapping(value = "getLogisticsErpPrice", method = RequestMethod.POST)
	public String getLogisticsErpPrice(HttpServletRequest request) {
		RestCode code = RestCode.CODE_0000;
		String logisticsModelPk = ServletUtils.getStringParameter(request,
				"logisticsModelPk", "");
		String addressPk = ServletUtils.getStringParameter(request,
				"addressPk", "");
		Integer type = ServletUtils.getIntParameterr(request, "type", 0);// 合同下单的标识为1，不取最低运费
		String purchaserPk = ServletUtils.getStringParameter(request,
				"purchaserPk", "");
		String goods = ServletUtils.getStringParameter(request, "goods", "");
		if ("".equals(logisticsModelPk) || "".equals(addressPk)
				|| "".equals(purchaserPk) || "".equals(goods)) {
			code = RestCode.CODE_A001;
			return code.toJson();
		} else {
			BackLogistics bl = logisticsService.getLogisticsErpPrice(
					logisticsModelPk, addressPk, goods, purchaserPk, type);
			code = bl.getRestCode();
			if (null != bl.getList() && bl.getList().size() > 0) {
				return code.toJson(bl.getList());
			} else {
				return code.toJson();
			}

		}
	}

	/**
	 * 线路详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getLogisticsErpDetail", method = RequestMethod.POST)
	public String getLogisticsErpDetail(HttpServletRequest request) {
		String pk = ServletUtils.getStringParameter(request, "pk", "");
		return RestCode.CODE_0000.toJson(logisticsService.getLogisticsErp(pk));
	}

	/**
	 * 泡货详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getBubbleDetail", method = RequestMethod.POST)
	public String getBubbleDetail(HttpServletRequest request) {
		String pk = ServletUtils.getStringParameter(request, "pk", "");
		return RestCode.CODE_0000.toJson(logisticsService.getBubbleDetail(pk));
	}

}
