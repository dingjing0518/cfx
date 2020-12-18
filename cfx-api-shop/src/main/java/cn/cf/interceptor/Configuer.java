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
		registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/shop/*")
				.excludePathPatterns("/shop/searchGoodsList","/shop/leftProductDetails","/shop/search","/shop/getByCompany","/shop/getByCompanyList",
						 "/shop/getCompanyByName");


		/**
		 * 监听session
		 */
		registry.addInterceptor(new SessionInterceptor())
				.addPathPatterns("/shop/*")
				.excludePathPatterns("/shop/helpList","/shop/helpDetail","/shop/leftNavigationAll","/shop/helpMenu","/shop/recommendSuppliers",
						"/shop/news","/shop/categories",	"/shop/banner","/shop/recommendSupplierByPage",
						"/shop/informationDetails","/shop/leftProductDetails","/shop/searchGoodsList","/shop/getStoreInfo",
						 "/shop/homeOrders","/shop/search","/shop/searchProducts","/shop/searchBrandsList","/shop/searchBrandsListPage",
						"/shop/getGoodDetails","/shop/searchGradeList","/shop/searchProductsList","/shop/searchSpecList",
						"/shop/searchVarietiesList","/shop/luckDrawHomePage","/shop/goodsListByCompany","/shop/leftNavigationCompany",
						"/shop/leftNavigationProduct","/shop/leftNavigationVarieties","/shop/goodsPriceHistory","/shop/hotSpec","/shop/searchStoreByBrandPk",
						"/shop/liveBroadCategory","/shop/liveBroadList","/shop/searchFutures","/shop/searchFuturesType" ,
						"/shop/searchVarietiesPage","/shop/searchSpecPage","/shop/searchKeyWordList","/shop/searchFriendLink",
						"/shop/searchPriceMovement","/shop/getByCompany","/shop/getByCompanyList", 
						 "/shop/searchSomeNameByPk","/shop/searchStoreAlbumByStorePk","/shop/getSxSpecList","/shop/myGoodsBrandList",
						"/shop/searchRecomendGoodsList","/shop/getCompanyByName","/shop/searchMemubarManageList","/shop/searchSxTechnologyList","/shop/searchSxMaterialList","/shop/getSxTechnologyTypeByTpk",
						"/shop/aboutUs");


		/**
		 * 监听Sign 
		 */
		registry.addInterceptor(new SignInterceptor()).addPathPatterns("/shop/*").excludePathPatterns("/shop/search","/shop/getByCompany","/shop/getByCompanyList",
				 "/shop/getCompanyByName");

		super.addInterceptors(registry);

		
		
	}

		
}
