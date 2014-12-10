package com.ontime.resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Path("/api/v1/")
@Singleton
public class ChartResource {

  public static final String CHART_ENTITY_NAME = "Chart";
  
  private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  private Map<String, SomeJson> storage = new HashMap<>();

  @POST
  @Path("_save")
  @Produces("application/json")
  public SomeJson createNewObject(@FormParam("data") String text,
      @FormParam("severity") Integer severity) {
    Entity chartEntity = new Entity(CHART_ENTITY_NAME);
    chartEntity.setProperty("data", text);
    chartEntity.setProperty("severity", severity);
    Key result = datastore.put(chartEntity);
    String shortenedID = ShortIdService.encode(result.getId());

    SomeJson json = new SomeJson();
    json.chartId = shortenedID;
    json.data = text;
    json.severity = severity;
    return json;
  }
  
  @GET
  @Path("{chartId}")
  @Produces("application/json")
  public SomeJson getObject(@PathParam("chartId") String chartId) {
    long chartKeyId = ShortIdService.decode(chartId);
    Key chartKey = KeyFactory.createKey(CHART_ENTITY_NAME, chartKeyId);
    Entity chart = null;
    try {
      chart = datastore.get(chartKey);
    } catch (EntityNotFoundException enf) {
    }
    
    SomeJson json = new SomeJson();
    if (chart != null) {
      json.chartId = chartId;
      json.data = (String) chart.getProperty("data");
      json.severity = ((Long) chart.getProperty("severity")).intValue();
    }
    return json;
  }

  @POST
  @Path("{chartId}")
  @Produces("application/json")
  public SomeJson createObject(@PathParam("chartId") String chartId,
      @FormParam("data") String text, @FormParam("severity") Integer severity) {
    SomeJson json = new SomeJson();
    json.chartId = chartId;
    json.data = text;
    json.severity = severity;
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
  public SomeJson getUserObject(@PathParam("userId") String userId,
      @PathParam("chartId") String chartId) {
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
