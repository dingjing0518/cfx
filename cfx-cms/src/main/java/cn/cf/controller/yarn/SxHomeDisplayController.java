package cn.cf.controller.yarn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.QueryModel;
import cn.cf.json.JsonUtils;
import cn.cf.service.yarn.YarnHomeDisplayService;
import cn.cf.util.ServletUtils;


/**
 * 纱线首页显示管理
 * 
 * @author why
 *
 */
@Controller
@RequestMapping("/")
public class SxHomeDisplayController extends BaseController{
    
    @Autowired
    private YarnHomeDisplayService  yarnHomeDisplayService;
    
 

    /**
     * 热搜关键词显示页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("showHotSearch")
    public ModelAndView showHotSearch(HttpServletRequest request, HttpServletResponse response) {
        String modelType = ServletUtils.getStringParameter(request, "modelType");
        ModelAndView mvn = new ModelAndView("operation/opermg/keywordHot");
        mvn.addObject("modelType",modelType);
        return mvn;
    }
    /**
     * 根据parentPk查询二级原料
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("searchRawMaterialList")
    @ResponseBody
    public String searchRawMaterialList(HttpServletRequest request, HttpServletResponse response,String parentPk) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("isDelete", Constants.ONE);
        map.put("isVisable", Constants.ONE);
        map.put("parentPk", parentPk);
        return JsonUtils.convertToString(yarnHomeDisplayService.getMaterialListByGrade(map));
    }
}
