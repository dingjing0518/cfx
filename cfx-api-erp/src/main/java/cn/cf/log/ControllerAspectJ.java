package cn.cf.log;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;



@Aspect
@Component
@Order(1)  
public class ControllerAspectJ {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());  
    
    private String requestPath = null ; // 请求地址  
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
    @Before("execution(* cn.cf.controller..*.*(..))")  
    public void doBeforeInServiceLayer(JoinPoint joinPoint) {  
        startTimeMillis = System.currentTimeMillis(); // 记录方法开始执行的时间  
      
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
    @After("execution(* cn.cf.controller..*.*(..))")  
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
    @Around("execution(* cn.cf.controller..*.*(..))")  
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {  
   
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;  
        HttpServletRequest request = sra.getRequest();  
       
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
        
        String optTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTimeMillis);  
        logger.info("  url："+requestPath+"; op_time：" + optTime + " pro_time：" + (endTimeMillis - startTimeMillis) + "ms ;"  
                +" param："+JSON.toJSONString(inputParamMap)+";"+"\n result："+JSON.toJSONString(outputParamMap));  
    }
    
}
