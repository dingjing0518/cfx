package cn.cf;


import static cn.cf.util.EncodeUtil.des3Decrypt3Base64;
import static cn.cf.util.EncodeUtil.des3Encrypt3Base64;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import cn.cf.tools.ChangeRequestWrapper;
import cn.cf.util.SpringContextsUtil;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class StatisticsFilter extends ZuulFilter {

    @Override
    public boolean shouldFilter() {

//        RequestContext contenxt = RequestContext.getCurrentContext();
//        HttpServletRequest request = contenxt.getRequest();
//        String requestURI = request.getRequestURI();
        
        Boolean flag = true;
//        if(requestURI.contains("Notice")){
//        	flag = false;
//        }else if(requestURI.contains("accessToken")){
//        	if(!requestURI.contains("member")){
//        		flag = false;
//        	}else{
//        		flag = true;
//        	}
//        }else if(requestURI.contains("updateOrderAvailableWeight") || requestURI.contains("updateOrderShiped")
//        		|| requestURI.contains("updateOrderById") || requestURI.contains("updateSubOrderType")
//        		|| requestURI.contains("getCorrespondenceInfo")){
//        	flag = false;
//        }
        return flag;
    }

	@Override
    public Object run() {

        RequestContext contenxt = RequestContext.getCurrentContext();
        HttpServletRequest request = contenxt.getRequest();
        HttpServletResponse response = contenxt.getResponse();
        String queryString = request.getParameter("encodeData");

        if (queryString != null && !queryString.isEmpty()) {
            Map<String, String[]> parameterMap = new HashMap<>(request.getParameterMap());
            String json = "";
            String[] strings = new String[0];
            try {
                strings = des3Decrypt3Base64(queryString);

            } catch (Exception e) {
                e.printStackTrace();
                try {
                    PrintWriter writer = response.getWriter();
                    writer.write(des3Encrypt3Base64("{\"msg\", \"服务器内部错误\",\"code\", \"500\"}"));
                    writer.flush();
                    writer.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            if (strings != null && strings.length > 1 && strings[1] != null) {
                json = strings[1];
            } else {
                try {
                    PrintWriter writer = response.getWriter();
                    writer.write(des3Encrypt3Base64("{\"msg\", \"服务器内部错误\",\"code\", \"500\"}"));
                    writer.flush();
                    writer.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            if (json.length() > 3) {
                JSONObject jsonObject = JSONObject.parseObject(json);
                for (String key:jsonObject.keySet()) {
                    String value = jsonObject.getString(key);
                    parameterMap.put(key, new String[]{(String) value});
                }
            }

            ChangeRequestWrapper changeRequestWrapper = new ChangeRequestWrapper((HttpServletRequest) request);
            changeRequestWrapper.setParameterMap(parameterMap);
            contenxt.setRequest(changeRequestWrapper);
            StatisticsService service = SpringContextsUtil.getApplicationContext().getBean("statisticsService", StatisticsService.class);
            service.setRequest(changeRequestWrapper);
            service.run();
            return null;
        }else
        {
            StatisticsService service = SpringContextsUtil.getApplicationContext().getBean("statisticsService", StatisticsService.class);
            service.setRequest(request);
            service.run();
            return null;
        }


    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -2;
    }

}
