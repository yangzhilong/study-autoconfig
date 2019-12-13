package com.longge.autoconfig.service.impl;

import com.longge.autoconfig.service.TestService;

public class SuperTestServiceImpl implements TestService{
	private String val = "hehe";
	
	@Override
	public String test() {
		return "SuperTestService 's method is called, value is:" + val;
	}
	
	@Override
	public void setValue(String value) {
		val = value;
	}
}
