#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Authentication Functionality

  @tag1
  Scenario Outline: User login with username and password
    When user enters username as <username> and password as <password>
    Then the result should contain <result>
    
    Examples:
    	| username | password | result  |
    	| admin		 | 87904    | Failed  |
    	| admin		 | 123      | Success |
  
  @tag2  
  Scenario: Welcome message after authentication
  	When user hits home with token
  	Then Welcome Home admin
  
  @tag3
  Scenario: Get users count
  	When user request getAllUsers with token
  	Then count should be greater than 0
  
  @tag4
  Scenario: Logout
  	When user make exit with token
  	Then the result should be Success


