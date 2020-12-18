package cn.cf.controler;

import java.util.HashMap;
import java.util.List;
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
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bStoreAlbumDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bStoreDtoEx;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bCompany;
import cn.cf.model.B2bStore;
import cn.cf.service.B2bCompanyService;
import cn.cf.service.B2bFacadeService;
import cn.cf.service.B2bStoreService;
import cn.cf.service.CollectionService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

@RestController
@RequestMapping("shop")
public class B2bCompanyController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(B2bCompanyController.class);
    @Autowired
    private B2bCompanyService b2bCompanyService;

    @Autowired
    private B2bFacadeService b2bFacadeService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private B2bStoreService b2bStoreService;

    /**
     * 公司详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getCompany", method = RequestMethod.POST)
    public String getCompany(HttpServletRequest request) {
        Sessions session = this.getSessions(request);
        String companyPk = ServletUtils.getStringParameter(request, "companyPk", "");
        if ("".equals(companyPk)) {
            companyPk = session.getCompanyPk();
        }
        return RestCode.CODE_0000.toJson(b2bCompanyService.getCompany(companyPk));

    }

    /**
     * 编辑公司信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "updateCompany", method = RequestMethod.POST)
	public String updateCompany(HttpServletRequest request) {
		RestCode restCode = RestCode.CODE_0000;
		B2bCompany company = new B2bCompany();
		company.bind(request);
		String companyPk = ServletUtils.getStringParameter(request, "companyPk", "");
		if ("".equals(companyPk)) {
			  return RestCode.CODE_A001.toJson();
		}
		if (null!=company.getName() && company.getName().length()>=40) {
			return RestCode.CODE_C019.toJson();
		}
		if (null!=company.getOrganizationCode()&&company.getOrganizationCode().length()>=80) {
			return RestCode.CODE_C024.toJson();
		}
		if (null!=company.getContacts()&&company.getContacts().length()>=80) {
			return RestCode.CODE_C025.toJson();
		}
		if (null!=company.getContactsTel()&&company.getContactsTel().length()>=20) {
			return RestCode.CODE_C026.toJson();
		}
		company.setPk(companyPk);
		if(null != company.getOrganizationCode() && !"".equals(company.getOrganizationCode())){
			company.setOrganizationCode(company.getOrganizationCode().toUpperCase());
		}
		restCode = b2bCompanyService.updateCompany(company);
		return restCode.toJson();
	}

    /**
     * 获取公司列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "searchCompanyList", method = RequestMethod.POST)
    public String searchCompanyList(HttpServletRequest request) {
        String result = RestCode.CODE_0000.toJson();
        Integer companyType = ServletUtils.getIntParameterr(request, "companyType", 1);   //1:采购商 2:供应商
        Integer returnType = ServletUtils.getIntParameterr(request, "returnType", 1);  //1:返回子母所有 2:返回子公司
        String companyPk = ServletUtils.getStringParameter(request, "companyPk", "");
        Integer auditStatus = ServletUtils.getIntParameterr(request, "auditStatus", 0);//采购商审核状态(1待审核2审核通过3审核不通过),默认0全部
        Integer auditSpStatus = ServletUtils.getIntParameterr(request, "auditSpStatus", 0);//供应商审核状态(1待审核2审核通过3审核不通过),默认0全部
        if (auditStatus==0) {
        	auditStatus=null;
		}
        if (auditSpStatus==0) {
        	auditSpStatus=null;
		}
        if (companyPk == null || "".equals(companyPk)) {
            Sessions session = this.getSessions(request);
        	if (session !=null && !"".equals(session)) {
        		 companyPk = session.getCompanyPk();
			}
        }
        if (companyPk == null || "".equals(companyPk)) {
            result = RestCode.CODE_A001.toJson();
        } else {
            Map<String, Object> map = this.paramsToMap(request);
            PageModel<B2bCompanyDtoEx> pm = b2bCompanyService.searchCompanyListByLimit(companyType, companyPk, returnType, map,auditStatus,auditSpStatus);
            result = RestCode.CODE_0000.toJson(pm);
        }
        return result;
    }
    /**
     * 获取当前登录人店铺信息
     * @param req
     * @return
     */
    @RequestMapping(value = "getStore", method = RequestMethod.POST)
    public String getStore(HttpServletRequest req) {
    	B2bStoreDto store = this.getStoreBysessionsId(req);
        B2bStoreDtoEx storeDto = new B2bStoreDtoEx();
        if(null != store.getPk() || !"".equals(store.getPk())){
        	storeDto =  b2bStoreService.getCompStoreByStorePk(store.getPk());
        }
        return RestCode.CODE_0000.toJson(storeDto);
    }
    

    /**
     * 店铺设置新增店铺简介，店铺公告；LOGO设置
     * @param req
     * @return
     */
    @RequestMapping(value = "settingUpShop", method = RequestMethod.POST)
    public String settingUpShop(HttpServletRequest req) {
        B2bCompanyDto company=this.getCompanyBysessionsId(req);
        B2bStoreDto store = this.getStoreBysessionsId(req);
        if(null==company){
            return RestCode.CODE_M008.toJson();
        } else{
            if(null==store||null==store.getPk()){
                return RestCode.CODE_A007.toJson();
            }
        }

        B2bStore model = new B2bStore ();
        model.bind(req);
        model.setPk(store.getPk());
        RestCode restCode = b2bStoreService.updateIsOpen(model);
        return restCode.toJson();
    }

    /**
     * 获取子母公司PK
     */
    @RequestMapping(value = "getCompanyPks", method = RequestMethod.POST)
    public String getCompanyPks(HttpServletRequest request) {
        String result = RestCode.CODE_0000.toJson();
        Integer companyType = ServletUtils.getIntParameterr(request, "companyType", 1);   //1:采购商 2:供应商
        Integer returnType = ServletUtils.getIntParameterr(request, "returnType", 1);  //1:返回子母所有 2:返回子公司
        String companyPk = ServletUtils.getStringParameter(request, "companyPk", "");
        B2bCompanyDto company;
        if (companyPk == null || "".equals(companyPk)) {
            Sessions session = this.getSessions(request);
            companyPk = session.getCompanyPk();
        }
        if (companyPk == null || "".equals(companyPk)) {
            result = RestCode.CODE_A001.toJson();
        } else {
            company = this.getCompanyBysessionsId(request);
            String[] companyPks = b2bCompanyService.getCompanyPks(company, companyType, returnType);
            result = RestCode.CODE_0000.toJson(companyPks);
        }
        return result;
    }

    /**
     * 采购商列表:不包含当前公司的子母公司
     */
    @RequestMapping(value = "searchPurchaserList", method = RequestMethod.POST)
    public String searchPurchaserCompany(HttpServletRequest request) {
        String result = RestCode.CODE_0000.toJson();
        String companyPk = ServletUtils.getStringParameter(request, "companyPk", "");
        Map<String, Object> map = paramsToMap(request);
        map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
        map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
        
        if (companyPk == null || "".equals(companyPk)) {
            Sessions session = this.getSessions(request);
            companyPk = session.getCompanyPk();
        }
        if (companyPk == null || "".equals(companyPk)) {
            result = RestCode.CODE_A001.toJson();
        } else {
            B2bCompanyDtoEx dto = new B2bCompanyDtoEx();
            dto.setPk(companyPk);
            map.put("uname", b2bCompanyService.getCompanyPks(dto, 1, 1));
            List<B2bCompanyDtoEx> list = b2bCompanyService.searchPurchaserCompany(map);
            result = RestCode.CODE_0000.toJson(list);
        }
        return result;
    }

    /**
     * 添加子公司
     *
     * @param request
     * @return
     */
	@RequestMapping(value = "addSubCompany", method = RequestMethod.POST)
	public String addSubCompany(HttpServletRequest request) {
		RestCode code = RestCode.CODE_0000;
		Sessions session = this.getSessions(request);
		B2bCompanyDtoEx dto = new B2bCompanyDtoEx();
		dto.bind(request);
		String organizationCode = ServletUtils.getStringParameter(request, "organizationCode", "");
		String name = ServletUtils.getStringParameter(request, "name", "");
		String contactsTel = ServletUtils.getStringParameter(request, "contactsTel", "");
		String companyType = ServletUtils.getStringParameter(request, "companyType", "");
		if (organizationCode == null || "".equals(organizationCode) || name == null || "".equals(name)
				|| contactsTel == null || "".equals(contactsTel) || companyType == null || "".equals(companyType)) {
			return RestCode.CODE_A001.toJson();
		}
		if (null!=dto.getName() && dto.getName().length()>=40) {
			return RestCode.CODE_C019.toJson();
		}
		if (null!=dto.getOrganizationCode()&&dto.getOrganizationCode().length()>=80) {
			return RestCode.CODE_C024.toJson();
		}
		if (null!=dto.getContacts()&&dto.getContacts().length()>=80) {
			return RestCode.CODE_C025.toJson();
		}
		if (null!=dto.getContactsTel()&&dto.getContactsTel().length()>=20) {
			return RestCode.CODE_C026.toJson();
		}
		dto.setParentPk(session.getCompanyPk());
		dto.setAddMemberPk(session.getMemberPk());
		dto.setOrganizationCode(dto.getOrganizationCode().toUpperCase());
		code = b2bFacadeService.addSubCompany(dto, session.getMemberPk());
		return code.toJson();
	}

    /**
     * 获取店铺信息
     */
    @RequestMapping(value = "getStoreInfo", method = RequestMethod.POST)
    public String getStoreInfo(HttpServletRequest request) {
        String restCode = RestCode.CODE_0000.toJson();
        String storePk = ServletUtils.getStringParameter(request, "storePk", "");
        if ("".equals(storePk)) {
            restCode = RestCode.CODE_A001.toJson();
        } else {
            Sessions session = this.getSessions(request);
            String memberPk = "";
            if (session != null) {
                memberPk = session.getMemberPk();
            }
            try {
                B2bStoreDtoEx store = b2bStoreService.getCompStoreByStorePk(storePk);
                if (store == null) {
                    return RestCode.CODE_0000.toJson();
                }
                store.setIsCollection(2);
                if ("".equals(memberPk) || !collectionService.isCollection(memberPk, storePk))
                    store.setIsCollection(1);
                restCode = RestCode.CODE_0000.toJson(store);
            } catch (Exception e) {
                e.printStackTrace();
                restCode = RestCode.CODE_S999.toJson();
            }
        }
        return restCode;
    }
    /***
     * 企业相册  添加(cf-api文档未补充)
     */
    @RequestMapping(value = "addStoreAlbum", method = RequestMethod.POST)
    public String addStoreAlbum (HttpServletRequest request) {
    	RestCode code = RestCode.CODE_0000;
    	String storePk ="";
    	Sessions session=this.getSessions(request);
		if (null != session.getCompanyDto() && null != session.getStoreDto()||null!=session.getStoreDto().getPk()&&!"".equals(session.getStoreDto().getPk())) {
			storePk=session.getStoreDto().getPk();
		}
		String url = ServletUtils.getStringParameter(request,"url", ""); 
		if(null!=storePk&&!"".equals(storePk)&&null!=url&&!"".equals(url)){
			 code = b2bFacadeService.addStoreAlbum(storePk,url);
		}else{
			code=RestCode.CODE_A001;
		}
		
		 return code.toJson();
    }
    
    /***
     * 企业相册  删除(cf-api文档未补充)
     */
    @RequestMapping(value = "deleteStoreAlbumByPk", method = RequestMethod.POST)
    public String deleteStoreAlbum (HttpServletRequest request) {
    	RestCode code = RestCode.CODE_0000;
    	String pk = ServletUtils.getStringParameter(request, "pk", "");
		 
		if(null!=pk&&!"".equals(pk)){
			 code = b2bFacadeService.deleteStoreAlbum(pk);
		}else{
			code=RestCode.CODE_A001;
		}
		
		 return code.toJson();
    }

	/**
	 *  企业相册
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "searchStoreAlbumByStorePk", method = RequestMethod.POST)
	public String searchStoreAlbum(HttpServletRequest req) {
		String result = RestCode.CODE_0000.toJson();
		try {
			String storePk = ServletUtils.getStringParameter(req,"storePk", ""); 
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", ServletUtils.getIntParameter(req, "start", 0));
			map.put("limit", ServletUtils.getIntParameter(req, "limit", 10));
			map.put("orderName", ServletUtils.getStringParameter (req, "orderName", "insertTime"));
			map.put("orderType", ServletUtils.getStringParameter(req, "orderType", "desc"));
			if(null==storePk||"".equals(storePk)){
			return	RestCode.CODE_0000.toJson() ;
			}else{
				map.put("storePk", storePk);
			}
			
			PageModel<B2bStoreAlbumDto> pm = b2bFacadeService.searchStoreAlbum(map);
			result = RestCode.CODE_0000.toJson(pm);

		} catch (Exception e) {
			logger.error("searchStoreAlbum Error", e);
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	
	
	/**
	 * 编辑发票信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateInvoice", method = RequestMethod.POST)
	public String updateInvoice(HttpServletRequest request) {
		Sessions session = this.getSessions(request);
		B2bCompanyDto companyDto = new B2bCompanyDto();
		companyDto.bind(request);
		String pk = ServletUtils.getStringParameter(request, "pk","");
		if (null == pk || "".equals(pk)) {
			return RestCode.CODE_A001.toJson();
		}
		if(null != companyDto.getOrganizationCode() && !"".equals(companyDto.getOrganizationCode())){
			companyDto.setOrganizationCode(companyDto.getOrganizationCode().toUpperCase());
		}
		return b2bCompanyService.updateInvoice(companyDto,session.getMemberPk());
	}
	
	
    /**
     * 根据公司名称查询公司
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getCompanyByCompanyName", method = RequestMethod.POST)
    public String getCompanyByName(HttpServletRequest request) {
        String name = ServletUtils.getStringParameter(request, "name", "");
        if (null == name || "".equals(name)) {
        	return RestCode.CODE_A001.toJson();
        }
        return RestCode.CODE_0000.toJson(b2bCompanyService.getCompanyByName(name));
    }
	
    
}
