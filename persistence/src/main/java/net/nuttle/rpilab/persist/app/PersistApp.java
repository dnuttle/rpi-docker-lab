package net.nuttle.rpilab.persist.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import net.nuttle.rpilab.model.Tenant;

@SpringBootApplication
@ComponentScan(basePackages={"net.nuttle.rpilab"})
public class PersistApp {

  private static final Logger LOG = LoggerFactory.getLogger(PersistApp.class);
  
  public static void main(String[] args) {
    SpringApplication.run(PersistApp.class, args);
    /*
    try {
      Tenant t = new TenantDAO().getTenant("1000");
      if (t == null) {
        LOG.debug("Tenant record not found");
      } else {
        LOG.debug(t.toString());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    String userDir = System.getProperty("user.dir").replace("\\", "/");
    String url = "jdbc:sqlite:" + userDir + "/src/main/resources/data.sqlite";
    Connection conn = null;
    try {
      conn = getConnection(url);
      System.out.println("CONNECTED");
      createTable(conn);
      System.out.println("TABLE CREATED");
      Tenant t = getTenant(conn, "1000");
      if (t == null) {
        addTenant(conn, "1000", "tenant 1000", "Tenant No. 1000");
        System.out.println("TENANT CREATED");
      } else {
        System.out.println("TENANT RETRIEVED");
      }
      close(conn);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close(conn);
    }
    */
  }
  
  
  private static Tenant getTenant(Connection conn, String id) throws SQLException {
    String sql = "SELECT ID, NAME, DESC FROM TENANT WHERE ID=?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, id);
    ResultSet rs = stmt.executeQuery();
    if (!rs.next()) {
      return null;
    }
    String name = rs.getString("NAME");
    String desc = rs.getString("DESC");
    Tenant tenant = new Tenant(id, name, desc);
    return tenant;
  }
  
  private static void addTenant(Connection conn, String id, String name, String desc) 
    throws SQLException {
    String sql = "INSERT INTO tenant(id, name, desc) VALUES(?,?,?);";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, id);
    stmt.setString(2,  name);
    stmt.setString(3,  desc);
    stmt.executeUpdate();
  }
  
  private static Connection getConnection(String url) throws SQLException {
    return DriverManager.getConnection(url);
  }
  
  private static void close(Connection conn) {
    if (conn == null) return;
    try {
      conn.close();
    } catch (SQLException e) {
      //TODO log the exception
    }
  }
}
