package cn.cf.controler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bPackNumberDto;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bPackNumber;
import cn.cf.service.B2bFacadeService;
import cn.cf.service.B2bPackNumberService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/**
 * 
 * @description:生产厂区接口管理
 * @author XHT
 * @date 2018-4-13 下午5:20:27
 */
@RestController
@RequestMapping("shop")
public class B2bPackNumberController extends BaseController {

	@Autowired
	private B2bFacadeService facadeService;
	
	@Autowired
	private B2bPackNumberService packNumberService;

	/**
	 * 添加包装批号
	 * 
	 * @param req
	 * @param pk
	 * @param productPk
	 * @param productName
	 * @param bucketsNum
	 * @param grainWeight
	 * @param packNumber
	 * @param batchNumber
	 * @param netWeight
	 * @return
	 */
	@RequestMapping(value = "addPackNumber", method = RequestMethod.POST)
	public String addPackNumber(HttpServletRequest req) {
		RestCode restCode = RestCode.CODE_0000;
		B2bPackNumber packNumberModel = new B2bPackNumber();
		packNumberModel.bind(req);
		Sessions session =this.getSessions(req);
		if (session.getCompanyDto() == null) {
			restCode = RestCode.CODE_M008;
		} else {
			restCode = facadeService.addPackNumber(packNumberModel, session);
		}
		return restCode.toJson();
	}
	
	/***
	 * 编辑包装批号
	 * @param isDelete 
	 */
	@RequestMapping(value = "updatePackNumber", method = RequestMethod.POST)
	public String updatePackNumber(HttpServletRequest req) {
		try {
			B2bPackNumber packNumberModel = new B2bPackNumber();
			packNumberModel.setPk(ServletUtils.getStringParameter(req, "pk", ""));
			packNumberModel.setIsDelete(ServletUtils.getIntParameterr(req, "isDelete", 0));
			int result = packNumberService.update(packNumberModel);
			if (result > 0) {
				return RestCode.CODE_0000.toJson();
			}
			return RestCode.CODE_S999.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toString();
		}
	}
	
	/***
	 * 包装批号列表
	 */
	@RequestMapping(value = "packNumberList", method = RequestMethod.POST)
	public String packNumberList(HttpServletRequest req) {
		String result = null;
		try {
			Map<String, Object> map = this.paramsToMap(req);
			Sessions session=this.getSessions(req);
			if (null == session.getCompanyDto()) {
				result = RestCode.CODE_M008.toJson();
			}else {
				map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
				map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
				map.put("orderName", req.getParameter("orderName"));
				map.put("orderType", req.getParameter("orderType"));
				map.put("storePk", session.getStoreDto().getPk());
				map.put("isDelete", 1);
				PageModel<B2bPackNumberDto> pm = packNumberService.searchPackNumberList(map);
				result =RestCode.CODE_0000.toJson(pm);
			}
			return result ;
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	

}
