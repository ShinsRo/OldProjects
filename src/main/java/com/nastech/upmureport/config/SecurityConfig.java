package com.nastech.upmureport.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nastech.upmureport.domain.security.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	@Autowired
//	AuthenticationManager authenticationManager;  // Spring Security에서 등록하는 빈을 DI 받는다. 
//    
	@Autowired UserService userService;
	@Autowired PasswordEncoder passwordEncoder;
     
	@Override
     protected void configure(HttpSecurity http) throws Exception {
    	 http
         .csrf().disable()
         .authorizeRequests()
              .anyRequest().authenticated()
              .and()
         .formLogin()
         .usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/test").permitAll();             
     }


     @Override
     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
          auth.eraseCredentials(false)
          .userDetailsService(userService)
          .passwordEncoder(userService.passwordEncoder());
     }
     
     @Bean
     public PasswordEncoder passwordEncoder() {
    	 return new BCryptPasswordEncoder();
     }
     
}
