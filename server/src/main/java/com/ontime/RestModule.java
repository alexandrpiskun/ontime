package com.ontime;

import com.google.inject.AbstractModule;
import com.ontime.resource.HelloResource;

public class RestModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(HelloResource.class);
	}

}
