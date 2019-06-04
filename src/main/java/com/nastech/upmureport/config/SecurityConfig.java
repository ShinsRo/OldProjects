package com.nastech.upmureport.config;

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
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import com.nastech.upmureport.feature.user.domain.security.UserService;

/**
 * 
 * @author 마규석
 * 
 * SprinSecurity를 위한 설정 파일
 *
 */


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	@Autowired
//	AuthenticationManager authenticationManager;  // Spring Security에서 등록하는 빈을 DI 받는다. 
//    
	@Autowired UserService userService;
	@Autowired PasswordEncoder passwordEncoder;
	//SimpleCorsFilter 적용
	@Autowired SimpleCorsFilter scf;
	
	/**
	 * Spring Security 인가
	 */
	
	@Override
     protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.cors().and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
			.and()
		.authorizeRequests()
				.antMatchers("/api/users/login").permitAll()
				.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
				.antMatchers("/api/users/userlist").permitAll()
				.antMatchers("/api/career/getdeptposi").permitAll()
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
     
//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {
//         CorsConfiguration configuration = new CorsConfiguration();
//         configuration.setAllowedOrigins(Arrays.asList("*"));
//         configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//         configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "X-Auth-Token"));
//         configuration.setExposedHeaders(Arrays.asList("X-Auth-Token"));
//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", configuration);
//         return source;
//     } 
     
     
     // Spring Security의 권장사항에 따른 비밀번호 암호화 
     @Bean
     public PasswordEncoder passwordEncoder() {
    	 return new BCryptPasswordEncoder();
     }
     
     @Bean
     @Override
     public AuthenticationManager authenticationManagerBean() throws Exception {
          return super.authenticationManagerBean();
     }
     
     @Bean
     public HttpSessionIdResolver httpSessionIdResolver() {
    	 return HeaderHttpSessionIdResolver.xAuthToken();
     }
     
//   @Bean
//   public HttpSessionStrategy httpSessionStrategy() {
//  	 
//       return new HeaderHttpSessionStrategy();
//   }
     
}
