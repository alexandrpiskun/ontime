package com.ontime.model;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.inject.Inject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.ontime.resource.IdShortener;

public class ChartDaoImpl implements ChartDao {
  private final DatastoreService datastore;

  @Inject
  public ChartDaoImpl(DatastoreService datastore) {
    this.datastore = checkNotNull(datastore, "datastore is missing");
  }
   
  @Override
  public Chart createNewChart(String data, int severity) {
    Entity chartEntity = new Entity(CHART_ENTITY_NAME);
    chartEntity.setProperty("data", data);
    chartEntity.setProperty("severity", severity);
    Key result = datastore.put(chartEntity);
    String shortenedID = IdShortener.encode(result.getId());
    return new Chart(shortenedID, data, severity);
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
      String data = (String) chartEntity.getProperty("data");
      int severity = ((Long) chartEntity.getProperty("severity")).intValue();
      chart = new Chart(chartId, data, severity);
    }
    return chart;
  }
  
  @Override
  public Chart update(String chartId, String data, int severity) {
    long chartKeyId = IdShortener.decode(chartId);
    Key chartKey = KeyFactory.createKey(CHART_ENTITY_NAME, chartKeyId);
    Entity chartEntity = new Entity(chartKey);
    chartEntity.setProperty("data", data);
    chartEntity.setProperty("severity", severity);
    datastore.put(chartEntity);
    return new Chart(chartId, data, severity);
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
  
}
