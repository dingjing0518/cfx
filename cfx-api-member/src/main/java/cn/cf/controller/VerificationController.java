package cn.cf.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import cn.cf.constant.Source;
import cn.cf.utils.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bTokenDto;
import cn.cf.entity.Sessions;
import cn.cf.jedis.JedisUtils;
import cn.cf.json.JsonUtils;
import cn.cf.service.SendSmsService;
import cn.cf.service.CuccSmsService;
import cn.cf.service.TokenService;
import cn.cf.util.DateUtil;
import cn.cf.util.ServletUtils;
import cn.cf.util.SignUtils;
/**
 * 
 * @description:Token管理
 * @author FJM
 * @date 2018-4-11 上午11:15:10
 */
@RestController
@RequestMapping(value={"member","cf_api"}) 
public class VerificationController extends BaseController {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private SendSmsService smsService;

	@Autowired
	private CuccSmsService commonSmsService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
//		String effectiveTime = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
		if (appId == null || "".equals(appId)) {
			return RestCode.CODE_A001.toJson();
		}
		if (appSecret == null || "".equals(appSecret)) {
			return RestCode.CODE_A001.toJson();
		}
		map.put("appId", appId);
		map.put("appSecret", appSecret);
		if(JedisUtils.existsObject(appId)){
			return RestCode.CODE_0000.toJson(JedisUtils.get(appId,Map.class));
		}else{
			B2bTokenDto b2btoken = tokenService.searchToken(map);
			Map<String,Object> tokenMap = null;
			if (b2btoken != null) {
				SortedMap<Object, Object> parameters = this.mySignByParameters(b2btoken);
				parameters.put("time", DateUtil.formatDateAndTime(new Date()));
				String token = SignUtils.createSign(characterEncoding, parameters);
				if (!"".equals(token)) {
					JedisUtils.set(token, b2btoken,24*3600);
					tokenMap = JsonUtils.toHashMap(b2btoken);
					tokenMap.put("token", token);
					JedisUtils.set(appId, tokenMap,24*3600);
				}
			}
			return RestCode.CODE_0000.toJson(tokenMap);
		}
	}
	
	
	public SortedMap<Object, Object> mySignByParameters(B2bTokenDto b2btoken) {
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appId", b2btoken.getAppId());
		parameters.put("appSecret", b2btoken.getAppSecret());
		parameters.put("pk", b2btoken.getPk());
		parameters.put("storePk", b2btoken.getStorePk());
		parameters.put("storeName", b2btoken.getStoreName());
		return parameters;
	}
	
	
	/**
	 * 获取短信验证码接口
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "verificationCode", method = RequestMethod.POST)
	public String verificationCode(HttpServletRequest req) {
		logger.error("----------获取短信验证码start--------------");
		String token = ServletUtils.getStringParameter(req, "token");
		logger.error("token:"+token);
		B2bTokenDto b2bToken = JedisUtils.get(token, B2bTokenDto.class);
		logger.error(JsonUtils.convertToString(b2bToken));
		logger.error("----------获取短信验证码end----------------");
		Integer type =this.getSource(req);
		if(type == 4){
            return RestCode.CODE_S001.toJson();
        }
		String mobile = ServletUtils.getStringParameter(req, "mobile","");
		if("".equals(mobile)){
			return RestCode.CODE_A001.toJson();
		}
		return commonSmsService.sendCuccCode(mobile);
	}
	
	/**
	 * 校验短信验证码接口
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "checkCode", method = RequestMethod.POST)
	public String checkCode(HttpServletRequest req) {
		RestCode restCode = RestCode.CODE_0000;
		String mobile = ServletUtils.getStringParameter(req, "mobile", "");
		String code = ServletUtils.getStringParameter(req, "code", "");
		// 验证手机验证码
		boolean flag =  smsService.checkSmsCode(mobile, code);
		if(!flag){
			restCode = RestCode.CODE_S004;
		}
		return restCode.toJson();
	}
	
	
	/**
	 * 验证session是否有效
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "verificationSession", method = RequestMethod.POST)
	public String verificationSession(HttpServletRequest request){
		String sessionId = ServletUtils.getStringParameter(request,
				"sessionId", "");
		Sessions session = JedisUtils.get(sessionId, Sessions.class);
		if(null == session){
			return RestCode.CODE_S003.toJson();
		}
		return RestCode.CODE_0000.toJson();
	}
	
}
