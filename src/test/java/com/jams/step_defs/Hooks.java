package com.jams.step_defs;

import com.jams.utilities.Driver;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @BeforeStep(value = "ui")
    public void horizontalLineStart(){
        System.err.println("------------------- START STEP -------------------");
    }

    /*@AfterStep
    public void horizontalLineEnd(){
        System.out.println("-------------------- END STEP -------------------");
    }*/

    @After(value = "ui")
    public void tearDown(Scenario scenario){

        //if scenario fails, it takes screenshot
        if (scenario.isFailed()){
            byte[] screenshot = ( (TakesScreenshot) Driver.getDriver() ).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }

        //for closing the browser
        //Driver.closeDriver();
    }

}
