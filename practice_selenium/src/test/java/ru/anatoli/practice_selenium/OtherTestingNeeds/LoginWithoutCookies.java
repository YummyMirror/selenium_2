package ru.anatoli.practice_selenium.OtherTestingNeeds;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class LoginWithoutCookies {
    private WebDriver wd;
    private WebDriverWait wait;

    @BeforeMethod
    public void before() throws IOException {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();

        Set<Cookie> cookies = getJSONCookie();
        wd.navigate().to("http://localhost/litecart/public_html/admin/loginAs.php");
        cookies.stream().forEach(c -> wd.manage().addCookie(c));
        wd.navigate().to("http://localhost/litecart/public_html/admin/?app=countries&doc=countries");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), ' Countries')]")));
    }

    public Set<Cookie> getCSVCookies() throws IOException {
        Set<Cookie> cookies = new HashSet<Cookie>(0);
        File fileWithCookies = new File("d:/cookies.csv");
        FileReader reader = new FileReader(fileWithCookies);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        while (line != null) {
            String[] split = line.split(";");
            cookies.add(new Cookie(split[0], split[1]));
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        reader.close();
        return cookies;
    }

    public Set<Cookie> getJSONCookie() throws IOException {
        File fileWithCookies = new File("d:/cookies.json");
        FileReader reader = new FileReader(fileWithCookies);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        String json = "";
        while (line != null) {
            json += line;
            line = bufferedReader.readLine();
        }
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Set<Cookie>>(){}.getType();
        return gson.fromJson(json, collectionType);
    }

    @Test(enabled = true)
    public void test() {
        wd.navigate().to("http://localhost/litecart/public_html/admin/");
    }

    @AfterMethod
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}
