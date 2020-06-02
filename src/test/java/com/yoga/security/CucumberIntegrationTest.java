/**
 * 
 */
package com.yoga.security;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
/**
 * @author Yoga
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/auth.feature", plugin = {"pretty", "json:target/cucumbertest/report.json"})
public class CucumberIntegrationTest {

}
	