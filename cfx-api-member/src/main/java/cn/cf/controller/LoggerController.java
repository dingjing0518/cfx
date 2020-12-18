package cn.cf.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.entity.Sessions;
import cn.cf.entry.MangoInterfaceInfo;
import cn.cf.entry.MangoOperationInfo;
import cn.cf.service.MangoService;
import cn.cf.util.CusAccessObjectUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/**
 * @author:FJM
 * @describe:日志记录接口
 * @time:2017-5-23 上午9:46:43
 */
@RestController
@RequestMapping("/member")
public class LoggerController extends BaseController {

	@Autowired
	private MangoService mangoService;

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 插入用户访问日志接口
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "insertMango", method = RequestMethod.POST)
	public String insertMango(HttpServletRequest req) {
		Sessions session = this.getSessions(req);
		String userId = "";
		if (session != null) {
			userId = session.getMemberPk();
		}
		String sessionId = ServletUtils.getStringParameter(req, "sessionId", "");
		String url = ServletUtils.getStringParameter(req, "url","");
		String details = ServletUtils.getStringParameter(req, "details", "");
		String information = ServletUtils.getStringParameter(req,"information", "");
		String type = ServletUtils.getStringParameter(req,"type", "");
		if (url == null || "".equals(url)) {
			return RestCode.CODE_A001.toJson();
		}
		MangoOperationInfo mangoOperationInfo = new MangoOperationInfo();
		mangoOperationInfo.setUserId(userId);
		mangoOperationInfo.setSessionId(sessionId);
		mangoOperationInfo.setUrl(url);
		mangoOperationInfo.setDetails(details);
		mangoOperationInfo.setInsertTime(DateUtil.formatDateAndTime(new Date()));
		mangoOperationInfo.setIp(CusAccessObjectUtil.getIpAddress(req));
		mangoOperationInfo.setInformation(information);
		mangoOperationInfo.setType(type);
		try {
			mangoService.insertMangoOperationInfo(mangoOperationInfo);
			return RestCode.CODE_0000.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}

	}

	/**
	 * 查询日志
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "findMango", method = RequestMethod.POST)
	public String findMango(HttpServletRequest req) {
		// MangoInterfaceInfo info = new MangoInterfaceInfo();
		// info.setId(req.getParameter("id").toString());
		// mongoTemplate.remove(info);
		List<MangoInterfaceInfo> list = mongoTemplate
				.findAll(MangoInterfaceInfo.class);
		return RestCode.CODE_0000.toJson(list);
	}
}
