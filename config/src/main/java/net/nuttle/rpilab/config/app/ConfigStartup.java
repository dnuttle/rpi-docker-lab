package net.nuttle.rpilab.config.app;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import net.nuttle.rpilab.config.Constants;

public class ConfigStartup implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) throws StartupException {
    if (System.getenv(Constants.ENV_PERSIST_URL)==null) {
      throw new StartupException("The env var " + Constants.ENV_PERSIST_URL + " must be defined");
    }
    
  }
}
