package cn.cf.controller.operation;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.entity.GoodsDataTypeParams;
import cn.cf.model.*;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.chemifiber.PriceTrendCfHistoryRunnable;
import cn.cf.task.schedule.chemifiber.PriceTrendSxHistoryRunnable;
import cn.cf.task.schedule.chemifiber.PriceTrendRunnable;
import cn.cf.util.KeyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bFriendLinkDto;
import cn.cf.dto.B2bFriendLinkExtDto;
import cn.cf.dto.B2bGoodsExtDto;
import cn.cf.dto.B2bKeywordHotDto;
import cn.cf.dto.B2bKeywordHotExtDto;
import cn.cf.dto.B2bPriceMovementExtDto;
import cn.cf.dto.B2bSpecDto;
import cn.cf.dto.SxHomeSecondnavigationExtDto;
import cn.cf.dto.SxHomeThirdnavigationExtDto;
import cn.cf.dto.SysHomeBannerMassageDto;
import cn.cf.dto.SysHomeBannerMassageExtDto;
import cn.cf.dto.SysHomeBannerProductnameDto;
import cn.cf.dto.SysHomeBannerProductnameExtDto;
import cn.cf.dto.SysHomeBannerSeriesDto;
import cn.cf.dto.SysHomeBannerSeriesExtDto;
import cn.cf.dto.SysHomeBannerSpecDto;
import cn.cf.dto.SysHomeBannerSpecExtDto;
import cn.cf.dto.SysHomeBannerVarietiesDto;
import cn.cf.dto.SysHomeBannerVarietiesExtDto;
import cn.cf.entity.B2bPriceMovementEntity;
import cn.cf.entity.SxPriceMovementEntity;
import cn.cf.json.JsonUtils;
import cn.cf.service.operation.GoodsManageService;
import cn.cf.service.operation.HomeDisplayService;
import cn.cf.util.ServletUtils;

/**
 * 首页显示管理
 * 
 * @author why
 *
 */
@Controller
@RequestMapping("/")
public class HomeDisplayController extends BaseController {

	@Autowired
	HomeDisplayService homeDisplayService;

	@Autowired
	private GoodsManageService goodsManageService;

	@Autowired
	private DynamicTask dynamicTask;

	/**
	 * 首页品名管理显示页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("homePageProductNameBannerManage")
	public ModelAndView homePageProductNameBannerManage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvn = new ModelAndView("operation/opermg/homePageProductNameBanner");
		mvn.addObject("homePageProductList", homeDisplayService.getAllProductList());
		mvn.addObject("homePageProductNameList", homeDisplayService.getB2bProductList());// 品名
		return mvn;
	}

	/**
	 * 查询首页品名管理列表
	 * 
	 * @param request
	 * @param response
	 * @param productnameDto
	 * @return
	 */
	@RequestMapping("searchHomePageProductNameBannerList")
	@ResponseBody
	public String getHomePageProductNameBannerList(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerProductnameExtDto productnameDto) {

		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "");
		QueryModel<SysHomeBannerProductnameExtDto> qm = new QueryModel<SysHomeBannerProductnameExtDto>(productnameDto,
				start, limit, orderName, orderType);
		PageModel<SysHomeBannerProductnameDto> pm = homeDisplayService.searchHomeBannerProductnameList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;

	}

	/**
	 * 修改首页显示商品名称
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateHomePageProductNameBanner")
	@ResponseBody
	public String updateHomePageProductNameBanner(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerProductnameExtDto dto) {

		int result = homeDisplayService.updateHomeBannerProductname(dto);
		String msg = "";
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			msg = "{\"success\":false,\"msg\":\"此品名已经存在或内容没有修改!\"}";
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 增加首页显示商品名称
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("insertHomePageProductNameBanner")
	@ResponseBody
	public String insertHomePageProductNameBanner(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerProductname dto) {

		int result = homeDisplayService.insertHomeBannerProductname(dto);
		String msg = "";
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			msg = "{\"success\":false,\"msg\":\"此品名已经存在!\"}";
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 更新为是否可用商品
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateIsVisAndDelHomePgProNameBanner")
	@ResponseBody
	public String updateIsVisAndDelHomePgProNameBanner(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerProductnameExtDto dto) {

		int result = homeDisplayService.updateIsVisAndDelHomePgProNameBanner(dto);
		String msg = "";
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 排序更新
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateHomePageProductNameBannerSort")
	@ResponseBody
	public String updateHomePageProductNameBannerSort(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerProductnameDto dto) {

		int result = homeDisplayService.updateHomeBannerProductnameSort(dto);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 首页品名BANNER管理显示页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("homePageMessageBannerManage")
	public ModelAndView homePageMessageBannerManage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvn = new ModelAndView("operation/opermg/homePageMessageBanner");
		mvn.addObject("homePageProductMessageList", homeDisplayService.getAllSysBannerProductNameList());
		mvn.addObject("homePageProductMessageOtherList", homeDisplayService.getSysBannerProductNameToOtherList());
		return mvn;
	}

	/**
	 * 查询首页品名BANNER管理列表
	 * 
	 * @param request
	 * @param response
	 * @param productnameDto
	 * @return
	 */
	@RequestMapping("searchHomePageMessageBannerList")
	@ResponseBody
	public String searchHomePageMessageBannerList(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerMassageExtDto productnameDto) {

		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "");
		QueryModel<SysHomeBannerMassageExtDto> qm = new QueryModel<SysHomeBannerMassageExtDto>(productnameDto, start,
				limit, orderName, orderType);
		PageModel<SysHomeBannerMassageExtDto> pm = homeDisplayService.searchHomeBannerMessageList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;

	}

	/**
	 * 修改显示图片
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateHomePageMessageBanner")
	@ResponseBody
	public String updateHomePageMessageBanner(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerMassageExtDto dto) {

		int result = homeDisplayService.updateHomeBannerMessage(dto);
		String msg = "";
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			msg = "{\"success\":false,\"msg\":\"此BANNER图片数据已经存在!\"}";
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 更新和删除
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateIsVisAndDelMessageBanner")
	@ResponseBody
	public String updateIsVisAndDelMessageBanner(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerMassageExtDto dto) {

		int result = homeDisplayService.updateIsVisAndDelMessageBanner(dto);
		String msg = "";
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("insertHomePageMessageBanner")
	@ResponseBody
	public String insertHomePageMessageBanner(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerMassage dto) {

		int result = homeDisplayService.insertHomeBannerMessage(dto);
		String msg = "";
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			msg = "{\"success\":false,\"msg\":\"此BANNER图片已经存在!\"}";
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 修改排序
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateHomePageMessageBannerSort")
	@ResponseBody
	public String updateHomePageMessageBannerSort(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerMassageDto dto) {

		int result = homeDisplayService.updateHomeBannerMassagenameSort(dto);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 首页品种管理显示页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("homePageVarietiesManage")
	public ModelAndView homePageVarietiesBannerManage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvn = new ModelAndView("operation/opermg/homePageVarietiesBanner");
		mvn.addObject("homePageProductNameList", homeDisplayService.getAllSysBannerProductNameList());
		mvn.addObject("homePageProductNameOtherList", homeDisplayService.getSysBannerProductNameToOtherList());
		mvn.addObject("homePageVarietiesList", homeDisplayService.getB2bVarietiesPidList());
		return mvn;
	}

	/**
	 * 查询首页品种管理列表
	 * 
	 * @param request
	 * @param response
	 * @param varietiesDto
	 * @return
	 */
	@RequestMapping("searchHomePageVarietiesBannerList")
	@ResponseBody
	public String searchHomePageVarietiesBannerList(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerVarietiesExtDto varietiesDto) {

		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "");
		QueryModel<SysHomeBannerVarietiesExtDto> qm = new QueryModel<SysHomeBannerVarietiesExtDto>(varietiesDto, start,
				limit, orderName, orderType);
		PageModel<SysHomeBannerVarietiesExtDto> pm = homeDisplayService.searchHomeBannerVarietiesList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;

	}

	/**
	 * 更新首页显示品种
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateHomePageVarietiesBanner")
	@ResponseBody
	public String updateHomePageVarietiesBanner(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerVarietiesExtDto dto) {

		int result = homeDisplayService.updateHomeBannerVarieties(dto);
		String msg = "";
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			msg = "{\"success\":false,\"msg\":\"已存在相同的品种或内容没有修改!\"}";
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 新增首页显示品种
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("insertHomePageVarietiesBanner")
	@ResponseBody
	public String insertHomePageVarietiesBanner(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerVarieties dto) {

		int result = homeDisplayService.insertHomeBannerVarieties(dto);
		String msg = "";
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			msg = "{\"success\":false,\"msg\":\"已存在相同的品种或内容没有修改!\"}";
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 首页显示品种状态修改和删除
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateIsVisAndDelPgVarietiesBanner")
	@ResponseBody
	public String updateIsVisAndDelPgVarietiesBanner(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerVarietiesExtDto dto) {

		int result = homeDisplayService.updateIsVisAndDelPgVarietiesBanner(dto);
		String msg = "";
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 查询首页规格管理列表
	 * 
	 * @param request
	 * @param response
	 * @param specDto
	 * @return
	 */
	@RequestMapping("searchHomePageSpecBannerList")
	@ResponseBody
	public String searchHomePageSpecBannerList(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerSpecExtDto specDto) {

		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "");
		QueryModel<SysHomeBannerSpecExtDto> qm = new QueryModel<SysHomeBannerSpecExtDto>(specDto, start, limit,
				orderName, orderType);
		PageModel<SysHomeBannerSpecExtDto> pm = homeDisplayService.searchHomeBannerSpecList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;

	}

	/**
	 * 首页规格管理显示页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("homePageSpecManage")
	public ModelAndView homePageSpecManage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvn = new ModelAndView("operation/opermg/homePageSpecBanner");
		mvn.addObject("homePageProductNameList", homeDisplayService.getAllSysBannerProductNameList());
		mvn.addObject("homePageProductNameOtherList", homeDisplayService.getSysBannerProductNameToOtherList());
		mvn.addObject("homePageSpecList", homeDisplayService.getb2bSpecs());
		return mvn;
	}

	/**
	 * 修改首页显示规格
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateHomePageSpecBanner")
	@ResponseBody
	public String updateHomePageSpecBanner(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerSpecExtDto dto) {
		int result = homeDisplayService.updateHomeBannerSpec(dto);
		String msg = "";
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			msg = "{\"success\":false,\"msg\":\"已存在相同的规格或内容没有修改!\"}";
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 首页显示规格状态修改和删除
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateIsVisAndDelHomePgSpecBanner")
	@ResponseBody
	public String updateIsVisAndDelHomePgSpecBanner(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerSpecExtDto dto) {
		int result = homeDisplayService.updateIsVisAndDelHomePgSpecBanner(dto);
		String msg = "";
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 首页显示规格新增
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("insertHomePageSpecBanner")
	@ResponseBody
	public String insertHomePageSpecBanner(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerSpec dto) {
		int result = homeDisplayService.insertHomeBannerSpec(dto);
		String msg = "";
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			msg = "{\"success\":false,\"msg\":\"已存在相同的规格!\"}";
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 首页规格系列管理显示页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("homePageSeriesManage")
	public ModelAndView homePageSeriesManage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvn = new ModelAndView("operation/opermg/homePageSeriesBanner");
		mvn.addObject("homePageProductNameList", homeDisplayService.getAllSysBannerProductNameList());
		// mvn.addObject("homePageSeriesSpecList",operationService.getAllSpecList());
		mvn.addObject("homePageProductNameOtherList", homeDisplayService.getSysBannerProductNameToOtherList());
		mvn.addObject("homePageSeriesSpecList", homeDisplayService.getb2bSpecs());
		mvn.addObject("specList", goodsManageService.getb2bSpecPid());
		return mvn;
	}

	/**
	 * 根据品名查询首页显示规格对象
	 * 
	 * @param request
	 * @param response
	 * @param productnamePk
	 * @return
	 */
	@RequestMapping("getchangePruductName")
	@ResponseBody
	public String getchangePruductName(HttpServletRequest request, HttpServletResponse response, String productnamePk) {
		List<SysHomeBannerSpecExtDto> list = homeDisplayService.getSysHomeBannerSpecByProductNamePk(productnamePk);
		return JsonUtils.convertToString(list);
	}

	/**
	 * 根据品名查询品种对象
	 * 
	 * @param request
	 * @param response
	 * @param productnamePk
	 * @return
	 */
	@RequestMapping("getchangeVirietiesName")
	@ResponseBody
	public String getchangeVirietiesName(HttpServletRequest request, HttpServletResponse response,
			String productnamePk) {
		List<SysHomeBannerVarietiesDto> list = homeDisplayService.searchSysHomeBannerVarieties(productnamePk);
		return JsonUtils.convertToString(list);
	}

	/**
	 * 查询首页规格系列管理列表
	 * 
	 * @param request
	 * @param response
	 * @param specDto
	 * @return
	 */
	@RequestMapping("searchHomePageSeriesBannerList")
	@ResponseBody
	public String searchHomePageSeriesBannerList(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerSeriesExtDto specDto) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "");
		QueryModel<SysHomeBannerSeriesExtDto> qm = new QueryModel<SysHomeBannerSeriesExtDto>(specDto, start, limit,
				orderName, orderType);
		PageModel<SysHomeBannerSeriesExtDto> pm = homeDisplayService.searchHomeBannerSeriesList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 首页显示规格系列修改
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateHomePageSeriesBanner")
	@ResponseBody
	public String updateHomePageSeriesBanner(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerSeriesExtDto dto) {
		int result = homeDisplayService.updateHomeBannerSeries(dto);
		String msg = "";
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			msg = "{\"success\":false,\"msg\":\"已存在相同的规格系列或内容没有修改!\"}";
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 首页显示规格系列新增
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("insertHomePageSeriesBanner")
	@ResponseBody
	public String insertHomePageSeriesBanner(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerSeries dto) {
		int result = homeDisplayService.insertHomeBannerSeries(dto);
		String msg = "";
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			msg = "{\"success\":false,\"msg\":\"已存在相同的规格系列或内容没有修改!\"}";
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 首页显示规格系列状态修改和删除
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateIsVisAndDelHomePgSeriesBanner")
	@ResponseBody
	public String updateIsVisAndDelHomePgSeriesBanner(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerSeriesExtDto dto) {
		int result = homeDisplayService.updateIsVisAndDelHomePgSeriesBanner(dto);
		String msg = "";
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 根据Pk查询规格系列
	 * 
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 */
	@RequestMapping("getHomePageSeries")
	@ResponseBody
	public String getHomePageSeries(HttpServletRequest request, HttpServletResponse response, String pk) {
		SysHomeBannerSpecDto dto = homeDisplayService.getSysHomeBannerSpecPk(pk);
		List<B2bSpecDto> list = null;
		if (dto != null) {
			list = homeDisplayService.getb2bSpecByPid(dto.getSpecPk());
		}
		return JsonUtils.convertToString(list);
	}

	/**
	 * 查询所有规格系列
	 * 
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 */
	@RequestMapping("getHomePageAllSeries")
	@ResponseBody
	public String getHomePageAllSeries(HttpServletRequest request, HttpServletResponse response, String pk) {
		List<B2bSpecDto> list = homeDisplayService.getb2bSpecByPid(pk);
		return JsonUtils.convertToString(list);
	}

	/**
	 * 根据Pk查询规格
	 * 
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 */
	@RequestMapping("getHomePageSpec")
	@ResponseBody
	public String getHomePageSpec(HttpServletRequest request, HttpServletResponse response, String pk) {
		List<SysHomeBannerSpecExtDto> list = homeDisplayService.getSysHomeBannerSpecByProductNamePk(pk);
		return JsonUtils.convertToString(list);
	}

	/**
	 * 修改首页显示品种排序
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateHomePageVarietiesBannerSort")
	@ResponseBody
	public String updateHomePageVarietiesBannerSort(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerVarietiesDto dto) {

		int result = homeDisplayService.updateHomeBannerVarietiesnameSort(dto);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 修改首页显示规格系列排序
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateHomePageSpecBannerSort")
	@ResponseBody
	public String updateHomePageSpecBannerSort(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerSpecDto dto) {

		int result = homeDisplayService.updateHomeBannerSpecnameSort(dto);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 修改首页显示规格系列
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateHomePageSeriesBannerSort")
	@ResponseBody
	public String updateHomePageSeriesBannerSort(HttpServletRequest request, HttpServletResponse response,
			SysHomeBannerSeriesDto dto) {

		int result = homeDisplayService.updateHomeBannerSeriesnameSort(dto);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 热搜关键词显示页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("keywordHot")
	public ModelAndView keywordHot(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvn = new ModelAndView("operation/opermg/keywordHot");
		return mvn;
	}

	/**
	 * 热门关键词推荐
	 * 
	 * @param request
	 * @param response
	 * @param keywordHotDto
	 * @return
	 */
	@RequestMapping("searchKeywordHotList")
	@ResponseBody
	public String searchKeywordHotList(HttpServletRequest request, HttpServletResponse response,
			B2bKeywordHotExtDto keywordHotDto) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "");
		QueryModel<B2bKeywordHotExtDto> qm = new QueryModel<B2bKeywordHotExtDto>(keywordHotDto, start, limit, orderName,
				orderType);
		PageModel<B2bKeywordHotDto> pm = homeDisplayService.searchB2bKeywordHot(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 修改热搜关键词
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("updateKeywordHot")
	@ResponseBody
	public String updateKeywordHot(HttpServletRequest request, HttpServletResponse response, B2bKeywordHot model) {
		int result = homeDisplayService.updateB2bKeywordHot(model);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 友情链接显示页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("friendLink")
	public ModelAndView friendLink(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvn = new ModelAndView("operation/opermg/friendLink");
		return mvn;
	}

	/**
	 * 友情链接列表
	 * 
	 * @param request
	 * @param response
	 * @param friendLink
	 * @return
	 */
	@RequestMapping("searchFriendLinkList")
	@ResponseBody
	public String searchFriendLinkList(HttpServletRequest request, HttpServletResponse response,
			B2bFriendLinkExtDto friendLink) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "");
		QueryModel<B2bFriendLinkExtDto> qm = new QueryModel<B2bFriendLinkExtDto>(friendLink, start, limit, orderName,
				orderType);
		PageModel<B2bFriendLinkDto> pm = homeDisplayService.searchB2bFriendLink(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 友情链接修改
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("updateFriendLink")
	@ResponseBody
	public String updateFriendLink(HttpServletRequest request, HttpServletResponse response, B2bFriendLink model) {
		int result = homeDisplayService.updateB2bFriendLink(model);
		String msg = "";
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			msg = "{\"success\":false,\"msg\":\"网站名称已存在!\"}";
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 价格趋势显示页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("priceTrend")
	public ModelAndView priceTrend(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvn = null;

		String modelType = ServletUtils.getStringParameter(request, "modelType");
		if (modelType.contains("?")) {// 数据库默认
			modelType = modelType.substring(0, modelType.indexOf("?"));
		}
		if (Constants.BLOCK_CF_ONE.equals(modelType)){
			mvn = new ModelAndView("operation/opermg/priceTrend");
			mvn.addObject("block", Constants.BLOCK_CF);
			mvn.addObject("goodsBrandList", goodsManageService.getB2bGoodsBrandList(Constants.BLOCK_CF));// 厂家品牌
			mvn.addObject("productList", goodsManageService.getB2bProductList());// 品名
			mvn.addObject("varietiesList", goodsManageService.getB2bVarietiesPidList());// 品种
			mvn.addObject("specList", goodsManageService.getb2bSpecPid());
			mvn.addObject("gradeList", goodsManageService.getB2bGradeList());
			mvn.addObject("companyList", goodsManageService.getB2bCompayDtoByType(2, null,Constants.BLOCK_CF));
		}
		if (Constants.BLOCK_SX_TWO.equals(modelType)){
			mvn = new ModelAndView("yarn/opermg/yarnPriceTrend");
			mvn.addObject("block",Constants.BLOCK_SX);
			mvn.addObject("technologyList",goodsManageService.searchSxTechnologyList());
			mvn.addObject("firstMaterialList",goodsManageService.searchSxMaterialList());
			mvn.addObject("goodsBrandList", goodsManageService.getB2bGoodsBrandList(Constants.BLOCK_SX));// 厂家品牌
		}

		return mvn;
	}

	@RequestMapping("getSecondMaterialList")
	@ResponseBody
	public String getSecondMaterialList(HttpServletRequest request, HttpServletResponse response,String parentPk) {
	    if(StringUtils.isEmpty(parentPk)){
	        return JsonUtils.convertToString(new ArrayList());
	    }
		return JsonUtils.convertToString(goodsManageService.searchSxSecondMaterialList(parentPk));
	}



	/**
	 * 价格趋势查询
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @param isShowHome
	 * @return
	 */
	@RequestMapping("searchPriceTrendList")
	@ResponseBody
	public String searchPriceTrendList(HttpServletRequest request, HttpServletResponse response,
			B2bPriceMovementExtDto dto, Integer isShowHome) {

		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "");
		if (isShowHome != null && isShowHome > 0) {
			dto.setIsHome(isShowHome);
		}
		String jSon = "";
		QueryModel<B2bPriceMovementExtDto> qm = new QueryModel<B2bPriceMovementExtDto>(dto, start, limit, orderName,
				orderType);
			PageModel<B2bPriceMovementExtDto> resultCf = homeDisplayService.searchPriceTrendList(qm);
			jSon = JsonUtils.convertToString(resultCf);
		return jSon;
	}

	/**
	 * 价格趋势导出
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@RequestMapping("exportPriceTrend")
	@ResponseBody
	public String exportPriceTrend(HttpServletRequest request, HttpServletResponse response,
			GoodsDataTypeParams params) {
		ManageAccount account = this.getAccount(request);
//		ExportUtil<B2bPriceMovementExtDto> export = new ExportUtil<B2bPriceMovementExtDto>();
//			List<B2bPriceMovementExtDto> list = homeDisplayService.exportPriceTrendList(dto);
//			if (Constants.BLOCK_SX.equals(dto.getBlock())){
//				export.exportUtil("yarnPriceTrend", list, response, request);
//			}else{
//				export.exportUtil("priceTrend", list, response, request);
//			}

		String uuid = KeyUtils.getUUID();
		params.setUuid(uuid);
		homeDisplayService.savePriceTrendExcelToOss(params,account);
		String name = Thread.currentThread().getName();//获取当前执行线程
		dynamicTask.startCron(new PriceTrendRunnable(name,uuid),name);
		return Constants.EXPORT_MSG;
	}

	/**
	 * 价格趋势编辑
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updatePriceTrend")
	@ResponseBody
	public String updatePriceTrend(HttpServletRequest request, HttpServletResponse response,
			B2bPriceMovementExtDto dto) {
		int result = homeDisplayService.updateB2bPriceMovement(dto);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 编辑价格趋势历史数据
	 * 
	 * @param request
	 * @param response
	 * @param entity
	 * @return
	 */
	@RequestMapping("updatePriceTrendHistory")
	@ResponseBody
	public String updatePriceTrendHistory(HttpServletRequest request, HttpServletResponse response,
			B2bPriceMovementEntity entity) {
		int result = homeDisplayService.updateB2bPriceMovementEntity(entity);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 新增价格趋势历史数据
	 *
	 * @param request
	 * @param response
	 * @param entity
	 * @return
	 */
	@RequestMapping("insertPriceTrendHistory")
	@ResponseBody
	public String insertPriceTrendHistory(HttpServletRequest request, HttpServletResponse response,
										  B2bPriceMovementEntity entity) {
		int result = homeDisplayService.insertB2bPriceMovementEntity(entity);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}



	/**
	 * 显示不显示价格趋势历史数据
	 * 
	 * @param request
	 * @param response
	 * @param entity
	 * @return
	 */
	@RequestMapping("isShowPriceTrendHistory")
	@ResponseBody
	public String isShowPriceTrendHistory(HttpServletRequest request, HttpServletResponse response,
			B2bPriceMovementEntity entity) {
		int result = homeDisplayService.isShowB2bPriceMovementEntity(entity);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 显示不显示纱线价格趋势历史数据
	 *
	 * @param request
	 * @param response
	 * @param entity
	 * @return
	 */
	@RequestMapping("isShowSxPriceTrendHistory")
	@ResponseBody
	public String isShowSxPriceTrendHistory(HttpServletRequest request, HttpServletResponse response,
										  SxPriceMovementEntity entity) {
		int result = homeDisplayService.isShowSxPriceMovementEntity(entity);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 删除价格趋势历史数据
	 * 
	 * @param request
	 * @param response
	 * @param entity
	 * @return
	 */
	@RequestMapping("deletePriceTrendHistory")
	@ResponseBody
	public String deletePriceTrendHistory(HttpServletRequest request, HttpServletResponse response,
			B2bPriceMovementEntity entity) {
		int result = homeDisplayService.deleteB2bPriceMovementEntity(entity);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 删除纱线价格趋势历史数据
	 *
	 * @param request
	 * @param response
	 * @param entity
	 * @return
	 */
	@RequestMapping("deleteSxPriceTrendHistory")
	@ResponseBody
	public String deleteSxPriceTrendHistory(HttpServletRequest request, HttpServletResponse response,
										  SxPriceMovementEntity entity) {
		int result = homeDisplayService.deleteSxPriceMovementEntity(entity);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 编辑价格趋势历史数据
	 *
	 * @param request
	 * @param response
	 * @param entity
	 * @return
	 */
	@RequestMapping("updateSxPriceTrendHistory")
	@ResponseBody
	public String updateSxPriceTrendHistory(HttpServletRequest request, HttpServletResponse response,
											SxPriceMovementEntity entity) {
		int result = homeDisplayService.updateSxPriceMovementEntity(entity);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}


	/**
	 * 新增价格趋势历史数据
	 *
	 * @param request
	 * @param response
	 * @param entity
	 * @return
	 */
	@RequestMapping("insertSxPriceTrendHistory")
	@ResponseBody
	public String insertSxPriceTrendHistory(HttpServletRequest request, HttpServletResponse response,
										 SxPriceMovementEntity entity) {
		int result = homeDisplayService.insertSxPriceMovementEntity(entity);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 编辑价格趋势显示页面（价格趋势商品）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("showEditPriceTrend")
	public ModelAndView showEditPriceTrend(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvn = new ModelAndView("operation/opermg/editPriceTrendGoods");
		return mvn;
	}

	/**
	 * 编辑价格趋势显示页面（价格趋势纱线商品）
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("showEditYarnPriceTrend")
	public ModelAndView showEditYarnPriceTrend(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvn = new ModelAndView("yarn/opermg/editYarnPriceTrendGoods");
		return mvn;
	}
	/**
	 * 价格趋势查询
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("searchEditPriceTrendList")
	@ResponseBody
	public String searchEditPriceTrendList(HttpServletRequest request, HttpServletResponse response,
			B2bPriceMovementExtDto dto) {

		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "");
		QueryModel<B2bPriceMovementExtDto> qm = new QueryModel<B2bPriceMovementExtDto>(dto, start, limit, orderName,
				orderType);
		PageModel<B2bPriceMovementExtDto> result = homeDisplayService.searchEditPriceTrendList(qm);
		return JsonUtils.convertToString(result);
	}

	/**
	 * 添加商品到价格趋势表查询
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("searchGoodsToPriceTrendList")
	@ResponseBody
	public String searchGoodsToPriceList(HttpServletRequest request, HttpServletResponse response, B2bGoodsExtDto dto) {

		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "");
		QueryModel<B2bGoodsExtDto> qm = new QueryModel<B2bGoodsExtDto>(dto, start, limit, orderName, orderType);
		PageModel<B2bGoodsExtDto> result = goodsManageService.searchGoodsToPriceTrendList(qm);
		return JsonUtils.convertToString(result);
	}

	/**
	 * 把商品表信息添加到价格趋势表
	 * 
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 */
	@RequestMapping("insertGoodsPriceTrend")
	@ResponseBody
	public String insertGoodsPriceTrend(HttpServletRequest request, HttpServletResponse response, String pk) {

		String result = homeDisplayService.insertGoodsToPriceTrendList(pk);
		return result;
	}

//	/**
//	 * 添加纱线商品到价格趋势表查询
//	 *
//	 * @param request
//	 * @param response
//	 * @param dto
//	 * @return
//	 */
//	@RequestMapping("searchSxGoodsToPriceTrendList")
//	@ResponseBody
//	public String searchSxGoodsToPriceTrendList(HttpServletRequest request, HttpServletResponse response, SxGoodsDto dto) {
//
//		int start = ServletUtils.getIntParameter(request, "start", 0);
//		int limit = ServletUtils.getIntParameter(request, "limit", 5);
//		String orderName = ServletUtils.getStringParameter(request, "orderName", "");
//		String orderType = ServletUtils.getStringParameter(request, "orderType", "");
//		QueryModel<SxGoodsDto> qm = new QueryModel<SxGoodsDto>(dto, start, limit, orderName, orderType);
//		PageModel<SxGoodsDto> result = goodsManageService.searchSxGoodsToPriceTrendList(qm);
//		return JsonUtils.convertToString(result);
//	}

	/**
	 * 把纱线商品表信息添加到价格趋势表
	 *
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 */
	@RequestMapping("insertSxGoodsPriceTrend")
	@ResponseBody
	public String insertSxGoodsPriceTrend(HttpServletRequest request, HttpServletResponse response, String pk) {

		String result = homeDisplayService.insertSxGoodsToPriceTrendList(pk);
		return result;
	}

	/**
	 * 价格趋势修改历史记录数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("priceTrendHistory")
	public ModelAndView priceTrendHistory(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvn = null;
		String modelType = ServletUtils.getStringParameter(request, "modelType");
		if (modelType.contains("?")) {// 数据库默认
			modelType = modelType.substring(0, modelType.indexOf("?"));
		}
		if (Constants.BLOCK_CF_ONE.equals(modelType)) {
			mvn = new ModelAndView("operation/opermg/priceTrendHistory");
		}
		if (Constants.BLOCK_SX_TWO.equals(modelType)) {
			mvn = new ModelAndView("yarn/opermg/yarnPriceTrendHistory");
		}
		return mvn;
	}

	/**
	 * 价格趋势历史数据查询
	 * 
	 * @param request
	 * @param response
	 * @param entity
	 * @return
	 */
	@RequestMapping("searchPriceTrendHistoryList")
	@ResponseBody
	public String searchPriceTrendHistoryList(HttpServletRequest request, HttpServletResponse response,
			B2bPriceMovementExtDto entity) {

		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "");
		QueryModel<B2bPriceMovementExtDto> qm = new QueryModel<B2bPriceMovementExtDto>(entity, start, limit, orderName,
				orderType);
		PageModel<B2bPriceMovementEntity> result = homeDisplayService.searchPriceTrendHistoryList(qm);
		return JsonUtils.convertToString(result);
	}

	/**
	 * 纱线价格趋势历史数据查询
	 *
	 * @param request
	 * @param response
	 * @param entity
	 * @return
	 */
	@RequestMapping("searchSxPriceTrendHistoryList")
	@ResponseBody
	public String searchSxPriceTrendHistoryList(HttpServletRequest request, HttpServletResponse response,
											  B2bPriceMovementExtDto entity) {

		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "");
		QueryModel<B2bPriceMovementExtDto> qm = new QueryModel<B2bPriceMovementExtDto>(entity, start, limit, orderName,
				orderType);
		PageModel<SxPriceMovementEntity> result = homeDisplayService.searchSxPriceTrendHistoryList(qm);
		return JsonUtils.convertToString(result);
	}

	/**
	 * 导出价格趋势历史数据
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@RequestMapping("exportPriceTrendHistoryList")
	@ResponseBody
	public String exportPriceTrendHistoryList(HttpServletRequest request, HttpServletResponse response,
													GoodsDataTypeParams params) {
		ManageAccount account = this.getAccount(request);
//		ExportUtil<B2bPriceMovementEntity> export = new ExportUtil<>();
//		List<B2bPriceMovementEntity> list = homeDisplayService.exportPriceTrendHistoryList(entity);
//		export.exportUtil("priceTrendHistory", list, response, request);
		String uuid = KeyUtils.getUUID();
		params.setUuid(uuid);
		homeDisplayService.savePriceTrendHistoryToOss(params,account);
		String name = Thread.currentThread().getName();//获取当前执行线程
		dynamicTask.startCron(new PriceTrendCfHistoryRunnable(name,uuid),name);
		return Constants.EXPORT_MSG;
	}

	/**
	 * 导出价格趋势历史数据
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@RequestMapping("exportSxPriceTrendHistoryList")
	@ResponseBody
	public String exportSxPriceTrendHistoryList(HttpServletRequest request, HttpServletResponse response,
													  GoodsDataTypeParams params) {
		ManageAccount account = this.getAccount(request);
//		ExportUtil<SxPriceMovementEntity> export = new ExportUtil<>();
//		List<SxPriceMovementEntity> list = homeDisplayService.exportSxPriceTrendHistoryList(entity);
//		export.exportUtil("yarnPriceTrendHistory", list, response, request);

		String uuid = KeyUtils.getUUID();
		params.setUuid(uuid);
		homeDisplayService.savePriceTrendHistoryToOss(params,account);
		String name = Thread.currentThread().getName();//获取当前执行线程
		dynamicTask.startCron(new PriceTrendSxHistoryRunnable(name,uuid),name);
		return Constants.EXPORT_MSG;
	}
	
	
	/**
	 * 首页二级导航管理
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("homeSecondNavigation")
	public ModelAndView homeSecondNavigation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvn = new ModelAndView("operation/opermg/homeSecondNavigation");
		return mvn;
	}
	
	/**
	 *  首页二级导航列表
	 * @param request
	 * @param response
	 * @param entity
	 * @return
	 */
	@RequestMapping("homeSecondNavigationList")
	@ResponseBody
	public String homeSecondNavigationList(HttpServletRequest request, HttpServletResponse response,
			SxHomeSecondnavigationExtDto entity) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "");
		QueryModel<SxHomeSecondnavigationExtDto> qm = new QueryModel<SxHomeSecondnavigationExtDto>(entity, start, limit, orderName,
				orderType);
		PageModel<SxHomeSecondnavigationExtDto> result = homeDisplayService.searchHomeSecondNavigationList(qm);
		return JsonUtils.convertToString(result);
	}
	
	/**
	 * 首页三级导航管理
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("homeThiredNavigation")
	public ModelAndView homeThiredNavigation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvn = new ModelAndView("operation/opermg/homeThiredNavigation");
		mvn.addObject("secondNavigation", homeDisplayService.getAllSecondNavigationList());
		return mvn;
	}
	
	
	/**
	 *  首页级导航列表
	 * @param request
	 * @param response
	 * @param entity
	 * @return
	 */
	@RequestMapping("homeThirdNavigationList")
	@ResponseBody
	public String homeThirdNavigationList(HttpServletRequest request, HttpServletResponse response,
			SxHomeThirdnavigationExtDto entity) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "");
		QueryModel<SxHomeThirdnavigationExtDto> qm = new QueryModel<SxHomeThirdnavigationExtDto>(entity, start, limit, orderName,
				orderType);
		PageModel<SxHomeThirdnavigationExtDto> result = homeDisplayService.searchHomeThirdNavigationList(qm);
		return JsonUtils.convertToString(result);
	}
	
	/**
	 * 根据一级导航查询二级导航
	 * @param request
	 * @param response
	 * @param type
	 * @return
	 */
	@RequestMapping("changeSecondNavigation")
	@ResponseBody
	public String changeSecondNavigation(HttpServletRequest request, HttpServletResponse response,
			Integer   type) {
		String   result = homeDisplayService.changeSecondNavigation(type);
		return result;
	}
	
	/**
	 * 编辑二级导航
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateSecondNavigation")
	@ResponseBody
	public String updateSecondNavigation(HttpServletRequest request, HttpServletResponse response,
			SxHomeSecondnavigationExtDto dto) {
		Integer   result = homeDisplayService.updateSecondNavigation(dto);
		String msg = "";
		if (result > 0) {
			if (result==1) {
				msg = Constants.RESULT_SUCCESS_MSG;
			}else{
				msg = Constants.ISEXIT_NAVIGATION_MSG;
			}
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return  msg;
	}
	
	/**
	 * 二级导航排序
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateSecondNavigationSort")
	@ResponseBody
	public String updateSecondNavigationSort(HttpServletRequest request, HttpServletResponse response,
			SxHomeSecondnavigationExtDto dto) {
		Integer   result = homeDisplayService.updateSecondNavigationSort(dto);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return  msg;
	}
	
	/**
	 * 根据二级导航查询三级导航
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 */
	@RequestMapping("changeThirdNavigation")
	@ResponseBody
	public String changeThirdNavigation(HttpServletRequest request, HttpServletResponse response,
			String   pk) {
		String   result = homeDisplayService.changeThirdNavigation(pk);
		return result;
	}
	
	/**
	 * 编辑
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateThirdNavigation")
	@ResponseBody
	public String updateThirdNavigation(HttpServletRequest request, HttpServletResponse response,
			SxHomeThirdnavigationExtDto dto) {
		Integer   result = homeDisplayService.updateThirdNavigation(dto);
		String msg = "";
		if (result > 0) {
			if (result==1) {
				msg = Constants.RESULT_SUCCESS_MSG;
			}else{
				msg = Constants.ISEXIT_SECONDNAVIGATION_MSG;
			}
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return  msg;
	}
	
	/**
	 * 修改排序
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateThirdNavigationSort")
	@ResponseBody
	public String updateThirdNavigationSort(HttpServletRequest request, HttpServletResponse response,
			SxHomeThirdnavigationExtDto dto) {
		Integer   result = homeDisplayService.updateThirdNavigationSort(dto);
		String msg = "";
		if (result > 0) {
				msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return  msg;
	}
	
}
