package com.nastech.upmureport.support;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.TimeZone;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

public class Utils {
	private static final SimpleDateFormat _SDF 
		= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	public Utils() {
		TimeZone tz = TimeZone.getTimeZone("GMT+9");
		_SDF.setTimeZone(tz);
	}
	
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
	 * dto의 이름이 같은 필드에 대해 dto로 덮어쓰기한 entity를 반환합니다.
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
//						Object efVal = ef.get(entity);	// 필요 시 주석 해제 후 사용하세요.
						Object dfVal = df.get(dto);
						
						if (dfVal == null) break;
						if (df.getType().equals(ef.getType())) {
							value = dfVal;
						} else if (ef.getType().equals(java.math.BigInteger.class)) {
							value = StrToBigInt((String) dfVal);
						} 
//						else if (ef.getType().equals(java.time.LocalDateTime.class)) {
//							value = LocalDateTime.parse((String) dfVal, DateTimeFormatter.ISO_DATE_TIME);
//						}
						ef.set(entity, value);						
						break;
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (DateTimeParseException e) {
				e.printStackTrace();
			}
		}
	}
}