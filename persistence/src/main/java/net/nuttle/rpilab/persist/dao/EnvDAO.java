package net.nuttle.rpilab.persist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import net.nuttle.rpilab.model.Env;
import net.nuttle.rpilab.model.Tenant;

@Component
public class EnvDAO extends AbstractDAO<Env> {

  private static final String GET_SQL = "SELECT TENANTID, ID, DESC FROM ENV WHERE TENANTID=? AND ID=?";
  private static final String GET_ALL_SQL = "SELECT TENANTID, ID, DESC FROM ENV ORDER BY TENANTID, ID LIMIT 100";
  private static final String GET_ALL_FOR_TENANT_SQL = "SELECT TENANTID, ID, DESC FROM ENV " 
      + "WHERE TENANTID = ? ORDER BY TENANTID, ID LIMIT 100";
  private static final String DELETE_ALL_FOR_TENANT_SQL = "DELETE FROM ENV WHERE TENANTID = ?";
  private static final String CREATE_SQL = "INSERT INTO ENV (TENANTID, ID, DESC) VALUES(?, ?, ?)";
  private static final String UPDATE_SQL = "UPDATE ENV SET DESC=? WHERE TENANTID=? AND ID=?";
  private static final String DELETE_SQL = "DELETE FROM ENV WHERE TENANTID=? AND ID=?";
  
  @Override
  public Env get(Env e) throws SQLException {
    Connection conn = getConnection();
    try {
      PreparedStatement stmt = conn.prepareStatement(GET_SQL);
      stmt.setString(1, e.getTenantId());
      stmt.setString(2,  e.getId());
      ResultSet rs = stmt.executeQuery();
      if (!rs.next()) {
        return null;
      }
      e = Env.Builder.instance()
        .setTenantID(e.getTenantId())
        .setID(e.getId())
        .setDesc(rs.getString("DESC"))
        .build();
      return e;
    } finally {
      close(conn);
    }
  }
  
  @Override
  public int create(Env e) throws SQLException {
    Connection conn = getConnection();
    try {
      PreparedStatement stmt = conn.prepareStatement(CREATE_SQL);
      stmt.setString(1,  e.getTenantId());
      stmt.setString(2,  e.getId());
      stmt.setString(3, e.getDesc());
      return stmt.executeUpdate();
    } finally {
      close(conn);
    }
  }
  
  @Override
  public int update(Env e) throws SQLException {
    Connection conn = getConnection();
    try {
      PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL);
      stmt.setString(1,  e.getDesc());
      stmt.setString(2, e.getTenantId());
      stmt.setString(3, e.getId());
      return stmt.executeUpdate();
    } finally {
      close(conn);
    }
  }
  
  @Override
  public int delete(Env e) throws SQLException {
    Connection conn = getConnection();
    try {
      PreparedStatement stmt = conn.prepareStatement(DELETE_SQL);
      stmt.setString(1, e.getTenantId());
      stmt.setString(2, e.getId());
      return stmt.executeUpdate();
    } finally {
      close(conn);
    }
  }
  
  @Override
  public List<Env> getAll() throws SQLException {
    List<Env> envs = new ArrayList<>();
    Connection conn = getConnection();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(GET_ALL_SQL);
      while (rs.next()) {
        envs.add(Env.Builder.instance()
          .setTenantID(rs.getString("TENANTID"))
          .setID(rs.getString("ID"))
          .setDesc(rs.getString("DESC"))
          .build()
        );
      }
      return envs;
    } finally {
      close(conn);
    }
  }

  public List<Env> getAllForTenant(Tenant t) throws SQLException {
    List<Env> envs = new ArrayList<>();
    Connection conn = getConnection();
    try {
      PreparedStatement stmt = conn.prepareStatement(GET_ALL_FOR_TENANT_SQL);
      stmt.setString(1, t.getID());
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        envs.add(Env.Builder.instance()
          .setTenantID(rs.getString("TENANTID"))
          .setID(rs.getString("ID"))
          .setDesc(rs.getString("DESC"))
          .build()
        );
      }
      return envs;
    } finally {
      close(conn);
    }
  }
  
  public int deleteAllForTenant(Tenant t) throws SQLException {
    Connection conn = getConnection();
    try {
      PreparedStatement stmt = conn.prepareStatement(DELETE_ALL_FOR_TENANT_SQL);
      stmt.setString(1,  t.getID());
      return stmt.executeUpdate();
    } finally {
      close(conn);
    }
  }
  
  @Override
  public void createTable() throws SQLException {
    String sql = "CREATE TABLE IF NOT EXISTS env (\n"
      + "tenantid integer, \n"
      + "id integer, \n"
      + "desc text NOT NULL, "
      + "PRIMARY KEY (tenantid, id) "
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
  
  public void dropTable() throws SQLException {
    String sql = "DROP table 'env';";
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
