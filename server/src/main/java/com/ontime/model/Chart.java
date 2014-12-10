package com.ontime.model;

public class Chart {
  private final String id;
  private final String data;
  private final int severity;

  public Chart(String id, String data, int severity) {
    this.id = id;
    this.data = data;
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
