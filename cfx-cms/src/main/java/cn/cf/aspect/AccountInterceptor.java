package cn.cf.aspect;

import javax.servlet.http.HttpServletRequest;

import cn.cf.common.utils.JedisMaterialUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import cn.cf.model.ManageAccount;
import cn.cf.model.MarketingCompany;
/**
 * 对分配业务员进行记录
 * @author hxh
 *
 */

public class AccountInterceptor {
	
	@Pointcut(value = "execution(* cn.cf.controller.MarketingController.updateMarketingCompany(..))")
	public void getAccount(){

	}
	
	
	@Before(value = "getAccount()")
	public void getParams(){
		
	}
	
	@After("getAccount()")
	public void insertInfo(JoinPoint pjp){
		
		MarketingCompany marketingCompany = null;
		HttpServletRequest request = null;
		 for (int i = 0; i < pjp.getArgs().length; i++) {  
			 if(pjp.getArgs()[i]  instanceof MarketingCompany){
	        	 marketingCompany = (MarketingCompany) pjp.getArgs()[i];
	         } 
			 if(pjp.getArgs()[i]  instanceof HttpServletRequest){
	        	 request = (HttpServletRequest) pjp.getArgs()[i];
	         }
	        }
		 ManageAccount account = null;
		 if(request != null){
			 String sessionId = request.getSession().getId();
			 account = JedisMaterialUtils.get(sessionId,ManageAccount.class);
		 }
	}
}
