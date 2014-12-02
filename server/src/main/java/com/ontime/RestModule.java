package com.ontime;

import com.google.inject.AbstractModule;
import com.ontime.resource.ChartResource;

public class RestModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ChartResource.class);
	}

}
