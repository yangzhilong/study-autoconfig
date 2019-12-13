package com.longge.autoconfig.service.base;

import com.longge.autoconfig.service.RepositoryService;

public class BaseRepositoryService<T> implements RepositoryService<T> {

	@Override
	public void save(T entity) {
		System.out.println("save entity.......");
	}
}
