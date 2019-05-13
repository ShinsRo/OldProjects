package com.nastech.upmureport.common;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectException {
	
	private static final Log LOG = LogFactory.getLog(AspectException.class);
	
	@Before("execution(* com.nastech.upmureport.service.*.*(..))")
	public void startLog(JoinPoint jp) {
		
		LOG.info("---------------------------");
		LOG.info("---------------------------");
		
		/* 전달되는 모든 파라미터들을 Object의 배열로 가져온다. */
		LOG.info("param: " + Arrays.toString(jp.getArgs()));
		    
		/* 해당 Advice의 타입을 알아낸다. */
		LOG.info("advice type: " + jp.getKind());
		    
		/* 실행하는 대상 객체의 메소드에 대한 정보를 알아낼 때 사용 */
		LOG.info("method name: " + jp.getSignature().getName());
		    
		/* target 객체를 알아낼 때 사용 */
		LOG.info("target object: " + jp.getTarget().toString());
		    
		/* Advice를 행하는 객체를 알아낼 때 사용 */
		LOG.info("advice object: " + jp.getThis().toString());
	}
	
	@Around("execution(* com.nastech.upmureport.service.*.*(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable {		
		long startTime = System.currentTimeMillis();
		LOG.info(Arrays.toString(pjp.getArgs()));
			
		//실제 타겟을 실행하는 부분이다. 이 부분이 없으면 advice가 적용된 메소드가 동작을 안할것 같다.
		Object result = pjp.proceed();  //proceed는 Exception 보다 상위 Throwable을 처리해야 한다.
			
		long endTime = System.currentTimeMillis();
		LOG.info(pjp.getSignature().getName() + " : " + ( endTime - startTime) + "ms");  //target 메소드의 동작 시간을 출력한다.
		LOG.info("==============================");
			
		//Around를 사용할 경우 반드시 Object를 리턴해야 한다.
		return result;
	}
}
