package cn.cf.common;

import javax.servlet.http.HttpServletRequest;

import cn.cf.common.utils.JedisMaterialUtils;

public class Global {
	public static final String SESSION_USER = "SESS_USER";

	public static final void removeAllSession(HttpServletRequest request) {
		String sessionId = request.getSession().getId();
		JedisMaterialUtils.del(sessionId);
	}

}
