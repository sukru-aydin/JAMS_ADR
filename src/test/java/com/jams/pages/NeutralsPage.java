package com.jams.pages;

import com.github.javafaker.Faker;
import com.jams.utilities.BrowserUtils;
import com.jams.utilities.Driver;
import io.cucumber.java.it.Ma;
import io.cucumber.java.sl.In;
import org.codehaus.plexus.util.CollectionUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.SoftReference;
import java.text.Normalizer;
import java.util.*;
import java.net.URL;

import static org.junit.Assert.*;

public class NeutralsPage extends BasePage {

    public NeutralsPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    //================================= LOCATORS =================================
    @FindBy(xpath = "//div[@class='results']//article[@class='profile-card']")
    public List<WebElement> profileCards;

    @FindBy(xpath = "//div[@class='results']//article[@class='profile-card'][1]//footer//a[1]")
    public WebElement firstContactCaseManagerButton;


    @FindBy(xpath = "//a[@data-modal='modal-neutral-case-manager']")
    public List<WebElement> buttonsContactCaseManager;

    @FindBy(xpath = "//a[@class='btn btn-large btn-secondary']")
    public List<WebElement> buttonsViewCompleteProfile;


    @FindBy(xpath = "//div[@class='results']//article[@class='profile-card'][1]//footer//a[2]")
    public WebElement firstViewCompleteProfileButton;

    @FindBy(xpath = "//textarea[@id='submittor-message']")
    public WebElement inputBoxMessage;

    @FindBy(xpath = "//input[@id='submittor-name']")
    public WebElement inputBoxName;

    @FindBy(xpath = "//input[@id='submittor-company']")
    public WebElement inputBoxCompany;

    @FindBy(xpath = "//input[@id='submittor-email']")
    public WebElement inputBoxEmail;

    @FindBy(xpath = "//input[@id='submittor-phone']")
    public WebElement inputBoxPhoneNumber;

    @FindBy(xpath = "/html/body/div[2]/div[3]/div[1]/div/div/span/div[1]")
    public WebElement checkBoxReCaptcha1;

    @FindBy(xpath = "//iframe[@title='reCAPTCHA']")
    public WebElement frame;

    @FindBy(xpath = "//button[.='              SUBMIT']")
    public WebElement buttonSubmit;

    @FindBy(xpath = "//div[@class='thank-you-label']")
    public WebElement labelThankYou;

    @FindBy(xpath = "//article[@class='profile-card']//div[@class='profile-mobile']")
    public List<WebElement> articles;

    //returns number of "1 result" section as List
    @FindBy(xpath = "//article//div//div//div//div//div//p//span//a")
    public List<WebElement> numberOfResults;

    @FindBy(xpath = "//article/div/div[2]/div/div[2]/div[2]/p")
    public List<WebElement> containers;

    @FindBy(xpath = "//article/div/div[2]/div/div[2]/div[2]/p/a")
    public List<WebElement> highlightedKeywords;

    @FindBy(xpath = "//a[@class='profile-name']")
    public List<WebElement> profileNames;

    @FindBy(xpath = "//ul[@class='list location-list']//li[@data-acsb-menu='li']")
    public List<WebElement> displayedDropdownResults;

   @FindBy(xpath = "//li[@class='neutral']")
    public List<WebElement> allDropdownResults;

    @FindBy(xpath = "//h1[@class='heading-x-large hero-title']")
    public WebElement nameHeroTitleElement;




    //================================= METHODS ==================================

    private Faker faker;
    private JavascriptExecutor js;
    private List<Integer> displayedNeutralsIndexes;


    public void fillOutForm() {
        faker = new Faker();
        BrowserUtils.sleep(1);

        inputBoxMessage.sendKeys("Test message : " + faker.lorem().sentence(15));
        BrowserUtils.sleep(1);

        inputBoxName.sendKeys(faker.name().name());
        BrowserUtils.sleep(1);

        inputBoxCompany.sendKeys(faker.company().name());
        BrowserUtils.sleep(1);

        String email = faker.internet().emailAddress();
        String normalizedEmail = Normalizer.normalize(email, Normalizer.Form.NFD);
        String nonTurkishEmail = normalizedEmail.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        inputBoxEmail.sendKeys(nonTurkishEmail);
        BrowserUtils.sleep(1);

        inputBoxPhoneNumber.sendKeys(faker.number().digits(10));
        BrowserUtils.sleep(1);

        Driver.getDriver().switchTo().frame(frame);
        checkBoxReCaptcha1.click();
        Driver.getDriver().switchTo().parentFrame();
        BrowserUtils.sleep(20);

        new WebDriverWait(Driver.getDriver(), 60).until(ExpectedConditions.elementToBeClickable(buttonSubmit));
        buttonSubmit.click();
        BrowserUtils.sleep(5);

    }

    public void scrollDownToSeeAllElements() {
        int howManyNeutrals = Integer.parseInt(countTitle.getText());
        System.out.println("howManyNeutrals = " + howManyNeutrals);

        int size = articles.size();
        System.out.println("size = " + size);

        js = (JavascriptExecutor) Driver.getDriver();

        //max 60 times scrolling is presumed
        int scrolledTimes = 60;

        while (howManyNeutrals > size && scrolledTimes >0) {
            BrowserUtils.sleep(1);
            js.executeScript("window.scrollBy(0,1000)");
            size = Driver.getDriver().findElements(By.xpath("//article[@class='profile-card']//div[@class='profile-mobile']")).size();
            scrolledTimes--;

        }

        System.out.println("howManyScrolledNeutrals = " + size);
        System.out.println("============================================");

        if (scrolledTimes == 0){
            throw new RuntimeException("Displayed neutral number is NOT same as displayed number in title");
        }
    }

    public void has_CCM_and_VCP_Buttons(String expectedCCM, String expectedVCP) {
        for (WebElement button : buttonsContactCaseManager) {
            //System.out.println("buttonName = " + button.getAttribute("outerText"));
            assertEquals(button.getAttribute("outerText"), expectedCCM);
            assertTrue(button.isEnabled());
        }

        for (WebElement button : buttonsViewCompleteProfile) {
            //System.out.println("buttonName = " + button.getAttribute("outerText"));
            assertEquals(button.getAttribute("outerText"), expectedVCP);
            assertTrue(button.isEnabled());
        }
    }

    public boolean areResultsSame() {
        int countTitle = Integer.parseInt(new BasePage().countTitle.getText());
        System.out.println("countTitle = " + countTitle);

        int numberOfArticles = articles.size();
        System.out.println("numberOfArticles = " + numberOfArticles);
        System.out.println("============================================");

        return countTitle == numberOfArticles;
    }

    public boolean hasAtLeastOneRelevantResult() {

        int sizeOfNumberOfResults = numberOfResults.size();
        System.out.println("sizeOfNumberOfResults = " + sizeOfNumberOfResults);

        int isDigitNumber = 0;

        for (WebElement element : numberOfResults) {
            int x_results = Integer.parseInt(element.getText().substring(0, element.getText().indexOf(" ")));
            if (x_results > 0) {
                isDigitNumber++;
            }
            //System.out.println("x_results = " + x_results);
        }

        System.out.println("isDigitNumber = " + isDigitNumber);
        System.out.println("============================================");

        return isDigitNumber == sizeOfNumberOfResults;
    }

    public void isSearchKeywordHighlighted() {

        //result to be printed
        List<Map<String,String>> havingNonRelevantResultNeutrals = new ArrayList<>();

        //outer for loop
        for (int i = 0; i < numberOfResults.size(); i++) {

            //returns & prints the number of expectedHighlightedPages
            WebElement element = numberOfResults.get(i);
            int expectedHighlightedPages = Integer.parseInt(element.getText().substring(0, element.getText().indexOf(" ")));
            //System.out.println("expectedHighlightedPages "+(i+1)+" = " + expectedHighlightedPages);

            String locator = "";
            int actualHighlightedPages = 0;

            //inner for loop
            //takes the number of expectedHighlightedPages, and iterates through the pages
            for (int j = 1; j <= expectedHighlightedPages; j++) {

                //dynamic locator, returns a highlighted word
                //i--> each neutral
                //j--> each page for given neutral
                locator = "(//article[@class='profile-card']" +
                        "//div[@class='profile-mobile'])[" + (i + 1) + "]/preceding-sibling::div/div[2]/div/div[2]/div[2]/p[" + j + "]/a";

                List<WebElement> elementList = Driver.getDriver().findElements(By.xpath(locator));

                //if there is at least a highlighted word, actualHighlightedPages++
                //if not, expectedHighlightedPages will be less than actualHighlightedPages -- for next step
                if (elementList.size() > 0) {
                    actualHighlightedPages++;
                }

                //end of inner loop
            }

            //System.out.println("actualHighlightedPages = " + actualHighlightedPages);
            //System.out.println("actualHighlightedPages   "+(i+1)+" = " + actualHighlightedPages);
            System.out.println("expectedHighlightedPages "+(i+1)+" = " + expectedHighlightedPages
            +" , "+actualHighlightedPages+" = actualHighlightedPages "+(i+1));

            //comparing expectedHighlightedPages & actualHighlightedPages
            //putting non-relevant results in a list as a map
            if (expectedHighlightedPages != actualHighlightedPages) {
                Map<String,String> de_highlightedNeutral = new LinkedHashMap<>();
                de_highlightedNeutral.put("order",(i+1)+"");
                de_highlightedNeutral.put("name",profileNames.get(i).getText());
                havingNonRelevantResultNeutrals.add(de_highlightedNeutral);
            }

            //end of outer loop
        }

            //providing the result on console
            //if we have an element in havingNonRelevantResultNeutrals list of map --> assert.fail();
            if (havingNonRelevantResultNeutrals.size()>0){
                System.out.println("------------------------------------------------------------");
                System.out.println("Displayed numbered neutrals have de-highlighted results : "+havingNonRelevantResultNeutrals);
                System.out.println("------------------------------------------------------------");
                fail();
            }else {
                System.out.println("------------------------------------------------------------");
                System.out.println("All displayed neutrals have highlighted results! Well done!");
                System.out.println("------------------------------------------------------------");
            }


    }

    /**
     * returns all displayed neutrals' names
     * asserts displayed names contains @param searchKeyword
     * @param searchKeyword
     */
    public void isOnlyRelevantResultDisplayed(String searchKeyword){

        List<String> allNames = new ArrayList<>();

        //1-returning all names
        for (int i = 0; i < allDropdownResults.size(); i++) {
            WebElement nameElement = Driver.getDriver().findElement(By.xpath("(//li[@class='neutral']//span)["+(i+1)+"]"));
            allNames.add(nameElement.getText());
        }

        //System.out.println(allNames);



        //2-returning names containing searchKeyword from all names
        List<String> namesContainedSearchKeyword = new ArrayList<>();

        for (String each : allNames) {
            if (each.toLowerCase().contains(searchKeyword)){
                namesContainedSearchKeyword.add(each);
            }
        }

        System.out.println("namesContainedSearchKeyword = " + namesContainedSearchKeyword);



        //3-returning all displayed names

        //to store index numbers of displayed neutrals
        displayedNeutralsIndexes = new ArrayList<>();

        //finding the displayed neutrals on dropdown
        //displayed neutrals don't have style attribute as "display: none;"
        for (int i = 0; i < allDropdownResults.size(); i++) {
            if (allDropdownResults.get(i).getAttribute("style").equals("display: none;")){
            }else {
                displayedNeutralsIndexes.add(i);
            }
        }

        List<String> displayedNeutralsNames = new ArrayList<>();

        //printing the names of displayed neutrals
        //asserting if their names contain searched keyword
        for (Integer displayedNeutralIndex : displayedNeutralsIndexes) {
            WebElement nameElement = Driver.getDriver().findElement(By.xpath("(//li[@class='neutral']//span)["+(displayedNeutralIndex+1)+"]"));
            String name = nameElement.getText();
            displayedNeutralsNames.add(name);
        }

        System.out.println("displayedNeutralsNames =      " + displayedNeutralsNames);


        //4-comparing 2 Lists
        if (namesContainedSearchKeyword.equals(displayedNeutralsNames)){
            System.out.println("Step passed : namesContainedSearchKeywordList is equal to displayedNeutralsNamesList");
        }else {
            System.out.println("Step failed : namesContainedSearchKeywordList is NOT equal to displayedNeutralsNamesList");
            throw new RuntimeException();
        }


    }

    /**
     * gets parameter from this class --> private List<Integer> displayedNeutralsIndexes;
     * returns img>src value
     * asserts if they startsWith & endsWith certain text
     */
    public void hasImage(){

        int i = 0;

        List<String> imageLinks = new ArrayList<>();

        for (Integer displayedNeutralsIndex : displayedNeutralsIndexes) {
            WebElement imageElement = Driver.getDriver().findElement(By.xpath("(//li[@class='neutral']//img)["+(displayedNeutralsIndex+1)+"]"));
            String attributeSRC = imageElement.getAttribute("src");
            Assert.assertTrue(attributeSRC.startsWith("https://www.jamsadr.com/images/neutrals/"));
            Assert.assertTrue(attributeSRC.endsWith(".jpg"));
            imageLinks.add(attributeSRC);
            i++;
        }

        if (i == displayedNeutralsIndexes.size()){
            System.out.println("Step Passed : "+i+" neutral(s) has an image on dropdown. See below links:");
            System.out.println(imageLinks);
        }else {
            System.out.println("Step failed : "+i+" neutral(s) has an image on dropdown. "+(displayedNeutralsIndexes.size()-i)+" neutral(s) are missing an image. See below links");
            System.out.println(imageLinks);
            throw new RuntimeException();
        }

    }

    /**
     * gets parameter from this class --> private List<Integer> displayedNeutralsIndexes;
     * returns a>href value
     * asserts if they startsWith certain text
     */
    public void hasLink(){

        int i = 0;

        List<String> links = new ArrayList<>();

        for (Integer displayedNeutralsIndex : displayedNeutralsIndexes) {
            WebElement hrefElement = Driver.getDriver().findElement(By.xpath("(//li[@class='neutral']//a)["+(displayedNeutralsIndex+1)+"]"));
            String attributeHREF = hrefElement.getAttribute("href");
            Assert.assertTrue(attributeHREF.startsWith("https://www.jamsadr.com/"));
            links.add(attributeHREF);
            i++;
        }

        if (i == displayedNeutralsIndexes.size()){
            System.out.println("Step Passed : "+i+" neutral(s) has a link to navigate to. See below links:");
            System.out.println(links);
        }else {
            System.out.println("Step failed : "+i+" neutral(s) has a link to navigate to. "+(displayedNeutralsIndexes.size()-i)+" neutral(s) are missing a link. See below links");
            System.out.println(links);
            throw new RuntimeException();
        }
    }

    /**
     * gets parameter from this class --> private List<Integer> displayedNeutralsIndexes;
     * returns name of first neutral from elements
     * returns name of first neutral from neutral detail page
     * asserts if correct page is opened or not
     */
    public void clickOnFirstNeutralOnDropdown(){

        //System.out.println(displayedNeutralsIndexes);

        //returning name of clicked neutral from element
        WebElement nameFromClickedNeutralElement = Driver.getDriver().findElement(By.xpath("(//li[@class='neutral']//span)["+(displayedNeutralsIndexes.get(0)+1)+"]"));
        String nameFromClickedNeutral = nameFromClickedNeutralElement.getText();
        System.out.println("nameFromClickedNeutral = " + nameFromClickedNeutral);

        //clicking the first displayed neutral on dropdown
        //sometimes it does not click interestingly, so I used try catch block
        try {
            WebElement elementFirstNeutral = Driver.getDriver().findElement(By.xpath("(//li[@class='neutral'])["+(displayedNeutralsIndexes.get(0)+1)+"]"));
            click(elementFirstNeutral);
        } catch (RuntimeException e){
            WebElement elementFirstNeutral = Driver.getDriver().findElement(By.xpath("(//li[@class='neutral'])["+(displayedNeutralsIndexes.get(0)+1)+"]"));
            elementFirstNeutral.click();
        }


        //since navigating a new url, it is crucial to use explicit wait
        waitVisibilityOf(nameHeroTitleElement);

        //returning nameHeroTitle from neutral detail page
        String nameHeroTitle = nameHeroTitleElement.getText();
        System.out.println("nameHeroTitle = " + nameHeroTitle);

        //asserting --> "nameHeroTitle" is containing "nameFromClickedNeutral" or not
        //if yes, correct page is opened
        if (nameHeroTitle.contains(nameFromClickedNeutral)){
            System.out.println("Step Passed : Browser is navigated to right page");
        }else {
            System.out.println("Step Failed : Browser is navigated to wrong page");
        }

    }






}