package net.nuttle.rpilab.config.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"net.nuttle.rpilab"})
public class ConfigApp {

  @SuppressWarnings("unused")
  private static final Logger LOG = LoggerFactory.getLogger(ConfigApp.class);
  
  public static void main(String[] args) {
    SpringApplication.run(ConfigApp.class, args);
    
  }
}
