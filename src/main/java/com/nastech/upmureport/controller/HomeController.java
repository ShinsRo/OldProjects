package com.nastech.upmureport.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping(value = "/")
    public String index(HttpServletRequest req, HttpServletResponse res) {
        return "index.html";
    }
}