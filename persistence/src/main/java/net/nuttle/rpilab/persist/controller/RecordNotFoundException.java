package net.nuttle.rpilab.persist.controller;

public class RecordNotFoundException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 8442957455085471831L;

  public RecordNotFoundException(String msg) {
    super(msg);
  }
  
  public RecordNotFoundException(String msg, Throwable t) {
    super(msg, t);
  }
}
