package cn.cf.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
import cn.cf.service.foreign.B2bTokenService;
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
@RequestMapping("economics") 
public class VerificationController {

	@Autowired
	private B2bTokenService tokenService;

	/**
	 * 获取token接口
	 * @param request
	 * @return
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
				B2bTokenDto b2btoken = tokenService.searchToken(map);
				if (b2btoken != null) {
					SortedMap<Object, Object> parameters = this.mySignByParameters(b2btoken);
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
			return RestCode.CODE_A001.toJson();
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
}
