package net.nuttle.rpilab.persist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.nuttle.rpilab.model.Tenant;

@Component
public class TenantDAO extends AbstractDAO<Tenant>  {

  @SuppressWarnings("unused")
  private static final Logger LOG = LoggerFactory.getLogger(TenantDAO.class);
  
  public TenantDAO() {}

  @Override
  public Tenant get(Tenant t) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection();
      String sql = "SELECT * FROM TENANT WHERE ID=?";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, t.getID());
      ResultSet rs = stmt.executeQuery();
      if (!rs.next()) {
        return null;
      }
      String name = rs.getString("NAME");
      String desc = rs.getString("DESC");
      t = new Tenant(t.getID(), name, desc);
      return t;
    } finally {
      close(conn);
    }
  }

  @Override
  public int create(Tenant t) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection();
      String sql = "INSERT INTO TENANT (ID, NAME, DESC) VALUES(?, ?, ?)";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1,  t.getID());
      stmt.setString(2, t.getName());
      stmt.setString(3,  t.getDesc());
      stmt.execute();
      return stmt.getUpdateCount();
    } finally {
      close(conn);
    }
  }

  @Override
  public int update(Tenant t) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection();
      String sql = "UPDATE TENANT SET NAME=?, DESC=? WHERE ID=?";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, t.getName());
      stmt.setString(2,  t.getDesc());
      stmt.setString(3,  t.getID());
      stmt.execute();
      return stmt.getUpdateCount();
    } finally {
      close(conn);
    }
  }

  @Override
  public int delete(Tenant t) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection();
      String sql = "DELETE FROM TENANT WHERE ID = ?";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1,  t.getID());
      stmt.execute();
      return stmt.getUpdateCount();
    } finally {
      close(conn);
    }
  }
  
  public List<Tenant> getAll() throws SQLException {
    List<Tenant> tenants = new ArrayList<>();
    Connection conn = null;
    try {
      conn = getConnection();
      //TODO: Could add pagination with overloaded method
      String sql = "SELECT ID, NAME, DESC FROM TENANT ORDER BY ID LIMIT 100";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      while(rs.next()) {
        String id = rs.getString("ID");
        String name = rs.getString("NAME");
        String desc = rs.getString("DESC");
        Tenant t = new Tenant(id, name, desc);
        tenants.add(t);
      }
      return tenants;
    } finally {
      close(conn);
    }
  }
  
  @Override
  public void createTable() throws SQLException {
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
