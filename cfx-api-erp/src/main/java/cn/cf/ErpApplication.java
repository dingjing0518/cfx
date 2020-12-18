package cn.cf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableAspectJAutoProxy
@MapperScan({"cn.cf.dao"})
@ComponentScan(basePackages = "cn.cf")
@EnableTransactionManagement
@PropertySource(value = "classpath:server.properties",encoding = "utf-8")
@EnableAsync
public class ErpApplication extends SpringBootServletInitializer{
	static{
		System.setProperty("axis.socketSecureFactory", "org.apache.axis.components.net.SunFakeTrustSocketFactory");
	}
	public static void main(String[] args) {
		SpringApplication.run(ErpApplication.class, args);
	}
}
