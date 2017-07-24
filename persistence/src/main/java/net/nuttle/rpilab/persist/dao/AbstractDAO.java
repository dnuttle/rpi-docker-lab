package net.nuttle.rpilab.persist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.nuttle.rpilab.config.Constants;
import net.nuttle.rpilab.model.BaseEntity;

public abstract class AbstractDAO<T extends BaseEntity> {
  
  private static final Logger LOG = LoggerFactory.getLogger(AbstractDAO.class);
  private static final String SQLITE_URL;
  
  static {
    SQLITE_URL = "jdbc:sqlite:" + System.getenv(Constants.ENV_SQLITE_URL);
  }

  protected Connection getConnection() throws SQLException {
    return DriverManager.getConnection(SQLITE_URL);
  }
  
  protected void close(Connection conn) {
    if (conn == null) {
      return;
    }
    try { 
      conn.close();
    } catch (Exception e) {
      LOG.error("Error closing connection", e);
    }
  }
  
  public abstract T get(T entity) throws SQLException;
  
  public abstract int create(T entity) throws SQLException;
  
  public abstract int update(T entity) throws SQLException;
  
  public abstract int delete(T entity) throws SQLException;
  
  public abstract List<T> getAll() throws SQLException;
  
  public abstract void createTable() throws SQLException;
  
}
