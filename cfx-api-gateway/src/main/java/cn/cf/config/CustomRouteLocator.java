package cn.cf.config;



import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.cf.json.JsonUtils;
  
public class CustomRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {  
  
    public final static Logger logger = LoggerFactory.getLogger(CustomRouteLocator.class);  
  
  
    private ZuulProperties properties;  

  
    public CustomRouteLocator(String servletPath, ZuulProperties properties) {  
        super(servletPath, properties);  
        this.properties = properties;  
        //logger.info("servletPath:{}", servletPath);  
    }  
  
  
    @Override  
    public void refresh() {  
        doRefresh();  
    }  
  
    @Override  
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {  
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();  

        //从json文件加载路由信息
        routesMap.putAll(locateRoutesFromJson());
        
        //优化一下配置  
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();  
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {  
            String path = entry.getKey();  
      
            if (!path.startsWith("/")) {  
                path = "/" + path;  
            }  
            if (StringUtils.hasText(this.properties.getPrefix())) {  
                path = this.properties.getPrefix() + path;  
                if (!path.startsWith("/")) {  
                    path = "/" + path;  
                }  
            }  
            values.put(path, entry.getValue());  
        }  
        return values;  
    }  
    
	private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromJson() {
		Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();
		List<ZuulRouteVO> results = getFromJson();

		for (ZuulRouteVO result : results) {
			if (StringUtils.isEmpty(result.getPath())) {
				continue;
			}
			if (StringUtils.isEmpty(result.getServiceId()) && StringUtils.isEmpty(result.getUrl())) {
				continue;
			}
			ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
			try {
				BeanUtils.copyProperties(result, zuulRoute);
			} catch (Exception e) {
				logger.error("=============from json with error==============", e);
			}
			routes.put(zuulRoute.getPath(), zuulRoute);
		}
		return routes;
	}
  
    @SuppressWarnings("deprecation")
	private List<ZuulRouteVO> getFromJson() {

    	 ClassLoader classLoader = getClass().getClassLoader();  
    	 
    

		List<ZuulRouteVO>list=null;
		JSONArray jsonArray = null;
		try {
			String content=IOUtils.toString(classLoader.getResourceAsStream("gateway.json"));
			JSONObject jsonObject = JsonUtils.toJSONObject(content);
			if (jsonObject != null) {
				jsonArray = jsonObject.getJSONArray("list");
				list=new ArrayList<ZuulRouteVO>();
				
				for(int i=0;i<jsonArray.size();i++){
					
					ZuulRouteVO a=JsonUtils.toBean(jsonArray.get(i).toString(), ZuulRouteVO.class);
					list.add(a);
				}
				
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonArray = null;
		}
    	
		return list;
	}

	public static class ZuulRouteVO {  
  
     
        private String id;  
  
        
        private String path;  
  
       
        private String serviceId;  
  
       
        private String url;  
  
       
        private boolean stripPrefix = true;  
  
       
        private Boolean retryable;  
  
        private Boolean enabled;  
  
        public String getId() {  
            return id;  
        }  
  
        public void setId(String id) {  
            this.id = id;  
        }  
  
        public String getPath() {  
            return path;  
        }  
  
        public void setPath(String path) {  
            this.path = path;  
        }  
  
        public String getServiceId() {  
            return serviceId;  
        }  
  
        public void setServiceId(String serviceId) {  
            this.serviceId = serviceId;  
        }  
  
        public String getUrl() {  
            return url;  
        }  
  
        public void setUrl(String url) {  
            this.url = url;  
        }  
  
        public boolean isStripPrefix() {  
            return stripPrefix;  
        }  
  
        public void setStripPrefix(boolean stripPrefix) {  
            this.stripPrefix = stripPrefix;  
        }  
  
        public Boolean getRetryable() {  
            return retryable;  
        }  
  
        public void setRetryable(Boolean retryable) {  
            this.retryable = retryable;  
        }  
  
        public Boolean getEnabled() {  
            return enabled;  
        }  
  
        public void setEnabled(Boolean enabled) {  
            this.enabled = enabled;  
        }  
    }  
}  

