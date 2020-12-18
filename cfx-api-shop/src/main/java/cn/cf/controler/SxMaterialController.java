package cn.cf.controler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.dto.SxMaterialDto;
import cn.cf.dto.SxSpecDto;
import cn.cf.dto.SxTechnologyDto;
import cn.cf.service.SxMaterialService;
import cn.cf.utils.BaseController;

/***
 *
 * @author zlb
 * @describe:原料构成和工艺    接口汇总
 * @time:2019-05-05
 */
@RestController
@RequestMapping("/shop")
public class SxMaterialController  extends BaseController {
	@Autowired
	private SxMaterialService materialService;
	/**
	 *工艺列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchSxTechnologyList", method = RequestMethod.POST)
	public String searchSxTechnologyList(HttpServletRequest request) {
		String result = null;
		try {
			Map<String, Object> map = this.paramsToMap(request);
			List<SxTechnologyDto> list = materialService.searchTechnologyList(map);
			result = RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	/**
	 *原材料构成列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchSxMaterialList", method = RequestMethod.POST)
	public String searchSxMaterialList(HttpServletRequest request) {
		String result = null;
		try {
			Map<String, Object> map = this.paramsToMap(request);
			List<SxMaterialDto> list = materialService.searchSxMaterialList(map);
			result = RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	
	  /**
		 * 纱线规格
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "getSxSpecList", method = RequestMethod.POST)
	    public String getSxSpecList(HttpServletRequest request) {
			String result = null;
			try {
				Map<String, Object> map = this.paramsToMap(request);
				List<SxSpecDto> list = materialService.getSxSpecList(map);
				result = RestCode.CODE_0000.toJson(list);
			} catch (Exception e) {
				e.printStackTrace();
				result = RestCode.CODE_S999.toJson();
			}
			return result;
		}

}
