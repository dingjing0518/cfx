package cn.cf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@SpringBootApplication
@MapperScan({"cn.cf.dao"})
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ServletComponentScan
@PropertySource(value = "classpath:server.properties",encoding = "utf-8")
public class LogisticsApplication extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication.run(LogisticsApplication.class, args);
	}
}
