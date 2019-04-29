package com.nastech.upmureport.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.nastech.upmureport.domain.security.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	@Autowired
//	AuthenticationManager authenticationManager;  // Spring Security에서 등록하는 빈을 DI 받는다. 
//    
	@Autowired UserService userService;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired SimpleCorsFilter scf;
     
	@Override
     protected void configure(HttpSecurity http) throws Exception {
		http
		.addFilterBefore(new CorsFilter(corsConfigurationSource()), ChannelProcessingFilter.class)
		.csrf().disable()
		.cors().and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
			.and()
		.authorizeRequests()
				.antMatchers("/api/users/login").permitAll()
				.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
				.antMatchers("/api/users/userlist").permitAll()
				.anyRequest().permitAll()  //우선 모든 api요청 열어둠 
				.and()
			.formLogin();
			//.usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/test").permitAll();             
     }


     @Override
     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
          auth.eraseCredentials(false)
          .userDetailsService(userService)
          .passwordEncoder(userService.passwordEncoder());
     }
     
     @Bean
     public CorsConfigurationSource corsConfigurationSource() {
         CorsConfiguration configuration = new CorsConfiguration();
         configuration.setAllowedOrigins(Arrays.asList("*"));
         configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
         configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
         configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
         source.registerCorsConfiguration("/**", configuration);
         return source;
     } 
     
     
     @Bean
     public PasswordEncoder passwordEncoder() {
    	 return new BCryptPasswordEncoder();
     }
     
     @Bean
     @Override
     public AuthenticationManager authenticationManagerBean() throws Exception {
          return super.authenticationManagerBean();
     }
     
//     @Bean
//     public HttpSessionStrategy httpSessionStrategy() {
//    	 
//         return new HeaderHttpSessionStrategy();
//     }
     
}
