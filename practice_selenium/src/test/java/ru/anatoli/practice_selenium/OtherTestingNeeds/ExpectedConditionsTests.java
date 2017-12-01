package ru.anatoli.practice_selenium.OtherTestingNeeds;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
import java.util.List;
import java.util.Set;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ExpectedConditionsTests {
    private WebDriver wd;
    private WebDriverWait wait;

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
    }

    @Test
    public void alertIsPresent() throws IOException {
        Set<Cookie> jsonCookie = getJSONCookie();
        wd.navigate().to("http://localhost/litecart/public_html/admin/login.php");
        jsonCookie.stream().forEach(cookie -> wd.manage().addCookie(cookie));
        wd.navigate().to("http://localhost/litecart/public_html/admin/?app=countries&doc=edit_country&country_code=AL");
        click(By.xpath("//button[@name = 'delete']"));
        //IsAlertPresent
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText = wd.switchTo().alert().getText();
        wd.switchTo().alert().dismiss();
        //And
        click(By.xpath("//a[contains(@href, 'app=countries&doc=countries')]"));
        wait.until(and(visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Countries')]")), elementToBeClickable(By.xpath("//a[contains(@href, 'app=countries&doc=edit_country')]"))));
        List<WebElement> countries = wd.findElements(By.xpath("//table[@class = 'dataTable']//tr[@class = 'row']"));
        wait.until(visibilityOfAllElementsLocatedBy(By.xpath("//table[@class = 'dataTable']//tr[@class = 'row']")));
        wait.until(visibilityOfAllElements(countries));
        //AttributeContains
        click(By.xpath("//a[contains(@href, '?app=countries&doc=edit_country&country_code=AD')]"));
        WebElement inputField = wd.findElement(By.xpath("//input[@name = 'name']"));
        //wait.until(attributeContains(By.xpath("//input[@name = 'name']"), "value", "Andorra"));
        wait.until(attributeContains(inputField, "value", "Andorra"));
        //NumberOfElementsToBe
        wait.until(numberOfElementsToBe(By.xpath("//ul[@id = 'box-apps-menu']/li[@id = 'app-']"), 17));
        //NumberOfElementsToBeLessThan
        wait.until(numberOfElementsToBeLessThan(By.xpath("//ul[@id = 'box-apps-menu']/li[@id = 'app-']"), 20));
        //NumberOfElementsToBeMoreThan
        wait.until(numberOfElementsToBeMoreThan(By.xpath("//ul[@id = 'box-apps-menu']/li[@id = 'app-']"), 15));
        //VisibilityOfAllElementsLocatedBy
        List<WebElement> menuItems = wd.findElements(By.xpath("//ul[@id = 'box-apps-menu']/li[@id = 'app-']"));
        wait.until(visibilityOfAllElementsLocatedBy(By.xpath("//ul[@id = 'box-apps-menu']/li[@id = 'app-']")));
        wait.until(visibilityOfAllElements(menuItems));
        //NumberOfWindowsToBe
        String currentWindowHandle = wd.getWindowHandle();
        Set<String> oldHandles = wd.getWindowHandles();
        ((JavascriptExecutor) wd).executeScript("window.open();");
        int numberOfWindows = wd.getWindowHandles().size();
        wait.until(numberOfWindowsToBe(numberOfWindows));
        String newWindowHandle = wait.until(waitNewWindow(oldHandles));
        wd.switchTo().window(newWindowHandle);
        wd.navigate().to("https://yandex.by");
        wait.until(titleIs("Яндекс"));
        wait.until(titleContains("Ян"));
        wait.until(urlContains("yandex"));
        wait.until(urlToBe("https://yandex.by/"));
        wd.close();
        wd.switchTo().window(currentWindowHandle);
        //PresenceOfAllElementsLocatedBy
        wait.until(presenceOfAllElementsLocatedBy(By.xpath("//input[@name = 'iso_code_2']")));
        wait.until(presenceOfAllElementsLocatedBy(By.xpath("//input[@value]")));
        //PresenceOfNestedElement
        wait.until(presenceOfNestedElementLocatedBy(By.xpath("//td[@id = 'content']"), By.xpath(".//table[@id = 'table-zones']")));
        //StatelessOf
        WebElement logoutButton = wd.findElement(By.xpath("//a[contains(@href, 'logout.php')]"));
        click(By.xpath("//a[contains(@href, 'logout.php')]"));
        wait.until(stalenessOf(logoutButton));
        wait.until(textToBe(By.xpath("//button[@name = 'login']"), "Login"));

        WebElement loginButton = wd.findElement(By.xpath("//button[@name = 'login']"));
        ((JavascriptExecutor) wd).executeScript("arguments[0].style.border = '5px solid red';", loginButton);
        ((JavascriptExecutor) wd).executeScript("arguments[0].style['font-weight'] = 'normal'", loginButton);
    }

    public ExpectedCondition<String> waitNewWindow(Set<String> oldWindowHandles) {
        return wd -> {
            Set<String> handles = wd.getWindowHandles();
            handles.removeAll(oldWindowHandles);
            if (handles.size() > 0)
                return handles.stream().findAny().get();
            else
                return null;
        };
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

    public void click(By locator) {
        wait.until(elementToBeClickable(locator)).click();
    }

    @AfterMethod
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}
