package com.nastech.upmureport.support;

import java.math.BigInteger;

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
}