package com.jams.pages;

import com.jams.utilities.BrowserUtils;
import com.jams.utilities.ConfigurationReader;
import com.jams.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;

public class BasePage {

    public BasePage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    //================================= LOCATORS =================================

    @FindBy(id = "input-search")
    public WebElement searchBarHome;

    @FindBy(id = "search-neutral")
    public WebElement searchBarNeutral;

    @FindBy(name = "//input[@name=\"search-locations\"]")
    public WebElement searchBarLocations;

    @FindBy(id = "input-search-news")
    public WebElement searchBarNews;

    @FindBy(xpath = "//h3[@class='heading-large content-title']//span[@class='count']")
    public WebElement countTitle;

    @FindBy(xpath = "//div[@class='search-container']//div[@class='searchbar']/following-sibling::div[@hidden='true']")
    public WebElement allNeutralsText_hidden_true;

    //================================= METHODS ==================================

    /**
     * explicitly wait object
     */
    private WebDriverWait wait = new WebDriverWait(Driver.getDriver(),20);


    /**
     * navigates to given page
     * asserts title
     * @param pageName
     */
    public void navigateTo(String pageName){

        switch (pageName){
            case "home":
                Driver.getDriver().get(ConfigurationReader.getProperty("home_url"));
                verifyTitle("JAMS: Mediation, Arbitration and ADR Services");
                break;
            case "neutrals":
                Driver.getDriver().get(ConfigurationReader.getProperty("neutrals_url"));
                verifyTitle("Search");
                break;
            case "locations":
                Driver.getDriver().get(ConfigurationReader.getProperty("locations_url"));
                verifyTitle("Locations");
                break;
            case "news":
                Driver.getDriver().get(ConfigurationReader.getProperty("news_url"));
                verifyTitle("News");
                break;
            default:
                System.out.println("Wrong page name is provided!");
                Assert.fail("Wrong page name is provided!");
        }

    }


    /**
     * searches given keyword according to page
     * @param keyword
     */
    public void search(String keyword){

        switch (Driver.getDriver().getTitle()){
            case "JAMS: Mediation, Arbitration and ADR Services":
                searchBarHome.sendKeys(keyword + Keys.ENTER);
                waitVisibilityOf(countTitle);
                break;
            case "Search":
                searchBarNeutral.sendKeys(keyword + Keys.ENTER);
                waitVisibilityOf(countTitle);
                break;
            case "Locations":
                searchBarLocations.sendKeys(keyword + Keys.ENTER);
                waitVisibilityOf(countTitle);
                break;
            case "News":
                searchBarNews.sendKeys(keyword + Keys.ENTER);
                waitVisibilityOf(countTitle);
                break;
        }
    }

    private Actions actions;
    public void searchWithoutPressingEnter(String keyword){

        actions = new Actions(Driver.getDriver());

        switch (Driver.getDriver().getTitle()){
            case "JAMS: Mediation, Arbitration and ADR Services":
                searchBarHome.sendKeys(keyword);
                actions.moveToElement(searchBarHome).sendKeys(Keys.SPACE).perform();
                //waitVisibilityOf(countTitle);
                break;
            case "Search":
                searchBarNeutral.sendKeys(keyword);
                BrowserUtils.sleep(1);
                actions.sendKeys(searchBarNeutral,Keys.SPACE).build().perform();
                checkInvisibilityOf(allNeutralsText_hidden_true);
                break;
            case "Locations":
                searchBarLocations.sendKeys(keyword);
                actions.moveToElement(searchBarLocations).sendKeys(Keys.SPACE).perform();
                //waitVisibilityOf(countTitle);
                break;
            case "News":
                searchBarNews.sendKeys(keyword);
                actions.moveToElement(searchBarNews).sendKeys(Keys.SPACE).perform();
                //waitVisibilityOf(countTitle);
                break;
        }
    }


    /**
     * first (explicitly) waits for title containing the given title
     * then, compares the actual title with the given title
     * @param expectedTitle
     */
    public void verifyTitle(String expectedTitle){
        wait.until(ExpectedConditions.titleContains(expectedTitle));
        Assert.assertEquals(expectedTitle,Driver.getDriver().getTitle());
    }


    /**
     * (explicitly) waits for the visibility of given WebElement
     * @param element
     */
    public void waitVisibilityOf(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void checkInvisibilityOf(WebElement element){
        try {
            Assert.assertFalse(element.isDisplayed());
            System.out.println("Step Failed : Element is displayed, should have not been displayed");
        }catch (RuntimeException e){
            System.out.println("Step Passed : Element is not displayed, which means ALL NEUTRAL text is displayed");
        }
    }

    public void click(WebElement element){
        waitVisibilityOf(element);
        element.click();
    }



}
