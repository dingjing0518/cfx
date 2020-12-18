package cn.cf;

import org.springframework.stereotype.Component;

import cn.cf.util.SpringContextsUtil;

@Component
public class HandlerFactory {
	public static Handler createHandler(){
		Handler pv=SpringContextsUtil.getApplicationContext().getBean("pvhandler",Handler.class);  
	    Handler uv=SpringContextsUtil.getApplicationContext().getBean("uvhandler",Handler.class);  
	    pv.setSuccessor(uv);
	    return pv;
	}
}
