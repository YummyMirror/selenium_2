package ru.anatoli.practice_selenium.JQueryUIActions;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.io.File;
import java.util.List;
import java.util.Set;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class JSManipulations {
    private WebDriver wd;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeSuite
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        js = (JavascriptExecutor) wd;
        wd.manage().window().maximize();
    }

    @Test
    public void test() {
        wd.navigate().to("http://usemycar.exposit.com/");
        wait.until(urlToBe("http://usemycar.exposit.com/"));
        String readyState = js.executeScript("return document.readyState;").toString();
        String domainName = js.executeScript("return document.domain;").toString();
        String url = js.executeScript("return document.URL;").toString();
        String windowHandle = wd.getWindowHandle();
        Set<String> oldHandles = wd.getWindowHandles();
        js.executeScript("window.open('https://yandex.by');");
        String newWindow = wait.until(waitNewWindowHandle(oldHandles));
        wd.switchTo().window(newWindow);
        js.executeScript("alert('Alert');");
        wait.until(alertIsPresent());
        wd.switchTo().alert().accept();
        wd.close();
        wd.switchTo().window(windowHandle);

        WebElement loginButton = wd.findElement(By.xpath("//button[contains(text(), 'Войти')]"));
        WebElement footer = wd.findElement(By.xpath("//a[contains(text(), 'iPhone приложение')]"));
        for (int i = 0; i < 5; i++) {
            js.executeScript("arguments[0].scrollIntoView(true);", footer);
            js.executeScript("arguments[0].scrollIntoView(true);", loginButton);
        }
    }

    @Test
    public void changeStyle() {
        wd.navigate().to("http://usemycar.exposit.com/");
        wait.until(urlToBe("http://usemycar.exposit.com/"));
        WebElement loginButton = wd.findElement(By.xpath("//button[contains(text(), 'Войти')]"));
        //Version 1:
        String script = "arguments[0].style.float = 'left';" +
                        "arguments[0].style['border-radius'] = '7px';" +
                        "arguments[0].style.color = 'green';" +
                        "arguments[0].style['font-size'] = '20px';";
        //Version 2:
        /*js.executeScript("arguments[0].style.float = 'left';", loginButton);
        js.executeScript("arguments[0].style['border-radius'] = '7px';", loginButton);
        js.executeScript("arguments[0].style.color = 'green';", loginButton);
        js.executeScript("arguments[0].style['font-size'] = '20px';", loginButton); */

        js.executeScript(script, loginButton);
    }

    @Test
    public void uploading() {
        wd.navigate().to("http://geniuscarrier.com/how-to-style-a-html-file-upload-button-in-pure-css/");
        wait.until(presenceOfElementLocated(By.xpath("//input[@id = 'uploadBtn']")));
        WebElement uploadButton = wd.findElement(By.xpath("//input[@id = 'uploadBtn']"));
        String script = "arguments[0].scrollIntoView(true);" +
                        "arguments[0].style.position = 'relative';" +
                        "arguments[0].style.opacity = '1';";
        js.executeScript(script, uploadButton);
        wd.findElement(By.xpath("//input[@id = 'uploadBtn']")).sendKeys(new File("d:/cookies.json").getAbsolutePath());
    }

    @Test
    public void uploading2() {
        wd.navigate().to("http://geniuscarrier.com/how-to-style-a-html-file-upload-button-in-pure-css/");
        wait.until(presenceOfElementLocated(By.xpath("//input[@id = 'uploadBtn']")));
        List<WebElement> uploadButtons = wait.until(presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'file-upload btn')]" +
                                                                                            "/input[not(@id)]")));
        String script = "arguments[0].scrollIntoView(true);" +
                        "arguments[0].style.position = 'relative';" +
                        "arguments[0].style.opacity = 1;";
        js.executeScript(script, uploadButtons.get(3));
        uploadButtons.get(3).sendKeys(new File("d:/cookies.json").getAbsolutePath());
    }

    @Test
    public void uploading3() {
        wd.navigate().to("http://blueimp.github.io/jQuery-File-Upload/basic.html");
        wait.until(presenceOfElementLocated(By.xpath("//input[@id = 'fileupload']")));
        WebElement uploadButton = wd.findElement(By.xpath("//input[@id = 'fileupload']"));
        String script = "arguments[0].style.position = 'relative';" +
                        "arguments[0].style.opacity = '1';";
        js.executeScript(script, uploadButton);
        uploadButton.sendKeys(new File("d:/cookies.json").getAbsolutePath());
        wait.until(attributeToBe(By.xpath("//div[contains(@class, 'progress-bar-success')]"), "style", "width: 100%;"));
    }

    public ExpectedCondition<String> waitNewWindowHandle(Set<String> oldWindow) {
        return wd -> {
            Set<String> handles = wd.getWindowHandles();
            handles.removeAll(oldWindow);
            if (handles.size() > 0)
                return handles.stream().findAny().get();
            else
                return null;
        };
    }

    @AfterSuite
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}