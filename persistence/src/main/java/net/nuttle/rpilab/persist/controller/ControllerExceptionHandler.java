package net.nuttle.rpilab.persist.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.nuttle.rpilab.controller.InvalidHeaderException;

@ControllerAdvice
public class ControllerExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);
  
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(RecordNotFoundException.class)
  @ResponseBody
  public ErrorMessage recordNotFound(RuntimeException ex, HttpServletRequest req) {
    return new ErrorMessage(HttpStatus.NOT_FOUND, req.getRequestURI(), ex.getMessage());
  }
  
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(InvalidHeaderException.class)
  @ResponseBody
  public ErrorMessage missingHeader(RuntimeException ex, HttpServletRequest req) {
    return new ErrorMessage(HttpStatus.BAD_REQUEST, req.getRequestURI(), ex.getMessage());
  }
  
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(RuntimeException.class)
  @ResponseBody
  public ErrorMessage unexpectedException(RuntimeException ex, HttpServletRequest req) {
    LOG.error("Unexpected RuntimeException", ex);
    return new ErrorMessage(HttpStatus.BAD_REQUEST, req.getRequestURI(), 
      "Unexpected exception [" + ex.getClass().getCanonicalName() + "]: " + ex.getMessage());
  }
  
  public static class ErrorMessage {
    private String msg;
    private HttpStatus status;
    private String path;
    public ErrorMessage(HttpStatus status, String path, String msg) {
      this.path = path;
      this.msg = msg;
      this.status = status;
    }
    public String getMessage() {
      return msg;
    }
    public int getStatus() {
      return status.value();
    }
    public HttpStatus getError() {
      return status;
    }
    public long getTimestamp() {
      return System.currentTimeMillis();
    }
    public String getPath() {
      return path;
    }
  }
}
