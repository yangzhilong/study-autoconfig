package com.longge.autoconfig.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.longge.autoconfig.service.TestService;
import com.longge.autoconfig.service.impl.SuperSupperTestServiceImpl;
import com.longge.autoconfig.service.impl.SuperTestServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestServiceBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof TestService) {
			log.info("TestService is before initialization");
			// update TestService to SuperTestService
			return new SuperTestServiceImpl();
		}
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof TestService) {
			log.info("TestService is after initialization");
		}
		if(bean instanceof SuperTestServiceImpl) {
			log.info("SuperTestService is after initialization");
			// update SuperTestService to SuperSupperTestService
			TestService result = new SuperSupperTestServiceImpl();
			// update field value
			result.setValue("haha~hoho~hehe");
			return result;
		}
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}

}
