package cn.cf.controller.operation;

import java.math.BigDecimal;
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
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bStoreAlbumDto;
import cn.cf.dto.B2bStoreExtDto;
import cn.cf.dto.ManageAccountDto;
import cn.cf.dto.MarketingPersonnelDto;
import cn.cf.dto.MarketingPersonnelExtDto;
import cn.cf.entity.B2bStoreAlbumEntry;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bStore;
import cn.cf.model.ManageAccount;
import cn.cf.model.MarketingPersonnel;
import cn.cf.service.manage.AccountManageService;
import cn.cf.service.operation.B2bStoreService;
import cn.cf.util.ServletUtils;

/**
 * 店铺管理
 * 
 * @author why
 *
 */
@Controller
@RequestMapping("/")
public class MarketingController extends BaseController {

	@Autowired
	private B2bStoreService b2bStoreService;
	@Autowired
	private AccountManageService accountManageService;

	/**
	 * 店铺管理页面
	 * 
	 * @param request
	 * @param response
	 * @param store
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("storeM")
	public ModelAndView storeM(HttpServletRequest request, HttpServletResponse response, B2bStore store)
			throws Exception {
		ModelAndView mav = new ModelAndView("marketing/storeM");
		return mav;
	}

	/**
	 * 店铺列表
	 * 
	 * @param request
	 * @param response
	 * @param store
	 * @return
	 */
	@ResponseBody
	@RequestMapping("storeM_data")
	public String storeMData(HttpServletRequest request, HttpServletResponse response, B2bStoreExtDto store) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		ManageAccount account = getAccount(request);
		store.setAccountPk(account.getPk());
		QueryModel<B2bStoreExtDto> qm = new QueryModel<B2bStoreExtDto>(store, start, limit, orderName, orderType);

		PageModel<B2bStoreExtDto> pm = b2bStoreService.searchStoreList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 店铺相册编辑管理页面
	 * 
	 * @param request
	 * @param response
	 * @param store
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("enditStoreAlbum")
	public ModelAndView enditStoreAlbum(HttpServletRequest request, HttpServletResponse response, String storePk)
			throws Exception {
		ModelAndView mav = new ModelAndView("marketing/storeAlbum");
		mav.addObject("storePk", storePk);
		return mav;
	}

	/**
	 * 店铺编辑页面
	 * 
	 * @param request
	 * @param response
	 * @param store
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("enditStore")
	public ModelAndView enditStore(HttpServletRequest request, HttpServletResponse response, String storePk)
			throws Exception {
		ModelAndView mav = new ModelAndView("marketing/editStore");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pk", storePk);
		List<B2bStoreExtDto> list = b2bStoreService.searchStoreList(map);
		B2bStoreExtDto dto = null;
		if (list != null && list.size() > 0) {
			dto = list.get(0);
			dto.setWeight(dto.getUpperWeight() == null ? new BigDecimal(0.0) : new BigDecimal(dto.getUpperWeight()));
		}
		mav.addObject("store", dto);
		mav.addObject("storePk", storePk);
		return mav;
	}

	/**
	 * 查询公司相册
	 * 
	 * @param request
	 * @param response
	 * @param store
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("enditStoreAblum")
	@ResponseBody
	public String enditStoreAblum(HttpServletRequest request, HttpServletResponse response, String storePk)
			throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		B2bStoreAlbumDto dto = new B2bStoreAlbumDto();
		dto.setStorePk(storePk);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<B2bStoreAlbumDto> qm = new QueryModel<B2bStoreAlbumDto>(dto, start, limit, orderName, orderType);
		PageModel<B2bStoreAlbumEntry> list = b2bStoreService.searchStoreAlbumList(qm);
		return JsonUtils.convertToString(list);
	}

	/**
	 * 修改公司相册
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateStoreAblum")
	@ResponseBody
	public String updateStoreAblum(HttpServletRequest request, HttpServletResponse response, B2bStoreAlbumDto dto)
			throws Exception {
		int result = b2bStoreService.updateStoreAlbum(dto);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result <= 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 店铺编辑
	 * 
	 * @param request
	 * @param response
	 * @param store
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateStore")
	public String updateStore(HttpServletRequest request, HttpServletResponse response, B2bStoreExtDto store) {
		int result = b2bStoreService.updateStore(store);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result <= 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 人员管理页面
	 * 
	 * @param request
	 * @param response
	 * @param store
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("personnel")
	public ModelAndView personnel() {
		ModelAndView mav = new ModelAndView("marketing/personnel");
		mav.addObject("OnlineAccount", accountManageService.getOnline());
		mav.addObject("accountList", accountManageService.getAllNoisDeleteAccountList());
		mav.addObject("regionAllList", accountManageService.getRegionAllList());
	    mav.addObject("regionNoIsVisiableList", accountManageService.getRegionNoIsVisiablList());

		return mav;
	}
	/**
	 * 根据类别查询人员管理
	 * @param marketingPersonnel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("personnelType2")
	public String personnels(MarketingPersonnelDto marketingPersonnel) {
		List<MarketingPersonnelDto> list = accountManageService.getPersonByType(marketingPersonnel.getType());
		String json = JsonUtils.convertToString(list);
		return json;
	}
	
	/**
	 * 查询未分配人员
	 * @return
	 */
	@ResponseBody
	@RequestMapping("onlineAccount")
	public String OnlineAccount() {
		List<ManageAccountDto> list = accountManageService.getOnline();
		String json = JsonUtils.convertToString(list);
		return json;
	}

	/**
	 * 人员管理列表
	 * 
	 * @param request
	 * @param response
	 * @param store
	 * @return
	 */
	@ResponseBody
	@RequestMapping("personnel_data")
	public String personneldata(HttpServletRequest request, HttpServletResponse response,
	        MarketingPersonnelExtDto marketingPersonnel) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "updateTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		ManageAccount account = getAccount(request);
		
		QueryModel<MarketingPersonnelExtDto> qm = new QueryModel<MarketingPersonnelExtDto>(marketingPersonnel, start, limit,
				orderName, orderType);
		
		PageModel<MarketingPersonnelExtDto> pm = accountManageService.searchPersonnelList(qm,account.getPk());
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 添加人员管理
	 * @param marketingPersonnel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertPersonnel")
	public String insertPersonnel(MarketingPersonnel marketingPersonnel) {
		String msg = Constants.RESULT_SUCCESS_MSG;
        int result = accountManageService.insertPersonnel(marketingPersonnel);
        if (result <= 0) {
        	msg = Constants.RESULT_FAIL_MSG;
        }
		return msg;
	}
}
