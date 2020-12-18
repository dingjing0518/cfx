package cn.cf.interceptor;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.cf.util.SpringContextsUtil;


@Configuration
public class Configuer extends WebMvcConfigurerAdapter {
	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(
				Charset.forName("UTF-8"));
		return converter;
	}

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(responseBodyConverter());
	}
    @Bean
    public SpringContextsUtil getSpringContextsUtil(){
    	return new SpringContextsUtil();
    }
	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false);
	}

	public void addInterceptors(InterceptorRegistry registry) {

		// 多个拦截器组成一个拦截器链

		// addPathPatterns 用于添加拦截规则

		// excludePathPatterns 用户排除拦截
		/**
		 * 监听Token
		 */
		registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/member/*")
				.excludePathPatterns("/member/accessToken","/member/getSaleMan",
						"/member/searchPointList","/member/addPoint",
						"/member/addPoint2","/member/addPointForThing",
						"/member/cancelThing","/member/cancelOrder",
						"/member/addPointForOrder","/member/addExtPointForOrder","/member/addCustomerByManagement",
						"/member/getGoodsByMember","/member/getByMember","/member/sendMessageContent",
						"/member/sendMsg"
						);
		
		
		/**
		 * 监听session
		 */
		registry.addInterceptor(new SessionInterceptor())
		.addPathPatterns("/member/*")
		.excludePathPatterns("/member/verificationLogin","/member/accessToken","/member/getMember",
				"/member/verificationCode","/member/register","/member/resetPassword","/member/checkCode"
				,"/member/verificationSession","/member/regions","/member/regionsAll"
				,"/member/verificationSession","/member/getSaleMan","/member/verificationLoginByToken",
				"/member/insertMango",
				"/member/searchPointList","/member/addPoint",
				"/member/addPoint2","/member/addPointForThing",
				"/member/cancelThing","/member/cancelOrder",
				"/member/addPointForOrder","/member/addExtPointForOrder","/member/addCustomerByManagement",
				"/member/getGoodsByMember","/member/getByMember","/member/sendMessageContent",
				"/member/sendMsg","/member/getSessionById","/member/getVersion"
				);

		
		
		/**
		 * 监听Sign
		 */
		registry.addInterceptor(new SignInterceptor())
			.addPathPatterns("/member/*")
			.excludePathPatterns("/member/accessToken",
					"/member/searchPointList","/member/addPoint",
					"/member/addPoint2","/member/addPointForThing",
					"/member/cancelThing","/member/cancelOrder",
					"/member/addPointForOrder","/member/addExtPointForOrder","/member/addCustomerByManagement",
					"/member/getSaleMan","/member/getGoodsByMember","/member/getByMember","/member/sendMessageContent",
					"/member/sendMsg"
					);



		super.addInterceptors(registry);
		
		
	}

		
}
