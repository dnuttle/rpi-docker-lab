package net.nuttle.rpilab.config.controller;

import static net.nuttle.rpilab.config.Constants.CONTENT_TYPE_APP_JSON;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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
import net.nuttle.rpilab.controller.HttpHeaderUtil;
import net.nuttle.rpilab.controller.InvalidHeaderException;
import net.nuttle.rpilab.model.Tenant;

@Controller
public class ConfigController {

  private static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);
  private static final String PERSIST_URL = System.getenv(Constants.ENV_PERSIST_URL);
  
  @Autowired
  HttpHeaderUtil httpHeaderUtil;
  
  @RequestMapping(value="/tenant/{id}", method=RequestMethod.GET)
  public ResponseEntity<Tenant> getTenant(@PathVariable("id") String id,
      @RequestHeader(value="Content-Type") String contentType) {
    HttpEntity<String> entity = new HttpEntity<>(null, getHeaders());
    return doGet(PERSIST_URL + "/tenant/" + id, contentType, entity, "Failed to get tenant from persistence layer");
  }
  
  @RequestMapping(value="/tenant/{id}", method=RequestMethod.POST)
  public ResponseEntity<String> postTenant(@PathVariable("id") String id,
    @RequestBody Tenant tenant,
    @RequestHeader(value="Content-Type") String contentType) {
    HttpEntity<Tenant> entity = new HttpEntity<>(tenant, getHeaders());
    return doPost(PERSIST_URL + "/tenant/" + id, contentType, entity, "Failed to post tenant to persistence layer");
  }
  
  @RequestMapping(value="/tenant/{id}", method=RequestMethod.PUT)
  public ResponseEntity<String> putTenant(@PathVariable("id") String id, 
    @RequestBody Tenant tenant,
    @RequestHeader(value="Content-Type") String contentType) {
    HttpEntity<Tenant> entity = new HttpEntity<>(tenant, getHeaders());
    return doPut(PERSIST_URL + "/tenant/" + id, contentType, entity, "Failed to post tenant to persistence layer");
  }
  
  @RequestMapping(value="/tenant/{id}", method=RequestMethod.DELETE)
  public ResponseEntity<String> deleteTenant(@PathVariable("id") String id,
    @RequestHeader(value="Content-Type") String contentType) {
    HttpEntity<String> entity = new HttpEntity<>(null, getHeaders());
    return doDelete(PERSIST_URL + "/tenant/" + id, contentType, entity, "Failed to delete tenant from peristence layer");
  }
  
  @RequestMapping(value="/tenant", method=RequestMethod.GET)
  public ResponseEntity<List<Tenant>> getAllTenants(@RequestHeader(value="Content-Type") String contentType) {
    HttpEntity<String> entity = new HttpEntity<>(null, getHeaders());
    return doGet(PERSIST_URL + "/tenant", contentType, entity, "Failed to get all tenants from persistence layer");
  }
  
  private <T,S> ResponseEntity<T> doGet(String url, String contentType, HttpEntity<S> entity, String errMessage) {
    LOG.info("url: " + url);
    return exchange(url, contentType, entity, errMessage, HttpMethod.GET);
  }
  
  private <T,S> ResponseEntity<T> doPost(String url, String contentType, HttpEntity<S> entity, String errMessage) {
    return exchange(url, contentType, entity, errMessage, HttpMethod.POST);
  }

  private <T,S> ResponseEntity<T> doPut(String url, String contentType, HttpEntity<S> entity, String errMessage) {
    return exchange(url, contentType, entity, errMessage, HttpMethod.PUT);
  }

  private <T,S> ResponseEntity<T> doDelete(String url, String contentType, HttpEntity<S> entity, String errMessage) {
    return exchange(url, contentType, entity, errMessage, HttpMethod.DELETE);
  }

  private <T,S> ResponseEntity<T> exchange(String url, String contentType, HttpEntity<S> entity,
    String errMessage, HttpMethod method) {
    httpHeaderUtil.checkContentHeader(contentType);
    try {
      RestTemplate template = new RestTemplate();
      return template.exchange(url, method, entity, new ParameterizedTypeReference<T>() { });
    } catch (HttpClientErrorException e) {
      LOG.error(errMessage + ": " + e.getMessage());
      return new ResponseEntity<>(e.getStatusCode());
    } catch (Exception e) {
      LOG.error(errMessage, e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
  }
  
  private HttpHeaders getHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return headers;
  }
  
}
