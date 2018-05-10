package net.nuttle.rpilab.persist.app;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import net.nuttle.rpilab.persist.dao.EnvDAO;
import net.nuttle.rpilab.persist.dao.TenantDAO;

@Component
public class PersistStartup implements ApplicationRunner {

  private static final Logger LOG = LoggerFactory.getLogger(PersistStartup.class);
  
  @Autowired
  TenantDAO tenantDao;
  @Autowired
  EnvDAO envDao;
  
  public void run(ApplicationArguments args) throws StartupException {
    try {
      tenantDao.createTable();
      envDao.createTable();
    } catch (SQLException e) {
      LOG.error("Exception creating tenant table", e);
    }
  }
}

