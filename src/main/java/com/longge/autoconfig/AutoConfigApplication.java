package com.longge.autoconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.longge.autoconfig.service.ProxyService;
import com.longge.autoconfig.service.TestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@RestController
public class AutoConfigApplication {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AutoConfigApplication.class, args);
		log.info("Last value of server.address: {}", context.getEnvironment().getProperty("server.address"));
		
		// in TestServiceBeanPostProcessor's postProcessBeforeInitialization method, change result to SuperTestServiceImpl
		// in TestServiceBeanPostProcessor's postProcessAfterInitialization method, change result to SuperSupperTestServiceImpl
		// so last result is SuperSupperTestServiceImpl's test method is call
		TestService testService = context.getBean(TestService.class);
		log.info(testService.test());
		
		// use ServiceProxy to proxy service
		ProxyService proxyService = context.getBean(ProxyService.class);
		proxyService.test();
		
		// auto exit
		System.exit(-1);
	}
	
	@GetMapping("/")
	public String test() {
		return "hello";
	}
}
