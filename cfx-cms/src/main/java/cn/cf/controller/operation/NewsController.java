package cn.cf.controller.operation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.apache.bcel.generic.IINC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dto.SysCategoryExtDto;
import cn.cf.dto.SysNewsDto;
import cn.cf.dto.SysNewsExtDto;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.entity.SysNewsStorageEntity;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.model.SysNews;
import cn.cf.service.operation.NewsService;
import cn.cf.service.operation.OperationManageService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.marketing.SysNewsStorageRunnable;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;

/**
 * 文章列表
 * 
 * @author why
 *
 */
@Controller
@RequestMapping("/")
public class NewsController extends BaseController {

	@Autowired
	private NewsService newsService;
	
	
	@Autowired
	private OperationManageService operationManageService;
	
	@Autowired
	private DynamicTask dynamicTask;

	/**
	 * 新增文章
	 * 
	 * @param request
	 * @param response
	 * @param news
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addnews")
	public ModelAndView addnews(HttpServletRequest request, HttpServletResponse response, SysNewsDto news)
			throws Exception {
		ModelAndView mav = new ModelAndView("operation/addnews");
		String pk = news.getPk();
		SysNewsExtDto sysNewsExtDto = newsService.getSysNewsByPk(pk);
		List<SysCategoryExtDto> categorys = newsService.getCategorys(pk);
		mav.addObject("categorys", categorys);
		mav.addObject("news", sysNewsExtDto);
		mav.addObject("sysCategory", categorys);//通过查询出的数据得到下拉框，判断条件pId和PName判断。

		return mav;
	}

	/**
	 * 修改文章内容
	 * 
	 * @param sysNews
	 * @param categoryPk
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateSysNews")
	public String updateSysNews(SysNews sysNews, String categoryPk,String block) {

		int result = newsService.updateSysNews(sysNews, categoryPk);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result <= 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;

	}

	/**
	 * 修改文章状态
	 *
	 * @param sysNews
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateSysNewsStatus")
	public String updateSysNewsStatus(SysNews sysNews) {

		int result = newsService.updateSysNewsStatus(sysNews);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result <= 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;

	}

	/**
	 * 推送资讯到APP，同时修改资讯推送状态
	 *
	 * @param sysNews
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updatePushStatus")
	public String updatePushStatus(SysNews sysNews) {

		int result = newsService.updateNewsPushStatus(sysNews);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result <= 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 查询文章列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("searchSysNewsStorageEntity")
	@ResponseBody
	public String searchSysNewsStorageEntity(HttpServletRequest request, HttpServletResponse response,
			SysNewsStorageEntity info) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<SysNewsStorageEntity> qm = new QueryModel<SysNewsStorageEntity>(info, start, limit, orderName,
				orderType);
		PageModel<SysNewsStorageEntity> pm = newsService.searchSysNewsStorageList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 导出文章列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("exportSysNewsStorageEntity")
	@ResponseBody
	public String exportSysNewsStorageEntity(HttpServletRequest request, HttpServletResponse response,
			CustomerDataTypeParams params) throws Exception {
//		List<ExportSysNewsStorageEntity> list = newsService.searchExportSysNewsStorageList(info);
//		ExportUtil<ExportSysNewsStorageEntity> export = new ExportUtil<>();
//		export.exportUtil("sysNews", list, response, request);
//		return null;
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
	    operationManageService.saveCustomerExcelToOss(params, account, "exportSysNewsStorageEntity_"+uuid, "运营中心-资讯中心-文章列表",7);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new SysNewsStorageRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;  
	}
}
