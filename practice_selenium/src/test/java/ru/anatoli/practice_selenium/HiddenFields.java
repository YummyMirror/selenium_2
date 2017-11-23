package ru.anatoli.practice_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.util.List;

public class HiddenFields {
    WebDriver wd;
    WebDriverWait wait;
    Actions actions;

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
        actions = new Actions(wd);
    }

    @Test(enabled = true)
    private void test() {
        wd.navigate().to("http://blueimp.github.io/jQuery-File-Upload/basic.html");
        WebElement uploadField = wd.findElement(By.xpath("//input[@id = 'fileupload']"));
        ((JavascriptExecutor) wd).executeScript("arguments[0].style.opacity = 1;" +
                                                        "arguments[0].style = ", uploadField);
        System.out.println(new File(".").getAbsoluteFile());
        uploadField.sendKeys(new File("src/test/resources/1.jpg").getAbsolutePath());
    }

    @Test(enabled = true)
    public void test2() {
        wd.navigate().to("http://jqueryui.com/sortable/");
        WebElement iFrame = wd.findElement(By.xpath("//iframe[@class = 'demo-frame']"));
        wd.switchTo().frame(iFrame);
        List<WebElement> elements = wd.findElements(By.xpath("//ul[@id = 'sortable']/li"));
        WebElement firstElement = elements.get(0);
        WebElement forthElement = elements.get(5);
        actions.moveToElement(firstElement)
                .clickAndHold()
                .moveToElement(forthElement)
                //.release()
                .perform();
        actions.moveToElement(firstElement)
                .clickAndHold()
                .moveByOffset(0, 50)
                .release()
                .perform();
    }

    @Test(enabled = true)
    public void test3() {
        wd.navigate().to("http://jsbin.com/?html,output");
        WebElement parentFrame = wd.findElement(By.xpath("//iframe[@class = 'stretch']"));
        wd.switchTo().frame(parentFrame);
        WebElement childFrame = wd.findElement(By.xpath("//iframe[@name = 'JS Bin Output ']"));
        wd.switchTo().frame(childFrame);
    }

    @AfterMethod
    public void after() {
        wd.quit();
        wd = null;
    }
}
