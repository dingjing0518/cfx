package cn.cf.controller.yarn;

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
import cn.cf.dto.SxMaterialDto;
import cn.cf.dto.SxMaterialExtDto;
import cn.cf.json.JsonUtils;
import cn.cf.service.yarn.SxMaterialService;
import cn.cf.util.ServletUtils;

/**
 * @author hxh 纱线-商品配置-原料管理
 */
@Controller
@RequestMapping("/")
public class RawMaterialController extends BaseController{


    @Autowired
    private SxMaterialService sxMaterialService;
    
    /**
     * 原料列表显示页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("rawMaterialOne")
    public ModelAndView helps(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("yarn/goodsmgr/rawMaterial");
        return mav;
    }

    /**
     * 原料列表显示页面
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("rawMaterialTwo")
    public ModelAndView rawMaterialTwo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("yarn/goodsmgr/rawMaterialTwo");
        SxMaterialDto dto = new SxMaterialDto();
        dto.setParentPk("-1");
        mav.addObject("rawMaterialList",sxMaterialService.searchParentRawMaterial(dto));
        dto.setIsVisable(1);
        mav.addObject("rawNoIsMaterialList",sxMaterialService.searchParentRawMaterial(dto));

        
        return mav;
    }
    
    
    /**
     * 原料查询集合列表
     *
     * @param request
     * @param response
     * @param sxMaterialExtDto
     * @return
     */
    @RequestMapping("searchRawMaterial")
    @ResponseBody
    public String searchRawMaterail(HttpServletRequest request, HttpServletResponse response, SxMaterialExtDto sxMaterialExtDto) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<SxMaterialExtDto> qm = new QueryModel<SxMaterialExtDto>(sxMaterialExtDto, start, limit, orderName,
                orderType);
        PageModel<SxMaterialExtDto> pm = sxMaterialService.searchRawMaterialList(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    /**
     * 原料查询集合列表
     *
     * @param request
     * @param response
     * @param sxMaterialExtDto
     * @return
     */
    @RequestMapping("searchRawMaterialTwo")
    @ResponseBody
    public String searchRawMaterialTwo(HttpServletRequest request, HttpServletResponse response, SxMaterialExtDto sxMaterialExtDto) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<SxMaterialExtDto> qm = new QueryModel<>(sxMaterialExtDto, start, limit, orderName,
                orderType);
        PageModel<SxMaterialExtDto> pm = sxMaterialService.searchRawMaterialListTwo(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    /**
     * 原料查询原料集合
     *
     * @param request
     * @param response
     * @param parentPk
     * @return
     */
    @RequestMapping("getRawMaterialTwo")
    @ResponseBody
    public String getRawMaterial(HttpServletRequest request, HttpServletResponse response, SxMaterialDto dto) {
        List<SxMaterialDto> list = sxMaterialService.searchParentRawMaterial(dto);
        return JsonUtils.convertToString(list);
    }
    
    

    /**
     * 修改商品原料
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("updateRawMaterial")
    @ResponseBody
    public String updateRawMaterail(HttpServletRequest request, HttpServletResponse response, SxMaterialExtDto sxMaterialExtDto) {
        
        sxMaterialExtDto.setName(sxMaterialExtDto.getName().trim());
        int retVal = sxMaterialService.updateMaterail(sxMaterialExtDto);
        String msg = "";
        if (retVal > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else if (retVal == 0) {
            msg = Constants.RESULT_FAIL_MSG;
        } else if (retVal == -3) {
            msg = Constants.MATERIAL_NAME_REPEAT;
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
    @RequestMapping("updateRawMaterialEx")
    @ResponseBody
    public String updateRawMaterialEx(HttpServletRequest request, HttpServletResponse response, SxMaterialExtDto sxMaterialExtDto) {
        int retVal = sxMaterialService.updateMaterailEx(sxMaterialExtDto);
        String msg = "";
        if (retVal > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else if (retVal == 0) {
            msg = Constants.RESULT_FAIL_MSG;
        } else if (retVal == -1) {
            msg = Constants.MATERIAL_NAME_REPEAT;
        }
        return msg;
    }
    
    
    
    /**
     * 根据原料一级查询 ，级联二级
     * @param request
     * @param response
     * @param sxMaterialExtDto
     * @return
     */
    @RequestMapping("getchangeSecondMaterial")
    @ResponseBody
    public String getchangeSecondMaterial(HttpServletRequest request, HttpServletResponse response, String rawMaterialPk) {
    	List<SxMaterialDto> list = sxMaterialService.getchangeSecondMaterial(rawMaterialPk);
        return JsonUtils.convertToString(list);
    }
}
