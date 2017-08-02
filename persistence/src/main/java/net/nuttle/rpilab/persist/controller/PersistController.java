package net.nuttle.rpilab.persist.controller;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.nuttle.rpilab.controller.InvalidHeaderException;
import net.nuttle.rpilab.model.Env;
import net.nuttle.rpilab.model.Tenant;
import net.nuttle.rpilab.persist.dao.EnvDAO;
import net.nuttle.rpilab.persist.dao.TenantDAO;

import static net.nuttle.rpilab.config.Constants.CONTENT_TYPE_APP_JSON;

@Controller
public class PersistController {

  private static final Logger LOG = LoggerFactory.getLogger(PersistController.class);
  
  @Autowired
  TenantDAO tenantDao;

  @Autowired
  EnvDAO envDao;
  
  @RequestMapping(value="/tenant/{id}", method=RequestMethod.GET)
  public ResponseEntity<Tenant> getTenant(@PathVariable("id") String id, 
    @RequestHeader(value="Content-Type") String contentType) {
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    try {
      Tenant t = tenantDao.get(Tenant.Builder.instance().setId(id).build());
      if (t == null) {
        throw new RecordNotFoundException("No record found for tenant " + id);
      }
      return new ResponseEntity<>(t, HttpStatus.OK);
    } catch (SQLException e) {
      LOG.error("Error retrieving tenant", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
      return new ResponseEntity<>("Path variable id id not match tenant id", HttpStatus.CONFLICT);
    }
    try {
      int count = tenantDao.update(tenant);
      if (count == 0) {
        return new ResponseEntity<>("No record found for id", HttpStatus.CONFLICT);
      }
    } catch (SQLException e) {
      LOG.error("Error creating tenant", e);
      return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }
  
  @RequestMapping(value="/tenant/{id}", method=RequestMethod.PUT)
  public ResponseEntity<String> putTenant(@PathVariable("id") String id,
      @RequestBody Tenant tenant,
      @RequestHeader(value="Content-Type") String contentType) {
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    if (!id.equals(tenant.getID())) {
      return new ResponseEntity<>("Path variable id id not match tenant id", HttpStatus.CONFLICT);
    }
    try {
      tenantDao.create(tenant);
    } catch (SQLException e) {
      LOG.error("Error updating tenant", e);
      return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }

  @RequestMapping(value="/tenant/{id}", method=RequestMethod.DELETE)
  public ResponseEntity<String> deleteTenant(@PathVariable("id") String id,
      @RequestHeader(value="Content-Type") String contentType) {
    
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    try {
      tenantDao.delete(Tenant.Builder.instance().setId(id).build());
    } catch (SQLException e) {
      LOG.error("Error updating tenant", e);
      return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }
  
  @RequestMapping(value="/tenant", method=RequestMethod.GET)
  public ResponseEntity<List<Tenant>> getAllTenants(@RequestHeader(value="Content-Type") String contentType) {
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    try {
      return new ResponseEntity<List<Tenant>>(tenantDao.getAll(), HttpStatus.OK);
    } catch (SQLException e) {
      LOG.error("Error getting tenant list", e);
      return new ResponseEntity<List<Tenant>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @RequestMapping(value="/tenant/{tenant}/env/{id}", method=RequestMethod.GET)
  public ResponseEntity<Env> getEnv(@PathVariable("tenant") String tenant,
    @PathVariable("id") String id,
    @RequestHeader(value="Content-Type") String contentType) {
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    Env e = Env.Builder.instance().setTenantID(tenant).setID(id).build();
    try {
      e = envDao.get(e);
      if (e == null) {
        throw new RecordNotFoundException("No record found for env " + id);
      }
      return new ResponseEntity<Env>(e, HttpStatus.OK);
    } catch (SQLException ex) {
      LOG.error("Error getting env", ex);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @RequestMapping(value="/tenant/{tenant}/env/{id}", method=RequestMethod.PUT)
  public ResponseEntity<String> putEnv(@PathVariable("tenant") String tenant,
    @PathVariable("id") String id,
    @RequestBody Env env,
    @RequestHeader(value="Content-Type") String contentType) {
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    if (!tenant.equals(env.getTenantId())) {
      return new ResponseEntity<>("Path variable tenant does not match env tenant", HttpStatus.CONFLICT);
    }
    if (!id.equals(env.getId())) {
      return new ResponseEntity<>("Path variable id does not match env id", HttpStatus.CONFLICT);
    }
    try {
      envDao.create(env);
      return new ResponseEntity<>("OK", HttpStatus.OK);
    } catch (SQLException ex) {
      LOG.error("Error putting env", ex);
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
  }

  @RequestMapping(value="/tenant/{tenant}/env/{id}", method=RequestMethod.POST)
  public ResponseEntity<String> postEnv(@PathVariable("tenant") String tenant,
    @PathVariable("id") String id,
    @RequestBody Env env,
    @RequestHeader(value="Content-Type") String contentType) {
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    if (!tenant.equals(env.getTenantId())) {
      return new ResponseEntity<>("Path variable tenant does not match env tenant", HttpStatus.CONFLICT);
    }
    if (!id.equals(env.getId())) {
      return new ResponseEntity<>("Path variable id does not match env id", HttpStatus.CONFLICT);
    }
    try {
      envDao.update(env);
      return new ResponseEntity<>("OK", HttpStatus.OK);
    } catch (SQLException e) {
      LOG.error("Error updating env", e);
      return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
  }

  @RequestMapping(value="/tenant/{tenant}/env/{id}", method=RequestMethod.DELETE)
  public ResponseEntity<String> deleteEnv(@PathVariable("tenant") String tenant,
    @PathVariable("id") String id,
    @RequestHeader(value="Content-Type") String contentType) {
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    try {
      Env e = Env.Builder.instance()
        .setTenantID(tenant)
        .setID(id)
        .build();
      envDao.delete(e);
      return new ResponseEntity<>("OK", HttpStatus.OK);
    } catch (SQLException e) {
      LOG.error("Error deleting env", e);
      return new ResponseEntity<>("Error deleting env", HttpStatus.CONFLICT);
    }
  }

  @RequestMapping(value="/tenant/{tenant}/env", method=RequestMethod.GET)
  public ResponseEntity<List<Env>> getAllEnvsForTenant(@PathVariable("tenant") String tenant,
    @RequestHeader(value="Content-Type") String contentType) {
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    try {
      Tenant t = Tenant.Builder.instance().setId(tenant).build();
      return new ResponseEntity<>(envDao.getAllForTenant(t), HttpStatus.OK);
    } catch (SQLException e) {
      LOG.error("Error retrieving envs", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @RequestMapping(value="/tenant/{tenant}/env", method=RequestMethod.DELETE)
  public ResponseEntity<String> deleteAllEnvsForTenant(@PathVariable("tenant") String tenant,
    @RequestHeader(value="Content-Type") String contentType) {
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    try {
      Tenant t = Tenant.Builder.instance().setId(tenant).build();
      envDao.deleteAllForTenant(t);
      return new ResponseEntity<>("OK", HttpStatus.OK);
    } catch (SQLException e) {
      LOG.error("Error deleting envs for tenant", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
}
