package cn.cf.controller.economics;

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
import cn.cf.dto.B2bBillInventoryExtDto;
import cn.cf.dto.B2bBillOrderExtDto;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.service.enconmics.BillOrderService;
import cn.cf.service.operation.OperationManageService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.economics.BillOrderRunnable;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;
/**
 * 票据支付管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class BillPayController extends BaseController {
	
	@Autowired
	private BillOrderService billOrderService;
	

	@Autowired
	private OperationManageService operationManageService;
	
	@Autowired
	private DynamicTask dynamicTask;
	
	/**
     * 票据支付页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("billPayManage")
    public ModelAndView billPayManage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("economics/bill/billPay");
        mav.addObject("billGoodsList",null);
        return mav;
    }

    
    @ResponseBody
    @RequestMapping("billOrder_data")
    public String billOrder_data(HttpServletRequest request, HttpServletResponse response, B2bBillOrderExtDto order) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        ManageAccount account = getAccount(request);
        order.setAccountPk(account.getPk());
        QueryModel<B2bBillOrderExtDto> qm = new QueryModel<B2bBillOrderExtDto>(order, start, limit, orderName, orderType);
        PageModel<B2bBillOrderExtDto> pm = billOrderService.getBillOrderList(qm);
        return JsonUtils.convertToString(pm);
    }
    
    @ResponseBody
    @RequestMapping("billInventory_data")
    public String billInventory_data(HttpServletRequest request, HttpServletResponse response, B2bBillInventoryExtDto dto) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<B2bBillInventoryExtDto> qm = new QueryModel<B2bBillInventoryExtDto>(dto, start, limit, orderName, orderType);
        PageModel<B2bBillInventoryExtDto> pm = billOrderService.getBillInventory(qm);
        return JsonUtils.convertToString(pm);
    }

    
	@RequestMapping("exportBillOrder")
	@ResponseBody
	public String exportBillOrder(HttpServletRequest request, HttpServletResponse response,
			OrderDataTypeParams params) throws Exception {
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
	    operationManageService.saveOrderExcelToOss(params, account, "exportBillOrder_"+uuid, "金融中心-票据管理-票据支付记录",3);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new BillOrderRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;  
	}
}
