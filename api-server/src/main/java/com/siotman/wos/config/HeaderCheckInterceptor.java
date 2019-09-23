package com.siotman.wos.config;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HeaderCheckInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return request.getHeader("SIOTMAN_API_TOKEN")
                .equals(System.getenv("SIOTMAN_API_TOKEN")) && super.preHandle(request,response,handler);
    }
}
