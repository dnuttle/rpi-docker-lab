package net.nuttle.rpilab.model;

public class Env {

  private String tenantId;
  private String id;
  
  public Env(String tenantId, String id) {
    this.tenantId = tenantId;
    this.id = id;
  }
  
  public String getTenantId() {
    return tenantId;
  }
  
  public String getId() {
    return id;
  }
  
  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }
  
  public void setId(String id) {
    this.id = id;
  }
}
