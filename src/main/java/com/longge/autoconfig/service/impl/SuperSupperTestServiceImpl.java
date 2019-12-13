package com.longge.autoconfig.service.impl;

import com.longge.autoconfig.service.TestService;

public class SuperSupperTestServiceImpl implements TestService{
	private String val = "hoho";
	
	@Override
	public String test() {
		return "SuperSupperTestService 's method is called, value is:" + val;
	}
	
	@Override
	public void setValue(String value) {
		val = value;
	}
}
