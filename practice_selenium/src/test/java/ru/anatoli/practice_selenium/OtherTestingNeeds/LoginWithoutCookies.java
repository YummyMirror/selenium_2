package ru.anatoli.practice_selenium.OtherTestingNeeds;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class LoginWithoutCookies {
    private WebDriver wd;
    private WebDriverWait wait;
    private Actions actions;

    @BeforeMethod
    public void before() throws IOException {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        actions = new Actions(wd);
        wd.manage().window().maximize();

        Set<Cookie> cookies1 = wd.manage().getCookies();

        Set<Cookie> cookies = new HashSet<Cookie>(0);
        File fileWithCokkies = new File("d:/cookies.csv");
        FileReader reader = new FileReader(fileWithCokkies);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        while (line != null) {
            String[] split = line.split(";");
            cookies.add(new Cookie(split[0], split[1]));
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        reader.close();

        cookies.stream().forEach((c) -> System.out.println("Name: " + c.getName() + " Value: " + c.getValue()));
        for (Cookie c : cookies) {
            wd.manage().addCookie(c);
        }
        Set<Cookie> cookies2 = wd.manage().getCookies();
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
