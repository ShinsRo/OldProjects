/** 
 * 웹 MVC 기본 설정 정의
 * 
 * 2019.05.22.
 * @author 김승신, 김윤상, 마규석
 */
package com.nastech.upmureport.config;

import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = {"com.nastech.upmureport", "com.nastech.upmureport.jpa"})
@EnableAspectJAutoProxy
public class WebConfig implements WebMvcConfigurer {
	
	/**
	 * SPA 형식의 라우팅을 지원하기 위해 모든 리소스 URL, 페이지 등을 루트로 포워딩한다.
	 * 
	 * @author 김승신 19.05.22.
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/{spring:\\w+}")
				.setViewName("forward:/");
		registry.addViewController("/**/{spring:\\w+}")
				.setViewName("forward:/");
		registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}")
				.setViewName("forward:/");
	  }
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("swagger-ui.html")
        	.addResourceLocations("classpath:/META-INF/resources/");

	    registry.addResourceHandler("/webjars/**")
        	.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("Content-Disposition")
                .allowCredentials(false).maxAge(3600);
    }

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter());
		// http
	    converters.add(new StringHttpMessageConverter());	    
	    // string
	    converters.add(new FormHttpMessageConverter());
	    
	    converters.add(new ByteArrayHttpMessageConverter());	    
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
	    return new StandardServletMultipartResolver();
	}
	
	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("multipart.max_file_size", "128KB");
		properties.setProperty("multipart.max_request_size", "128KB");
		return properties;
	}
	
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
	    configurer
	            .setUseSuffixPatternMatch(false);  // to use special character in path variables, for example, `email@dom.com`
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
	    configurer
	            .favorPathExtension(false); // to  avoid HttpMediaTypeNotAcceptableException on standalone tomcat
	}
	
	
}
