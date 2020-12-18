package cn.cf;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
@Service(value="statisticsService")
public class StatisticsService implements Runnable{

	private  ExecutorService pool;
	
	private HttpServletRequest request;
	
	
	
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public StatisticsService(){
		pool = Executors.newCachedThreadPool(); 
	}
	@Override
	public void run() {
		if(threadPoolIsRunning()) {
			 pool.execute(new StatisticsThread(request)); 
	     }
			
		 
	}
	
	 private boolean threadPoolIsRunning() {
	       return !pool.isShutdown();
	    }

}
