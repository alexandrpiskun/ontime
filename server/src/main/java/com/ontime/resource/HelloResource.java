package com.ontime.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/")
public class HelloResource {

    @GET
    @Path("/")
    @Produces("application/json")
    public SomeJson getObject() {
    	SomeJson json = new SomeJson();
    	json.a = "aaa";
    	json.b = "bbb";
        return json;
    }
    
    public static class SomeJson {
    	public String a;
    	public String b;
    }
	
}
