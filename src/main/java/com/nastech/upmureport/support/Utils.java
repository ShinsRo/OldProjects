package com.nastech.upmureport.support;

import java.math.BigInteger;
import java.util.Arrays;
import java.lang.reflect.*;
import java.util.Enumeration;
import java.util.Properties;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

public class Utils {

	public static Object unproxy(Object proxy) {
		Object unproxiedEntity = null;
		
		if(proxy instanceof HibernateProxy) {
			HibernateProxy hibernateProxy = (HibernateProxy) proxy;
			LazyInitializer initializer = hibernateProxy.getHibernateLazyInitializer();
			unproxiedEntity = initializer.getImplementation();
		}
		return unproxiedEntity;
	}
	
	public static BigInteger StrToBigInt(String target) {
		return BigInteger.valueOf(Integer.valueOf(target));
	}
	
	/**
	 * dto의 필드값과 같은 필드에 대해 덮어쓰기한 엔티티를 반환합니다.
	 * @param entity
	 * @param dto
	 * @return
	 */
	public static <Entity, DTO> void overrideEntity(Entity entity, DTO dto){
		
		Field[] entityFields = entity.getClass().getDeclaredFields();
		Field[] dtoFields = dto.getClass().getDeclaredFields();
		for (Field ef : entityFields) {
			ef.setAccessible(true);
			Object value = null;
			
			try {
				for (Field df: dtoFields) {
					df.setAccessible(true);
					if (df.getName().equals(ef.getName())) {
						Object efVal = ef.get(entity);
						Object dfVal = df.get(dto);
						
						if (dfVal.getClass().equals(String.class) 
								&& efVal.getClass().equals(BigInteger.class)) {
							value = StrToBigInt((String) dfVal);
						} else {
							value = dfVal;
						}
						ef.set(entity, value);
						break;
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
		}
	}
}