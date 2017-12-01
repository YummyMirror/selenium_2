package ru.anatoli.practice_selenium.OtherTestingNeeds;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class STLoginWithoutCookies {
    private WebDriver wd;
    private WebDriverWait wait;
    private String format = "json";
    private File file;

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
    }

    @Test
    public void setCookies() throws IOException {
        openUrl("http://software-testing.ru/lms/login/index.php");
        loginAs("yummymirror", "9009608ffb063b0");

        Set<Cookie> cookies = wd.manage().getCookies();
        if (format.equals("csv")) {
            file = new File("d:/cookies." + format);
            saveAsCSV(file, cookies);
        } else if(format.equals("json")) {
            file = new File("d:/cookies." + format);
            saveAsJSON(file, cookies);
        }
    }

    public void saveAsJSON(File file, Set<Cookie> cookies) throws IOException {
        if (file.exists())
            file.delete();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(cookies);
        FileWriter writer = new FileWriter(file);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    public void saveAsCSV(File file, Set<Cookie> cookies) throws IOException {
        if (file.exists())
            file.delete();
        FileWriter writer = new FileWriter(file);
        cookies.stream().forEach(c -> {
            try {
                writer.write(String.format("%s;%s\n", c.getName(), c.getValue()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.flush();
        writer.close();
    }

    public void loginAs(String login, String password) {
        input(By.xpath("//input[@id = 'username']"), login);
        input(By.xpath("//input[@id = 'password']"), password);
        check(By.xpath("//input[@id = 'rememberusername']"));
        click(By.xpath("//input[@id = 'loginbtn']"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class = 'usertext']")));
    }

    public void check(By locator) {
        wd.findElement(locator).click();
    }

    public void openUrl(String url) {
        wd.navigate().to(url);
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void input(By locator, String value) {
        if (value != null) {
            String currentValue = wd.findElement(locator).getAttribute("value");
            if (!value.equals(currentValue)) {
                wd.findElement(locator).click();
                wd.findElement(locator).clear();
                wd.findElement(locator).sendKeys(value);
            }
        }
    }

    @AfterMethod
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}
