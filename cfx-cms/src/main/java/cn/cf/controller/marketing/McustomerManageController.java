/**
 * 
 */
package cn.cf.controller.marketing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.common.*;
import cn.cf.entity.GoodsDataTypeParams;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.service.CmsService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.chemifiber.GoodsRunnable;
import cn.cf.task.schedule.chemifiber.OrderRunnable;
import cn.cf.task.schedule.marketing.MkOrderRunnable;
import cn.cf.util.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.dto.B2bCompanyExtDto;
import cn.cf.dto.B2bGoodsExtDto;
import cn.cf.dto.B2bManageRegionExtDto;
import cn.cf.dto.B2bOrderExtDto;
import cn.cf.dto.ManageAuthorityDto;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.model.MarketingCompany;
import cn.cf.service.marketing.McustomerManageService;
import cn.cf.service.operation.GoodsManageService;
import cn.cf.service.operation.InformationCenterService;
import cn.cf.service.operation.SysRegionsService;
import cn.cf.util.ServletUtils;

/**
 * @author bin 营销中心
 */
@Controller
@RequestMapping("/")
public class McustomerManageController extends BaseController {
	@Autowired
	private McustomerManageService mcustomerManageService;


	
	

	/**
	 * 修改管理查询
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateManageRegion")
	@ResponseBody
	public String updateManageRegion(HttpServletRequest request, HttpServletResponse response, B2bManageRegionExtDto dto) throws Exception {

		int result = mcustomerManageService.updateManageRegion(dto);
		String msg = Constants.RESULT_FAIL_MSG;
		if(result > 0){
			msg = Constants.RESULT_SUCCESS_MSG;
		}
		return msg;
	}

	/**
	 * 修改管理查询
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delManageRegion")
	@ResponseBody
	public String delManageRegion(HttpServletRequest request, HttpServletResponse response, String pk) throws Exception {

		int result = mcustomerManageService.delManageRegion(pk);
		String msg = Constants.RESULT_FAIL_MSG;
		if(result > 0){
			msg = Constants.RESULT_SUCCESS_MSG;
		}
		return msg;
	}




}
