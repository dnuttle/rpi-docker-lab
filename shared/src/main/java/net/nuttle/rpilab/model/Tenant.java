package net.nuttle.rpilab.model;

public class Tenant {

  private String id;
  private String name;
  private String desc;

  public Tenant() {
    
  }
  
  public Tenant(String id, String name, String desc) {
    this.id = id;
    this.name = name;
    this.desc = desc;
  }
  
  public String getID() {
    return id;
  }
  
  public String getName() {
    return name;
  }
  
  public String getDesc() {
    return desc;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setDesc(String desc) {
    this.desc = desc;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\"id\":\"").append(id).append("\",");
    sb.append("\"name\":\"").append(name).append("\",");
    sb.append("\"desc\":").append(desc).append("\"}");
    return sb.toString();
  }
}
