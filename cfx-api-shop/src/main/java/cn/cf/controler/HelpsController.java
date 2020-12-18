package cn.cf.controler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.cf.common.RestCode;
import cn.cf.dto.SysHelpsCategoryDtoEx;
import cn.cf.dto.SysHelpsDto;
import cn.cf.service.SysHelpService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

@RestController
@RequestMapping("/shop")
public class HelpsController extends BaseController{
	
	@Autowired
	private SysHelpService helpService;

	/**
	 * 左上方菜单
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/helpList", method = RequestMethod.POST)
	public String helpList(HttpServletRequest request) throws Exception {
		Integer showPlace = this.getSource(request);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("status", 2);
		map.put("showPlace", showPlace+"");
		List<SysHelpsCategoryDtoEx> list = helpService.searchAll(map);
		return RestCode.CODE_0000.toJson(list);
	}

	/**
	 * 底部菜单
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/helpMenu", method = RequestMethod.POST)
	public String helpMenu(HttpServletRequest request) throws Exception {
		Integer showPlace = this.getSource(request);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("showType", 1);
		map.put("status", 2);
		map.put("showPlace", showPlace+"");
		List<SysHelpsCategoryDtoEx> list = helpService.searchAll(map);
		return RestCode.CODE_0000.toJson(list);
	}
	
	
	/**
	 * 文章详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/helpDetail", method = RequestMethod.POST)
	public String helpDeatil(HttpServletRequest request) {
		String pk = ServletUtils.getStringParameter(request, "pk", "");
		SysHelpsDto dto = helpService.searchByPk(pk);
		return RestCode.CODE_0000.toJson(dto);
	}

	/**
	 * 关于我们
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/aboutUs", method = RequestMethod.POST)
	public String aboutUs(HttpServletRequest request) {
		Integer showPlace = this.getSource(request);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("showPlace", showPlace+"");
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("status", 2);
		map.put("title","关于我们");
		SysHelpsDto dto = helpService.getAboutUs(map);
		return RestCode.CODE_0000.toJson(dto);
	}
	
	
}
