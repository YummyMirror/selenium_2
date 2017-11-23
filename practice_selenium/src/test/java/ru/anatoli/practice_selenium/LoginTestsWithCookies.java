package ru.anatoli.practice_selenium;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class LoginTestsWithCookies {
    WebDriver wd;
    WebDriverWait wait;

    @BeforeMethod
    public void init() {
        System.setProperty("webdriver.gecko.driver", "F:\\Private\\Programs\\geckodriver\\geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
        wd = new FirefoxDriver(options);
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wd.manage().window().maximize();
        wait = new WebDriverWait(wd, 10);
    }

    @Test
    public void loginTestsWithCookies() throws IOException {
        List<Cookie> cookieList = new ArrayList<Cookie>(0);
        File cookiesFile = new File("d:/cookies.csv");
        FileReader reader = new FileReader(cookiesFile);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        while (line != null) {
            String[] split = line.split(";");
            cookieList.add(new Cookie(split[0], split[1]));
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        reader.close();

        wd.manage().addCookie(cookieList.get(0));
        wd.manage().addCookie(cookieList.get(1));
        wd.manage().addCookie(cookieList.get(2));

        wd.navigate().to("http://localhost/litecart/public_html/admin/");
    }

    @AfterMethod
    public void stop() {
        wd.quit();
        wd = null;
    }
}
