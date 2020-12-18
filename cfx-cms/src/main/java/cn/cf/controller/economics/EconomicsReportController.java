package cn.cf.controller.economics;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bEconomicsBankExtDao;
import cn.cf.dto.B2bEconomicsBankDto;
import cn.cf.entity.*;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.property.PropertyConfig;
import cn.cf.service.enconmics.*;
import cn.cf.service.operation.OperationManageService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.economics.*;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 金融报表
 * 
 * @author xht 2018年7月18日
 */
@Controller
@RequestMapping("/")
public class EconomicsReportController extends BaseController {

    @Autowired
    private EconReportCustomerService econReportCustomerService;

    @Autowired
    private EconReportBankApproveAmountService econReportBankApproveAmountService;

    @Autowired
    private EconReportProductUseService econReportProductUseService;

    @Autowired
    private EconomicsCustomerRFService economicsCustomerRFService;

    @Autowired
    private EconomicsBankService economicsBankService;
    
    @Autowired
    private EconProductBalanceService  productBalanceService;
    
	@Autowired
	private B2bEconomicsBankExtDao b2bEconomicsBankExtDao;
	
	@Autowired
	private OperationManageService operationManageService;
	
	@Autowired
	private DynamicTask dynamicTask;

    /**
     * 客户申请表
     * 
     * @param request
     * @param response
     * @param years
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("customApprove")
    public ModelAndView customApprove(HttpServletRequest request, HttpServletResponse response, String years) throws Exception {
        if (years == null || "".equals(years)) {
            years = new SimpleDateFormat("yyyy").format(new Date());
        }
        ModelAndView mav = new ModelAndView("economics/report/customApprove");
        Map<String, Object> map = economicsCustomerRFService.getCustomerCounts(years);
        mav.addObject("monthSum", (EconCustMonthSumRF) map.get("monthSumRF"));
        mav.addObject("custRFList", (List<EconomicsCustomerReportForms>) map.get("tempList"));
        mav.addObject("years", years);
        return mav;
    }

    /**
     * 客户申请表导出
     * 
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("exportCustomerRF")
    @ResponseBody
    public String downloadRF(HttpServletRequest request, HttpServletResponse response, ReportFormsDataTypeParams params) throws Exception {
        String years = "";
        if (params.getYears() == null || "".equals(params.getYears())) {
            years = new SimpleDateFormat("yyyy").format(new Date());
        }else{
            years = params.getYears();
        }

//        Map<String, Object> map = economicsCustomerRFService.getCustomerCounts(years);
//        exportEconomicsCustomer(
//                "econCustRF",
//                (List<EconomicsCustomerReportForms>) map.get("tempList"),
//                (EconCustMonthSumRF) map.get("monthSumRF"),
//                response,
//                request);

        ManageAccount account = this.getAccount(request);
        String uuid = KeyUtils.getUUID();
        params.setUuid(uuid);
        economicsCustomerRFService.saveCustomApproveToOss(params,account);
        String name = Thread.currentThread().getName();//获取当前执行线程
        dynamicTask.startCron(new CustomApproveRunnable(name,years,uuid),name);
        return Constants.EXPORT_MSG;
    }

    public void exportEconomicsCustomer(String name, List<EconomicsCustomerReportForms> list, EconCustMonthSumRF monthSumRF,
            HttpServletResponse response, HttpServletRequest request) {

        URL url = this.getClass().getClassLoader().getResource("template");
        String templateFileNameFilePath = url.getPath() + "/" + name + ".xls"; // 模板路径
        String tempPath = PropertyConfig.getProperty("FILE_PATH");
        File file = new File(tempPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        String fileName = name + DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss") + ".xls";
        String destFileNamePath = tempPath + "/" + fileName;// 生成文件路径
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("dto", list);
        beans.put("dates", new Date());
        beans.put("counts", list.size());
        beans.put("monthSumRF", monthSumRF);

        Configuration config = new Configuration();
        XLSTransformer transformer = new XLSTransformer(config);
        FileInputStream is = null;
        OutputStream out = null;
        try {
            transformer.transformXLS(templateFileNameFilePath, beans, destFileNamePath);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            is = new FileInputStream(destFileNamePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            while ((len = is.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
            is.close();
            out.close();
        } catch (ParsePropertyException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 银行审批客户数
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("bankApproveCustomer")
    public ModelAndView bankApproveCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("economics/report/bankApproveCustomer");
        String bankPk = ServletUtils.getStringParameter(request, "bankPk", null);
        String years = ServletUtils.getStringParameter(request, "years", null);
        if (bankPk.contains("?")) {// 数据库默认
            bankPk = bankPk.substring(0, bankPk.indexOf("?"));
        }
        // 银行Pk不能为空
        if (bankPk != null) {
            if (years == null || "".equals(years)) {
                years = new SimpleDateFormat("yyyy").format(new Date());
            }
            Map<String, Object> map = economicsCustomerRFService.getBankCreditCustomerCounts(years, bankPk);

            mav.addObject("bankList", economicsBankService.searchBankList());

            mav.addObject("monthSum", (EconCustBankMonthSumRF) map.get("monthSumRF"));
            mav.addObject("custRFList", (List<EconomicsCustomerBankReportForms>) map.get("tempList"));
            mav.addObject("years", years);
            mav.addObject("bankPk", bankPk);
        }
        return mav;
    }

    /**
     * 银行审批客户数导出
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("exportBankApproveCustomer")
    @ResponseBody
    public String downLoandBankApproveCustomer(HttpServletRequest request, HttpServletResponse response,ReportFormsDataTypeParams params) throws Exception {

        ManageAccount account = this.getAccount(request);

        String bankPk = ServletUtils.getStringParameter(request, "bankPk", null);
        if (bankPk.contains("?")) {// 数据库默认
            bankPk = bankPk.substring(0, bankPk.indexOf("?"));
        }
        String years = ServletUtils.getStringParameter(request, "years", null);
        // 银行Pk不能为空
//        if (bankPk != null) {
//            if (years == null || "".equals(years)) {
//                years = new SimpleDateFormat("yyyy").format(new Date());
//            }
//
//            Map<String, Object> map = economicsCustomerRFService.getBankCreditCustomerCounts(years, bankPk);
//            exportEconomicsBankCustomer(
//                    "econCustBankRF",
//                    (List<EconomicsCustomerBankReportForms>) map.get("tempList"),
//                    (EconCustBankMonthSumRF) map.get("monthSumRF"),
//                    response,
//                    request);
//        }
        if (bankPk != null) {
            String uuid = KeyUtils.getUUID();
            params.setUuid(uuid);
            economicsCustomerRFService.saveBankApproveCustomerToOss(params, account);
            String name = Thread.currentThread().getName();//获取当前执行线程
            dynamicTask.startCron(new BankApproveCustomerRunnable(name,years,bankPk,uuid), name);
        }
        return Constants.EXPORT_MSG;
    }

    public void exportEconomicsBankCustomer(String name, List<EconomicsCustomerBankReportForms> list, EconCustBankMonthSumRF monthSumRF,
            HttpServletResponse response, HttpServletRequest request) {

        URL url = this.getClass().getClassLoader().getResource("template");
        String templateFileNameFilePath = url.getPath() + "/" + name + ".xls"; // 模板路径

        String tempPath = PropertyConfig.getProperty("FILE_PATH");
        File file = new File(tempPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        String fileName = name + DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss") + ".xls";
        String destFileNamePath = tempPath + "/" + fileName;// 生成文件路径
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("dto", list);
        beans.put("dates", new Date());
        beans.put("counts", list.size());
        beans.put("monthSumRF", monthSumRF);

        Configuration config = new Configuration();
        XLSTransformer transformer = new XLSTransformer(config);
        FileInputStream is = null;
        OutputStream out = null;
        try {
            transformer.transformXLS(templateFileNameFilePath, beans, destFileNamePath);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            is = new FileInputStream(destFileNamePath);
            // 3.通过response获取ServletOutputStream对象(out)
            int len = 0;
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            while ((len = is.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
            is.close();
            out.close();
        } catch (ParsePropertyException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 审批表
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("approvalManage")
    public ModelAndView approvalManage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pk = ServletUtils.getStringParameter(request, "bankPk", null);
        if (pk.contains("?")) {// 数据库默认
            pk = pk.substring(0, pk.indexOf("?"));
        }
        String year = ServletUtils.getStringParameter(request, "year", CommonUtil.yesterday(1).toString());
        ModelAndView mav = new ModelAndView("economics/report/approvalManage");
        mav.addObject("approvalManageList", econReportCustomerService.approvalManage_data(pk, year));
        mav.addObject("bankPk", pk);
        mav.addObject("bankList", economicsBankService.searchBankList());
        mav.addObject("year", year);
        return mav;
    }

    /**
     * 银行审批额度
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("bankApproveAmount")
    public ModelAndView bankApproveAmount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pk = ServletUtils.getStringParameter(request, "bankPk", null);
        if (pk.contains("?")) {// 数据库默认
            pk = pk.substring(0, pk.indexOf("?"));
        }
        String year = ServletUtils.getStringParameter(request, "year", CommonUtil.yesterday(1).toString());
        ModelAndView mav = new ModelAndView("economics/report/bankApproveAmount");
        mav.addObject("bankApproveAmountList", econReportBankApproveAmountService.bankApproveAmount_data(pk, year));
        mav.addObject("bankPk", pk);
        mav.addObject("bankList", economicsBankService.searchBankList());
        mav.addObject("year", year);
        return mav;
    }

    /**
     * 金融产品使用情况
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("econProductUse")
    public ModelAndView econProductUse(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pk = ServletUtils.getStringParameter(request, "bankPk", null);
        if (pk.contains("?")) {// 数据库默认
            pk = pk.substring(0, pk.indexOf("?"));
        }
        String year = ServletUtils.getStringParameter(request, "year", CommonUtil.yesterday(1).toString());
        ModelAndView mav = new ModelAndView("economics/report/econProductUse");
        mav.addObject("econProductUseList", econReportProductUseService.econProductUse_data(pk, year));
        mav.addObject("bankPk", pk);
        mav.addObject("bankList", economicsBankService.searchBankList());
        mav.addObject("year", year);
        return mav;
    }

    /**
     * 供应商金融产品交易
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("econProductBussiness")
    public ModelAndView econProductBussiness(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String bankPk = ServletUtils.getStringParameter(request, "bankPk", null);
        String years = ServletUtils.getStringParameter(request, "years", null);
        if (bankPk.contains("?")) {// 数据库默认
            bankPk = bankPk.substring(0, bankPk.indexOf("?"));
        }
        // 银行Pk不能为空
        if (years == null || "".equals(years)) {
            years = new SimpleDateFormat("yyyy").format(new Date());
        }
        ModelAndView mav = new ModelAndView("economics/report/econProductBussiness");
        mav.addObject("bankPk", bankPk);
        mav.addObject("years", years);
        mav.addObject("bankList", economicsBankService.searchBankList());
        return mav;
    }

    /**
     * 查询供应商金融产品交易列表
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("searchEconProdBussList")
    @ResponseBody
    public String searchEconProdBussList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = 1000;
        String bankPk = ServletUtils.getStringParameter(request, "bankPk", null);
        String years = ServletUtils.getStringParameter(request, "years", null);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        ManageAccount account = getAccount(request);
        if (bankPk.contains("?")) {// 数据库默认
            bankPk = bankPk.substring(0, bankPk.indexOf("?"));
        }
        String msgList = "";
        // 银行Pk不能为空
        if (bankPk != null) {

            SupplierEconomicsOrderReportForms orderReportForms = new SupplierEconomicsOrderReportForms();
            if (years == null || "".equals(years)) {
                years = new SimpleDateFormat("yyyy").format(new Date());
            }
            orderReportForms.setYear(years);
            orderReportForms.setBankPk(bankPk);
            QueryModel<SupplierEconomicsOrderReportForms> qm = new QueryModel<SupplierEconomicsOrderReportForms>(orderReportForms, start, limit,
                    orderName, orderType);
            PageModel<SupplierEconomicsOrderReportForms> rfList = economicsCustomerRFService.searchEconProdBussCountsList(qm,account.getPk());
            msgList = JsonUtils.convertToString(rfList);
        }
        return msgList;
    }

    /**
     * 导出供应商金融产品交易列表
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("exportEconProdBussRF")
    @ResponseBody
    public String exportEconProdBussRF(HttpServletRequest request, HttpServletResponse response,ReportFormsDataTypeParams params) throws Exception {
    	String uuid = KeyUtils.getUUID();
        ManageAccount account = getAccount(request);
	    operationManageService.saveReportExcelToOss(params, account, "exportEconProdBussRF_"+uuid, "金融中心-数据管理-供应商金融产品交易",4);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new EconProdBussRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;    
    }

    /**
     * 导出审批情况
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("exportApprovalManageList")
    @ResponseBody
    public String exportApprovalManageist(HttpServletRequest request, HttpServletResponse response,ReportFormsDataTypeParams params) throws Exception {

        ManageAccount account = this.getAccount(request);
        String pk = ServletUtils.getStringParameter(request, "bankPk", null);
        String years = ServletUtils.getStringParameter(request, "years", null);
//
//        ExportUtil<EconCustomerApproveExport> export = new ExportUtil<EconCustomerApproveExport>();
//        List<EconCustomerApproveExport> lst = econReportCustomerService.approvalManage_data(pk, years);
//        export.exportUtil("approvalManage", lst, response, request);
        String uuid = KeyUtils.getUUID();
        params.setUuid(uuid);
        econReportCustomerService.saveApproveManageToOss(params,account);
        String name = Thread.currentThread().getName();//获取当前执行线程
        dynamicTask.startCron(new ApproveManageRunnable(name,years,pk,uuid),name);
        return Constants.EXPORT_MSG;
    }

    /**
     * 导出银行审批额度
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "exportBankApproveAmountList")
	@ResponseBody
    public String exportBankApproveAmountList(HttpServletRequest request, HttpServletResponse response,
    		ReportFormsDataTypeParams params) throws Exception {
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
	    operationManageService.saveReportExcelToOss(params, account, "exportBankApproveAmountList_"+uuid, "金融中心-数据管理-银行审批额度",2);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new BankApproveAmountRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;    
	}

    /**
     * 导出金融产品更新
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("exportEconProductUseList")
	@ResponseBody
    public String exportEconProductUseList(HttpServletRequest request, HttpServletResponse response
    		,ReportFormsDataTypeParams params) throws Exception {
    	String uuid = KeyUtils.getUUID();
    	ManageAccount account = getAccount(request);
	    operationManageService.saveReportExcelToOss(params, account, "exportEconProductUseList_"+uuid, "金融中心-数据管理-金融产品使用情况",3);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new EconProductUseRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;    
    }
    
    
    
   /**
    * 
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
    @RequestMapping("productBalance")
    public ModelAndView productBalance(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("economics/report/productBalance");
		List<B2bEconomicsBankDto> list = b2bEconomicsBankExtDao.searchListOrderName();

		String startTime = ServletUtils.getStringParameter(request, "startTime",  CommonUtil.yesterDay());
	    String endTime = ServletUtils.getStringParameter(request, "endTime", CommonUtil.today());
		String  content = productBalanceService.getEveryDayProductBalance(list,startTime,endTime);
		  mav.addObject("bankList", list);
		  mav.addObject("content", content);
		  mav.addObject("start",startTime);
		  mav.addObject("end", endTime);
        return mav;
    }
    
    
    
    /**
     * 金融产品使用余额统计表
     * @param
     */
    @RequestMapping("exportProductBalance")
    @ResponseBody
    public String exportProductBalance(HttpServletRequest request, HttpServletResponse response,ReportFormsDataTypeParams params){
    	String uuid = KeyUtils.getUUID();
    	ManageAccount account = getAccount(request);
	    operationManageService.saveReportExcelToOss(params, account, "exportProductBalance_"+uuid, "金融中心-数据管理-金融产品使用余额统计表",5);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new ProductBalanceRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;    
    }
}
