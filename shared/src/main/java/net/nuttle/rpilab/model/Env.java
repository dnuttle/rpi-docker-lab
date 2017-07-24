package net.nuttle.rpilab.model;

public class Env implements BaseEntity {

  private String tenantId;
  private String id;
  private String desc;
  
  public Env() {}
  
  public Env(String tenantId, String id, String desc) {
    this.tenantId = tenantId;
    this.id = id;
    this.desc = desc;
  }
  
  public String getTenantId() {
    return tenantId;
  }
  
  public String getId() {
    return id;
  }
  
  public String getDesc() {
    return desc;
  }
  
  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public void setDesc(String desc) {
    this.desc = desc;
  }
  
  public static class Builder {
    private String tenantId;
    private String id;
    private String desc;
    
    private Builder() {}
    
    public static Builder instance() {
      return new Builder();
    }
    
    public Builder setTenantID(String tenantID) {
      this.tenantId = tenantID;
      return this;
    }
    
    public Builder setID(String id) {
      this.id = id;
      return this;
    }
    
    public Builder setDesc(String desc) {
      this.desc = desc;
      return this;
    }
    
    public Env build() {
      Env e = new Env();
      e.setTenantId(tenantId);
      e.setId(id);
      e.setDesc(desc);
      return e;
    }
  }
}
