package com.nastech.upmureport.support;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

public class Utiles {

	public static Object unproxy(Object proxy) {
		Object unproxiedEntity = null;
		
		if(proxy instanceof HibernateProxy) {
			HibernateProxy hibernateProxy = (HibernateProxy) proxy;
			LazyInitializer initializer = hibernateProxy.getHibernateLazyInitializer();
			unproxiedEntity = initializer.getImplementation();
		}
		return unproxiedEntity;
	}
}
