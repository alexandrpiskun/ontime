package com.ontime.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


@Path("/")
public class ChartResource {

    @GET
    @Path("{chartId}")
    @Produces("application/json")
    public SomeJson getObject(@PathParam("chartId") String chartId) {
    	SomeJson json = new SomeJson();
    	json.chartId = chartId;
    	json.data = "bbb";
        return json;
    }
    
    @GET
    @Path("{userId}/{chartId}")
    @Produces("application/json")
    public SomeJson getUserObject(@PathParam("userId") String userId, @PathParam("chartId") String chartId) {
    	SomeJson json = new SomeJson();
    	json.chartId = chartId;
    	json.data = userId;
        return json;
    }
    
    public static class SomeJson {
    	public String chartId;
    	public String data;
    	public int severity;
    }
	
}
