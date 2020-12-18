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

import com.alibaba.fastjson.JSONObject;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bTokenDto;
import cn.cf.jedis.JedisUtils;
import cn.cf.json.JsonUtils;
import cn.cf.service.SysService;
import cn.cf.util.DateUtil;
import cn.cf.util.ServletUtils;
import cn.cf.util.SignUtils;

@RestController

@RequestMapping(value = { "/" })
public class VerificationController {

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
	public String accessToken(HttpServletRequest request) {
		String characterEncoding = "UTF-8";
		String encodeData = ServletUtils.getStringParameter(request, "jsonData");
		if (encodeData != null && !"".equals(encodeData)) {
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject json = JSONObject.parseObject(encodeData);
			String appId = json.getString("appId");
			String appSecret = json.getString("appSecret");
			if (appId == null || "".equals(appId)) {
				return RestCode.CODE_A001.toJson();
			}
			if (appSecret == null || "".equals(appSecret)) {
				return RestCode.CODE_A001.toJson();
			}
			
			map.put("appId", appId);
			map.put("appSecret", appSecret);
			Map<String,Object> tokenMap = new HashMap<String,Object>();
			if(JedisUtils.existsObject(appId)){
				 return RestCode.CODE_0000.toJson(JedisUtils.get(appId,Map.class));
			}else{
				B2bTokenDto b2btoken = sysService.searchToken(map);
				if (b2btoken != null) {
					SortedMap<Object, Object> parameters = sysService.mySignByParameters(b2btoken);
					parameters.put("time", DateUtil.formatDateAndTime(new Date()));
					String token = SignUtils.createSign(characterEncoding, parameters);
					if (!"".equals(token)) {
						JedisUtils.set(token, b2btoken,24*3600);
						tokenMap = JsonUtils.toHashMap(b2btoken);
						tokenMap.put("token", token);
						tokenMap.put("effectiveTime", DateUtil.timeOfBefore(-24));
						JedisUtils.set(appId, tokenMap,24*3600);
					}
				}
			}
			return RestCode.CODE_0000.toJson(tokenMap);
		} else {
			return RestCode.CODE_T000.toJson();
		}

	}
}
