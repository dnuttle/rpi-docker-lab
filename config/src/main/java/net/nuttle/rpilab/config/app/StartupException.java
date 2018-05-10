package net.nuttle.rpilab.config.app;

public class StartupException extends Exception {
  
  /**
   * 
   */
  private static final long serialVersionUID = -868650932503270325L;

  public StartupException(String msg) {
    super(msg);
  }
  
  public StartupException(String msg, Throwable t) {
    super(msg, t);
  }

}
