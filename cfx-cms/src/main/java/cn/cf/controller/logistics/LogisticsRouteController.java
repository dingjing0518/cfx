package cn.cf.controller.logistics;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.ExportUtil;
import cn.cf.common.QueryModel;
import cn.cf.dto.LogisticsRouteDto;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.entity.LogisticsRouteExport;
import cn.cf.json.JsonUtils;
import cn.cf.model.LogisticsRouteGridModel;
import cn.cf.model.LogisticsRouteModel;
import cn.cf.model.ManageAccount;
import cn.cf.service.logistics.LogisticsRouteService;
import cn.cf.service.logistics.LogisticsService;
import cn.cf.service.operation.GoodsManageService;
import cn.cf.service.operation.OperationManageService;
import cn.cf.service.operation.SysRegionsService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.logistics.LogisticsRouteRunnable;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

/**
 * 物流承运商线路管理
 */
@Controller
@RequestMapping("/")
public class LogisticsRouteController extends BaseController {

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private LogisticsRouteService logisticsRouteService;

    @Autowired
    private GoodsManageService goodsManageService;

    @Autowired
    private SysRegionsService sysRegionsService;
    
    @Autowired
	private OperationManageService operationManageService;
    
    @Autowired
	private DynamicTask dynamicTask;

    /**
     * 页面跳转：线路管理
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("logisticsRouteManager")
    public ModelAndView purchaser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("logistics/logisticsRouteManager");
        mav.addObject("lgCompanyList", logisticsService.getAllLgCompanyList());// 物流公司名
        mav.addObject("productList", goodsManageService.getB2bProductList());// 品名
        mav.addObject("gradeList", goodsManageService.getB2bGradeList()); // 等级
        mav.addObject("province", sysRegionsService.getSysregisByCityList("-1"));
        return mav;
    }


    /**
     * 线路列表
     * @param request
     * @param response
     * @param dto
     * @return
     */
    @RequestMapping("logisticsRouteGrid")
    @ResponseBody
    public String logisticsRouteGrid(HttpServletRequest request, HttpServletResponse response,
                                     LogisticsRouteModel dto) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 5);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "updateTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
       ManageAccount account = getAccount(request);
        QueryModel<LogisticsRouteModel> qm = new QueryModel<LogisticsRouteModel>(dto, start, limit, orderName,
                orderType);
        PageModel<LogisticsRouteGridModel> pm = logisticsRouteService.getLogisticsRoute(qm,account.getPk());
        return JsonUtils.convertToString(pm);

    }

    /**
     * 删除线路
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("delLogisticsRoute")
    @ResponseBody
    public String delLogisticsRoute(HttpServletRequest request, HttpServletResponse response) {
        // 首先判断isDelete，1：删除，2：根据state改变条目的删除或启用
        String pk = ServletUtils.getStringParameter(request, "pk", null);
        int retVal = logisticsRouteService.delLogisticsRoute(pk);
        String msg = "";
        if (retVal > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    /**
     * 线路详情
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("LogisticsRouteDetail")
    @ResponseBody
    public String LogisticsRouteDetail(HttpServletRequest request, HttpServletResponse response) {
        String pk = ServletUtils.getStringParameter(request, "pk", null);
        return JsonUtils.convertToString(logisticsRouteService.selectOne(pk));
    }

    /**
     * 保存线路
     *
     * @param dto
     * @param showstartweights
     * @param showendweights
     * @param showlogisticsprices
     * @param showinlogisticsprices
     * @param pricePks
     * @return
     */
    @RequestMapping("saveLogisticsRoute")
    @ResponseBody
    public String saveLogisticsRoute(LogisticsRouteDto dto, String showstartweights, String showendweights,
                                     String showlogisticsprices, String showinlogisticsprices, String pricePks) {
        int retVal = logisticsRouteService.saveLogisticsRoute(dto, showstartweights, showendweights,
                showlogisticsprices, showinlogisticsprices, pricePks);
        String msg = "";
        if (retVal > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else {
            if (retVal == -2) {
                msg = "{\"success\":false,\"msg\":\"模板名已存在!\"}";
            } else {
                msg = Constants.RESULT_FAIL_MSG;
            }
        }
        return msg;
    }

    /**
     * 导出：物流线路导出
     *
     * @param request
     * @param response
     * @param logisticsRouteModel
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "exportLogisticsRoute")
    @ResponseBody
    public String exportLogisticsRoute(HttpServletRequest request, HttpServletResponse response,
                                             CustomerDataTypeParams params){
        if (params == null) {
        	params = new CustomerDataTypeParams();
        }
        
//        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
//        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
//        ManageAccount account =getAccount(request);
//        QueryModel<LogisticsRouteModel> qm = new QueryModel<LogisticsRouteModel>(logisticsRouteModel, 0, 0, orderName,
//                orderType);
//        PageModel<LogisticsRouteExport> pm = logisticsRouteService.searchLogisticsRouteList(qm,account);
//      	ExportUtil<LogisticsRouteExport> export = new ExportUtil<LogisticsRouteExport>();
//		export.exportUtil("LogisticsRouteForm", pm.getDataList(), response, request);
    	String uuid = KeyUtils.getUUID();
        ManageAccount account = getAccount(request);
		operationManageService.saveCustomerExcelToOss(params, account, "exportLogisticsRoute_"+uuid, "物流中心-线路管理",4);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new LogisticsRouteRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;
        
        
    }

    /**
     * 线路导入
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("importRoute")
    @ResponseBody
    public String importRoute(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(value = "filename") MultipartFile file) {
    	String msg = "导入成功";
        InputStream is = null;
		try {
			is = file.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        if (!(file.getSize() > 0)) {
			msg = "导入的文件为空!";
			return JsonUtils.convertToString(msg);
		}
        try {
                // 创建工作簿
        		WorkbookSettings settings=new WorkbookSettings(); 
        		settings.setEncoding("UTF-8");
                Workbook workbook = Workbook.getWorkbook(is,settings);
                // 获得第一个工作表sheet1
                Sheet sheet = workbook.getSheet(0);
                Integer retVal = logisticsRouteService.saveImportRoute(sheet);
                if (retVal < 0) {
                    msg = "导入失败";
                }
                workbook.close();// 关闭

        } catch (Exception e) {
            e.printStackTrace();
            msg = "导入失败";
        }
        return msg;
    }
    


}
