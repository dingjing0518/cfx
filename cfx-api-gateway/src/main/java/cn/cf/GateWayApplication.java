package cn.cf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

import cn.cf.util.SpringContextsUtil;


@EnableZuulProxy
@EnableAspectJAutoProxy
@SpringBootApplication
@ComponentScan("cn.cf")
@PropertySource(value = "classpath:server.properties",encoding = "utf-8")
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }
    @Bean
    public SpringContextsUtil springContextsUtil() {
        return new SpringContextsUtil();
    }
}
