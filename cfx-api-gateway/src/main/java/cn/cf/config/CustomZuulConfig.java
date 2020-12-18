package cn.cf.config;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.boot.autoconfigure.web.ServerProperties;  
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  

  
@Configuration  
public class  CustomZuulConfig{  
  
    @Autowired  
    ZuulProperties zuulProperties;  
    @Autowired  
    ServerProperties server;  

  
    @Bean  
    public CustomRouteLocator routeLocator() {  
        CustomRouteLocator routeLocator = new CustomRouteLocator(this.server.getServletPrefix(), this.zuulProperties);  
        return routeLocator;  
    }  
  
}  