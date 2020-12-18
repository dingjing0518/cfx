/**
 * 
 */
package cn.cf.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import cn.cf.constant.Source;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bTokenDto;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LgMemberDto;
import cn.cf.entity.Sessions;
import cn.cf.jedis.JedisUtils;
import cn.cf.util.EncodeUtil;
import cn.cf.util.ServletUtils;

/**
 * @author bin
 * 
 */
public class BaseController {

	protected Sessions getSessions(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String sessionId = ServletUtils.getStringParameter(request,
				"sessionId", "");
		return JedisUtils.get(sessionId, Sessions.class);
	}

	protected B2bCompanyDto getCompanyBysessionsId(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String sessionId = ServletUtils.getStringParameter(request,
				"sessionId", "");
		Sessions session = JedisUtils.get(sessionId, Sessions.class);
		B2bCompanyDto c = null;
		if (null != session) {
			c = session.getCompanyDto();
		}
		return c;
	}

	protected B2bMemberDto getMemberBysessionsId(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String sessionId = ServletUtils.getStringParameter(request,
				"sessionId", "");
		Sessions session = JedisUtils.get(sessionId, Sessions.class);
		B2bMemberDto m = null;
		if (null != session && null != session.getMemberPk()
				&& !"".equals(session.getMemberPk())) {
			m = session.getMemberDto();
		}
		return m;
	}

	protected B2bStoreDto getStoreBysessionsId(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String sessionId = ServletUtils.getStringParameter(request,
				"sessionId", "");
		Sessions session = JedisUtils.get(sessionId, Sessions.class);
		B2bStoreDto m = null;
		if (null != session) {
			m = session.getStoreDto();
		}
		return m;
	}
	protected LgCompanyDto getLgCompanyBysessionsId(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String sessionId = ServletUtils.getStringParameter(request,
				"sessionId", "");
		Sessions session = JedisUtils.get(sessionId, Sessions.class);
		LgCompanyDto c = null;
		if (null != session && null != session.getCompanyPk()
				&& !"".equals(session.getCompanyPk())) {
			c = JedisUtils.get(session.getCompanyPk(), LgCompanyDto.class);
		}
		return c;
	}


	protected LgMemberDto getLgMemberBysessionsId(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String sessionId = ServletUtils.getStringParameter(request,
				"sessionId", "");
		Sessions session = JedisUtils.get(sessionId, Sessions.class);
		LgMemberDto m = null;
		if (null != session && null != session.getMemberPk()
				&& !"".equals(session.getMemberPk())) {
			m = JedisUtils.get(session.getMemberPk(), LgMemberDto.class);
		}
		return m;
	}
	protected Map<String, Object> paramsToMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Set<Entry<String, String[]>> set = request.getParameterMap().entrySet();
		Iterator<Entry<String, String[]>> it = set.iterator();
		while (it.hasNext()) {
			Entry<String, String[]> entry = it.next();
			for (String i : entry.getValue()) {
				if (!"token".equals(entry.getKey())
						&& !"sign".equals(entry.getKey())
						&& !"sessionId".equals(entry.getKey())) {
					if ("start".equals(entry.getKey())
							|| "limit".equals(entry.getKey())) {
						int start = 0;
						try {
							if (Integer.valueOf(EncodeUtil.URLDecode(i)) > 0) {
								start = Integer
										.valueOf(EncodeUtil.URLDecode(i));
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block

						}
						map.put(entry.getKey(), start);
					} else {
						map.put(entry.getKey(), EncodeUtil.URLDecode(i));
					}

				}

			}
		}
		return map;
	}

	protected Integer getSource(HttpServletRequest request) {
		B2bTokenDto dto = JedisUtils.get(request.getParameter("token"),
				B2bTokenDto.class);
		Integer source = null;
		if (null != dto) {
			source = Source.getByTokenType(dto.getTokenType()).getSource();
		} else {
			source = 1;
		}
		return source;
	}
}
