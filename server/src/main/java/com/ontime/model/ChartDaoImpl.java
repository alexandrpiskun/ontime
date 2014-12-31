package com.ontime.model;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.inject.Inject;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.ontime.resource.IdShortener;

public class ChartDaoImpl implements ChartDao {
  private final DatastoreService datastore;

  @Inject
  public ChartDaoImpl(DatastoreService datastore) {
    this.datastore = checkNotNull(datastore, "datastore is missing");
  }
   
  @Override
  public Chart createNewChart(Chart chart) {
    Entity chartEntity = chartToEntity(chart, null);
    Key result = datastore.put(chartEntity);
    String shortenedID = IdShortener.encode(result.getId());
    
    chart.setId(shortenedID);
    return chart;
  }

  @Override
  public Chart getById(String chartId) {
    long chartKeyId = IdShortener.decode(chartId);
    Key chartKey = KeyFactory.createKey(CHART_ENTITY_NAME, chartKeyId);
    Entity chartEntity = null;
    try {
      chartEntity = datastore.get(chartKey);
    } catch (EntityNotFoundException enf) {
      // Ignored.
    }
    
    Chart chart = null;
    if (chartEntity != null) {
      Blob data = (Blob) chartEntity.getProperty("data");
      ByteArrayInputStream bis = new ByteArrayInputStream(data.getBytes());
      try (ObjectInputStream ois = new ObjectInputStream(bis)) {
      	chart = (Chart) ois.readObject();
      } catch (IOException | ClassNotFoundException ioe) {
      	Throwables.propagate(ioe);
      }
    }
    return chart;
  }
  
  @Override
  public Chart update(String chartId, Chart chart) {
  	Preconditions.checkArgument(chartId.equals(chart.getId()), "ids do not match: %s, %s", chartId, chart.getId());
    long chartKeyId = IdShortener.decode(chartId);
    Key chartKey = KeyFactory.createKey(CHART_ENTITY_NAME, chartKeyId);
    Entity chartEntity = chartToEntity(chart, chartKey);
    datastore.put(chartEntity);
    return chart;
  }
  
  @Override
  public List<Chart> getAll() {
    Query query = new Query(CHART_ENTITY_NAME);
    PreparedQuery preparedQuery = datastore.prepare(query);
    return Lists.transform(preparedQuery.asList(withLimit(100)), new Function<Entity, Chart>() {
      @Override
      public Chart apply(Entity chartEntity) {
        String data = (String) chartEntity.getProperty("data");
        int severity = ((Long) chartEntity.getProperty("severity")).intValue();
        String chartId = IdShortener.encode(chartEntity.getKey().getId());
        return new Chart(chartId, data, severity);
      }
    });
  }

  
	private Entity chartToEntity(Chart chart, Key chartKey) {
		Entity chartEntity = null;
		if (chartKey != null) {
			chartEntity = new Entity(chartKey);
		} else {
			chartEntity = new Entity(CHART_ENTITY_NAME);
		}
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    ObjectOutputStream out;
    try {
	    out = new ObjectOutputStream(stream);
	    out.writeObject(chart);
    } catch (IOException e) {
    	throw new RuntimeException(e);
    }
    
    Blob blob = new Blob(stream.toByteArray());
    chartEntity.setProperty("data", blob);
	  return chartEntity;
  }
  
}
