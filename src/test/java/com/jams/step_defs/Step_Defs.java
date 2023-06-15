package com.jams.step_defs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.jams.pages.BasePage;
import com.jams.pages.NeutralsPage;
import com.jams.utilities.BrowserUtils;
import com.jams.utilities.Driver;
import io.cucumber.java.en.*;


import static org.junit.Assert.*;

public class Step_Defs {

    private NeutralsPage neutralsPage = new NeutralsPage();
    private String searchKeyword;

    @Given("User navigates to {string} page")
    public void user_navigates_to_page(String pageName) {
        new BasePage().navigateTo(pageName);
    }

    @When("User searches {string}")
    public void user_searches(String keyword) {
        new BasePage().search(keyword);
        searchKeyword = keyword;
    }

    @When("User verifies neutrals have {string} and {string} buttons and they are clickable")
    public void user_verifies_neutrals_have_and_buttons_and_they_are_clickable(String expectedCCM, String expectedVCP) {
        neutralsPage.has_CCM_and_VCP_Buttons(expectedCCM,expectedVCP);
    }


    @When("User clicks on Contact Case Manager button of the first neutral")
    public void user_clicks_on_contact_case_manager_button_of_the_first_neutral() {
        neutralsPage.click(neutralsPage.firstContactCaseManagerButton);
    }

    @When("User fills out the Contact Case Manager form and click on submit button")
    public void user_fills_out_the_contact_case_manager_form_and_click_on_submit_button() {
        neutralsPage.fillOutForm();
    }

    @Then("User sees below message on page")
    public void user_sees_below_message_on_page(String expectedMessage) {
        String actualMessage = neutralsPage.labelThankYou.getAttribute("textContent");
        System.out.println(actualMessage);
        System.out.println(expectedMessage);

        //comparing actual & expected
        assertEquals(actualMessage,expectedMessage);
    }


    @When("User scrolls down to see all neutrals")
    public void user_scrolls_down_to_see_all_neutrals() {
        neutralsPage.scrollDownToSeeAllElements();
    }


    @When("User verifies displayed result number is correct")
    public void user_verifies_displayed_result_number_is_correct() {
        neutralsPage.areResultsSame();
    }

    @When("User verifies each neutral has at least one relevant result")
    public void user_verifies_each_neutral_has_at_least_one_relevant_result() {
        neutralsPage.hasAtLeastOneRelevantResult();
    }


    @When("User verifies search keyword is highlighted for all displayed neutrals")
    public void user_verifies_search_keyword_is_highlighted_for_all_displayed_neutrals() {
        neutralsPage.isSearchKeywordHighlighted();
    }



    @When("User searches {string} without pressing ENTER key")
    public void user_searches_without_pressing_enter_key(String keyword) {
        new BasePage().searchWithoutPressingEnter(keyword);
        searchKeyword = keyword;
    }



    @Then("User verifies only relevant result is displayed on dropdown")
    public void user_verifies_only_relevant_result_is_displayed_on_dropdown() {
        neutralsPage.isOnlyRelevantResultDisplayed(searchKeyword);
    }


    @Then("User verifies each displayed neutral has his image on dropdown")
    public void user_verifies_each_displayed_neutral_has_his_image_on_dropdown() {
        neutralsPage.hasImage();
    }

    @Then("User verifies each displayed neutral has a link to navigate to")
    public void user_verifies_each_displayed_neutral_has_a_link_to_navigate_to() {
        neutralsPage.hasLink();
    }
    @Then("User verifies when clicked on a neutral on dropdown, it navigates to neutral detail page")
    public void user_verifies_when_clicked_on_a_neutral_on_dropdown_it_navigates_to_neutral_detail_page() {
        neutralsPage.clickOnFirstNeutralOnDropdown();
    }







    //practice
    @Given("bbb")
    public void bbb() {


    }


}
