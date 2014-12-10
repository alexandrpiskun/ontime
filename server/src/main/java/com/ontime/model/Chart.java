package com.ontime.model;

import static com.google.common.base.Preconditions.checkNotNull;

public class Chart {
  private final String id;
  private final String data;
  private final int severity;

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

}
