package cn.cf.controler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bPlantDto;
import cn.cf.dto.B2bPlantDtoEx;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bPlant;
import cn.cf.service.B2bFacadeService;
import cn.cf.service.B2bPlantService;
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
public class B2bPlantController extends BaseController {

	@Autowired
	private B2bFacadeService facadeService;

	@Autowired
	private B2bPlantService b2bPlantService;

	/**
	 * 编辑/添加厂区
	 */
	@RequestMapping(value = "addPlant", method = RequestMethod.POST)
	public String addPlant(HttpServletRequest req) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			Sessions sessions = this.getSessions(req);
			if (null == sessions.getCompanyDto()) {
				restCode = RestCode.CODE_M008;
			}else {
				B2bPlantDto dto = new B2bPlantDto();
				dto.bind(req);
				dto.setStorePk(sessions.getStoreDto().getPk());
				dto.setStoreName(sessions.getStoreDto().getName());
				restCode = facadeService.addPlant(dto,sessions.getMemberPk());
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode.toJson();

	}

	/**
	 * 删除 
	 * 
	 * @param isDelete  是否删除 1否2是 
	 * @return
	 */
	@RequestMapping(value = "updatePlant", method = RequestMethod.POST)
	public String updatePlant(HttpServletRequest req) {
		RestCode restCode = RestCode.CODE_0000;
		B2bPlant model = new B2bPlant();
		model.bind(req);
		Integer result = b2bPlantService.update(model);
		if (result == 0) {
			restCode = RestCode.CODE_A001;
		}
		return restCode.toJson();
	}

	/**
	 * 获取单个厂区详情
	 */
	@RequestMapping(value = "getPlantByPk", method = RequestMethod.POST)
	public String getPlantByPk(HttpServletRequest req) {
		String pk = ServletUtils.getStringParameter(req, "pk", "");
		B2bPlantDto dto = b2bPlantService.getByPk(pk);
		return RestCode.CODE_0000.toJson(dto);
	}

	/**
	 * 公司厂区列表 
	 * @param request
	 * @param limit -1 :全部
	 * @return
	 */
	@RequestMapping(value = "searchPlantList", method = RequestMethod.POST)
	public String searchPlantList(HttpServletRequest request) {
		String result = null ;
		Sessions sessions = this.getSessions(request);
		if(null==sessions.getCompanyDto()){
			result= RestCode.CODE_M008.toJson();
		}else{
			Map<String, Object> map = this.paramsToMap(request);
			try {
				map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
				map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
				map.put("storePk", sessions.getStoreDto()==null||sessions.getStoreDto().getPk()==null?"":sessions.getStoreDto().getPk());
				map.put("isDelete", 1);
				PageModel<B2bPlantDtoEx> pm = b2bPlantService.searchPlantList(map);
				result =  RestCode.CODE_0000.toJson(pm);
			} catch (Exception e) {
				e.printStackTrace();
				result = RestCode.CODE_S999.toJson();
			}
		}
		return result;
	}
	

}
