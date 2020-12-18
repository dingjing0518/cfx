package cn.cf.controller.economics;

import java.util.List;
import java.util.Objects;

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
import cn.cf.entity.EconomicsEquipment;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.EconomicsDimensionEquipmentService;
import cn.cf.util.ServletUtils;

@Controller
@RequestMapping("/")
public class DimensionEquipmentController extends BaseController {

    @Autowired
    EconomicsDimensionEquipmentService economicsDimensionEquipmentService;


    /**
     * 设备：列表页面
     *
     * @return
     */
    @RequestMapping("equipment")
    public ModelAndView equipmentIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("dimension/equipment");
        List<EconomicsEquipment> equipmentList = economicsDimensionEquipmentService.getEquipmentList();
        mav.addObject("equipmentList", equipmentList);
        return mav;
    }


    /**
     * 设备：维护页面
     *
     * @return
     */
    @RequestMapping("addEquipment")
    public ModelAndView addEquipment(HttpServletRequest request, HttpServletResponse response, EconomicsEquipment economicsEquipment) throws Exception {

        EconomicsEquipment equipment = new EconomicsEquipment();
        ModelAndView mav = new ModelAndView("dimension/addEquipment");
        if (economicsEquipment.getPk() != null && !"".equals(economicsEquipment.getPk())) {
            equipment = economicsDimensionEquipmentService.getEquipmentByPk(economicsEquipment.getPk());
        }
        mav.addObject("equipment", equipment);
        return mav;
    }


    @ResponseBody
    @RequestMapping("equipment_list")
    public String capacityList(HttpServletRequest request, HttpServletResponse response, EconomicsEquipment economicsEquipment) {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");

        QueryModel<EconomicsEquipment> qm = new QueryModel<EconomicsEquipment>(economicsEquipment, start, limit, orderName, orderType);
        PageModel<EconomicsEquipment> pm = economicsDimensionEquipmentService.getEquipmentData(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }


    @ResponseBody
    @RequestMapping("updateEquipmentStatus")
    public String updateEquipmentStatus(EconomicsEquipment economicsEquipment) {
        int result = economicsDimensionEquipmentService.updateEquipmentStatus(economicsEquipment);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping("updateEquipment")
    public String updateEquipment(EconomicsEquipment economicsEquipment) {
        int result = 0;
        if (economicsEquipment.getPk() != null && !Objects.equals(economicsEquipment.getPk(), ""))
            result = economicsDimensionEquipmentService.updateEquipment(economicsEquipment);
        else
            result = economicsDimensionEquipmentService.insertEquipment(economicsEquipment);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }


}
