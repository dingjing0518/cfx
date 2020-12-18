/**
 * 
 */
package cn.cf.controller.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bCompanyExtDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemberExtDto;
import cn.cf.dto.B2bRoleDto;
import cn.cf.dto.B2bRoleExtDto;
import cn.cf.dto.SysBankNamecodeDto;
import cn.cf.dto.SysBankNamecodeExtDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.dto.SysCompanyBankcardDtoEx;
import cn.cf.dto.SysRegionsDto;
import cn.cf.entity.B2bInvoiceEntity;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bCompany;
import cn.cf.model.B2bMember;
import cn.cf.model.ManageAccount;
import cn.cf.service.CuccSmsService;
import cn.cf.service.enconmics.EconomicsBankService;
import cn.cf.service.operation.B2bMemberService;
import cn.cf.service.operation.CustomerManageService;
import cn.cf.service.operation.InformationCenterService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.operation.CompanyRunnable;
import cn.cf.task.schedule.operation.MemberRunnable;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;

/**
 * 运营中心公司管理
 * @author why
 */
@Controller
@RequestMapping("/")
public class CustomerManageController extends BaseController {
	
	@Autowired
	private CustomerManageService customerManageService;

	@Autowired
	private InformationCenterService informationCenterService;

	@Autowired
	private B2bMemberService b2bMemberService;

	@Autowired
	private CuccSmsService sysService;
	@Autowired
	private EconomicsBankService  economicsBankService;

	@Autowired
	private DynamicTask dynamicTask;

	/**
	 * 采购商审核页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("purchaser")
	public ModelAndView purchaser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/purchaser");
		String modelType = ServletUtils.getStringParameter(request, "modelType");
		if (modelType.contains("?")) {// 数据库默认
			modelType = modelType.substring(0, modelType.indexOf("?"));
		}
		List<SysRegionsDto> list = informationCenterService.searchSysRegionsList("-1");
		mav.addObject("province", list);
		mav.addObject("modelType", modelType);
		return mav;
	}

	/**
	 * 查询采购商供应商公司集合列表
	 * 
	 * @param request
	 * @param response
	 * @param b2bcompay
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchCompanyList")
	public String purchaserData(HttpServletRequest request, HttpServletResponse response, B2bCompanyExtDto b2bcompay) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		ManageAccount account = this.getAccount(request);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "updateTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		setModelTypeValue(b2bcompay);
		QueryModel<B2bCompanyExtDto> qm = new QueryModel<B2bCompanyExtDto>(b2bcompay, start, limit, orderName,
				orderType);
		PageModel<B2bCompanyExtDto> pm = customerManageService.searchCompanyList(qm,account);
		for (B2bCompanyExtDto b : pm.getDataList()) {
			if (null != b.getRemarks() && !"".equals(b.getRemarks())) {
				b.setRemarks(b.getRemarks().replaceAll("\r|\n", ""));
			}
		}
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 设置不同模块标识
	 * @param b2bcompay
	 */
	private void setModelTypeValue(B2bCompanyExtDto b2bcompay) {
		if (b2bcompay != null) {
			String modelType = b2bcompay.getModelType();
			if (modelType != null && modelType.contains("1")) {
				b2bcompay.setModelType("1");
			}
			if (modelType != null && modelType.contains("2")) {
				b2bcompay.setModelType("2");
			}
		}
	}


	/**
	 * 查询供应商子公司集合列表
	 *
	 * @param request
	 * @param response
	 * @param b2bcompay
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchCompanySubList")
	public String searchCompanySubList(HttpServletRequest request, HttpServletResponse response, B2bCompanyExtDto b2bcompay) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		ManageAccount account = this.getAccount(request);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "updateTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");

		QueryModel<B2bCompanyExtDto> qm = new QueryModel<B2bCompanyExtDto>(b2bcompay, start, limit, orderName,
				orderType);
		PageModel<B2bCompanyExtDto> pm = customerManageService.searchCompanySubList(qm,account);
		for (B2bCompanyExtDto b : pm.getDataList()) {
			if (null != b.getRemarks() && !"".equals(b.getRemarks())) {
				b.setRemarks(b.getRemarks().replaceAll("\r|\n", ""));
			}
		}
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 公司管理包括总公司和子公司
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchCompanyManageList")
	public String searchCompanyManageList(HttpServletRequest request, HttpServletResponse response) {
		B2bCompanyExtDto b2bcompay = new B2bCompanyExtDto();
		b2bcompay.bind(request);
		ManageAccount account = this.getAccount(request);
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "updateTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<B2bCompanyExtDto> qm = new QueryModel<B2bCompanyExtDto>(b2bcompay, start, limit, orderName,
				orderType);
		PageModel<B2bCompanyExtDto> pm = customerManageService.searchCompanyManageList(qm,account);
		for (B2bCompanyExtDto b : pm.getDataList()) {
			if (null != b.getRemarks() && !"".equals(b.getRemarks())) {
				b.setRemarks(b.getRemarks().replaceAll("\r|\n", ""));
			}
		}
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 供应商审核页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("supplier")
	public ModelAndView supplier(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/supplier");
		String modelType = ServletUtils.getStringParameter(request,"modelType");
		if (modelType.contains("?")) {// 数据库默认
			modelType = modelType.substring(0, modelType.indexOf("?"));
		}
		List<SysRegionsDto> list = informationCenterService.searchSysRegionsList("-1");
		mav.addObject("province", list);
		mav.addObject("modelType", modelType);
		return mav;
	}

	/**
	 * 修改公司信息
	 * 
	 * @param request
	 * @param companyExtDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateB2bCompany", method = RequestMethod.POST)
	public String updateB2bCompany(HttpServletRequest request, B2bCompanyExtDto companyExtDto) {
		ManageAccount adto = this.getAccount(request);

		String isExist = customerManageService.isExistOrgCode(companyExtDto,false);
		String msg = "";
		if (isExist != "") {
			msg = isExist;
		} else {
			msg = customerManageService.updateB2bCompany(companyExtDto, adto);
		}
		return msg;
	}

	/**
	 * 添加公司
	 * 
	 * @param request
	 * @param companyExtDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertB2bCompany", method = RequestMethod.POST)
	public String insertB2bCompany(HttpServletRequest request, B2bCompanyExtDto companyExtDto) {
		ManageAccount adto = this.getAccount(request);
		String isExist = customerManageService.isExistOrgCode(companyExtDto,true);
		String msg = "";
		if (isExist != "") {
			msg = isExist;
		} else {
			B2bCompany company=new B2bCompany();
			BeanUtils.copyProperties(companyExtDto, company);
			msg=customerManageService.insertB2bCompany(company, adto);
		}
		return msg;
	}

	/**
	 * 根据公司获取授信信息
	 * 
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getCreditByCompanyPk")
	public String getCreditByCompanyPk(HttpServletRequest request, HttpServletResponse response, String pk) {
		return customerManageService.getCreditByCompanyPk(pk);
	}

	/**
	 * 获取实体账户
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getSysCompanyBankCardByPk")
	public String getSysCompanyBankCardByPk(HttpServletRequest request, HttpServletResponse response) {
		String companyPk = ServletUtils.getStringParameter(request, "companyPk", "");
		String isCompanyType = ServletUtils.getStringParameter(request, "isCompanyType", "");
		String modelType = ServletUtils.getStringParameter(request, "modelType", "");

		ManageAccount account = this.getAccount(request);
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "pk");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		// 查询条件
		SysCompanyBankcardDtoEx dto = new SysCompanyBankcardDtoEx();
		dto.setCompanyPk(companyPk);
		dto.setIsCompanyType(isCompanyType);
		dto.setModelType(modelType);
		QueryModel<SysCompanyBankcardDtoEx> qm = new QueryModel<SysCompanyBankcardDtoEx>(dto, start, limit, orderName,
				orderType);
		PageModel<SysCompanyBankcardDtoEx> bankCardDto = customerManageService.getBankCard(qm,account);
		String json = null;
		if (bankCardDto != null) {
			json = JsonUtils.convertToString(bankCardDto);
		}
		return json;
	}

	/**
	 * 删除账户银行
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delSupplierBank")
	public String delSupplierBank(HttpServletRequest request, HttpServletResponse response) {
		String pk = ServletUtils.getStringParameter(request, "pk", "");
		String msg = "";
		int retVal =  customerManageService.delSupplierBank(pk);
		 if (retVal > 0) {
	            msg = Constants.RESULT_SUCCESS_MSG;
	        } else {
	            msg = Constants.RESULT_FAIL_MSG;
	        }
		return msg;
	}
	
	
	/**
	 * 获取开户行
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getSysBankNameCode")
	public String getSysBankNameCode(HttpServletRequest request, HttpServletResponse response,
			SysBankNamecodeExtDto dto, String creditBankName) {

		if (creditBankName != null && !"".equals(creditBankName)) {
			dto.setBankname(creditBankName);
		}
		List<SysBankNamecodeExtDto> dtoList = customerManageService.getSysBankNameCode(dto);
		String json = "";
		if (dtoList != null && dtoList.size() > 0) {
			json = JsonUtils.convertToString(dtoList);
		}
		return json;
	}

	/**
	 * 精确获取开户行信息
	 * 
	 * @param request
	 * @param response
	 * @param bankname
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getSysBankNameCodeByBankName")
	public String getSysBankNameCodeByBankName(HttpServletRequest request, HttpServletResponse response,
			String bankname) {
		String msg = Constants.RESULT_FAIL_MSG;
		if (bankname != null && !"".equals(bankname)) {
			SysBankNamecodeDto dto = customerManageService.getSysBankName(bankname);
			if (dto != null) {
				msg = "{\"success\":true,\"msg\":\"操作成功!\",\"bankno\":\"" + dto.getBankno() + "\",\"bankclscode\":\"" + dto.getBankclscode() + "\"}";
			} else {
				msg = Constants.RESULT_FAIL_MSG;
			}
		}
		return msg;
	}

	/**
	 * 同步银行信息到mongo
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getSysBankName")
	public String getSysBankName(HttpServletRequest request, HttpServletResponse response) {
		int resu = customerManageService.getAllSysBankNameCode();
		String msg = "";
		if (resu > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 修改实体账户
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateCompanyBankCard")
	public String updateCompanyBankCard(HttpServletRequest request, HttpServletResponse response,
			SysCompanyBankcardDto dto) {
		String msg = "";
		//公司和银行是否已存在
		Boolean flag = customerManageService.checkCompanyAndBank(dto);
		if (flag) {
			msg = "{\"success\":true,\"msg\":\"已存在该银行绑定记录!\",\"code\":\"0001\"}";
		}else{
			Map<String, Object> map = customerManageService.updateCompanyBankCard(dto);

			String pk = map.get("pk").toString();
			
			if (Integer.valueOf(map.get("result").toString()) > 0) {
				msg = "{\"success\":true,\"msg\":\"操作成功!\",\"pk\":\"" + pk + "\"}";
			} else {
				msg = "{\"success\":false,\"msg\":\"操作失败!\",\"pk\":\"" + pk + "\"}";
			}	
		}
		return msg;
	}

	/**
	 * 导出采购商或供应商
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exportB2bCompany")
	@ResponseBody
	public String exportPurchaserOrSupplier(HttpServletRequest request, HttpServletResponse response,
												  CustomerDataTypeParams params) throws Exception {

		ManageAccount account = this.getAccount(request);

//		int start = ServletUtils.getIntParameter(request, "start", 0);
//		int limit = ServletUtils.getIntParameter(request, "limit", 10);
//		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
//		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
//		QueryModel<B2bCompanyExtDto> qm = new QueryModel<B2bCompanyExtDto>(b2bCompayExtDto, start, limit, orderName,
//				orderType);
//
//		setModelTypeValue(b2bCompayExtDto);
//
//		List<B2bCompanyExtDto> list = customerManageService.exportB2bCompany(qm,account);
//		String name = "";
//		if (b2bCompayExtDto.getCompanyType() != null && b2bCompayExtDto.getCompanyType() == 2) {
//			name = "supplier";
//		} else {
//			name = "purchaser";
//		}
//		ExportUtil<B2bCompanyExtDto> export = new ExportUtil<B2bCompanyExtDto>();
//		export.exportUtil(name, list, response,request);
		String uuid = KeyUtils.getUUID();
		params.setUuid(uuid);
		customerManageService.saveCompanyExcelToOss(params,account);
		String name = Thread.currentThread().getName();//获取当前执行线程
		dynamicTask.startCron(new CompanyRunnable(name,uuid),name);
		return Constants.EXPORT_MSG;
	}

	/**
	 * 公司设置超级管理员列表查询
	 * 
	 * @param request
	 * @param pk
	 * @return
	 */
	@RequestMapping("getMemberListByAdmin")
	@ResponseBody
	public String getMemberListByAdmin(HttpServletRequest request, String pk,String mobile,Integer companyType,String modelType) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		ManageAccount account = this.getAccount(request);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "pk");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		B2bMemberExtDto dto = new B2bMemberExtDto();
		dto.setCompanyPk(pk);
		dto.setMobile(mobile);
		dto.setCompanyType(companyType);
		dto.setModelType(modelType);
		QueryModel<B2bMemberExtDto> qm = new QueryModel<B2bMemberExtDto>(dto, start, limit, orderName, orderType);
		PageModel<B2bMemberDto> pm = customerManageService.getAllMemberList(qm,account);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 设置超级管理员
	 * 
	 * @param request
	 * @param memberPk
	 * @return
	 */
	@RequestMapping("updateSetAdmin")
	@ResponseBody
	public String updateSetAdmin(HttpServletRequest request, String memberPk, String oldMemberPk, String companyPk) {
		int retVal = customerManageService.updateSetAdmin(memberPk, oldMemberPk, companyPk);
		String msg = "";
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
			 Map<String, String> smsMap = new HashMap<String, String>();
			 B2bMemberDto member = b2bMemberService.getMemberByPk(memberPk);
			 smsMap.put("product", member.getCompanyName());
			 //sysService.sendMessageContent(member.getMobile(),"setup_manager",smsMap);
			sysService.sendMSM(member,member.getMobile(),"setup_manager",smsMap);
		} else {
			msg = Constants.RESULT_SUCCESS_MSG;
		}
		return msg;
	}

	/**
	 * 会员页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("purchaserMembers")
	public ModelAndView purchaserMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/purchaserMembers");
		String modelType = ServletUtils.getStringParameter(request,"modelType");
		if (modelType.contains("?")) {// 数据库默认
			modelType = modelType.substring(0, modelType.indexOf("?"));
		}
		mav.addObject("modelType", modelType);
		// 查询公司
		mav.addObject("companyList", customerManageService.getB2bCompayDtoByType(1, null));
		mav.addObject("purchaserRoleList",customerManageService.getB2bRoleList(1));
		mav.addObject("supplierRoleList",customerManageService.getB2bRoleList(2));
		return mav;
	}

	/**
	 * 会员管理列表
	 * 
	 * @param request
	 * @param response
	 * @param b2bMember
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchMembersList")
	public String purchaserMembersData(HttpServletRequest request, HttpServletResponse response,
			B2bMemberExtDto b2bMember) {
	
		ManageAccount account = this.getAccount(request);
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String modelType = b2bMember.getModelType();
		if (modelType != null && modelType.contains("1")) {
			b2bMember.setModelType("1");
		}
		if (modelType != null && modelType.contains("2")) {
			b2bMember.setModelType("2");
		}
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
		QueryModel<B2bMemberExtDto> qm = new QueryModel<B2bMemberExtDto>(b2bMember, start, limit, orderName, orderType);
		PageModel<B2bMemberExtDto> pm = customerManageService.searchb2bMemberExtList(qm,account);
		String json = JsonUtils.convertToString(pm);
	
		return json;
	}

	/**
	 * 会员管理列表
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("exportMembersList")
	public String exportMembersList(HttpServletRequest request, HttpServletResponse response,
			CustomerDataTypeParams params) {
		ManageAccount account = this.getAccount(request);
//		int start = ServletUtils.getIntParameter(request, "start", 0);
//		int limit = ServletUtils.getIntParameter(request, "limit", 10);
//		String modelType = b2bMember.getModelType();
//		if (modelType != null && modelType.contains("1")) {
//			b2bMember.setModelType("1");
//		}
//		if (modelType != null && modelType.contains("2")) {
//			b2bMember.setModelType("2");
//		}
//		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
//		String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
//		QueryModel<B2bMemberExtDto> qm = new QueryModel<B2bMemberExtDto>(b2bMember, start, limit, orderName, orderType);
//		List<B2bMemberExtDto> list = customerManageService.exportB2bMemberExtList(qm,account);
//		ExportUtil<B2bMemberExtDto> export = new ExportUtil<B2bMemberExtDto>();
//		export.exportUtil("member", list, response,request);

		String uuid = KeyUtils.getUUID();
		params.setUuid(uuid);
		customerManageService.saveMemberExcelToOss(params,account);
		String name = Thread.currentThread().getName();//获取当前执行线程
		dynamicTask.startCron(new MemberRunnable(name,uuid),name);
		return Constants.EXPORT_MSG;
	}

	/**
	 * 删除会员
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delMember", method = RequestMethod.POST)
	public String delMember(HttpServletRequest req) {
		String pk;
		String result = Constants.RESULT_SUCCESS_MSG;
		try {
			pk = ServletUtils.getStringParameter(req, "pk");
			result=b2bMemberService.delMember(pk);
		} catch (Exception e) {
			result = Constants.RESULT_FAIL_MSG;
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 禁用/会员启用
	 * @param request
	 * @param b2bMember
	 * @param isAuditStatusTwo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateMember")
	public String updateMember(HttpServletRequest request, B2bMember b2bMember,String isAuditStatusTwo) {

		String result = Constants.RESULT_SUCCESS_MSG;
		ManageAccount adto = this.getAccount(request);

		try {
			result = b2bMemberService.updateMember(adto, b2bMember,isAuditStatusTwo);
		} catch (Exception e) {
			result = Constants.RESULT_FAIL_MSG;
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * 根据会员查询已绑定的角色
	 * 
	 * @param request
	 * @param memberPk
	 * @return
	 */
	@RequestMapping("getRoleListByMember")
	@ResponseBody
	public String getRoleListByMember(HttpServletRequest request, String memberPk) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "pk");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		B2bRoleDto dto = new B2bRoleDto();
		QueryModel<B2bRoleDto> qm = new QueryModel<B2bRoleDto>(dto, start, limit, orderName, orderType);
		PageModel<B2bRoleExtDto> pm = b2bMemberService.getAllRoleList(qm, memberPk);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 会员添加角色
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("insertMemberRole")
	@ResponseBody
	public String insertMemberRole(HttpServletRequest request) {
		String msg = Constants.RESULT_SUCCESS_MSG;
		String memberPk = request.getParameter("memberPk");
		String rolePks = request.getParameter("rolePks");
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			String[] rolePk = rolePks.split(",");
			if (!"".equals(rolePk)) {
				for (int j = 0; j < rolePk.length; j++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("rolePk", rolePk[j]);
					map.put("memberPk", memberPk);
					map.put("pk", KeyUtils.getUUID());
					list.add(map);
				}
			}
			int retVal = b2bMemberService.insertMemberRole(list);
			if (retVal <= 0) {
				msg = Constants.RESULT_FAIL_MSG;
			}
		} catch (Exception e) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	
	/**
	 * 获取会员角色
	 * @param request
	 * @return
	 */
	@RequestMapping("getRoleByMember")
	@ResponseBody
	public String getRoleByMember(HttpServletRequest request) {
		String memberPk = request.getParameter("memberPk");
		List<B2bRoleDto> list = b2bMemberService.getRoleByMember(memberPk);
		return JsonUtils.convertToString(list);
	}
	
	/**
	 * 更新发票信息
	 * @param request
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("editInvoiceDto")
	public String editInvoiceDto(HttpServletRequest request, B2bInvoiceEntity dto) {
		int result = customerManageService.updateCompanyInvoice(dto);
		String msg = "";
		if(result > 0){
			msg = Constants.RESULT_SUCCESS_MSG;
		}else{
			msg = Constants.RESULT_FAIL_MSG;
		}
		
		return msg;
	}
	
	/**
	 * 供应商账户页面跳转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("jumpSupplierBank")
	public ModelAndView jumpSupplierBank(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String companyPk = ServletUtils.getStringParameter(request, "companyPk","");
		String companyName = ServletUtils.getStringParameter(request, "companyName","");
		String isCompanyType = ServletUtils.getStringParameter(request, "isCompanyType","");
		String modelType = ServletUtils.getStringParameter(request, "modelType","");
		ModelAndView mav = null;
		if("1".equals(isCompanyType)) {
			mav = new ModelAndView("operation/supplierBank");
		}else{
			mav = new ModelAndView("operation/subSupplierBank");
		}
		mav.addObject("companyPk", companyPk);
		mav.addObject("companyName", companyName);
		mav.addObject("isCompanyType", isCompanyType);
		mav.addObject("modelType", modelType);
		mav.addObject("bankList", economicsBankService.searchBankList());
		return mav;
	}


	/**
	 * 查询总公司
	 * @param request
	 * @param parentPk
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getParentCompanyByParentPk")
	public String getParentCompanyByParentPk(HttpServletRequest request,String parentPk) {
		return JsonUtils.convertToString(customerManageService.getParentCompanyByParentPk(parentPk));
	}
	
}
