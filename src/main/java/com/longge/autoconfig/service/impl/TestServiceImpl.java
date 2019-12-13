package com.longge.autoconfig.service.impl;

import org.springframework.stereotype.Service;

import com.longge.autoconfig.service.TestService;

@Service
public class TestServiceImpl implements TestService {
	private String val = "haha";;
	
	@Override
	public String test() {
		return "TestService's method is called, value is:" + val;
	}

	@Override
	public void setValue(String value) {
		val = value;
	}
}
