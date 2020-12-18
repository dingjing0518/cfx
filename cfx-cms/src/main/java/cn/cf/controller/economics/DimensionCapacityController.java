package cn.cf.controller.economics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.entity.EconomicsCapacity;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bEconomicsDimensionModel;
import cn.cf.service.enconmics.EconomicsDimensionCapacityService;
import cn.cf.util.ServletUtils;

@Controller
@RequestMapping("/")
public class DimensionCapacityController extends BaseController {

    @Autowired
    private EconomicsDimensionCapacityService economicsDimensionService;

    /**
     * 产能：列表页面
     *
     * @return
     */
    @RequestMapping("capacity")
    public ModelAndView capacityIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("dimension/capacity");
        List<EconomicsCapacity> capacityList = economicsDimensionService.getCapacityList();
        mav.addObject("capacityList", capacityList);
        return mav;
    }

    /**
     * 产能：维护页面
     *
     * @return
     */
    @RequestMapping("addCapacity")
    public ModelAndView addCapacity(HttpServletRequest request, HttpServletResponse response, EconomicsCapacity economicsCapacity) throws Exception {


        EconomicsCapacity capacity = new EconomicsCapacity();
        ModelAndView mav = new ModelAndView("dimension/addCapacity");
        if (economicsCapacity.getPk() != null && !"".equals(economicsCapacity.getPk())) {
            capacity = economicsDimensionService.getCapacityByPk(economicsCapacity.getPk());
        }
        mav.addObject("capacity", capacity);
        return mav;
    }

    @ResponseBody
    @RequestMapping("capacity_list")
    public String capacityList(HttpServletRequest request, HttpServletResponse response, EconomicsCapacity economicsCapacity) {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<EconomicsCapacity> qm = new QueryModel<EconomicsCapacity>(economicsCapacity, start, limit, orderName, orderType);
        PageModel<EconomicsCapacity> pm = economicsDimensionService.getCapacityData(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }


    @ResponseBody
    @RequestMapping("updateCapacityStatus")
    public String updateCapacityStatus(EconomicsCapacity economicsCapacity) {
        int result = economicsDimensionService.updateCapacityStatus(economicsCapacity);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping("updateCapacity")
    public String updateCapacity(EconomicsCapacity economicsCapacity) {
        int result = 0;
        if (economicsCapacity.getPk() != null && !Objects.equals(economicsCapacity.getPk(), ""))
            result = economicsDimensionService.updateCapacity(economicsCapacity);
        else
            result = economicsDimensionService.insertCapacity(economicsCapacity);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }


    @RequestMapping("capacityData")
    public String capacityData() {
        EconomicsCapacity economicsCapacity = new EconomicsCapacity();
        List<EconomicsCapacity> list = new ArrayList<>();
        B2bEconomicsDimensionModel b2bEconomicsDimensionModel = new B2bEconomicsDimensionModel();
        economicsCapacity.setPk("dsa");
        economicsCapacity.setScore(5);
        economicsCapacity.setOpen(1);
        economicsCapacity.setInsertTime(new Date());
        economicsCapacity.setMaxCapacity(10);
        economicsCapacity.setMinCapacity(1);
        list.add(economicsCapacity);
        b2bEconomicsDimensionModel.setPk("dsadsa");
        b2bEconomicsDimensionModel.setUpdateTime(new Date());
        b2bEconomicsDimensionModel.setItem("capacity");
        b2bEconomicsDimensionModel.setCategory("operation");
        b2bEconomicsDimensionModel.setContent(JSON.toJSONString(list));
        economicsDimensionService.insert(b2bEconomicsDimensionModel);
        return "1";
    }

}
