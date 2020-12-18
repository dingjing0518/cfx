package cn.cf.controller.yarn;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dto.SxSpecDto;
import cn.cf.dto.SxSpecExtDto;
import cn.cf.dto.SxTechnologyExtDto;
import cn.cf.json.JsonUtils;
import cn.cf.service.yarn.SxSpecExtService;
import cn.cf.service.yarn.SxTechnologyService;
import cn.cf.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hxh 纱线-商品配置-工艺管理
 */
@Controller
@RequestMapping("/")
public class TechnologyController extends BaseController{
    
    @Autowired
    private SxTechnologyService sxTechnologyService;

    @Autowired 
    private SxSpecExtService sxSpecExtService;
    
    /**
     * 工艺
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("technology")
    public ModelAndView technology(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("yarn/goodsmgr/technology");
        return mav;
    }
    
    
    /**
     * 原料查询集合列表
     *
     * @param request
     * @param response
     * @param sxTechnologyExtDto
     * @return
     */
    @RequestMapping("searchTechnology")
    @ResponseBody
    public String searchTechnology(HttpServletRequest request, HttpServletResponse response, SxTechnologyExtDto sxTechnologyExtDto) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<SxTechnologyExtDto> qm = new QueryModel<SxTechnologyExtDto>(sxTechnologyExtDto, start, limit, orderName,
                orderType);
        PageModel<SxTechnologyExtDto> pm = sxTechnologyService.searchTechnology(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }
    
    

    /**
     * 修改商品原料
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("updateTechnology")
    @ResponseBody
    public String updateTechnology(HttpServletRequest request, HttpServletResponse response, SxTechnologyExtDto sxTechnologyExtDto) {
        
        sxTechnologyExtDto.setName(sxTechnologyExtDto.getName().trim());
        
        int retVal = sxTechnologyService.updateTechnology(sxTechnologyExtDto);
        String msg = "";
        if (retVal > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else if (retVal == 0) {
            msg = Constants.RESULT_FAIL_MSG;
        } else if (retVal == -3) {
            msg = Constants.TECHNOLOGY_NAMESHORTNAME_REPEAT;
        }
        return msg;
    }
    
    /**
     * 删除/启用/禁用
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("updateTechnologyExt")
    @ResponseBody
    public String updateTechnologyExt(HttpServletRequest request, HttpServletResponse response, SxTechnologyExtDto sxTechnologyExtDto) {
        int retVal = sxTechnologyService.updateTechnologyExt(sxTechnologyExtDto);
        String msg = "";
        if (retVal > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else if (retVal == 0) {
            msg = Constants.RESULT_FAIL_MSG;
        } else if (retVal == -3) {
            msg = Constants.TECHNOLOGY_NAMESHORTNAME_REPEAT;
        }
        return msg;
    }
    

    /**
     * 纱线规格
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("sxSpec")
    public ModelAndView sxTechnologyType(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("yarn/goodsmgr/sxSpec");
        return mav;
    }

    /**
     * 产品类型列表
     *
     * @param request
     * @param respons
     * @param b2bVarieties
     * @return
     */
    @RequestMapping("searchSxSpec")
    @ResponseBody
    public String searchSxTechnologyType(HttpServletRequest request, HttpServletResponse respons,
            SxSpecExtDto b2bVarieties) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
        QueryModel<SxSpecExtDto> qm = new QueryModel<>(b2bVarieties, start, limit, orderName,
                orderType);
        PageModel<SxSpecExtDto> pm = sxSpecExtService.searchSxSpecList(qm);
        String json = JsonUtils.convertToString(pm);

        return json;
    }
    
    
    /**
     * 更新纱线规格
     * @param request
     * @param respons
     * @param dto
     * @return
     */
    @RequestMapping("updateSxSpec")
    @ResponseBody
    public String updateTechnologyType(HttpServletRequest request, HttpServletResponse respons,
                                       SxSpecExtDto dto) {
            String msg = "";
           Map<String,Object> map = new HashMap<String,Object>();
           map.put("name",dto.getName());
            int result = 0;
           if(CommonUtil.isNotEmpty(dto.getName())) {
               List<SxSpecDto> list = sxSpecExtService.searchSxSpecList(map);
               if (list != null && list.size() > 0){
                  return Constants.ISEXTIST_YARN_SPEC_NAME;
               }else{
                   result = sxSpecExtService.updateSxSpec(dto);
               }
           }else{
               result = sxSpecExtService.updateSxSpec(dto);
           }
               if (result > 0) {
                   msg = Constants.RESULT_SUCCESS_MSG;
               } else {
                   msg = Constants.RESULT_FAIL_MSG;
               }
        return msg;
    }

    /**
     * 更新纱线规格
     * @param request
     * @param respons
     * @param pk
     * @return
     */
    @RequestMapping("updateSxSpecStatus")
    @ResponseBody
    public String delSxSpec(HttpServletRequest request, HttpServletResponse respons,
                                       String pk,String isVisable) {


        SxSpecExtDto dto = new SxSpecExtDto();
        dto.setPk(pk);
        dto.setIsVisable(Integer.valueOf(isVisable));
        int result = sxSpecExtService.updateSxSpecStatus(dto);
        String msg = "";
        if (result > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }
}
