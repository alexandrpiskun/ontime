package com.ontime.model;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
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
    return null;
  }
  
}
