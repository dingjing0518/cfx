package cn.cf.controller.yarn;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.ExportUtil;
import cn.cf.common.QueryModel;
import cn.cf.dto.*;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.service.operation.OperationManageService;
import cn.cf.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author why 纱线订单管理
 */
@Controller
@RequestMapping("/")
public class YarnOrderManageController extends BaseController {

    @Autowired
    private OperationManageService operationManageService;



    @RequestMapping("yarnOrder")
    public ModelAndView yarnOrder(HttpServletRequest request, HttpServletResponse response){

        ModelAndView mv = new ModelAndView("yarn/ordermg/yarnOrder");
        mv.addObject("paymentList", operationManageService.getPaymentList());

        return mv;
    }

//    @RequestMapping("searchYarnOrderList")
//    @ResponseBody
//    public String searchYarnOrderList(HttpServletRequest request, HttpServletResponse response, SxOrderExtDto dto){
//
//        int start = ServletUtils.getIntParameter(request, "start", 0);
//        int limit = ServletUtils.getIntParameter(request, "limit", 5);
//        String orderName = ServletUtils.getStringParameter(request, "orderName", "");
//        String orderType = ServletUtils.getStringParameter(request, "orderType", "");
//        ManageAccount account = this.getAccount(request);
//        QueryModel<SxOrderExtDto> qm = new QueryModel<>(dto, start,
//                limit, orderName, orderType);
//        PageModel<SxOrderExtDto> pm = sxOrderService.searchSxOrderList(qm,account);
//        return JsonUtils.convertToString(pm);
//    }
//
//    @RequestMapping("closeYarnOrder")
//    @ResponseBody
//    public String closeYarnOrder(HttpServletRequest request, HttpServletResponse response, String orderNumber){
//        String msg = "";
//        int closeResult = sxOrderService.closeYarnOrder(orderNumber);
//        if (closeResult > 1){
//            msg = Constants.RESULT_SUCCESS_MSG;
//        }else{
//            msg = Constants.RESULT_FAIL_MSG;
//        }
//        return JsonUtils.convertToString(msg);
//    }
//
//
//    @RequestMapping("searchYarnOrderGoodsList")
//    @ResponseBody
//    public String searchYarnOrderGoodsList(HttpServletRequest request, HttpServletResponse response,String orderNumber){
//
//        int start = ServletUtils.getIntParameter(request, "start", 0);
//        int limit = ServletUtils.getIntParameter(request, "limit", 5);
//        String orderName = ServletUtils.getStringParameter(request, "orderName", "");
//        String orderType = ServletUtils.getStringParameter(request, "orderType", "");
//        SxOrderGoodsExtDto dto = new SxOrderGoodsExtDto();
//        dto.setOrderNumber(orderNumber);
//        QueryModel<SxOrderGoodsExtDto> qm = new QueryModel<>(dto, start,
//                limit, orderName, orderType);
//        PageModel<SxOrderGoodsExtDto> pm = sxOrderService.searchSxOrderGoodsList(qm);
//        return JsonUtils.convertToString(pm);
//    }
//
//    /**
//     * 导出订单数据之前先查询，如果超过5000条，提示导出小于5000
//     *
//     * @param request
//     * @param response
//     * @param orderExtDto
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("getExportYarnOrderGoodsNumbers")
//    public String getExportOrderGoodsNumbers(HttpServletRequest request, HttpServletResponse response,
//                                             SxOrderExtDto orderExtDto) {
//        QueryModel<SxOrderExtDto> qm = new QueryModel<>(orderExtDto, 0, 0, null, null);
//        int list = sxOrderService.getSxOrderGoodsCounts(qm);
//        if (list > 5000) {
//            return "fail";
//        } else {
//            return "success";
//        }
//    }
//
//    /**
//     * 导出订单商品数据
//     *
//     * @param request
//     * @param response
//     * @param orderExtDto
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("exportYarnOrderGoods")
//    public void exportYarnOrderGoodsNumbers(HttpServletRequest request, HttpServletResponse response,
//                                             SxOrderExtDto orderExtDto) {
//        QueryModel<SxOrderExtDto> qm = new QueryModel<>(orderExtDto, 0, 0, null, null);
//        ManageAccount account = this.getAccount(request);
//        List<SxOrderGoodsExtDto> list = sxOrderService.exportSxOrderGoodsCounts(qm,account);
//        ExportUtil<SxOrderGoodsExtDto> export = new ExportUtil<>();
//        export.exportUtil("yarnOrderGoods", list, response, request);
//    }
//
//
//    /**
//     * 订单导出
//     *
//     * @param request
//     * @param response
//     * @param orderExtDto
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("exportYarnOrder")
//    public void exportYarnOrder(HttpServletRequest request, HttpServletResponse response,
//                                            SxOrderExtDto orderExtDto) {
//        QueryModel<SxOrderExtDto> qm = new QueryModel<>(orderExtDto, 0, 0, null, null);
//        ManageAccount account = this.getAccount(request);
//        List<SxOrderGoodsExtDto> list = sxOrderService.exportSxOrderCounts(qm,account);
//        ExportUtil<SxOrderGoodsExtDto> export = new ExportUtil<>();
//        export.exportUtil("yarnOrder", list, response, request);
//    }
//
//    /**
//     * 订单合同导出PDF文档
//     *
//     * @param req
//     * @return
//     */
//    @RequestMapping(value = "/uploadYarnPDF", method = RequestMethod.GET)
//    public String uploadPDF(HttpServletRequest req, HttpServletResponse resp, String orderNumber) {
//        try {
//            sxOrderService.exportYarnOrderPDF(orderNumber, resp, req);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
