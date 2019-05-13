package com.nastech.upmureport.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 김윤상
 * @since 19/05/09
 */
@RestControllerAdvice()
public class GlobalExceptionHandler {
	
	private final Log LOG = LogFactory.getLog(GlobalExceptionHandler.class);


	@ExceptionHandler(value = Exception.class)
	public String handleException(Exception e) {
		LOG.info("---------------------------");
		LOG.info("에러 메세지 : " + e.getMessage());
		LOG.info("---------------------------");
		e.printStackTrace();
		
		
		return "---------------------------\n"
			+ "에러 메세지 : " + e.getMessage() 
			+ "\n---------------------------" ;
	}

}
