/**
 * 
 */
package com.yoga.security;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.yoga.security.model.AuthResponse;
import com.yoga.security.model.TenantModel;

import io.cucumber.junit.Cucumber;

/**
 * @author Yoga
 *
 */
@RunWith(Cucumber.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class SpringCucumberIntegrationTests {
	
	private final String hostname = "localhost";
	
	@LocalServerPort
	private int port;
	
	private String status;
	
	private String token;
	
	private RestTemplate restTemplate;
	
	/**
	 * 
	 */
	public SpringCucumberIntegrationTests() {
		this.restTemplate = new RestTemplate();
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	 public TestContext testContext() {
		 return TestContext.CONTEXT;
	 }
	
	private String getEndPoint(final String path) {
		return "http://" + hostname + ":" + port + path;
	}
	
	public void authenticateUser(String username, String password) {
		TenantModel login = new TenantModel();
		login.setUserName(username);
		login.setPassword(password);
		AuthResponse res = restTemplate.postForObject(getEndPoint("/authenticate"), login, AuthResponse.class);
		setStatus(res.getResult());
		testContext().set("token", res.getToken());
	}
	
	public void postLogin(final String path) {
		ResponseEntity<String> res = restTemplate.exchange(getEndPoint("/" + path), HttpMethod.GET, getHeader(), String.class);
		String status = res.getBody();
		setStatus(status);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HttpEntity getHeader() {
		HttpHeaders headers = new HttpHeaders();
		String token = testContext().get("token");
		headers.add("Authorization", "Bearer " + token);
		return new HttpEntity(headers);
	}
	
	public void getUserCount(final String path) {
		ResponseEntity<Integer> res = restTemplate.exchange(getEndPoint("/" + path), HttpMethod.GET, getHeader(), Integer.class);
		int result = res.getBody();
		setStatus(String.valueOf(result));
	}	
	
	public void logout(final String path) {
		ResponseEntity<String> res = restTemplate.exchange(getEndPoint("/" + path), HttpMethod.DELETE, getHeader(), String.class);
		String status = res.getBody();
		setStatus(status);
	}
}
