package cn.cf.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.common.RestCode;
import cn.cf.utils.VerifyUtils;



/**
 * @author bin 拦截所有的B2B的地址 做token，Sign 验证
 */
public class SignInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {


	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {


	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		response.setContentType("application/json;charset=utf-8");

		boolean falge = VerifyUtils.verifySign(request);
		if (!falge) {
			response.getWriter().append(RestCode.CODE_S002.toJson());
		}
		return falge;
	}

}
