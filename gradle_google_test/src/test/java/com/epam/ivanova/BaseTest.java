package com.epam.ivanova;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;
/**
 * Created by Daria_Ivanova2 on 11/25/2016.
 */
public class BaseTest {
    private String URL = "https://www.google.com.ua";
    private String searchValue;
    private String pathToDriver = "C:\\Users\\Daria_Ivanova2\\Desktop\\New folder\\maven_google_test\\MavenGoogleTest\\src\\resources\\geckodriver.exe";
    private String driverName = "webdriver.gecko.driver";
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        searchValue = System.getProperty("search_value");
        System.setProperty(driverName, pathToDriver);
        driver = new FirefoxDriver();
    }

    @Test
    public void goToGooglePage(){
        driver.get(URL);
        driver.findElement(By.name("q")).sendKeys(searchValue, Keys.ENTER);
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[1]/div/h3/a")));
        String header = driver.findElement(By.xpath("//div[1]/div/h3/a")).getText();
        assertThat(header).containsIgnoringCase(searchValue);
    }

    @AfterTest
    public void tearDown(){
        driver.close();
        driver.quit();
    }
}
