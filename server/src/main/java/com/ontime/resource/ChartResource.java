package com.ontime.resource;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.common.base.Strings;
import com.ontime.model.Chart;
import com.ontime.model.ChartDao;

@Path("/api/v1/")
@Singleton
public class ChartResource {
  
  private final ChartDao chartDao;
  
  @Inject
  public ChartResource(ChartDao chartDao) {
    this.chartDao = checkNotNull(chartDao, "chartDao is missing");
  }

  
  @POST
  @Path("_save")
  @Produces("application/json")
  @Consumes("application/json")
  public Chart saveChart(Chart chart) {
  	if (Strings.isNullOrEmpty(chart.getId())) {
  		return chartDao.createNewChart(chart);
  	}
  	else {
  		return chartDao.update(chart.getId(), chart);
  	}
    
  }
  
  @GET
  @Path("{chartId}")
  @Produces("application/json")
  public Response getChart(@PathParam("chartId") String chartId) {
    Chart chart = chartDao.getById(chartId);
    if (chart == null) {
      return Response.status(Status.NOT_FOUND).build();
    }
    return Response.ok(chart).build();
  }
  
 

  @GET
  @Path("all")
  @Produces("application/json")
  public List<Chart> readAll() {
    return chartDao.getAll();
  }

  

}
