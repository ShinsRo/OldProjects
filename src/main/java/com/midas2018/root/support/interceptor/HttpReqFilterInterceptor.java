package com.midas2018.root.support.interceptor;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.midas2018.root.model.UserStatus;
import com.midas2018.root.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HttpReqFilterInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //admin이 아니면 false
        if (!isAdmin(request)) {
            return false;
        }

        return super.preHandle(request, response, handler);
    }

    private boolean isAdmin(HttpServletRequest request) {
//        request.getS
        log.info("{}", request.getHeader("auth"));

        String userAuth = request.getHeader("auth");
        if (Objects.isNull(userAuth)) {
            return false;
        }

        UserStatus userStatus = userService.getUserStatusByUserId(userAuth);
        if (userStatus == UserStatus.ADMIN || userStatus == UserStatus.SUB_ADMIN) {
            return true;
        }

        return false;
    }
}
