package ru.anatoli.practice_selenium.RemoteExecution;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class CloudGrid {
    WebDriver wd;

    @BeforeMethod
    public void before() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("browserName", "chrome");
        options.setCapability("version", "62");
        options.setCapability("platform", "WIN8");
        options.setCapability("browserstack.debug", "true");
        options.setCapability("build", "First build");

        wd = new RemoteWebDriver(new URL("https://ivan1657:DXzpGyDpod7cAQpjjFHo@hub-cloud.browserstack.com/wd/hub"), options);
//        wd.manage().window().maximize();
        wd.navigate().to("https://google.com");
    }

    @Test
    public void test() {

    }

    @AfterMethod
    public void after() {
        wd.quit();
        wd = null;
    }
}
