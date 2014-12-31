package com.ontime.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chart  implements Serializable{
 
  private static final long serialVersionUID = -2999931401597015354L;
	
  private String id;
  private String data;
  private int severity;
  
  private List<Chart> items = new ArrayList<>();

  public Chart( ) {
  	
  }
  
  public Chart(String id, String data, int severity) {
    this.id = checkNotNull(id, "id is missing");
    this.data = checkNotNull(data, "data is missing");
    this.severity = severity;
  }
  
  public String getId() {
    return id;
  }

  public String getData() {
    return data;
  }

  public int getSeverity() {
    return severity;
  }
  
  public void addChart(Chart chart) {
	  checkNotNull(chart, "chart is missing");
	  items.add(chart);
  }
  
  public List<Chart> getItems() {
	  return items;
  }

	public void setId(String id) {
		this.id = id;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public void setItems(List<Chart> items) {
		this.items = items;
	}
  
}
