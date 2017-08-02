package net.nuttle.rpilab.controller;

public class InvalidHeaderException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 4073796760916537325L;

  
  public InvalidHeaderException(String msg) {
    super(msg);
  }
  
  public InvalidHeaderException(String msg, Throwable t) {
    super(msg, t);
  }
}
