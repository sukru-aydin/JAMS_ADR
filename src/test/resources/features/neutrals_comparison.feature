Feature: Neutrals comparison

  @smoke
  Scenario: Neutral IDs coming from "searchG2" & "live" should be same
    Given User gets searchG2 neutral ids as List
    When User gets live neutral ids as List
    Then User compares 2 lists