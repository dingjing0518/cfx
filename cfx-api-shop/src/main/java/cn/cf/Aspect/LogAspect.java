package cn.cf.Aspect;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.cf.constant.Source;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.entity.Sessions;
import cn.cf.jedis.JedisUtils;
import cn.cf.util.ServletUtils;
  
 
  
/** 
 *  
* @ClassName: LogAspect  
* @Description: 日志记录AOP实现  
* @author wuqp
* @date 2018年4月13日 下午1:51:59  
* 
 */  
@Aspect
@Component
public class LogAspect {  
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());  
  
    private String requestPath = null ; // 请求地址  
    private String userName = null ; // 用户名  
    private Map<?,?> inputParamMap = null ; // 传入参数  
    private Map<String, Object> outputParamMap = null; // 存放输出结果  
    private long startTimeMillis = 0; // 开始时间  
    private long endTimeMillis = 0; // 结束时间  
  
    /** 
     *  
     * @Title：doBeforeInServiceLayer 
     * @Description: 方法调用前触发  
     *  记录开始时间  
     * @author wuqp 
     * @date 2018年4月13日 下午4:45:53 
     * @param joinPoint 
     */  
    @Before("execution(* cn.cf.controler..*.*(..))")  
    public void doBeforeInServiceLayer(JoinPoint joinPoint) {  
        startTimeMillis = System.currentTimeMillis(); // 记录方法开始执行的时间  
        expireSession(joinPoint);//维护登录session
    }  
  
    /** 
     *  
     * @Title：doAfterInServiceLayer 
     * @Description: 方法调用后触发  
     *  记录结束时间 
     * @author wuqp 
     * @date 2018年4月13日 下午4:45:53  
     * @param joinPoint 
     */  
    @After("execution(* cn.cf.controler..*.*(..))")  
    public void doAfterInServiceLayer(JoinPoint joinPoint) {  
        endTimeMillis = System.currentTimeMillis(); // 记录方法执行完成的时间  
        this.printOptLog();  
    }  
  
    /** 
     *  
     * @Title：doAround 
     * @Description: 环绕触发  
     * @author wuqp  
     * @date 2018年4月13日 下午4:45:53 
     * @param pjp 
     * @return 
     * @throws Throwable 
     */  
    @Around("execution(* cn.cf.controler..*.*(..))")  
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {  
        /** 
         * 1.获取request信息 
         * 2.根据request获取session 
         * 3.从session中取出登录用户信息 
         */  
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;  
        HttpServletRequest request = sra.getRequest();  
        // 从session中获取用户信息  
        Sessions session=null;
        String sessionId = ServletUtils.getStringParameter(request,"sessionId", "");
		boolean a = JedisUtils.existsObject(sessionId);
		if (a) {
			session = JedisUtils.get(sessionId, Sessions.class);
			userName=session.getMobile();
		}else{
			userName = "用户未登录" ;  
		}
        // 获取输入参数  
        inputParamMap = request.getParameterMap();  
        // 获取请求地址  
        requestPath = request.getRequestURI();  
          
        // 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行  
        outputParamMap = new HashMap<String, Object>();  
        Object result = pjp.proceed();// result的值就是被拦截方法的返回值  
        outputParamMap.put("result", result);  
          
        return result;  
    }  
  
    /** 
     *  
     * @Title：printOptLog 
     * @Description: 输出日志  
     * @author wuqp
     * @date 2018年4月13日 下午4:45:53  
     */  
    private void printOptLog() {  
        
//        String optTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTimeMillis);  
//        logger.info("\n user："+userName  
//                +"  url："+requestPath+"; op_time：" + optTime + " pro_time：" + (endTimeMillis - startTimeMillis) + "ms ;"  
//                +" param："+JSON.toJSONString(inputParamMap)+";"+"\n result："+JSON.toJSONString(outputParamMap));  
    }
    
    
	public void expireSession(JoinPoint joinPoint) {
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
			Sessions session = JedisUtils.get(sessionid, Sessions.class);
			//维护session
			JedisUtils.expire(sessionid, Source.getBySource(session.getSource()).getSessionTime());

			Object oldSession = JedisUtils.get(Source.getBySource(session.getSource()).getSourceType()+session.getMemberDto().getMobile());
			if(!(oldSession instanceof Boolean)){
				JedisUtils.expire(Source.getBySource(session.getSource()).getSourceType()+session.getMemberDto().getMobile(), Source.getBySource(session.getSource()).getSessionTime());
			}
		}
	}
}  
