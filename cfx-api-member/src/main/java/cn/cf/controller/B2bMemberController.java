package cn.cf.controller;

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
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemberDtoEx;
import cn.cf.dto.B2bRoleDto;
import cn.cf.entity.Sessions;
import cn.cf.service.B2bMemberService;
import cn.cf.service.MemberCompanyService;
import cn.cf.service.MemberFacadeService;
import cn.cf.service.MemberPointService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/**
 * 
 * @description:人员管理
 * @author XHT
 * @date 2018-4-20 上午11:09:17
 */
@RestController
@RequestMapping("member")
public class B2bMemberController extends BaseController {

	@Autowired
	private MemberFacadeService memberFacadeService;

	@Autowired
	private B2bMemberService memberService;

	@Autowired
	private MemberCompanyService b2bCompanyService;

	@Autowired
	private MemberPointService memberPointService;


	
	/**
	 * test  测试接口
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "addPointForOrderTest", method = RequestMethod.POST)
	public String addPointForOrderTest(HttpServletRequest req) {
		/*Sessions session = this.getSessions(req);
		B2bMemberDtoEx dto = new B2bMemberDtoEx();
		dto.bind(req);
		if (dto.getCompanyPk() == null || "".equals(dto.getCompanyPk())) {
			dto.setCompanyPk(session.getCompanyPk());
			dto.setCompanyName(session.getCompanyName());
		}
		dto.setRegisterSource(this.getSource(req));
		dto.setAddPk(session.getMemberPk());*/
		
		memberPointService.addPointForOrder("201707251403183739849", 1);
		//RestCode restCode = memberFacadeService.addMember(dto);
		return "";
	}
	
	
	/**
	 * test  测试接口
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "addExtPointForOrderTest", method = RequestMethod.POST)
	public String addExtPointForOrderTest(HttpServletRequest req) {
		/*Sessions session = this.getSessions(req);
		B2bMemberDtoEx dto = new B2bMemberDtoEx();
		dto.bind(req);
		if (dto.getCompanyPk() == null || "".equals(dto.getCompanyPk())) {
			dto.setCompanyPk(session.getCompanyPk());
			dto.setCompanyName(session.getCompanyName());
		}
		dto.setRegisterSource(this.getSource(req));
		dto.setAddPk(session.getMemberPk());*/
		
		memberPointService.addExtPointForOrder("201707251403183739849", 1);
		//RestCode restCode = memberFacadeService.addMember(dto);
		return "调用成功";
	}
	
	
	
	/**
	 * 添加人员管理
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "addMember", method = RequestMethod.POST)
	public String addMember(HttpServletRequest req) {
		Sessions session = this.getSessions(req);
		B2bMemberDtoEx dto = new B2bMemberDtoEx();
		dto.bind(req);
		if (dto.getCompanyPk() == null || "".equals(dto.getCompanyPk())) {
			dto.setCompanyPk(session.getCompanyPk());
			dto.setCompanyName(session.getCompanyName());
		}
		dto.setRegisterSource(this.getSource(req));
		dto.setAddPk(session.getMemberPk());
		RestCode restCode = memberFacadeService.addMember(dto);
		return restCode.toJson();
	}

	/***
	 * 获取角色权限列表
	 * @param companyType 类型(1采购商 2供应商 ）
	 */
	@RequestMapping(value = "searchRoleList", method = RequestMethod.POST)
	public String searchRoleList(HttpServletRequest req) {
		int companyType = ServletUtils.getIntParameterr(req, "companyType", 0);
		List<B2bRoleDto> list = memberService.searchRoleList(companyType);
		return RestCode.CODE_0000.toJson(list);

	}

	/**
	 * 审核人员/删除人员
	 */
	@RequestMapping(value = "updateMember", method = RequestMethod.POST)
	public String updateMember(HttpServletRequest req) {
		RestCode restCode = RestCode.CODE_0000;
		Sessions session = this.getSessions(req);
		if (session == null) {
			restCode = RestCode.CODE_S001;
		} else {
			B2bMemberDtoEx dto = new B2bMemberDtoEx();
			dto.bind(req);
			restCode = memberFacadeService.updateMember(dto, session);
		}
		return restCode.toJson();
	}

	/***
	 * 人员列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchMemberList", method = RequestMethod.POST)
	public String searchMemberList(HttpServletRequest request) {
		String restCode = RestCode.CODE_0000.toJson();
		B2bCompanyDto company = this.getCompanyBysessionsId(request);
		if (company == null) {
			restCode = RestCode.CODE_M008.toJson();
		} else {
			Map<String, Object> map = this.paramsToMap(request);
			try {
				int companyType = ServletUtils.getIntParameterr(request, "companyType", 0);
				String[] companyPks = b2bCompanyService.getCompanyPks(company, companyType, 1);
				map.put("companyPks", companyPks);
				map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
				map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
				map.put("orderName", ServletUtils.getStringParameter(request, "orderName", "insertTime"));
				map.put("orderType", ServletUtils.getStringParameter(request, "orderType", "desc"));
				PageModel<B2bMemberDtoEx> pm = memberService.searchMemberList(map, companyType);
				restCode = RestCode.CODE_0000.toJson(pm);
			} catch (Exception e) {
				e.printStackTrace();
				restCode = RestCode.CODE_S999.toJson();
			}
		}
		return restCode;
	}
	
	/**
	 * 人员详情
	 */
	@RequestMapping(value = "getMemberByPk", method = RequestMethod.POST)
	public String getMemberByPk(HttpServletRequest req) {
		String pk = ServletUtils.getStringParameter(req, "pk", "");
		RestCode restCode = RestCode.CODE_0000;
		B2bMemberDtoEx dto = memberService.getByPk(pk);
		return restCode.toJson(dto);
	}
	
	/***
	 * 当前登录人下的组长
	 */
    @RequestMapping(value = "searchMemberByParentPk", method = RequestMethod.POST)
	public String searchMemberByParentPk(HttpServletRequest request) {
    	B2bCompanyDto company = this.getCompanyBysessionsId(request);
    	Map<String, Object> map = this.paramsToMap(request);
    	String companyPk=ServletUtils.getStringParameter(request, "companyPk","");
    	if(null!=companyPk&&!"".equals(companyPk)){
    		  map.put("companyPk", companyPk)	;
    	}else{
    		String[] companyPks = b2bCompanyService.getCompanyPks(company, null, 1);
    		map.put("companyPks", companyPks );
    	}
		List<B2bMemberDto> list = memberService.searchMemberByParentPk(map);
		return RestCode.CODE_0000.toJson(list);

	}

}
