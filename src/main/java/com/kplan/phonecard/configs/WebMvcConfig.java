package com.kplan.phonecard.configs;

import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.DispatcherType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.dozermapper.core.Mapper;
import com.kplan.phonecard.converters.StringToEnumConverterFactory;
import com.kplan.phonecard.filters.ParameterTrimFilter;
import com.kplan.phonecard.resolvers.MultiRequestBodyArgumentResolver;
import com.kplan.phonecard.resolvers.MultiRequestParamArgumentResolver;

@Configuration
//@EnableWebMvc (不能直接使用此注解,如果使用则表明所有mvc的配置都要customerize)
//没有@EnableWebMvc注解,则WebMvcConfigurer所有方法的参数都是AutoConfig得到,并且可以继续增加自己的配置
public class WebMvcConfig implements WebMvcConfigurer {
//	@Bean(name = "parameterTrimFilter") // 不写name属性，默认beanName为方法名
//	public FilterRegistrationBean<ParameterTrimFilter> parameterTrimFilter() {
//		FilterRegistrationBean<ParameterTrimFilter> filter = new FilterRegistrationBean<>();
//		filter.setDispatcherTypes(DispatcherType.REQUEST);
//		filter.setFilter(new ParameterTrimFilter()); // 必须设置
//		filter.addUrlPatterns("/*"); // 拦截所有请求，如果没有设置则默认“/*”
//		filter.setName("parameterTrimFilter"); // 设置注册的名称，如果没有指定会使用Bean的名称。此name也是过滤器的名称
//		filter.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);// 该filter在filterChain中的执行顺序
//		return filter;
//	}


	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new MultiRequestBodyArgumentResolver());
		argumentResolvers.add(0,new MultiRequestParamArgumentResolver());
	}

	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// super.configureMessageConverters(converters);
		converters.add(responseBodyConverter());
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(new StringToEnumConverterFactory());
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/sign_in").setViewName("login");
//		registry.addViewController("/sign_up").setViewName("registry");
	}
//
//	@Autowired
//	org.springframework.web.servlet.HandlerMapping HandlerMapping;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		System.out.println(registry);
//		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css");
//		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js");
//		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img");
//		registry.addResourceHandler("/icomoon/**").addResourceLocations("classpath:/static/icomoon");
		// registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

//		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css");
//		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js");
//		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img");
	}

}