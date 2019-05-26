package org.kosta.goodmove.controller;

import org.kosta.goodmove.exception.DefaultException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonErrorController {
	
	@RequestMapping("defaultError.do")
	public String defaultErrorPage(){
		return "error/error-default";
	}
	
	@RequestMapping("notFound.do")
	public String errorPage404(){
		return "error/error-404";
	}
	
	@SuppressWarnings("null")
	@RequestMapping("makeError.do")
	public void makeError(){
		Integer[] nullInt = null;
		System.out.println(nullInt[100].toString());
	}
	
	@RequestMapping("makeException.do")
	public void makeException() throws DefaultException{
			throw new DefaultException();
	}
}
