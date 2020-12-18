package cn.cf.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bTokenDto;
import cn.cf.jedis.JedisUtils;
import cn.cf.service.SysService;
import cn.cf.util.DateUtil;
import cn.cf.util.SignUtils;
import cn.cf.utils.BaseController;

@RestController
public class VerificationController extends BaseController{

	@Autowired
	private SysService sysService;

	/**
	 * 获取token接口
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "accessToken", method = RequestMethod.POST)
	public String accessToken(String appId, String appSecret) {
		String characterEncoding = "UTF-8";
		Map<String, Object> map = new HashMap<String, Object>();
		String effectiveTime = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
		if (appId == null || "".equals(appId)) {
			return RestCode.CODE_A001.toJson();
		}
		if (appSecret == null || "".equals(appSecret)) {
			return RestCode.CODE_A001.toJson();
		}
		map.put("appId", appId);
		map.put("appSecret", appSecret);
		B2bTokenDto b2btoken = sysService.searchToken(map);
		if (b2btoken != null) {
			SortedMap<Object, Object> parameters = sysService
					.mySignByParameters(b2btoken);
			parameters.put("time", DateUtil.formatDateAndTime(new Date()));
			String token = SignUtils.createSign(characterEncoding, parameters);
			if (!"".equals(token)) {
				JedisUtils.set(token, b2btoken,DateUtil.getSurplusSeconds(effectiveTime));
			}
		}
		return RestCode.CODE_0000.toJson(b2btoken);
	}
	
	
    /**
     * 测试接口
     * @param req
     * @return
     */
    @RequestMapping(value = "sign", method = RequestMethod.POST)
    public String test(HttpServletRequest request) {
    	String sign=SignUtils.createSign("utf-8",  SignUtils.paramsToTreeMap(request
				.getParameterMap()));
//    	Map<String, Object> map = this.paramsToMap(request);
//   	System.out.println("=====================token:"+map.get("token").toString()); 
//    	System.out.println("=====================sign:"+map.get("sign").toString()); 
//    	System.out.println("=====================sessionId:"+map.get("sessionId").toString()); 
    	return sign;
          
    }
	
}
