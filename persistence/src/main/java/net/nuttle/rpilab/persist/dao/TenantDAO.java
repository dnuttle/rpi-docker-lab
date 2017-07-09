package net.nuttle.rpilab.persist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.nuttle.rpilab.model.Tenant;

@Component
public class TenantDAO extends AbstractDAO  {

  private static final Logger LOG = LoggerFactory.getLogger(TenantDAO.class);
  
  public TenantDAO() {}
  
  public Tenant getTenant(String id) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection();
      String sql = "SELECT * FROM TENANT WHERE ID=?";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, id);
      ResultSet rs = stmt.executeQuery();
      if (!rs.next()) {
        return null;
      }
      String name = rs.getString("NAME");
      String desc = rs.getString("DESC");
      Tenant t = new Tenant(id, name, desc);
      return t;
    } finally {
      close(conn);
    }
  }
  
  public void createTenantTable() throws SQLException {
    String sql = "CREATE TABLE IF NOT EXISTS tenant (\n"
      + "id integer PRIMARY KEY, \n"
      + "name text NOT NULL \n, "
      + "desc text "
      + ");";
    Connection conn = null;
    try {
      conn = getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute(sql);
    } finally {
      close(conn);
    }
  }

}
