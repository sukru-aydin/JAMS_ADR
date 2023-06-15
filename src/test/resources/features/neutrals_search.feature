Feature: Neutral's search and filter functions

  @1
  Scenario: 01 - User can successfully fill out the Contact Case Manager form
    Given User navigates to "neutrals" page
    When User searches "real estate"
    And User clicks on Contact Case Manager button of the first neutral
    And User fills out the Contact Case Manager form and click on submit button
    Then User sees below message on page
      |Thank you for your email.Your message will be received by the neutral's case manager. We will respond to your request as soon as we are able.|


  @2
  Scenario: 02 - User can successfully see demanded result after search
    Given User navigates to "neutrals" page
    When User searches "turkish"
    And User scrolls down to see all neutrals
    And User verifies neutrals have "CONTACT CASE MANAGER" and "VIEW COMPLETE PROFILE" buttons and they are clickable
    And User verifies displayed result number is correct
    And User verifies each neutral has at least one relevant result
    And User verifies search keyword is highlighted for all displayed neutrals

  @3
  Scenario: 03 - User can successfully use search dropdown without clicking "SEARCH" button
    Given User navigates to "neutrals" page
    When User searches "ryan" without pressing ENTER key
    Then User verifies only relevant result is displayed on dropdown
    Then User verifies each displayed neutral has his image on dropdown
    Then User verifies each displayed neutral has a link to navigate to
    Then User verifies when clicked on a neutral on dropdown, it navigates to neutral detail page

  @4
  Scenario: 04 -
    Given User navigates to "neutrals" page
    
  @b
  Scenario: 05 - bb
    Given bbb