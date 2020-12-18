package cn.cf.controller.marketing;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bCompanyExtDto;
import cn.cf.dto.B2bContractExtDto;
import cn.cf.dto.B2bGoodsExtDto;
import cn.cf.dto.B2bManageRegionExtDto;
import cn.cf.dto.B2bOrderExtDto;
import cn.cf.entity.GoodsDataTypeParams;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.model.MarketingCompany;
import cn.cf.service.enconmics.B2bEconomicsGoodsService;
import cn.cf.service.enconmics.EconomicsOrdersService;
import cn.cf.service.marketing.McustomerManageService;
import cn.cf.service.operation.GoodsManageService;
import cn.cf.service.operation.InformationCenterService;
import cn.cf.service.operation.OperationManageService;
import cn.cf.service.operation.SysRegionsService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.chemifiber.GoodsRunnable;
import cn.cf.task.schedule.marketing.MContractRunnable;
import cn.cf.task.schedule.marketing.MkOrderRunnable;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;

/**
 * 营销订单
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class MOrderController extends BaseController {
	@Autowired
	private McustomerManageService mcustomerManageService;

	@Autowired
	private GoodsManageService goodsManageService;

	@Autowired
	private SysRegionsService sysRegionsService;

	@Autowired
	private InformationCenterService informationCenterService;

	@Autowired
	private DynamicTask dynamicTask;

	@Autowired
	private EconomicsOrdersService economicsOrdersService;

	@Autowired
	private B2bEconomicsGoodsService b2bEconomicsGoodsService;
	
	@Autowired
	private OperationManageService operationManageService;

	/**
	 * 采购商审核页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("purchaserM")
	public ModelAndView purchaser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("marketing/purchaserM");
		// 业务经理
		// mav.addObject("accountList",
		// mcustomerManageService.getPersonByType(1));

		mav.addObject("accountList", mcustomerManageService.getDistributePerson());
		mav.addObject("province", informationCenterService.searchSysRegionsList("-1"));
		return mav;
	}

	/**
	 * 供应商页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("supplierM")
	public ModelAndView supplierM(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("marketing/supplierM");
		// 线下业务员，区域经理
		mav.addObject("accountList", mcustomerManageService.getDistributePerson());
		mav.addObject("province", informationCenterService.searchSysRegionsList("-1"));
		return mav;
	}

	/**
	 * 采购商列表
	 * 
	 * @param request
	 * @param response
	 * @param b2bCompany
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchPurchaserMList")
	public String purchaserMData(HttpServletRequest request, HttpServletResponse response,
			B2bCompanyExtDto b2bCompany) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		// 查询所有已审核通过的采购商信息
		ManageAccount adto = this.getAccount(request);
		b2bCompany.setCompanyType(1);
		QueryModel<B2bCompanyExtDto> qm = new QueryModel<B2bCompanyExtDto>(b2bCompany, start, limit, orderName,
				orderType);
		PageModel<B2bCompanyExtDto> pm = mcustomerManageService.searchCompanyListByMarrieddealOrder(qm, adto);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 供应商列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("supplierM_data")
	public String supplierMData(HttpServletRequest request, HttpServletResponse response, B2bCompanyExtDto b2bCompany) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);

		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		// 查询所有已审核通过的采购商信息
		ManageAccount adto = this.getAccount(request);
		b2bCompany.setCompanyType(2);
		QueryModel<B2bCompanyExtDto> qm = new QueryModel<B2bCompanyExtDto>(b2bCompany, start, limit, orderName,
				orderType);
		PageModel<B2bCompanyExtDto> pm = mcustomerManageService.searchStoreListByAccount(qm, adto);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 公司分配线业务经理
	 * 
	 * @param request
	 * @param response
	 * @param marketingCompany
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateMarketingCompany")
	public String updateMarketingCompany(HttpServletRequest request, HttpServletResponse response,
			MarketingCompany marketingCompany) {
		int result = mcustomerManageService.insertOrUpdateMarketingCompany(marketingCompany);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 营销中心-订单管理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("orderM")
	public ModelAndView orderM(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("marketing/orderM");
		mav.addObject("paymentList", mcustomerManageService.getPaymentList());
		return mav;
	}

	/**
	 * 营销中心-订单列表查询
	 * 
	 * @param request
	 * @param response
	 * @param b2bOrder
	 * @return
	 */
	@ResponseBody
	@RequestMapping("orderM_data")
	public String orderM_data(HttpServletRequest request, HttpServletResponse response, B2bOrderExtDto b2bOrder) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");

		ManageAccount adto = this.getAccount(request);
		b2bOrder.setAccountPk(adto.getPk());
		QueryModel<B2bOrderExtDto> qm = new QueryModel<B2bOrderExtDto>(b2bOrder, start, limit, orderName, orderType);

		PageModel<B2bOrderExtDto> pm = mcustomerManageService.searchOrderMList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}
	

	/**
	 * 导出订单
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "exportOrderM")
	@ResponseBody
	public String exportOrderM(HttpServletRequest request, HttpServletResponse response, OrderDataTypeParams params)
			throws Exception {
		ManageAccount adto = this.getAccount(request);

		// QueryModel<B2bOrderExtDto> qm = new
		// QueryModel<B2bOrderExtDto>(b2bOrder, 0, 0, null, null);
		// b2bOrder.setAccountPk(adto.getPk());
		// b2bOrder.setRolePk(adto.getRolePk());
		// qm.getEntity().setRolePk(adto.getRolePk());
		// List<B2bOrderExtDto> list =
		// mcustomerManageService.exportOrderMList(qm,1);
		// ExportUtil<B2bOrderExtDto> export = new ExportUtil<B2bOrderExtDto>();
		// export.exportUtil("orderM", list, response, request);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		String uuid = KeyUtils.getUUID();
		params.setUuid(uuid);
		mcustomerManageService.saveOrderMExcelToOss(params, adto);
		dynamicTask.startCron(new MkOrderRunnable(name, uuid), name);
		return Constants.EXPORT_MSG;
	}

	/**
	 * 导出订单之前先查询，如果超过5000条，提示导出小于5000
	 * 
	 * @param request
	 * @param response
	 * @param b2bOrder
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getExportOrderM")
	public String getExportOrderM(HttpServletRequest request, HttpServletResponse response, B2bOrderExtDto b2bOrder) {
		QueryModel<B2bOrderExtDto> qm = new QueryModel<B2bOrderExtDto>(b2bOrder, 0, 0, null, null);
		ManageAccount adto = this.getAccount(request);
		b2bOrder.setAccountPk(adto.getPk());
		List<B2bOrderExtDto> list = mcustomerManageService.exportOrderMList(qm, 2);
		if (list.size() > 5000) {
			return "fail";
		} else {
			return "success";
		}
	}

	/**
	 * 
	 * 
	 * 商品上下架显示页面
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("goodsUpperM")
	public ModelAndView goodsUpper(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("marketing/goodsUpperM");
		mav.addObject("brandList", goodsManageService.getB2bGoodsBrandList(null));// 品牌
		mav.addObject("productList", goodsManageService.getB2bProductList());// 品名
		mav.addObject("varietiesList", goodsManageService.getB2bVarietiesPidList());// 品种
		mav.addObject("specList", goodsManageService.getb2bSpecPid());// 规格大类
		return mav;
	}

	/**
	 * 营销的商品统计
	 * 
	 * @param request
	 * @param response
	 * @param goods
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("goodsM_data")
	@ResponseBody
	public String goodsdata(HttpServletRequest request, HttpServletResponse response, B2bGoodsExtDto goods)
			throws Exception {
		ManageAccount account = this.getAccount(request);
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String sort = ServletUtils.getStringParameter(request, "orderName");
		String dir = ServletUtils.getStringParameter(request, "orderType");
		goods.setBlock("cf");
		QueryModel<B2bGoodsExtDto> qm = new QueryModel<B2bGoodsExtDto>(goods, start, limit, sort, dir);
		PageModel<B2bGoodsExtDto> pm = goodsManageService.searchGoodsUpAndDownList(qm, account, 1);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 导出 营销商品统计
	 * 
	 * @param request
	 * @param response
	 * @param goods
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "exportGoodsIsUpDown")
	@ResponseBody
	public String exportGoodsIsUpDown(HttpServletRequest request, HttpServletResponse response,
			GoodsDataTypeParams params) throws Exception {
		ManageAccount account = this.getAccount(request);

		// goods.setBlock("cf");
		// QueryModel<B2bGoodsExtDto> qm = new QueryModel<B2bGoodsExtDto>(goods,
		// 0, 0, "insertTime", "desc");
		// List<B2bGoodsExtDto> list =
		// goodsManageService.exportGoodsList(qm,account,1);
		// ExportUtil<B2bGoodsExtDto> export = new ExportUtil<B2bGoodsExtDto>();
		// export.exportUtil("goodsIsUpDown", list, response, request);

		params.setBlock("cf");
		String uuid = KeyUtils.getUUID();
		params.setUuid(uuid);
		goodsManageService.saveGoodsExcelToOss(params, account, 2);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new GoodsRunnable(name, uuid), name);
		return Constants.EXPORT_MSG;
	}

	/**
	 * 大区管理显示页面
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("manageRegion")
	public ModelAndView manageRegion(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("marketing/manageRegion");
		mav.addObject("province", sysRegionsService.getSysregisByCityList("-1"));
		return mav;
	}

	/**
	 * 大区管理查询
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchManageRegionList")
	@ResponseBody
	public String searchManageRegionList(HttpServletRequest request, HttpServletResponse response,
			B2bManageRegionExtDto dto) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String sort = ServletUtils.getStringParameter(request, "orderName");
		String dir = ServletUtils.getStringParameter(request, "orderType");
		QueryModel<B2bManageRegionExtDto> qm = new QueryModel<B2bManageRegionExtDto>(dto, start, limit, sort, dir);
		PageModel<B2bManageRegionExtDto> pm = mcustomerManageService.searchManageRegionList(qm);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 大区管理编辑展示查询
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getSearchManageRegionByPk")
	@ResponseBody
	public String getSearchManageRegionByPk(HttpServletRequest request, HttpServletResponse response, String pk)
			throws Exception {

		return JsonUtils.convertToString(mcustomerManageService.getSearchManageRegionByPk(pk));
	}

	/**
	 * 合同订单页面
	 */
	@RequestMapping("operContractManage")
	public ModelAndView creditContractManage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("operation/disContractManage");
		mav.addObject("paymentList", economicsOrdersService.getPaymentList());
		mav.addObject("economicsGoodsList", b2bEconomicsGoodsService.searchList());
		return mav;
	}
	
	/**
	 * 合同管理列表
	 * @param request
	 * @param response
	 * @param order
	 * @return
	 */
    @ResponseBody
    @RequestMapping("searchMContractList")
    public String searchMContractList(HttpServletRequest request, HttpServletResponse response, B2bContractExtDto order) {
        ManageAccount accout = this.getAccount(request);
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<B2bContractExtDto> qm = new QueryModel<B2bContractExtDto>(order, start, limit, orderName, orderType);
        PageModel<B2bContractExtDto> pm = economicsOrdersService.searchMContractList(qm,accout);
        return JsonUtils.convertToString(pm);
    }
    
    
    
    /**
     * 导出合同
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "exportMContact")
	@ResponseBody
	public String exportMContact(HttpServletRequest request, HttpServletResponse response,
			OrderDataTypeParams params) throws Exception {
		ManageAccount account = this.getAccount(request);
		String uuid = KeyUtils.getUUID();
		params.setUuid(uuid);
	    operationManageService.saveOrderExcelToOss(params, account, "exportMContact_"+uuid, "营销中心-合同管理",4);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new MContractRunnable(name, uuid), name);
		return Constants.EXPORT_MSG;
	}
}
