package com.longge.autoconfig.service.impl;

import org.springframework.stereotype.Service;

import com.longge.autoconfig.service.ProxyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProxyServiceImpl implements ProxyService {

	@Override
	public void test() {
		log.info("ProxyServiceImpl's test method is run");
	}

}
