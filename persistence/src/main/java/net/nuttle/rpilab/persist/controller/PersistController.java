package net.nuttle.rpilab.persist.controller;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
      Tenant t = dao.get(id);
      if (t == null) {
        throw new RecordNotFoundException("No record found for tenant " + id);
      }
      return new ResponseEntity<Tenant>(t, HttpStatus.OK);
    } catch (SQLException e) {
      LOG.error("Error retrieving tenant", e);
      return new ResponseEntity<Tenant>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @RequestMapping(value="/tenant/{id}", method=RequestMethod.POST)
  public ResponseEntity<String> postTenant(@PathVariable("id") String id, 
    @RequestBody Tenant tenant,
    @RequestHeader(value="Content-Type") String contentType) {
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    if (!id.equals(tenant.getID())) {
      return new ResponseEntity<String>("Path variable id id not match tenant id", HttpStatus.CONFLICT);
    }
    try {
      int count = dao.update(id, tenant.getName(), tenant.getDesc());
      if (count == 0) {
        return new ResponseEntity<String>("No record found for id", HttpStatus.CONFLICT);
      }
    } catch (SQLException e) {
      LOG.error("Error creating tenant", e);
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
    }
    return new ResponseEntity<String>("OK", HttpStatus.OK);
  }
  
  @RequestMapping(value="/tenant/{id}", method=RequestMethod.PUT)
  public ResponseEntity<String> putTenant(@PathVariable("id") String id,
      //@RequestBody Tenant tenant,
      @RequestBody ModelMap map,
      @RequestHeader(value="Content-Type") String contentType) {
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    /*
    if (!id.equals(tenant.getID())) {
      return new ResponseEntity<String>("Path variable id id not match tenant id", HttpStatus.CONFLICT);
    }
    */
    LOG.info("Key count: " + map.keySet().size());
    try {
      dao.create(id, (String) map.get("name"), (String) map.get("desc"));
    } catch (SQLException e) {
      LOG.error("Error updating tenant", e);
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
    }
    return new ResponseEntity<String>("OK", HttpStatus.OK);
  }

  @RequestMapping(value="/tenant/{id}", method=RequestMethod.DELETE)
  public ResponseEntity<String> deleteTenant(@PathVariable("id") String id,
      @RequestHeader(value="Content-Type") String contentType) {
    
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    try {
      dao.delete(id);
    } catch (SQLException e) {
      LOG.error("Error updating tenant", e);
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
    }
    return new ResponseEntity<String>("OK", HttpStatus.OK);
  }
  
  @RequestMapping(value="/tenant", method=RequestMethod.GET)
  public ResponseEntity<List<Tenant>> getAll(@RequestHeader(value="Content-Type") String contentType) {
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    try {
      return new ResponseEntity<List<Tenant>>(dao.getAll(), HttpStatus.OK);
    } catch (SQLException e) {
      LOG.error("Error getting tenant list", e);
      return new ResponseEntity<List<Tenant>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
