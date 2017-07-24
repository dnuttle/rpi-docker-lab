package net.nuttle.rpilab.model;

public class Tenant implements BaseEntity {

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
  
  public void setID(String id) {
    this.id = id;
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
  
  public static class Builder {
    private String id;
    private String name;
    private String desc;
    
    private Builder() {
    }
    
    public static Builder instance() {
      return new Builder();
    }
    
    public Builder setId(String id) {
      this.id = id;
      return this;
    }
    
    public Builder setName(String name) {
      this.name = name;
      return this;
    }
    
    public Builder setDesc(String desc) {
      this.desc = desc;
      return this;
    }
    
    public Tenant build() {
      Tenant t = new Tenant();
      t.setID(id);
      t.setName(name);
      t.setDesc(desc);
      return t;
    }
  }
}
