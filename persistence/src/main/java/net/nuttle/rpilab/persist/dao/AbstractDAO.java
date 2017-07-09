package net.nuttle.rpilab.persist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.nuttle.rpilab.config.Constants;

public abstract class AbstractDAO {
  
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
}
