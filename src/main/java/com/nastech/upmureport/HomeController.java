package com.nastech.upmureport;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.nastech.upmureport.domain.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
	
    @GetMapping(value = "/")
    public String index(HttpServletRequest req, HttpServletResponse res) {
    	// 임시 세션
    	HttpSession session = req.getSession(true);
    	session.setAttribute("user", 
    			User.builder()
	    			.userId(11111)
	    			.userPass("nas1234!")
	    			.userName("김승신")
	    			.dept("인턴")
	    			.build());
        return "index";
    }

    @GetMapping(value = "/temp")
    public String temp() {
        return "_template";
    }
}
