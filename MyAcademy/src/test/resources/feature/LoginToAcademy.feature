

@WithValidLoginCredentials
Feature: LoginToKhanAcademy

Scenario: LoginWithAccountCredentials--2
Given the user has valid login credentials
When the user enters valid user name and password
Then user should be logged in successfully