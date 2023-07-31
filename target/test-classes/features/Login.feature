Feature: Application Logon

  
  Scenario: Login with valid credentials
    Given Open any Browser
    And Navigate to Login page
    When User enters username as "jm@gm.com" and password as "testuser" into the fields
    And User clicks on Login button
    Then Verify user is able to successfully login
