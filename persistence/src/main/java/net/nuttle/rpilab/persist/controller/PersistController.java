package net.nuttle.rpilab.persist.controller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.nuttle.rpilab.model.Tenant;
import net.nuttle.rpilab.persist.dao.TenantDAO;

@Controller
public class PersistController {

  private static final Logger LOG = LoggerFactory.getLogger(PersistController.class);
  private static final String CONTENT_TYPE_APP_JSON = "application/json";
  
  @Autowired
  TenantDAO dao;
  
  @RequestMapping(value="/tenant/{id}", method=RequestMethod.GET)
  
  public ResponseEntity<Tenant> getTenant(@PathVariable("id") String id, 
    @RequestHeader(value="Content-Type") String contentType) {
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    try {
      Tenant t = dao.getTenant(id);
      if (t == null) {
        throw new RecordNotFoundException("No record found for tenant " + id);
      }
      if (true) {
        throw new NumberFormatException("abc");
      }
      return new ResponseEntity<Tenant>(t, HttpStatus.OK);
    } catch (SQLException e) {
      LOG.error("Error retrieving tenant", e);
      return new ResponseEntity<Tenant>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @RequestMapping(value="/tenant/{id}", method=RequestMethod.POST)
  public ResponseEntity<String> postTenant(@PathVariable("id") String id, 
    @RequestParam(name="name") String name, 
    @RequestParam(name="desc") String desc) {
  return new ResponseEntity<String>("OK", HttpStatus.OK);
  }
  
}
