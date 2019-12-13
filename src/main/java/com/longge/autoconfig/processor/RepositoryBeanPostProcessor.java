package com.longge.autoconfig.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.longge.autoconfig.service.RepositoryService;
import com.longge.autoconfig.service.base.BaseRepositoryService;

@Component
public class RepositoryBeanPostProcessor implements BeanPostProcessor  {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof RepositoryService) {
			return new BaseRepositoryService<>();
		}
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}

}
