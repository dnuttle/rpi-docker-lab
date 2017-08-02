package net.nuttle.rpilab.config.controller;

import static net.nuttle.rpilab.config.Constants.CONTENT_TYPE_APP_JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import net.nuttle.rpilab.config.Constants;
import net.nuttle.rpilab.controller.InvalidHeaderException;
import net.nuttle.rpilab.model.Tenant;

@Controller
public class ConfigController {

  private static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);
  private static final String PERSIST_URL = System.getenv(Constants.ENV_PERSIST_URL);
  
  @RequestMapping(value="/tenant/{id}", method=RequestMethod.GET)
  public ResponseEntity<Tenant> getTenant(@PathVariable("id") String id,
      @RequestHeader(value="Content-Type") String contentType) {
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    try {
      RestTemplate template = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> entity = new HttpEntity<>(null, headers);
      String url = PERSIST_URL + "/tenant/" + id;
      ResponseEntity<Tenant> resp = template.exchange(url, HttpMethod.GET, entity, Tenant.class);
      return resp;
    } catch (HttpClientErrorException e) {
      LOG.error("Failed to get tenant from persistence layer: " + e.getMessage());
      return new ResponseEntity<>(e.getStatusCode());
      
    } catch (Exception e) {
      LOG.error("Failed to get tenant from persistence layer", e);
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
    try {
      RestTemplate template = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<Tenant> entity = new HttpEntity<>(tenant, headers);
      String url = PERSIST_URL + "/tenant/" + id;
      ResponseEntity<String> resp = template.exchange(url, HttpMethod.POST, entity, String.class);
      return resp;
    } catch (HttpClientErrorException e) {
      LOG.error("Failed to post tenant to persistence layer: " + e.getMessage());
      return new ResponseEntity<>(e.getStatusCode());
    } catch (Exception e) {
      LOG.error("Failed to post tenant to persistence layer", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @RequestMapping(value="/tenant/{id}", method=RequestMethod.PUT)
  public ResponseEntity<String> putTenant(@PathVariable("id") String id, 
    @RequestBody Tenant tenant,
    @RequestHeader(value="Content-Type") String contentType) {
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    try {
      RestTemplate template = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<Tenant> entity = new HttpEntity<>(tenant, headers);
      String url = PERSIST_URL + "/tenant/" + id;
      ResponseEntity<String> resp = template.exchange(url,  HttpMethod.PUT, entity, String.class);
      return resp;
    } catch (HttpClientErrorException e) {
      LOG.error("Failed to put tenant to persistence layer: " + e.getMessage());
      return new ResponseEntity<>(e.getStatusCode());
    } catch (Exception e) {
      LOG.error("Failed to put tenant to persistence layer", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @RequestMapping(value="/tenant/{id}", method=RequestMethod.DELETE)
  public ResponseEntity<String> deleteTenant(@PathVariable("id") String id,
    @RequestHeader(value="Content-Type") String contentType) {
    if (!CONTENT_TYPE_APP_JSON.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected application/json");
    }
    try {
      RestTemplate template = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<Tenant> entity = new HttpEntity<>(null, headers);
      String url = PERSIST_URL + "/tenant/" + id;
      ResponseEntity<String> resp = template.exchange(url,  HttpMethod.DELETE, entity, String.class);
      return resp;
    } catch (HttpClientErrorException e) {
      LOG.error("Failed to delete tenant from persistence layer: " + e.getMessage());
      return new ResponseEntity<>(e.getStatusCode());
    } catch (Exception e) {
      LOG.error("Failed to delete tenant from persistence layer", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
