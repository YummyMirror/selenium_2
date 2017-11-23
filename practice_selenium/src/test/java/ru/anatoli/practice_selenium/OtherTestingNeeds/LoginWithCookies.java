package ru.anatoli.practice_selenium.OtherTestingNeeds;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class LoginWithCookies {
    private WebDriver wd;
    private WebDriverWait wait;
    private Actions actions;
    private String format = "json";

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        actions = new Actions(wd);

        wd.manage().window().maximize();
        wd.navigate().to("http://localhost/litecart/public_html/admin/login.php");
    }

    @Test(enabled = true)
    public void test() throws IOException {
        login("admin", "admin");
        Set<Cookie> cookies = wd.manage().getCookies();
        if (format.equals("csv")) {
            File fileWithCookiesSCV = new File("D:/cookies." + format);
            saveCSV(cookies, fileWithCookiesSCV);
        } else if (format.equals("json")){
            File fileWithCookiesJSON = new File("D:/cookies." + format);
            saveJSON(cookies, fileWithCookiesJSON);
        }
    }

    public void saveJSON(Set<Cookie> cookies, File file) throws IOException {
        if (file.exists())
            file.delete();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(cookies);
        FileWriter writer = new FileWriter(file);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    public void saveCSV(Set<Cookie> cookies, File file) throws IOException {
        if (file.exists())
            file.delete();
        FileWriter writer = new FileWriter(file);
        cookies.stream()
                .forEach((c) -> {
                    try {
                        writer.write(String.format("%s;%s\n", c.getName(), c.getValue()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        writer.flush();
        writer.close();
    }

    public void login(String login, String password) {
        input(By.xpath("//input[@name = 'username']"), login);
        input(By.xpath("//input[@name = 'password']"), password);
        checkBox(By.xpath("//input[@name = 'remember_me']"));
        submitForm(By.xpath("//button[@name = 'login']"));
        wait.until(visibilityOfElementLocated(By.xpath("//a[contains(@href, 'logout.php')]")));
    }

    public void submitForm(By locator) {
        wd.findElement(locator).click();
    }

    public void checkBox(By locator) {
        wd.findElement(locator).click();
    }

    public void input(By locator, String value) {
        wd.findElement(locator).click();
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(value);
    }

    @AfterMethod
    public void after() {
        wd.quit();
        wd = null;
    }
}
