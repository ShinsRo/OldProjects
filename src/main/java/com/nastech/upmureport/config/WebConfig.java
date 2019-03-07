package com.nastech.upmureport.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.nastech.upmureport"})
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureViewResolvers (ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/jsp/", ".jsp");
	}
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	    registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
	    registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/");
	    registry.addResourceHandler("/img/**").addResourceLocations("/resources/img/");
	    registry.addResourceHandler("/scss/**").addResourceLocations("/resources/scss/");
	    registry.addResourceHandler("/vendor/**").addResourceLocations("/resources/vendor/");
	}
}
