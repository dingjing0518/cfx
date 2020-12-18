package cn.cf.Aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.entity.Sessions;
import cn.cf.jedis.JedisUtils;
import cn.cf.property.PropertyConfig;

/**
 * @author:FJM
 * @describe:AOP配置日志
 * @time:2017-5-23 下午6:42:50
 */
@Aspect
@Component
public class SessionAspect {
	
	private static final String LOGIN_CODE = "login";

	@Pointcut("execution(* cn.cf.controller..*.*(..))")
	public void logs() {

	}

	@Before("logs()")
	public void doBeforeAdvice(JoinPoint joinPoint) {
		RequestAttributes requestAttributes = RequestContextHolder
				.getRequestAttributes();
		// //从获取RequestAttributes中获取HttpServletRequest的信息
		HttpServletRequest request = (HttpServletRequest) requestAttributes
				.resolveReference(RequestAttributes.REFERENCE_REQUEST);
		// //如果要获取Session信息的话，可以这样写：
		// HttpSession session = (HttpSession)
		// requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
		String sessionid = request.getParameter("sessionId");
		boolean a = JedisUtils.existsObject(sessionid);
		if (a) {
			JedisUtils.expire(sessionid, PropertyConfig.getIntProperty("session_time",3600));
			Sessions session = JedisUtils.get(sessionid, Sessions.class);
			JedisUtils.expire(session.getMemberPk(), PropertyConfig.getIntProperty("session_time",3600));
			JedisUtils.expire(session.getCompanyPk(), PropertyConfig.getIntProperty("session_time",3600));
			if(null!=session.getCompanyPk()&&!"".equals(session.getCompanyPk())){
				B2bCompanyDtoEx cdto = JedisUtils.get(session.getCompanyPk(), B2bCompanyDtoEx.class);
				if (null!=cdto&&null != cdto.getStorePk()
						&& !"".equals(cdto.getStorePk())) {
					JedisUtils.expire(cdto.getStorePk(), PropertyConfig.getIntProperty("session_time",3600));
				}
			}
			//单点登陆限制
			B2bMemberDto member  = JedisUtils.get(session.getMemberPk(), B2bMemberDto.class);
			Object oldSession = JedisUtils.get(LOGIN_CODE+member.getMobile());
			if(!(oldSession instanceof Boolean)){
				JedisUtils.expire(LOGIN_CODE+member.getMobile(), PropertyConfig.getIntProperty("session_time",3600));
			}
		}
	}
}
