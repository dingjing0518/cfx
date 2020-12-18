package cn.cf.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bCustomerManagementDto;
import cn.cf.dto.B2bCustomerSalesmanDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.entity.CustomerDataImport;
import cn.cf.entity.Sessions;
import cn.cf.service.B2bCustomerMangementService;
import cn.cf.service.B2bCustomerSaleManService;
import cn.cf.service.B2bMemberService;
import cn.cf.service.MemberCompanyService;
import cn.cf.service.MemberFacadeService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/**
 * 
 * @description:客户管理
 * @author xht
 * @date 2018-4-20 上午11:09:17
 */
@RestController
@RequestMapping("member")
public class B2bCustomerController extends BaseController {

	@Autowired
	private B2bCustomerMangementService customerMangementService;
	
	@Autowired
	private B2bCustomerSaleManService b2bCustomerSaleManService;
	
	
	@Autowired
	private B2bMemberService b2bMemberService;
	
	@Autowired
	private MemberCompanyService memberCompanyService;
	
	
	@Autowired
	private MemberFacadeService facadeService;

	/**
	 * 供应商添加采购商
	 * 
	 * @param purchaserPk
	 * @return
	 */
	@RequestMapping(value = "addCustomerManagement", method = RequestMethod.POST)
	public String addPurchaserManagement(HttpServletRequest req) {
		RestCode restCode = RestCode.CODE_0000;
		B2bCompanyDto cdto = this.getCompanyBysessionsId(req);
		B2bStoreDto store = this.getStoreBysessionsId(req);
		try {
			if (null == cdto) {
				restCode = RestCode.CODE_M008;
			} else {
				B2bCustomerManagementDto dto = new B2bCustomerManagementDto();			
				dto.bind(req);
				dto.setStorePk(null==store?null:store.getPk());
				restCode = customerMangementService.addCustomerManagement(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode=RestCode.CODE_S999;
		}
		return restCode.toJson();
	}
	
	/**
	 * 公司业务员列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "searchMembersByCompany", method = RequestMethod.POST)
	public String seachMembersByCompany(HttpServletRequest req) {
		try {
			String companyPk = ServletUtils.getStringParameter(req, "companyPk","");
			if("".equals(companyPk)){
				Sessions session = this.getSessions(req);
				companyPk = session.getCompanyPk();
			}
			B2bCompanyDtoEx companyDto = memberCompanyService.getCompany(companyPk);
			String[] companyPks = memberCompanyService.getCompanyPks(companyDto,2,1);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("companyPks", companyPks);
			List<B2bMemberDto> list = b2bMemberService.searchMembersByCompany(map);
			return RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
	
	/**
	 * 分配业务员
	 * @param customerPk 客户管理pk
	 * @param memberPk 业务员pk
	 * @param filiale 所属公司
	 * @param productPk 品名pk
	 * @return 
	 * 
	 */
	@RequestMapping(value = "distributeMember", method = RequestMethod.POST)
	public String distributionMember(HttpServletRequest req) {
		RestCode restCode  = RestCode.CODE_0000;
		B2bCompanyDto company=this.getCompanyBysessionsId(req);
		B2bStoreDto store = this.getStoreBysessionsId(req);
	//	String block = ServletUtils.getStringParameter(req, "block","cf");
		if (null == company) {
			restCode = RestCode.CODE_M008;
		} else {
			B2bCustomerSalesmanDto cusS=new B2bCustomerSalesmanDto();
			cusS.bind(req);
			cusS.setStorePk(null==store?null:store.getPk());
		//	cusS.setBlock(block);
			try {
				 restCode = facadeService.addCustomerSaleMan(cusS);
			} catch (Exception e) {
				e.printStackTrace();
				restCode = RestCode.CODE_S999;
			}
		}
		return restCode.toJson();
		
	}
	
	/*** 
	 * 客户管理 已分配业务员列表
	 */
	@RequestMapping(value = "searchCusSalesManList", method = RequestMethod.POST)
	public String searchCusSalesManList(HttpServletRequest req) {
		String result = null;
		try {
			String customerPk=ServletUtils.getStringParameter(req, "customerPk");
			if(null==customerPk&&"".equals(customerPk)){
				result = RestCode.CODE_A001.toJson();
			}else{
				Map<String, Object> map = this.paramsToMap(req);
				map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
				map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
				PageModel<B2bCustomerSalesmanDto> pm = b2bCustomerSaleManService.searchCusSalesManList(map);
				result =RestCode.CODE_0000.toJson(pm);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	/**
	 * 删除已分配数据
	 */
	@RequestMapping(value = "deleteCusSalesman", method = RequestMethod.POST)
	public String deleteCusSalesman(HttpServletRequest req) {
		String customerPk=ServletUtils.getStringParameter(req, "pk");
		RestCode restCode = b2bCustomerSaleManService.deleteCusSalesman(customerPk);
		return restCode.toJson();
	}
	
	/**
	 * 客户管理
	 */
	@RequestMapping(value = "searchCustomerList", method = RequestMethod.POST)
	public String searchCustomerList(HttpServletRequest req) {
	 B2bStoreDto store=this.getStoreBysessionsId(req);
	 B2bMemberDto member=this.getMemberBysessionsId(req);
	 Sessions session=this.getSessions(req);
		try {
			Map<String, Object> map = this.paramsToMap(req);
			map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
			map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
			map.put("orderName", ServletUtils.getStringParameter(req, "orderName", "insertTime"));
			map.put("orderType", 	ServletUtils.getStringParameter(req, "orderType", "desc"));
	        if(session.getIsAdmin()!=1){
	        	map.put("memberPk", member.getPk());	
	        	map.put("parentPk", member.getParentPk());	//-1 为组长，反之非组长
	        }
	    	map.put("storePk",store.getPk()==null?"":store.getPk());
			PageModel<B2bCompanyDtoEx> pm = memberCompanyService.searchCustomerList(map);
			return RestCode.CODE_0000.toJson(pm);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
	
	/**
	 * 客户导入管理
	 */
	@RequestMapping(value = "searchCustomerImportList", method = RequestMethod.POST)
	public String searchCustomerImportList(HttpServletRequest req) {
		 B2bStoreDto store=this.getStoreBysessionsId(req);
		 Integer start =  ServletUtils.getIntParameterr(req, "start", 0);
		 Integer limit =  ServletUtils.getIntParameterr(req, "limit", 10);
		 return RestCode.CODE_0000.toJson(facadeService.searchCustomerDataImport(null==store?null:store.getPk(), start, limit));
	}
	
	
	/**
	 * 客户导入
	 */
	@RequestMapping(value = "importCustomerData", method = RequestMethod.POST)
	public String importCustomerData(HttpServletRequest req) {
		String rest = RestCode.CODE_0000.toJson();
		B2bStoreDto store=this.getStoreBysessionsId(req);
		String url = ServletUtils.getStringParameter(req, "url","");
		if("".equals(url)){
			rest = RestCode.CODE_A001.toJson();
		}else{
			CustomerDataImport cd = new CustomerDataImport();
			cd.setUrl(url);
			cd.setStorePk(null==store?null:store.getPk());
			rest = facadeService.importCustomerData(cd);
		}
		return rest;
	}
}
