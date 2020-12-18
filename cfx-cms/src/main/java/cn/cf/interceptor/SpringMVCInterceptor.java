/**
 * 
 */
package cn.cf.interceptor;

import cn.cf.common.utils.JedisMaterialUtils;
import cn.cf.entity.MangoBackstageInfo;
import cn.cf.model.ManageAccount;
import cn.cf.util.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author bin
 * 
 */
public class SpringMVCInterceptor implements HandlerInterceptor {
	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 39. * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，
	 * 也就是DispatcherServlet渲染了视图执行， 40. *
	 * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
	 * 41.
	 */

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 24. *
	 * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的
	 * ，它的执行时间是在处理器进行处理之 25. *
	 * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行
	 * ，也就是说在这个方法中你可以对ModelAndView进行操 26. *
	 * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用
	 * ，这跟Struts2里面的拦截器的执行过程有点像， 27. *
	 * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法
	 * ，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor 28. *
	 * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前
	 * ，要在Interceptor之后调用的内容都写在调用invoke方法之后。
	 **/
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object object, ModelAndView mv)
			throws Exception {


	}

	/**
	 * 11. * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
	 * SpringMVC中的Interceptor拦截器是链式的，可以同时存在 12. *
	 * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行
	 * ，而且所有的Interceptor中的preHandle方法都会在 13. *
	 * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的
	 * ，这种中断方式是令preHandle的返 14. * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。 15.
	 */

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {
		String sessionId = request.getSession().getId();
		ManageAccount adto = JedisMaterialUtils.get(sessionId, ManageAccount.class);
		//每次操作会重新刷新session失效时间
		if (adto != null) {
			JedisMaterialUtils.set(sessionId, adto, 3600);
		}
		saveLogs(request,adto);
		if (null == adto) {
			String XRequested =request.getHeader("X-Requested-With");
			if("XMLHttpRequest".equals(XRequested)){
				response.getWriter().write("IsAjax"); //处理Ajax请求
			}else {
				response.sendRedirect("./login.do");//客户端跳转
			}
			return false;
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	private void saveLogs(HttpServletRequest request, ManageAccount adto) {
		
		String url = request.getRequestURL().toString();
		Map<String, String[]> params = request.getParameterMap();
		if (null != params && params.size() > 0) {
			String queryString = "";
			for (String key : params.keySet()) {
				String[] values = params.get(key);
				for (int i = 0; i < values.length; i++) {
					String value = values[i];
					queryString += key + "=" + value + "&";
				}
			}
			if (null != queryString && !"".equals(queryString)) {
				url += "?" + queryString;
			}
		}
		String accountName = null;
		String accountPk = null;
		if (null != adto) {
			accountName = adto.getName();
			accountPk = adto.getPk();
		}
		
		Date  date = new Date();
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

		try {
			MangoBackstageInfo backInfo = new MangoBackstageInfo();
			backInfo.setId(KeyUtils.getUUID());
			backInfo.setUrl(url);
			backInfo.setAccountName(accountName);
			backInfo.setAccountPk(accountPk);
			backInfo.setInsertTime(dateStr);
			mongoTemplate.save(backInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
