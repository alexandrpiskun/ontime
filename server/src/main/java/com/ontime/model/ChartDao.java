package com.ontime.model;

import java.util.List;

public interface ChartDao { 
  static final String CHART_ENTITY_NAME = "Chart";
  
  Chart createNewChart(Chart chart);
  Chart getById(String chartId);
  Chart update(String chartId, Chart chart);
  List<Chart> getAll();
}
