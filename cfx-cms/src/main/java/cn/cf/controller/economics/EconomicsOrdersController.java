package cn.cf.controller.economics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.entity.OrderDataTypeParams;
import cn.cf.entity.OrderRecord;
import cn.cf.property.PropertyConfig;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.economics.CreditOrderRunnable;
import cn.cf.util.KeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.http.HttpHelper;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dto.B2bContractExtDto;
import cn.cf.dto.B2bContractGoodsExtDto;
import cn.cf.dto.B2bLoanNumberExtDto;
import cn.cf.dto.B2bOrderExtDto;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bLoanNumber;
import cn.cf.model.ManageAccount;
import cn.cf.service.enconmics.B2bEconomicsGoodsService;
import cn.cf.service.enconmics.EconomicsBankService;
import cn.cf.service.enconmics.EconomicsOrdersService;
import cn.cf.util.ServletUtils;
import net.sf.json.JSONObject;

/***
 * @author 授信订单 订单管理 化纤贷订单
 */
@Controller
@RequestMapping("/")
public class EconomicsOrdersController extends BaseController {

    @Autowired
    private EconomicsOrdersService economicsOrdersService;

    @Autowired
    private B2bEconomicsGoodsService b2bEconomicsGoodsService;

    @Autowired
    private EconomicsBankService economicsBankService;

    @Autowired
    private DynamicTask dynamicTask;

    private static final Logger logger = LoggerFactory.getLogger(EconomicsOrdersController.class);

    /**
     * 授信订单页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("creditOrder")
    public ModelAndView creditOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("economics/creditOrder");
        mav.addObject("paymentList", economicsOrdersService.getPaymentList());
        mav.addObject("economicsGoodsList", b2bEconomicsGoodsService.searchList());
        mav.addObject("economicsBanksList", economicsBankService.searchAllList());

        return mav;
    }

    /**
     * 授信管理-订单管理-合同管理
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("creditContractManage")
    public ModelAndView creditContractManage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("economics/contractManage");
        String modelType = ServletUtils.getStringParameter(request, "modelType");
        if (modelType.contains("?")) {// 数据库默认
            modelType = modelType.substring(0, modelType.indexOf("?"));
        }
        mav.addObject("paymentList", economicsOrdersService.getPaymentList());
        mav.addObject("economicsGoodsList", b2bEconomicsGoodsService.searchList());
        if (Constants.BLOCK_CF_ONE.equals(modelType)){
            mav.addObject("block",Constants.BLOCK_CF);
        }
        if (Constants.BLOCK_SX_TWO.equals(modelType)){
            mav.addObject("block",Constants.BLOCK_SX);
        }
        return mav;
    }

    /**
     * 授信订单页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("creditContractEcoManage")
    public ModelAndView creditContractEcoManage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("economics/contractEcoManage");
        mav.addObject("paymentList", economicsOrdersService.getPaymentList());
        mav.addObject("economicsGoodsList", b2bEconomicsGoodsService.searchList());
        return mav;
    }

    /**
     * 授信订单列表
     * 
     * @param request
     * @param response
     * @param order
     * @return
     */
    @ResponseBody
    @RequestMapping("creditOrder_data")
    public String creditOrderData(HttpServletRequest request, HttpServletResponse response, B2bLoanNumberExtDto order) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        if (order == null) {
            order = new B2bLoanNumberExtDto();
        }
        ManageAccount account = getAccount(request);
        order.setAccountPk(account.getPk());
        QueryModel<B2bLoanNumberExtDto> qm = new QueryModel<B2bLoanNumberExtDto>(order, start, limit, orderName, orderType);
        PageModel<B2bLoanNumberExtDto> pm = economicsOrdersService.creditOrderList(qm, 1);
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    /**
     * 合同管理订单列表
     *
     * @param request
     * @param response
     * @param order
     * @return
     */
    @ResponseBody
    @RequestMapping("searchContractList")
    public String searchContractList(HttpServletRequest request, HttpServletResponse response, B2bContractExtDto order) {
        ManageAccount accout = this.getAccount(request);
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<B2bContractExtDto> qm = new QueryModel<B2bContractExtDto>(order, start, limit, orderName, orderType);
        PageModel<B2bContractExtDto> pm = economicsOrdersService.contractList(qm,accout);
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    /**
     * 订单合同追踪
     * @param request
     * @param response
     * @param contractNo
     * @return
     */
    @ResponseBody
    @RequestMapping("getOrderRecordTrack")
    public String getContractTrack(HttpServletRequest request, HttpServletResponse response, String contractNo) {
        List<OrderRecord> pm = economicsOrdersService.contractTrackList(contractNo);
        return JsonUtils.convertToString(pm);
    }

    /**
     * 合同商品查询
     * 
     * @param request
     * @param response
     * @param goodsExtDto
     * @return
     */
    @ResponseBody
    @RequestMapping("searchContractGoods")
    public String searchContractGoods(HttpServletRequest request, HttpServletResponse response, B2bContractGoodsExtDto goodsExtDto) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<B2bContractGoodsExtDto> qm = new QueryModel<B2bContractGoodsExtDto>(goodsExtDto, start, limit, orderName, orderType);
        PageModel<B2bContractGoodsExtDto> pm = economicsOrdersService.contractGoodsList(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    /**
     * 更新合同订单
     * 
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("updateContract")
    public String updateContract(B2bContractExtDto dto) {
        int retVal = economicsOrdersService.updateContract(dto);
        String msg = "";
        if (retVal > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;

    }

    /**
     * 合同管理-合同下载
     * 
     * @param request
     * @param response
     * @param contractNo
     * @return
     * @throws Exception
     */
    @RequestMapping("contractPDFDownLoad")
    public void contractPDFDownLoad(HttpServletRequest request, HttpServletResponse response, String contractNo,String block) throws Exception {
        economicsOrdersService.downContractOrder(contractNo, request, response,block);
    }

    /**
     * 化纤贷订单列表
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("fiberLoanOrder")
    public ModelAndView fiberLoanOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("economics/fiberLoanOrder");
        mav.addObject("paymentList", economicsOrdersService.getPaymentList());
        return mav;
    }

    /**
     * 导出授信订单
     * 
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "exportCreditOrders")
    @ResponseBody
    public String exportReportForms(HttpServletRequest request, HttpServletResponse response, OrderDataTypeParams params) throws Exception {
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        params.setOrderName(orderName);
        params.setOrderType(orderType);
        ManageAccount account = getAccount(request);
//        if (order == null) {
//            order = new B2bLoanNumberExtDto();
//        }
//        // 导出不做分页控制
//        ManageAccount account = getAccount(request);
//        order.setAccountPk(account.getPk());
//        QueryModel<B2bLoanNumberExtDto> qm = new QueryModel<B2bLoanNumberExtDto>(order, 0, 0, orderName, orderType);
//        PageModel<B2bLoanNumberExtDto> pm = economicsOrdersService.creditOrderList(qm, 2);
//        ExportUtil<B2bLoanNumberExtDto> export = new ExportUtil<B2bLoanNumberExtDto>();
//        if (order.getEconomicsGoodsName() != null && !order.getEconomicsGoodsName().equals("")) {// 化纤贷和化纤白条选择的模板不一样
//            B2bEconomicsGoodsDto goodsDto = b2bEconomicsGoodsService.getByName(order.getEconomicsGoodsName());
//            if (goodsDto != null && goodsDto.getGoodsType() != null && goodsDto.getGoodsType() == 2) {
//                export.exportUtil("creditDOrder", pm.getDataList(), response, request);
//            } else {
//                export.exportUtil("creditOrder", pm.getDataList(), response, request);
//            }
//        } else {
//            export.exportUtil("creditOrder", pm.getDataList(), response, request);
//        }

        String uuid = KeyUtils.getUUID();
        params.setUuid(uuid);
        economicsOrdersService.saveCreditOrderToOss(params,account);
        String name = Thread.currentThread().getName();//获取当前执行线程
        dynamicTask.startCron(new CreditOrderRunnable(name,orderName,orderType,uuid),name);
        return Constants.EXPORT_MSG;
    }

    /**
     * 订单还款还款记录
     * 
     * @param request
     * @param b2bRepaymentRecord
     * @return
     */
    @RequestMapping(value = "getB2bRepaymentRecordData")
    @ResponseBody
    public String getB2bRepaymentRecordData(HttpServletRequest request, B2bRepaymentRecord b2bRepaymentRecord) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "creditTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<B2bRepaymentRecord> qm = new QueryModel<B2bRepaymentRecord>(b2bRepaymentRecord, start, limit, orderName, orderType);
        PageModel<B2bRepaymentRecord> pm = economicsOrdersService.getB2bRepaymentRecordList(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    /**
     * 还款记录——扣款
     * 
     * @param request
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "deductRepayRecord")
    @ResponseBody
    public String deductRepayRecord(HttpServletRequest request, String id) throws Exception {
        String msg;
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", id);
            String url = PropertyConfig.getProperty("do_cf_api_bank_credit") + "/economics/updateAgentPay";
            String data = HttpHelper.sendPostRequest(url, map, null, null);
            msg = "{\"code\":\"0000\",\"msg\":\"操作成功!\"}";
            StringBuilder sb = new StringBuilder();
            sb.append("deductRepayRecord_HttpHelper.doPost_param:" + map + "\r\n");
            sb.append("deductRepayRecord_HttpHelper.doPost_url:" + url + "\r\n");
            sb.append("deductRepayRecord_HttpHelper.doPost_data:" + data + "\r\n");
            data = CommonUtil.deciphData(data);// 解密
            sb.append("deductRepayRecord_HttpHelper.doPost_deciphData:" + data + "\r\n");
            logger.error("returnValue:" + sb);
            if (data != null) {
                JSONObject obj = JSONObject.fromObject(data);
                msg = obj.toString();
            } else {
                msg = "{\"code\":\"0001\",\"msg\":\"操作失败!\"}";
            }
        } catch (Exception e) {
            msg = "{\"code\":\"0001\",\"msg\":\"操作失败!\"}";
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 化纤贷订单列表
     * 
     * @param request
     * @param response
     * @param order
     * @return
     */
    @ResponseBody
    @RequestMapping("fiberLoanOrder_data")
    public String fiberLoanOrder_data(HttpServletRequest request, HttpServletResponse response, B2bOrderExtDto order) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        if (order == null) {
            order = new B2bOrderExtDto();
        }
        QueryModel<B2bOrderExtDto> qm = new QueryModel<B2bOrderExtDto>(order, start, limit, orderName, orderType);
        PageModel<B2bOrderExtDto> pm = economicsOrdersService.fiberLoanOrderList(qm, 1);
        String json = JsonUtils.convertToString(pm);
        return json;
    }
    
//TODO
//    /**
//     * 导出化纤贷订单
//     * 
//     * @param request
//     * @param response
//     * @param order
//     * @return
//     * @throws Exception
//     */
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    @RequestMapping(value = "exportFiberLoanOrders")
//    @ResponseBody
//    public ModelAndView exportFiberLoanReportForms(HttpServletRequest request, HttpServletResponse response, B2bOrderExtDto order) throws Exception {
//        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
//        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
//        if (order == null) {
//            order = new B2bOrderExtDto();
//        }
//        // 导出不做分页控制
//        QueryModel<B2bOrderExtDto> qm = new QueryModel<B2bOrderExtDto>(order, 0, 0, orderName, orderType);
//        PageModel<B2bOrderExtDto> pm = economicsOrdersService.fiberLoanOrderList(qm, 2);
//        ExportUtil<B2bOrderExtDto> export = new ExportUtil<B2bOrderExtDto>();
//        export.exportUtil("creditOrder", pm.getDataList(), response, request);
//        return null;
//    }
//
//    /**
//     * 授信管理-订单管理 页面跳转
//     * 
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "financeOrderManage")
//    public ModelAndView financeOrderManage(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        ModelAndView mav = new ModelAndView("economics/financeOrderM");
//        mav.addObject("paymentList", economicsOrdersService.getPaymentList());
//        mav.addObject("economicsGoodsList", b2bEconomicsGoodsService.searchList());
//        return mav;
//    }
//
//    /**
//     * 授信管理-订单管理
//     * 
//     * @param request
//     * @param response
//     * @param b2bOrder
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("order_data")
//    public String orderList(HttpServletRequest request, HttpServletResponse response, B2bOrderExtDto b2bOrder) {
//        int start = ServletUtils.getIntParameter(request, "start", 0);
//        int limit = ServletUtils.getIntParameter(request, "limit", 10);
//        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
//        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
//       ManageAccount account = getAccount(request);
//        QueryModel<B2bOrderExtDto> qm = new QueryModel<B2bOrderExtDto>(b2bOrder, start, limit, orderName, orderType);
//        PageModel<B2bOrderExtDto> pm = economicsOrdersService.searchOrderList(qm,account);
//        String json = JsonUtils.convertToString(pm);
//        return json;
//    }

//    /**
//     * 授信管理-订单管理 导出订单
//     * 
//     * @param request
//     * @param response
//     * @param b2bOrder
//     * @return
//     * @throws Exception
//     */
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    @RequestMapping(value = "exportFinanceOrder")
//    @ResponseBody
//    public ModelAndView exportFinanceOrder(HttpServletRequest request, HttpServletResponse response, B2bOrderExtDto b2bOrder) throws Exception {
//        int start = ServletUtils.getIntParameter(request, "start", 0);
//        int limit = ServletUtils.getIntParameter(request, "limit", 10);
//        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
//        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
//        ManageAccount account = getAccount(request);
//        QueryModel<B2bOrderExtDto> qm = new QueryModel<B2bOrderExtDto>(b2bOrder, start, limit, orderName, orderType);
//        List<B2bOrderExtDto> list = economicsOrdersService.exportOrderListNew(qm,account);
//        ExportUtil<B2bOrderExtDto> export = new ExportUtil<B2bOrderExtDto>();
//        export.exportUtil("financeOrder", list, response, request);
//
//        return null;
//    }

//    /**
//     * 授信管理-订单管理-订单商品
//     * 
//     * @param request
//     * @param orderNumber
//     * @return
//     */
//    @RequestMapping("orderGoods_data")
//    @ResponseBody
//    public String orderGoods(HttpServletRequest request, String orderNumber) {
//        ManageAccount account = this.getAccount(request);
//        int start = ServletUtils.getIntParameter(request, "start", 0);
//        int limit = ServletUtils.getIntParameter(request, "limit", 10);
//        String orderName = ServletUtils.getStringParameter(request, "orderName", "pk");
//        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
//        B2bOrderGoodsExtDto dto = new B2bOrderGoodsExtDto();
//        dto.setOrderNumber(orderNumber);
//        QueryModel<B2bOrderGoodsExtDto> qm = new QueryModel<B2bOrderGoodsExtDto>(dto, start, limit, orderName, orderType);
//        PageModel<B2bOrderGoodsExtDto> pm = economicsOrdersService.getOrderGoods(qm,account);
//        return JsonUtils.convertToString(pm);
//    }

    /**
     * 编辑银行客户号
     * 
     * @param loanNumber
     * @return
     */
    @ResponseBody
    @RequestMapping("updateLoanNumber")
    public String updateLoanNumber(B2bLoanNumber loanNumber) {
        int retVal = economicsOrdersService.updateLoanNumber(loanNumber);
        String msg = "";
        if (retVal > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }
    
    
    
	@ResponseBody
	@RequestMapping(value = "getRepayLoan", method = RequestMethod.POST)
	public String getRepayLoan(HttpServletRequest request, HttpServletResponse response) {
		String orderNumber = ServletUtils.getStringParameter(request, "orderNumber", "");
		Double amount = ServletUtils.getDoubleParameter(request, "amount", 0);
        return JsonUtils.convertToString( economicsOrdersService.getRepayLoan(orderNumber,amount));
	}
	
	
	@ResponseBody
	@RequestMapping(value = "updataOverdue", method = RequestMethod.POST)
	public String updataOverdue(HttpServletRequest request, HttpServletResponse response, B2bLoanNumber model) {
		String msg ="";
		int result = economicsOrdersService.updateLoanNumber(model);
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}
}
