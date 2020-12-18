package cn.cf.controler;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.C2bMarrieddealDto;
import cn.cf.dto.C2bMarrieddealDtoEx;
import cn.cf.entity.Sessions;
import cn.cf.service.B2bFacadeService;
import cn.cf.util.ServletUtils;
import cn.cf.util.StringUtils;
import cn.cf.utils.BaseController;


@RestController
@RequestMapping("/shop")
public class MarrieddealController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(MarrieddealController.class);
	
	@Autowired
	private B2bFacadeService b2bFacadeService;
	
	/**
     * 发布求购信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "addMarrieddeal", method = RequestMethod.POST)
    public String addMarrieddeal(HttpServletRequest req) {
        B2bMemberDto memberDto = this.getMemberBysessionsId(req);
        if (memberDto==null|| memberDto.getAuditStatus() == null || memberDto.getAuditStatus() != 2) {
            return RestCode.CODE_M007.toJson();
        }
        B2bCompanyDto companyDto = this.getCompanyBysessionsId(req);
        if (companyDto==null|| companyDto.getAuditStatus() == null || companyDto.getAuditStatus() != 2) {
            return RestCode.CODE_M008.toJson();
        }
        C2bMarrieddealDto marrieddeal = new C2bMarrieddealDto();
        marrieddeal.bind(req);
        return b2bFacadeService.addMarrieddeal(marrieddeal,memberDto,companyDto);
    }
    
    
    
    /**
     * 编辑/删除 求购信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "updateMarrieddeal", method = RequestMethod.POST)
    public String updateMarrieddeal(HttpServletRequest req) {
        C2bMarrieddealDto marrieddeal = new C2bMarrieddealDto();
        marrieddeal.bind(req);
        //1:更新 2：删除
        Integer type = ServletUtils.getIntParameterr(req, "type", 1);
        RestCode code = RestCode.CODE_A001;
        if (marrieddeal == null || marrieddeal.getPk() == null) {
            return code.toJson();
        }
        try {
        	b2bFacadeService.updateMarrieddeal(marrieddeal,type);
            code = RestCode.CODE_0000;
        } catch (Exception e) {
        	logger.error("updateMarrieddeal",e);
            e.printStackTrace();
            code = RestCode.CODE_S999;
        }
        return code.toJson();
    }
    
    

    
    /**
     * 重新发布
     */
    @RequestMapping(value = "reSummitMarrieddeal", method = RequestMethod.POST)
    public String reSummitMarrieddeal(HttpServletRequest req, String marrieddealPk) {
        String result=RestCode.CODE_0000.toJson();
        if (marrieddealPk == null || "".equals(marrieddealPk)) {
        	result= RestCode.CODE_A001.toJson();
        }
        C2bMarrieddealDto marrieddealDto = b2bFacadeService.searchC2bMarrieddealByPk(marrieddealPk);
        try {
            if (marrieddealDto.getAuditStatus() != 1 ) {
            	result = RestCode.CODE_O005.toJson();
            } else {
            	b2bFacadeService.reSummitMarrieddeal(marrieddealPk);
            	result = RestCode.CODE_0000.toJson();
            }
        } catch (Exception e) {
        	logger.error("reSummitMarrieddeal",e);
            e.printStackTrace();
            result = RestCode.CODE_S999.toJson();
        }
        return result;
    }
    
    
    /**
     * 采购商角色求购列表以及在线求购首页列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "myMarrieddeals", method = RequestMethod.POST)
    public String marrieddeal(HttpServletRequest req) {
        try {
            Sessions session = this.getSessions(req);
            Map<String, Object> map = new HashMap<String, Object>();
            //type:1 在线求购首页列表 2采购商角色求购列表
            Integer type=ServletUtils.getIntParameterr(req, "type", 1);
            map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
            map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
            map.put("orderName", ServletUtils.getStringParameter(req,
                    "orderName", "startTime"));
            map.put("orderType",
                    ServletUtils.getStringParameter(req, "orderType", "desc"));
            map.put("productPk", StringUtils.splitStrs(ServletUtils
                    .getStringParameter(req, "productPk", "")));
            map.put("specPk", StringUtils.splitStrs(ServletUtils
                    .getStringParameter(req, "specPk", "")));
            map.put("seriesPk", StringUtils.splitStrs(ServletUtils
                    .getStringParameter(req, "seriesPk", "")));
            map.put("varietiesPk", StringUtils.splitStrs(ServletUtils
                    .getStringParameter(req, "varietiesPk", "")));
            map.put("seedvarietyPk", StringUtils.splitStrs(ServletUtils
                    .getStringParameter(req, "seedvarietyPk", "")));
            map.put("brandPk", StringUtils.splitStrs(ServletUtils
                    .getStringParameter(req, "brandPk", "")));
            map.put("gradePk", StringUtils.splitStrs(ServletUtils
                    .getStringParameter(req, "gradePk", "")));
            String auditStatus = ServletUtils.getStringParameter(req,
                    "auditStatus");
            if (auditStatus != null && !"-1".equals(auditStatus)) {
                map.put("auditStatus", auditStatus);
            }
            String status = ServletUtils.getStringParameter(req, "status");
            if (status != null && !"-1".equals(status)) {
                map.put("status", status);
            }
            if(type==1){
            	 map.put("searchKey", ServletUtils.getStringParameter(req, "searchKey", ""));
            }else{
            	map.put("supplierPk", session.getCompanyPk());
            }
            PageModel<C2bMarrieddealDtoEx> pm = b2bFacadeService.searchC2bMarrieddealByMem(map);
            return RestCode.CODE_0000.toJson(pm);
        } catch (Exception e) {
        	logger.error("myMarrieddeals",e);
            e.printStackTrace();
            return RestCode.CODE_S999.toJson();
        }
    }

}
