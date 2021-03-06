package cn.cf.aspect;

import static cn.cf.common.EncodeUtil.des3Encrypt3Base64;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class HTTPAspect {
	private static final String RESPONSE_CHARSET = "UTF-8";

	@Pointcut("execution(* cn.cf.controller.*.*(..))")
	public void code() {

	}

	@AfterReturning(pointcut = "code()", returning = "returnValue")
	public void returnValue(Object returnValue) {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getResponse();// 获取response
		response.setCharacterEncoding(RESPONSE_CHARSET);
		response.setContentType("application/text;charset=utf-8");
		response.resetBuffer();
		try {
			String s = des3Encrypt3Base64(returnValue.toString());
			response.setContentLength(s.length());
			response.getOutputStream().write(s.getBytes());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
