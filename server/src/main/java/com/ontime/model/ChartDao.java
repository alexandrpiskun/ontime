package com.ontime.model;

public interface ChartDao {
  static final String CHART_ENTITY_NAME = "Chart";
  
  Chart createNewChart(String data, int severity);
  Chart getById(String chartId);
  Chart update(String chartId, String data, int severity);
}
