package cn.cf;

import javax.servlet.http.HttpServletRequest;



public class StatisticsThread implements Runnable{

	
	private HttpServletRequest request;
	
	public StatisticsThread(HttpServletRequest request){
		this.request=request;
	}


	@Override
	public void run() {

		
		String url = request.getRequestURI();
		if("/member/insertMango".equals(url)){
			HandlerFactory.createHandler().handleRequest(request);
		}
	}

}
