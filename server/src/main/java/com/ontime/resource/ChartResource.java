package com.ontime.resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Singleton;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

//  domain.com/...
//  domain.com/api/...
@Path("/")
@Singleton
public class ChartResource {
    
    private Map<String, SomeJson> storage = new HashMap<>();
    
    @GET
    @Path("{chartId}")
    @Produces("application/json")
    public SomeJson getObject(@PathParam("chartId") String chartId) {
    	return this.storage.get(chartId);
    }

    @POST
    @Path("{chartId}")
    @Produces("application/json")
    public SomeJson createObject(@PathParam("chartId") String chartId, @FormParam("data") String text, @FormParam("severity") Integer severity) {
    	SomeJson json = new SomeJson();
    	json.chartId = chartId;
    	json.data = text;
        json.severity= severity;
        this.storage.put(chartId, json);
        return json;
    }

    @GET
    @Path("all")
    @Produces("application/json")
    public Collection<SomeJson> readAll() {
        return this.storage.values();
    }

    
    @GET
    @Path("{userId}/{chartId}")
    @Produces("application/json")
    public SomeJson getUserObject(@PathParam("userId") String userId, @PathParam("chartId") String chartId) {
    	SomeJson json = new SomeJson();
    	json.chartId = chartId;
    	json.data = userId;
        this.storage.put(chartId, json);
        return json;
    }
    
    public static class SomeJson {
    	public String chartId;
    	public String data;
    	public int severity;
    }
	
}
