package cn.cf.controller.operation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.cf.common.ExportUtil;
import cn.cf.common.QueryModel;
import cn.cf.dao.B2bDimensionalityExDao;
import cn.cf.dao.B2bDimensionalityRelevancyExtDao;
import cn.cf.dto.B2bDimensionalityDto;
import cn.cf.dto.B2bDimensionalityExtrewardExtDto;
import cn.cf.dto.B2bDimensionalityPresentExDto;
import cn.cf.dto.B2bDimensionalityRelevancyDto;
import cn.cf.dto.B2bDimensionalityRelevancyExtDto;
import cn.cf.dto.B2bDimensionalityRewardExtDto;
import cn.cf.dto.B2bMemberGradeExtDto;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.service.operation.MemberSysService;
import cn.cf.service.operation.OperationManageService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.operation.DimensionalityExtPresentRunnable;
import cn.cf.task.schedule.operation.DimensionalityHistoryRunnable;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;

/**
 * 会员体系controller
 * 
 * @author why
 *
 */
@Controller
@RequestMapping("/")
public class MemberSysController extends BaseController {

	@Autowired
	private MemberSysService memberSysService;

	@Autowired
	private B2bDimensionalityExDao bB2bDimensionalityDao;

	@Autowired
	private B2bDimensionalityRelevancyExtDao b2bDimensionalityRelevancyDao;
	
	@Autowired
	private OperationManageService operationManageService;
	
	@Autowired
	private DynamicTask dynamicTask;

	/**
	 * 维度页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dimensionalityManage")
	public ModelAndView dimensionalityManage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("operation/dimension/dimensionalityManage");
		return mav;
	}

	/**
	 * 维度类型页面
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dimensionalityRelevancy")
	public ModelAndView dimensionalityRelevancy(HttpServletRequest request, HttpServletResponse response,
			B2bDimensionalityDto dto) throws Exception {
		ModelAndView mav = new ModelAndView("operation/dimension/dimensionalityRelevancy");
		mav.addObject("dimenDto", dto);
		return mav;
	}

	/**
	 * 奖励管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dimensionalityRewardManage")
	public ModelAndView dimensionalityRewardManage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("operation/dimension/dimensionalityReward");

		mav.addObject("allDimenDtoList", memberSysService.getAllDimensionalityList());
		return mav;
	}

	/**
	 * 赠送列表页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dimensionalityPresent")
	public ModelAndView dimensionalityPresent(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("operation/dimension/dimensionalityPresent");

		String dimenCategory = ServletUtils.getStringParameter(request, "dimenCategory");
		String dimenType = ServletUtils.getStringParameter(request, "dimenType");
		String type = ServletUtils.getStringParameter(request, "type");

		mav.addObject("allDimenDtoList", memberSysService.getAllDimensionalityList());
		B2bDimensionalityDto b2bDimensionalityDto = bB2bDimensionalityDao.getByType(dimenCategory);

		Map<String, String> map = new HashMap<String, String>();
		map.put("dimenCategory", dimenCategory);
		map.put("dimenType", dimenType);
		B2bDimensionalityRelevancyDto b2bDimensionalityRelevancyDto = b2bDimensionalityRelevancyDao
				.getCategoryNamePk(map);
		if ("1".equals(type)) {
			mav.addObject("title", "奖励管理");
		}
		if ("2".equals(type)) {
			mav.addObject("title", "额外奖励管理");
		}

		mav.addObject("type", type);
		mav.addObject("dimenCategory", dimenCategory);
		mav.addObject("dimenCategoryName", b2bDimensionalityDto.getName());
		mav.addObject("dimenType", dimenType);
		mav.addObject("dimenTypeName", b2bDimensionalityRelevancyDto.getDimenTypeName());
		return mav;
	}

	/**
	 * 额外奖励管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dimensionalityExtRewardManage")
	public ModelAndView dimensionalityExtRewardManage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("operation/dimension/dimensionalityExtreward");
		String dimenCategory = ServletUtils.getStringParameter(request, "dimenCategory");
		String dimenType = ServletUtils.getStringParameter(request, "dimenType");
		mav.addObject("allDimenDtoList", memberSysService.getAllDimensionalityList());
		mav.addObject("dimenCategory", dimenCategory);
		mav.addObject("dimenType", dimenType);
		return mav;
	}

	/**
	 * 额外奖励管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("memberGrade")
	public ModelAndView memberGrade(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/dimension/memberGrade");
		return mav;
	}

	/**
	 * 奖励赠送记录页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("operationGiveHistory")
	public ModelAndView operationGiveHistory(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("operation/dimension/dimensionalityExtHistory");
		mav.addObject("allDimenDtoList", memberSysService.getAllDimensionalityList());
		return mav;
	}

	/**
	 * 额外奖励赠送记录页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("operationExtGive")
	public ModelAndView operationExtGive(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/dimension/dimensionalityExtGive");
		mav.addObject("allDimenDtoList", memberSysService.getAllDimensionalityList());
		return mav;
	}

	/**
	 * 查询会员维度
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchDimensionalityList")
	@ResponseBody
	public String searchDimensionalityList(HttpServletRequest request, HttpServletResponse response,
			B2bDimensionalityDto dto) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String sort = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String dir = ServletUtils.getStringParameter(request, "orderType", "DESC");
		QueryModel<B2bDimensionalityDto> qm = new QueryModel<B2bDimensionalityDto>(dto, start, limit, sort, dir);
		PageModel<B2bDimensionalityDto> pm = memberSysService.searchDimensionalityList(qm);
		return JsonUtils.convertToString(pm);
	}

	@RequestMapping("updateDimensionality")
	@ResponseBody
	public String updateDimensionality(HttpServletRequest request, HttpServletResponse response,
			B2bDimensionalityDto dto) throws Exception {
		String msg = "";
		int result = memberSysService.updateDimensionality(dto);
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			msg = Constants.ISEXIST_NAME_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}

		return msg;
	}

	@RequestMapping("geteByDimensionalityPk")
	@ResponseBody
	public String geteByDimensionalityPk(HttpServletRequest request, HttpServletResponse response, String pk)
			throws Exception {
		String json = "";
		B2bDimensionalityDto dto = memberSysService.geteByDimensionalityPk(pk);
		if (dto != null) {
			json = JsonUtils.convertToString(dto);
		}
		return json;
	}

	/**
	 * 查询会员维度
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchDimensionalityRelevancyList")
	@ResponseBody
	public String searchDimensionalityRelevancyList(HttpServletRequest request, HttpServletResponse response,
			B2bDimensionalityRelevancyExtDto dto) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String sort = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String dir = ServletUtils.getStringParameter(request, "orderType", "DESC");
		QueryModel<B2bDimensionalityRelevancyExtDto> qm = new QueryModel<B2bDimensionalityRelevancyExtDto>(dto, start,
				limit, sort, dir);
		PageModel<B2bDimensionalityRelevancyExtDto> pm = memberSysService.searchDimensionalityRelevancyList(qm);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 根据维度查询对应的类型
	 * 
	 * @param request
	 * @param response
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getDimenReListByCategory")
	@ResponseBody
	public String getDimenReListByCategory(HttpServletRequest request, HttpServletResponse response, Integer type)
			throws Exception {
		String json = "";
		List<B2bDimensionalityRelevancyDto> list = memberSysService.getDimenReByCategory(type);
		if (list != null && list.size() > 0) {
			json = JsonUtils.convertToString(list);
		}
		return json;
	}

	/**
	 * 修改会员维度
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateDimensionalityRelevancy")
	@ResponseBody
	public String updateDimensionalityRelevancy(HttpServletRequest request, HttpServletResponse response,
			B2bDimensionalityRelevancyExtDto dto) throws Exception {
		int result = memberSysService.updateDimensionalityRelevancy(dto);
		String json = "";
		if (result == 1) {
			json = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			json = Constants.ISEXIST_NAME_DIMENTYPE_MSG;
		} else {
			json = Constants.RESULT_FAIL_MSG;
		}

		return json;
	}

	/**
	 * 奖励管理列表查询
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchDimensionalityRewardList")
	@ResponseBody
	public String searchDimensionalityRewardList(HttpServletRequest request, HttpServletResponse response,
			B2bDimensionalityRewardExtDto dto) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String sort = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String dir = ServletUtils.getStringParameter(request, "orderType", "DESC");
		QueryModel<B2bDimensionalityRewardExtDto> qm = new QueryModel<B2bDimensionalityRewardExtDto>(dto, start, limit,
				sort, dir);
		PageModel<B2bDimensionalityRewardExtDto> pm = memberSysService.searchDimensionalityRewardList(qm);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 修改奖励
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateDimensionalityReward")
	@ResponseBody
	public String updateDimensionalityReward(HttpServletRequest request, HttpServletResponse response,
			B2bDimensionalityRewardExtDto dto) throws Exception {
		if (null != dto.getPeriodType1()) {
			dto.setPeriodType(Integer.valueOf(dto.getPeriodType1()));
		}

		dto.setUpdateTime(new Date());
		int result = memberSysService.updateDimensionalityReward(dto);
		String json = "";
		if (result == 1) {
			json = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			json = Constants.ISEXIST_NAME_REWARD_MSG;
		} else {
			json = Constants.RESULT_FAIL_MSG;
		}
		return json;
	}

	/**
	 * 删除奖励
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delDimensionalityReward")
	@ResponseBody
	public String delDimensionalityReward(HttpServletRequest request, HttpServletResponse response,
			B2bDimensionalityRewardExtDto dto) throws Exception {

		memberSysService.delDimensionalityReward(dto);

		String json = Constants.RESULT_SUCCESS_MSG;

		return json;
	}

	/**
	 * 会员维度赠送列表查询
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchDimensionalityPresentList")
	@ResponseBody
	public String searchDimensionalityPresentList(HttpServletRequest request, HttpServletResponse response,
			B2bDimensionalityPresentExDto dto) throws Exception {
		ManageAccount account  = this.getAccount(request);
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String sort = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String dir = ServletUtils.getStringParameter(request, "orderType", "DESC");
		QueryModel<B2bDimensionalityPresentExDto> qm = new QueryModel<B2bDimensionalityPresentExDto>(dto, start, limit,
				sort, dir);
		PageModel<B2bDimensionalityPresentExDto> pm = memberSysService.searchDimensionalityPresentList(qm,account);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 额外赠送列表查询
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchDimensionalityExtPresentList")
	@ResponseBody
	public String searchDimensionalityExtPresentList(HttpServletRequest request, HttpServletResponse response,
			B2bDimensionalityPresentExDto dto) throws Exception {
		ManageAccount account  = this.getAccount(request);
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String sort = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String dir = ServletUtils.getStringParameter(request, "orderType", "DESC");
		QueryModel<B2bDimensionalityPresentExDto> qm = new QueryModel<B2bDimensionalityPresentExDto>(dto, start, limit,
				sort, dir);
		PageModel<B2bDimensionalityPresentExDto> pm = memberSysService.searchDimensionalityExtPresentList(qm,account);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 导出额外维度赠送列表记录
	 * 
	 * @param request
	 * @param response
	 * @param pks
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exportDimensionalityExtPresent")
	@ResponseBody
	public String exportDimensionalityExtPresent(HttpServletRequest request, HttpServletResponse response,
			CustomerDataTypeParams params) throws Exception {
//		ManageAccount account = this.getAccount(request);
//		ExportUtil<B2bDimensionalityPresentExDto> export = new ExportUtil<B2bDimensionalityPresentExDto>();
//		List<B2bDimensionalityPresentExDto> list = memberSysService.exportDimensionalityExtPresentList(pks,account);
//
//		export.exportUtil("dimensionalityPresentExtGive", list, response, request);
//
//		return null;
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
	    operationManageService.saveCustomerExcelToOss(params, account, "exportDimensionalityExtPresent_"+uuid, "运营中心-会员体系-额外奖励赠送",6);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new DimensionalityExtPresentRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;  
	}

	/**
	 * 导出维度赠送列表查询
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exportDimensionalityPresentList")
	public ModelAndView exportDimensionalityPresentList(HttpServletRequest request, HttpServletResponse response,
			B2bDimensionalityPresentExDto dto) throws Exception {

		String sort = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String dir = ServletUtils.getStringParameter(request, "orderType", "DESC");
		QueryModel<B2bDimensionalityPresentExDto> qm = new QueryModel<B2bDimensionalityPresentExDto>(dto, 0, 0, sort,
				dir);

		ExportUtil<B2bDimensionalityPresentExDto> export = new ExportUtil<B2bDimensionalityPresentExDto>();
		List<B2bDimensionalityPresentExDto> lst = memberSysService.searchDimensionalityPresentExList(qm);

		export.exportUtil("dimensionalityPresent", lst, response, request);

		return null;
	}

	/**
	 * 导出维度赠送列表记录
	 * 
	 * @param request
	 * @param response
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exportDimensionalityHistoryList")
	@ResponseBody
	public String exportDimensionalityHistoryList(HttpServletRequest request, HttpServletResponse response,
			CustomerDataTypeParams params) throws Exception {
//		String[] pks = json.split(",");
//		ManageAccount account = this.getAccount(request);
//		ExportUtil<B2bDimensionalityPresentExDto> export = new ExportUtil<B2bDimensionalityPresentExDto>();
//		List<B2bDimensionalityPresentExDto> list = memberSysService.exportDimensionalityHistoryList(pks,account);
//		export.exportUtil("dimensionalityPresentHistory", list, response, request);
//		return null;
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
	    operationManageService.saveCustomerExcelToOss(params, account, "exportDimensionalityHistoryList_"+uuid, "运营中心-会员体系-奖励赠送记录",5);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new DimensionalityHistoryRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;  
	}

	/**
	 * 额外奖励管理列表查询
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchDimenExtrewardList")
	@ResponseBody
	public String searchDimenExtrewardList(HttpServletRequest request, HttpServletResponse response,
			B2bDimensionalityExtrewardExtDto dto) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String sort = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String dir = ServletUtils.getStringParameter(request, "orderType", "DESC");
		QueryModel<B2bDimensionalityExtrewardExtDto> qm = new QueryModel<B2bDimensionalityExtrewardExtDto>(dto, start,
				limit, sort, dir);
		PageModel<B2bDimensionalityExtrewardExtDto> pm = memberSysService.searchDimenExtrewardList(qm);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 修改额外奖励
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateDimenExtreward")
	@ResponseBody
	public String updateDimenExtreward(HttpServletRequest request, HttpServletResponse response,
			B2bDimensionalityExtrewardExtDto dto) throws Exception {
		if (null != dto.getPeriodType1()) {
			dto.setPeriodType(Integer.valueOf(dto.getPeriodType1()));
		}

		dto.setUpdateTime(new Date());
		int result = memberSysService.updateDimenExtreward(dto);
		String json = "";
		if (result == 1) {
			json = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			json = Constants.ISEXIST_NAME_EXTREWARD_MSG;
		} else {
			json = Constants.RESULT_FAIL_MSG;
		}
		return json;
	}

	/**
	 * 删除额外奖励
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delDimenExtreward")
	@ResponseBody
	public String delDimenExtreward(HttpServletRequest request, HttpServletResponse response,
			B2bDimensionalityExtrewardExtDto dto) throws Exception {

		memberSysService.delDimenExtreward(dto);
		String json = "";

		json = Constants.RESULT_SUCCESS_MSG;

		return json;
	}

	/**
	 * 奖励管理列表查询
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchMemberGradeList")
	@ResponseBody
	public String searchMemberGradeList(HttpServletRequest request, HttpServletResponse response,
			B2bMemberGradeExtDto dto) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String sort = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String dir = ServletUtils.getStringParameter(request, "orderType", "DESC");
		QueryModel<B2bMemberGradeExtDto> qm = new QueryModel<B2bMemberGradeExtDto>(dto, start, limit, sort, dir);
		PageModel<B2bMemberGradeExtDto> pm = memberSysService.searchMemberGradeList(qm);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 修改额外奖励
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateMemberGrade")
	@ResponseBody
	public String updateMemberGrade(HttpServletRequest request, HttpServletResponse response, B2bMemberGradeExtDto dto)
			throws Exception {
		int result = memberSysService.updateMemberGrade(dto);
		String json = "";
		if (result == 1) {
			json = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			json = Constants.ISEXIST_NAME_MEMBERGRADE_MSG;
		} else {
			json = Constants.RESULT_FAIL_MSG;
		}
		return json;
	}
}
