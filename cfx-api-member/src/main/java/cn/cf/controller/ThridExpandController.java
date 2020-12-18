package cn.cf.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.dto.SysRegionsDto;
import cn.cf.entry.RegionsMamge;
import cn.cf.service.SysRegionsService;
import cn.cf.service.VersionService;
import cn.cf.util.ServletUtils;

/**
 * 第三方拓展接口
 * 
 * @author:FJM
 * @describe:
 * @time:2017-6-5 上午10:56:21
 */
@RestController
@RequestMapping("member")
public class ThridExpandController {

	@Autowired
	private SysRegionsService sysRegionsService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private VersionService versionService;

	/**
	 * 获取地区级联列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "regions")
	public String getRegions(HttpServletRequest req) {
		try {
			String parentPk = ServletUtils.getStringParameter(req, "parentPk","-1");
			List<SysRegionsDto> list = sysRegionsService.getSysregisList("".equals(parentPk)?"-1":parentPk);
			return RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
	
	
	/**
	 * 获取地区级联列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "regionsAll")
	public String getRegionAll(HttpServletRequest req) {
		try {
			RegionsMamge dto = mongoTemplate.findOne(new Query(Criteria.where("getRegionsNumber").is("get_cf_sys_regions")), RegionsMamge.class);
			return RestCode.CODE_0000.toJson(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
	
	/**
	 * 获取app版本号
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "getVersion")
	public String getVersion(HttpServletRequest request, HttpServletResponse response)  {
			String type = ServletUtils.getStringParameter(request, "type", "");
			return RestCode.CODE_0000.toJson(versionService.searchVersionRecent(type));

		}
	}

