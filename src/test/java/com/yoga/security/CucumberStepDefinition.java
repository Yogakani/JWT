/**
 * 
 */
package com.yoga.security;

import static org.junit.Assert.assertTrue;

import io.cucumber.java8.En;

/**
 * @author Yoga
 *
 */
public class CucumberStepDefinition extends SpringCucumberIntegrationTests implements En {
	
	public CucumberStepDefinition() {
		When("user enters username as {word} and password as {word}" , (String username, String password) -> {
			authenticateUser(username, password);
		});
		
		Then("the result should contain {word}", (String result) -> {
			String status = getStatus();
			assertTrue(status.equals(result));
		});
		
		When("user hits {word} with token", (String path) -> {
			postLogin(path);
		});
		
		Then("Welcome Home {word}", (String result) -> {
			String status = getStatus();
			assertTrue(status.contains(result));
		});
		
		When("user request {word} with token", (String path) -> {
			getUserCount(path);
		});
		
		Then("count should be greater than {word}", (String result) -> {
			int status = Integer.parseInt(getStatus());
			assertTrue(status > Integer.parseInt(result));
		});
		
		When("user make {word} with token", (String path) -> {
			logout(path);
		});
		
		Then("the result should be {word}", (String result) -> {
			String status = getStatus();
			assertTrue(status.equals(result));
		});
	}
}
