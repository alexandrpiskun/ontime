package com.ontime;

import javax.inject.Singleton;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.ontime.model.ChartDao;
import com.ontime.model.ChartDaoImpl;
import com.ontime.resource.ChartResource;

public class RestModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ChartResource.class);
		
		bind(ChartDao.class).to(ChartDaoImpl.class).in(Scopes.SINGLETON);
	}
	
	@Provides @Singleton
	DatastoreService provideDatastore() {
	  return DatastoreServiceFactory.getDatastoreService();
	}

}
