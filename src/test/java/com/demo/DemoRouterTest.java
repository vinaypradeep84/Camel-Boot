package com.demo;

import com.demo.model.User;
import org.apache.camel.CamelContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DemoRouterTest{
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CamelContext camelContext;

    @LocalServerPort
     int randomServerPort;

    @Test
    public void testAddUserSuccess() throws URISyntaxException
   {
       final String baseUrl = "http://localhost:"+randomServerPort+"/camel/users";
       URI uri = new URI(baseUrl);
       User user = new User();
       user.setId(1);
       user.setName("Purna");

       HttpHeaders headers = new HttpHeaders();
       headers.set("application/json", "true");

        HttpEntity<User> request = new HttpEntity<>(user, headers);

       ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
       Assert.assertEquals(201, result.getStatusCodeValue());
   }

   @Test(expected = org.springframework.http.converter.HttpMessageNotReadableException.class)
    public void testGetUserSuccess() throws URISyntaxException
   {
       RestTemplate restTemplate = new RestTemplate();

       final String baseUrl = "http://localhost:"+randomServerPort+"/camel/users/";
       URI uri = new URI(baseUrl);

       HttpHeaders headers = new HttpHeaders();


       HttpEntity<User> requestEntity = new HttpEntity<>(null, headers);

       try
       {
           ResponseEntity<User> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, User.class);
           Assert.fail();
       }
       catch(HttpClientErrorException ex)
       {
           //Verify bad request and missing header
           Assert.assertEquals(400, ex.getRawStatusCode());

       }

    }

}