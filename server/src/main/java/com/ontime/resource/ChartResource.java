package com.ontime.resource;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ontime.model.Chart;
import com.ontime.model.ChartDao;

@Path("/api/v1/")
@Singleton
public class ChartResource {
  private Map<String, SomeJson> storage = new HashMap<>();
  
  private final ChartDao chartDao;
  
  @Inject
  public ChartResource(ChartDao chartDao) {
    this.chartDao = checkNotNull(chartDao, "chartDao is missing");
  }

  @POST
  @Path("_save")
  @Produces("application/json")
  public Chart createNewChart(@FormParam("data") String text, @FormParam("severity") Integer severity) {
    return chartDao.createNewChart(text, severity);
  }
  
  @GET
  @Path("{chartId}")
  @Produces("application/json")
  public Response getChart(@PathParam("chartId") String chartId) {
    Chart chart = chartDao.getById(chartId);
    if (chart == null) {
      String msg = String.format("Cannot find chart %s", chartId);
      return Response.status(Status.NOT_FOUND).entity(msg).build();
    }
    return Response.ok(chart).build();
  }

  @POST
  @Path("{chartId}")
  @Produces("application/json")
  public Response updateChart(@PathParam("chartId") String chartId,
      @FormParam("data") String text, @FormParam("severity") Integer severity) {
    Chart chart = chartDao.getById(chartId);
    if (chart == null) {
      String msg = String.format("Cannot find chart %s", chartId);
      return Response.status(Status.NOT_FOUND).entity(msg).build();
    }
    
    SomeJson json = new SomeJson();
    json.chartId = chartId;
    json.data = text;
    json.severity = severity;
    this.storage.put(chartId, json);
    return Response.ok(chart).build();
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
