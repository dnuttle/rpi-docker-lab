package net.nuttle.rpilab.persist.app;

public class StartupException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 287974257008039548L;

  public StartupException(String msg) {
    super(msg);
  }
  
  public StartupException(String msg, Throwable t) {
    super(msg, t);
  }
}
