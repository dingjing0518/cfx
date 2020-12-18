package cn.cf.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bTokenDto;
import cn.cf.entity.Sessions;
import cn.cf.jedis.JedisUtils;
import cn.cf.util.ServletUtils;
import cn.cf.util.SignUtils;

public class VerifyUtils {

	public static boolean verifySession(HttpServletRequest request) {
		boolean a = true;
		String sessionId = ServletUtils.getStringParameter(request,"sessionId", "");
		if (!"".equals(sessionId)) {
			a = JedisUtils.existsObject(sessionId);
			if (a) {
				Sessions session = JedisUtils.get(sessionId, Sessions.class);
				if (null != session.getCompanyDto()) {
						B2bCompanyDto dto = session.getCompanyDto();
						if (null != dto && null != dto.getAuditSpStatus()
								&& dto.getAuditSpStatus() == 2) {
							B2bStoreDto storeDto =session.getStoreDto();
							if (null==storeDto) {
								JedisUtils.del(sessionId);
							}
						}
				}
			}
		} else {
			a = false;
		}
		return a;
	}
	
	
	/**
	 * 物流系统验证session
	 * @param request
	 * @return
	 */
	public static boolean verifySessionLg(HttpServletRequest request) {
		boolean a = true;
		String sessionId = ServletUtils.getStringParameter(request,"sessionId", "");
		if (!"".equals(sessionId)) {
			a = JedisUtils.existsObject(sessionId);
		} else {
			a = false;
		}
		return a;
	}
	

	public static boolean verifyToken(HttpServletRequest request) {
		boolean falge = true;
		// //验证token
		String token = ServletUtils.getStringParameter(request, "token");
		// System.out.println("token-----------"+token);
		if (token != null && !"".equals(token)) {
			B2bTokenDto b2btoken = JedisUtils.get(token, B2bTokenDto.class);
			if (b2btoken == null) {
				falge = false;
			} else {
				falge = true;
			}
		} else {
			falge = false;
		}
		return falge;

	}

	public static boolean verifySign(HttpServletRequest request) {
		boolean falge = true;
		// b2b接口做签名验证
		// 验证比传参数
		Map<Object, Object> map = SignUtils.paramsToTreeMap(request
				.getParameterMap());
		// System.out.println("sign"+map.get("sign"));
		if (map.get("sign") == null || "".equals(map.get("sign"))) {
			falge = false;
		} else {
			String correctSign = SignUtils.createSign("utf-8", map);
			// System.out.println("correctSign"+correctSign);
			if (!correctSign.equals(request.getParameter("sign").toString())) {
				falge = false;
			}
		}
		return falge;

	}
}
