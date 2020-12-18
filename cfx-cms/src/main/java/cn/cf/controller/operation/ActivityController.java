package cn.cf.controller.operation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.dto.*;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.model.ManageAccount;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.operation.LotteryAwardRunnable;
import cn.cf.task.schedule.operation.MemberRunnable;
import cn.cf.util.KeyUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.ExportUtil;
import cn.cf.common.QueryModel;
import cn.cf.entity.LotteryActivityRule;
import cn.cf.json.JsonUtils;
import cn.cf.service.operation.ActivityService;
import cn.cf.service.operation.SysRegionsService;
import cn.cf.util.ServletUtils;

/**
 * 竞拍controller
 * @author why
 *
 */
@Controller
@RequestMapping("/")
public class ActivityController extends BaseController {

	@Autowired
	ActivityService activityService;

	@Autowired
	private SysRegionsService sysRegionsService;

	@Autowired
	private DynamicTask dynamicTask;

	// ====================================活动场次管理==========================================
	/**
	 * 活动场次管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("auctionScreenings")
	public ModelAndView auctionScreenings(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("auctionScreenings");
		return mav;
	}

	/**
	 * 删除活动规则
	 * 
	 * @param request
	 * @param pk
	 * @return
	 */
	@RequestMapping("delAuctionActivityRule")
	@ResponseBody
	public String delAuctionActivityRule(HttpServletRequest request, String pk) {
		int retVal = activityService.delAuctionActivityRule(pk);
		String msg = "";
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 抽奖活动页面
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("lotteryManage")
	public ModelAndView lotteryManage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mvn = new ModelAndView("operation/lottery/lotteryManager");
		return mvn;
	}

	/**
	 * 查询抽奖活动,返回pageModel
	 * 
	 * @param request
	 * @param response
	 * @param activityDto
	 * @return
	 */
	@RequestMapping("lotteryManageList")
	@ResponseBody
	public String lotteryManageList(HttpServletRequest request, HttpServletResponse response,
			B2bLotteryActivityExDto activityDto) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "DESC");
		QueryModel<B2bLotteryActivityExDto> qm = new QueryModel<B2bLotteryActivityExDto>(activityDto, start, limit,
				orderName, orderType);
		PageModel<B2bLotteryActivityExDto> pm = activityService.searchLotteryList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 获取所有抽奖活动
	 * 
	 * @param request
	 * @param response
	 * @param activityType
	 * @return
	 */
	@RequestMapping("getAllLotteryList")
	@ResponseBody
	public String getAllLotteryList(HttpServletRequest request, HttpServletResponse response, Integer activityType) {
		String json = "";
		List<B2bLotteryActivityExDto> pm = activityService.getAllLotteryActivity(activityType);
		if (pm != null) {
			json = JsonUtils.convertToString(pm);
		}
		return json;
	}

	/**
	 * 编辑新增活动场次
	 * 
	 * @param request
	 * @param activityExDto
	 * @return
	 */
	@RequestMapping("editLotteryActivity")
	@ResponseBody
	public String editLotteryActivity(HttpServletRequest request, HttpServletResponse response,
			B2bLotteryActivityExDto activityExDto) {
		int retVal = activityService.updateLottery(activityExDto);
		String msg = "";
		if (retVal > 0) {
			if (retVal == 2) {
				msg = "{\"success\":false,\"msg\":\"已存在此类型的活动!\"}";
			} else {
				msg = Constants.RESULT_SUCCESS_MSG;
			}

		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 删除活动来源规则
	 * 
	 * @param request
	 * @param pk
	 * @return
	 */
	@RequestMapping("delLotteryRule")
	@ResponseBody
	public String delLotteryRule(HttpServletRequest request, String pk) {
		int retVal = activityService.delLotteryRule(pk);
		String msg = "";
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 根据类型查询活动类型
	 * 
	 * @param request
	 * @param response
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("getLotteryActivityByType")
	public String getLotteryActivityByType(HttpServletRequest request, HttpServletResponse response, Integer type)
			throws Exception {
		B2bLotteryActivityDto activity = activityService.getLotteryByActivityType(type);
		String json = "{\"success\":false,\"msg\":\"未获取相应的活动！\"}";
		if (activity != null) {
			json = "{\"success\":true,\"msg\":\"操作成功！\",\"activityPk\":\"" + activity.getPk() + "\",\"name\":\"" + activity.getName() + "\"}";
		}
		return json;
	}


	/**
	 * 获取所有实物奖品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getAllLotteryMaterailList")
	@ResponseBody
	public String getAllLotteryMaterailList(HttpServletRequest request, HttpServletResponse response) {
		String json = "";
		List<B2bLotteryMaterialDto> pm = activityService.getAllLotteryMaterailList();
		if (pm != null) {
			json = JsonUtils.convertToString(pm);
		}
		return json;
	}


	@RequestMapping("getLotteryActivityByPk")
	@ResponseBody
	public String getLotteryActivityByPk(HttpServletRequest request, HttpServletResponse response, String pk) {
		String json = "";
		B2bLotteryActivityDto dto = activityService.getLotteryByPk(pk);
		if (dto != null) {
			json = JsonUtils.convertToString(dto);
		}
		return json;
	}

	/**
	 * 奖品设置页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lotteryAwardSetting")
	public ModelAndView lotteryAwardSetting(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mvn = new ModelAndView("operation/lottery/lotteryAwardSetting");
		// List<B2bLotterySourceDto> sourceList =
		// operationService.getAllLotterySource();
		// mvn.addObject("sourceList", sourceList);
		return mvn;
	}

	/**
	 * 中奖人名单页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lotteryAwardRoster")
	public ModelAndView lotteryAwardRoster(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mvn = new ModelAndView("operation/lottery/lotteryAwardRoster");
		List<SysRegionsDto> list = sysRegionsService.getSysregisByCityList("-1");
		mvn.addObject("province", list);
		return mvn;
	}

	/**
	 * 抽奖记录页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lotteryAwardHistory")
	public ModelAndView lotteryAwardHistory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mvn = new ModelAndView("operation/lottery/lotteryAwardHistory");
		List<SysRegionsDto> list = sysRegionsService.getSysregisByCityList("-1");
		mvn.addObject("province", list);
		return mvn;
	}

	/**
	 * 邀请管理页面
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("lotteryInvitationManage")
	public ModelAndView lotteryInvitationManage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mvn = new ModelAndView("operation/lottery/lotteryInvitationRecord");
		List<SysRegionsDto> list = sysRegionsService.getSysregisByCityList("-1");
		mvn.addObject("province", list);
		return mvn;
	}

	/**
	 * 邀请管理列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchLotteryInvReList")
	@ResponseBody
	public String searchLotteryInvReList(HttpServletRequest request, HttpServletResponse response,
			B2bInvitationRecordExtDto dto) throws Exception {

		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "DESC");
		QueryModel<B2bInvitationRecordExtDto> qm = new QueryModel<B2bInvitationRecordExtDto>(dto, start, limit,
				orderName, orderType);
		PageModel<B2bInvitationRecordExtDto> pm = activityService.searchLotteryInvitationRecordList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 查询好友公司
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getB2bCompayDto")
	@ResponseBody
	public String getB2bCompanyDto(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = "";
		List<B2bCompanyDto> list = activityService.getB2bCompayDto();
		if (list != null) {
			json = JsonUtils.convertToString(list);
		}
		return json;
	}

	/**
	 * 根据pk查询获奖记录
	 * 
	 * @param request
	 * @param response
	 * @param pk
	 * @param tcompanyName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getInvitationRecordBypk")
	@ResponseBody
	public String getInvitationRecordBypk(HttpServletRequest request, HttpServletResponse response, String pk,
			String tcompanyName) throws Exception {
		String json = "";

		B2bInvitationRecordExtDto extDto = activityService.getInvitationRecordByPk(pk, tcompanyName);
		if (extDto != null) {
			json = JsonUtils.convertToString(extDto);
		}
		return json;
	}

	/**
	 * 编辑邀请记录
	 * 
	 * @param request
	 * @param recordDto
	 * @param response
	 * @param recordDto
	 * @return
	 */
	@RequestMapping("updateInvitationRecord")
	@ResponseBody
	public String updateInvitationRecord(HttpServletRequest request, HttpServletResponse response,
			B2bInvitationRecordExtDto recordDto) {

		int retVal = activityService.updateInvitationRecord(recordDto);

		String msg = "";
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 奖品设置列表
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lotteryAwardSettingList")
	@ResponseBody
	public String lotteryAwardSettingList(HttpServletRequest request, HttpServletResponse response,
			B2bLotteryAwardExDto dto) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "DESC");
		QueryModel<B2bLotteryAwardExDto> qm = new QueryModel<B2bLotteryAwardExDto>(dto, start, limit, orderName,
				orderType);
		PageModel<B2bLotteryAwardExDto> pm = activityService.searchLotteryAwardList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 编辑/新增/删除 奖项
	 * 
	 * @param request
	 * @param response
	 * @param awardDto
	 * @return
	 */
	@RequestMapping("editLotteryAward")
	@ResponseBody
	public String editLotteryAward(HttpServletRequest request, HttpServletResponse response,
			B2bLotteryAwardExDto awardDto) {
		int retVal = activityService.editLotteryAward(awardDto);
		String msg = "";
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}


	/**
	 * 修改状态
	 *
	 * @param request
	 * @param response
	 * @param awardDto
	 * @return
	 */
	@RequestMapping("updateLotteryAwardStatus")
	@ResponseBody
	public String updateLotteryAwardStatus(HttpServletRequest request, HttpServletResponse response,
								   B2bLotteryAwardExDto awardDto) {
		int retVal = activityService.updateLotteryAward(awardDto);
		String msg = "";
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 根据Pk查询奖项
	 * 
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getLotteryAwardByPk")
	@ResponseBody
	public String getLotteryAwardByPk(HttpServletRequest request, HttpServletResponse response, String pk)
			throws Exception {
		String json = "";
		B2bLotteryAwardExDto pm = activityService.getLotteryAwardByPk(pk);
		if (pm != null) {
			json = JsonUtils.convertToString(pm);
		}
		return json;
	}

	/**
	 * 中奖名单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lotteryAwardRosterList")
	@ResponseBody
	public String lotteryAwardRosterList(HttpServletRequest request, HttpServletResponse response,
			B2bLotteryRecordExDto dto) throws Exception {
		ManageAccount account = this.getAccount(request);
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "DESC");
		QueryModel<B2bLotteryRecordExDto> qm = new QueryModel<B2bLotteryRecordExDto>(dto, start, limit, orderName,
				orderType);
		PageModel<B2bLotteryRecordExDto> pm = activityService.lotteryAwardRosterList(qm,account);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 根据pk查询获奖记录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getlotteryRecordBypk")
	@ResponseBody
	public String getlotteryRecordBypk(HttpServletRequest request, HttpServletResponse response, String pk)
			throws Exception {
		String json = "";
		B2bLotteryRecordExDto pm = activityService.getLotteryRecordByPk(pk);
		if (pm != null) {
			json = JsonUtils.convertToString(pm);
		}
		return json;
	}

	/**
	 * 编辑抽奖记录
	 * 
	 * @param request
	 * @param response
	 * @param recordDto
	 * @return
	 */
	@RequestMapping("editLotteryRecord")
	@ResponseBody
	public String editLotteryRecord(HttpServletRequest request, HttpServletResponse response,
			B2bLotteryRecordExDto recordDto) {
		int retVal = activityService.editLotteryRecord(recordDto);
		String msg = "";
		if (retVal > 0) {
			msg = "{\"success\":true,\"msg\":\"发放成功!\"}";
		} else {
			msg = "{\"success\":false,\"msg\":\"发放失败!\"}";
		}
		return msg;
	}

	/**
	 * 获奖名单-导出
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "exportAwardRoster")
	@ResponseBody
	public String exportAwardRoster(HttpServletRequest request, HttpServletResponse response,
									CustomerDataTypeParams params) throws Exception {
		ManageAccount account = this.getAccount(request);
//		int start = ServletUtils.getIntParameter(request, "start", 0);
//		int limit = ServletUtils.getIntParameter(request, "limit", 10);
//		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
//		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
//		QueryModel<B2bLotteryRecordExDto> qm = new QueryModel<B2bLotteryRecordExDto>(recordExDto, start, limit,
//				orderName, orderType);
		//List<B2bLotteryRecordExDto> list = activityService.searchExportLotteryAwardList(qm,account);
//		ExportUtil<B2bLotteryRecordExDto> export = new ExportUtil<B2bLotteryRecordExDto>();
//		export.exportUtil("lotteryRecord", list, response, request);


		String uuid = KeyUtils.getUUID();
		params.setUuid(uuid);
		String name = Thread.currentThread().getName();//获取当前执行线程
		activityService.saveLotteryAwardToOss(params,account);
		dynamicTask.startCron(new LotteryAwardRunnable(name,uuid),name);
		return Constants.EXPORT_MSG;
	}

	/**
	 * 获奖名单导入 （ 导入中奖记录奖品发放方式和状态）
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "importCouponMember")
	@ResponseBody
	public String importCouponMember(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "filename") MultipartFile file) throws Exception {
		int result = 0;
		String msg = "";
		InputStream is = file.getInputStream();
		// String name = file.getOriginalFilename();
		if (!(file.getSize() > 0)) {
			msg = "导入的文件为空!";
			return JsonUtils.convertToString(msg);
		}
		// 得到excel
		Workbook workbook = Workbook.getWorkbook(is);
		// 得到sheet
		Sheet sheet = workbook.getSheet(0);
		// 得到列数
		int colsNum = sheet.getColumns();
		List<Map<Integer, String>> list = getCell(workbook);
		if (list != null && list.size() > 0) {
			for (Map<Integer, String> map : list) {
				// 获取一列数要修改的数据
				B2bLotteryRecordExDto recordDto = new B2bLotteryRecordExDto();
				for (Map.Entry<Integer, String> entry : map.entrySet()) {
					recordDto = updateRecord(entry, colsNum, recordDto);
				}
				if (recordDto != null && (recordDto.getAwardStatus() != null || recordDto.getGrantType() != null
						|| recordDto.getNote() != null)) {

					try {
						result = activityService.importUpdateLotteryRecord(recordDto);
					} catch (Exception e) {
						e.printStackTrace();
						result = 0;
						break;
					}
				}
			}
		}
		if (result > 0) {
			msg = "导入成功!";
		} else {
			msg = "导入失败或导入内容没有修改!";
		}
		return msg;
	}

	// 获取Excel中的所有数据
	private List<Map<Integer, String>> getCell(Workbook workbook) {
		List<Map<Integer, String>> listCell = new ArrayList<Map<Integer, String>>();
		// 得到sheet
		Sheet sheet = workbook.getSheet(0);
		// 得到列数
		int colsNum = sheet.getColumns();
		// 得到行数
		int rowsNum = sheet.getRows();
		Cell cell;
		rowsNumbers: for (int i = 3; i < rowsNum; i++) {// 读取excel内容，从第四行开始,所以
														// i从3开始
			Map<Integer, String> map = new HashMap<Integer, String>();
			for (int j = 0; j < colsNum; j++) {
				cell = sheet.getCell(j, i);
				// 如果循环到某一行第一列没有值，说明已是最后一行。
				if (j == 0 && (cell.getContents() == null || "".equals(cell.getContents()))) {
					break rowsNumbers;
				}
				map.put(j, cell.getContents());
			}
			listCell.add(map);
		}
		return listCell;
	}

	// 逐行修改抽奖记录状态
	private B2bLotteryRecordExDto updateRecord(Map.Entry<Integer, String> entry, int colsNum,
			B2bLotteryRecordExDto recordDto) {
		Integer n = entry.getKey();
		if (n != null && n == 0) {
			recordDto.setPk(entry.getValue());
		}
		if (n != null && n == (colsNum - 1)) {
			String grantType = entry.getValue();
			if (grantType != null && !"".equals(grantType)) {
				String[] strArr = grantType.split("\\.");
				recordDto.setGrantType(Integer.valueOf(strArr[0]));
			}
		}
		if (n != null && n == (colsNum - 2)) {
			String awardStatus = entry.getValue();
			if (awardStatus != null && !"".equals(awardStatus)) {
				String[] strArr = awardStatus.split("\\.");
				recordDto.setAwardStatus(Integer.valueOf(strArr[0]));
			}
		}
		return recordDto;
	}


	/**
	 * 优惠券基本设置页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lotteryMaterialBaseSettings")
	public ModelAndView lotteryMaterialBaseSettings(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ModelAndView mvn = new ModelAndView("operation/lottery/lotteryMaterialSetting");

		return mvn;
	}

	/**
	 * 实物奖品列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchlotteryMaterialList")
	@ResponseBody
	public String lotteryMaterialList(HttpServletRequest request, HttpServletResponse response,
			B2bLotteryMaterialDto dto) throws Exception {

		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "DESC");
		QueryModel<B2bLotteryMaterialDto> qm = new QueryModel<B2bLotteryMaterialDto>(dto, start, limit, orderName,
				orderType);
		PageModel<B2bLotteryMaterialDto> pm = activityService.searchLotteryMaterialList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 编辑实物表
	 * 
	 * @param request
	 * @param response
	 * @param couponDto
	 * @return
	 */
	@RequestMapping("updateLotteryMaterial")
	@ResponseBody
	public String updateLotteryMaterial(HttpServletRequest request, HttpServletResponse response,
			B2bLotteryMaterialDto couponDto) {

		int retVal = activityService.updateLotteryMaterial(couponDto);

		String msg = "";
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if(retVal == -1) {
			msg = "{\"success\":false,\"msg\":\"存在相同的实物名称!\"}";
		}else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 优惠券使用规则页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lotteryCouponUseRule")
	public ModelAndView lotteryCouponUseRule(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ModelAndView mvn = new ModelAndView("operation/lottery/lotteryCouponUseRule");
		LotteryActivityRule rule = activityService.searchLotteryCouponUseRule(Constants.ONE);
		if (rule != null) {
			mvn.addObject("couponUseRule", rule);
		}
		return mvn;
	}

	/**
	 * 编辑优惠券使用规则页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateLotteryCouponUseRule")
	@ResponseBody
	public String editLotteryCouponUseRule(HttpServletRequest request, HttpServletResponse response,
			LotteryActivityRule rule) throws Exception {
		String msg = activityService.updateLotteryCouponUseRule(rule);

		return msg;
	}

	/**
	 * 优惠券基本设置页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lotteryCouponBaseSettings")
	public ModelAndView lotteryCouponBaseSettings(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ModelAndView mvn = new ModelAndView("operation/lottery/lotteryCouponSetting");
		return mvn;
	}

	/**
	 * 优惠券基本设置页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lotteryCouponMemberList")
	public ModelAndView lotteryCouponMemberList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mvn = new ModelAndView("operation/lottery/lotteryCouponMember");
		return mvn;
	}
	
	/**
	 * 导出邀请记录
	 * @param request
	 * @param response
	 * @param invitationRecordDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "exportInvitationRecord")
	@ResponseBody
	public ModelAndView exportInvitationRecord(HttpServletRequest request,
			HttpServletResponse response, B2bInvitationRecordExtDto invitationRecordDto)
			throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request,
				"orderName", "");
		String orderType = ServletUtils.getStringParameter(request,
				"orderType", "");
		QueryModel<B2bInvitationRecordExtDto> qm = new QueryModel<B2bInvitationRecordExtDto>(invitationRecordDto,
				start, limit, orderName, orderType);

		List<B2bInvitationRecordExtDto> list = activityService.exportLotteryInvitationRecord(qm);
		ExportUtil<B2bInvitationRecordExtDto> export = new ExportUtil<B2bInvitationRecordExtDto>();
		export.exportUtil("lotteryInvitationRecord", list, response,request);
		return null;
	}
	
	
	/**
	 * 邀请记录奖品发放方式和状态导入
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "importInvitationRecord")
	@ResponseBody
	public String importInvitationRecord(HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value="filename") MultipartFile file)
			throws Exception {

         return activityService.importLotteryInvitationRecord(file);
	}
	
	
}
