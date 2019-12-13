package com.longge.autoconfig.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * JDK Dynamic Proxy
 * @author roger yang
 *
 */
@Slf4j
public class ServiceProxy implements InvocationHandler  {
	private Object target;

    public ServiceProxy(Object target) {
        this.target = target;
    }
    
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		log.info("proxy before call method");
		Object obj = method.invoke(target, args);
		log.info("proxy after call method");
		return obj;
	}

}
