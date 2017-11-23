package ru.anatoli.practice_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

public class testActionsAPI {
    WebDriver wd;
    WebDriverWait wait;
    Actions actions;

    @BeforeMethod
    public void before() {
        ChromeOptions options = new ChromeOptions();
        wd = new ChromeDriver();
        wd.manage().window().maximize();
        actions = new Actions(wd);
    }

    @Test(enabled = false)
    public void practice() {
        WebElement element = wd.findElement(By.xpath("//"));
        WebElement element2 = wd.findElement(By.xpath("//"));
        actions.moveToElement(element)
                .keyDown(Keys.CONTROL) //put mouse over element
                .clickAndHold()
                .moveToElement(element2)
                .release()
                .keyUp(Keys.CONTROL)
                .perform();

        actions.keyDown(Keys.CONTROL)
                .dragAndDrop(element, element2)
                .keyUp(Keys.CONTROL)
                .perform();
    }

    @Test
    public void test() {
        wd.navigate().to("http://jqueryui.com/sortable/");
        WebDriver frame = wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        List<WebElement> elements = frame.findElements(By.xpath("//li[@class = 'ui-state-default ui-sortable-handle']"));
        WebElement firstElement = elements.get(0);
        actions = new Actions(frame);
        actions.moveToElement(firstElement)
                .clickAndHold()
                .moveToElement(elements.get(4))
                .release()
                .perform();
    }

    @Test(enabled = true)
    public  void slider() {
        wd.navigate().to("https://www.jqueryscript.net/demo/Minimalist-jQuery-Slider-Component-Amino-Slider/");
        List<WebElement> sliders = wd.findElements(By.xpath("//amino-slider[@class = 'slider']"));
        WebElement firstSlider = sliders.get(0);
        WebElement firstScrubber = firstSlider.findElement(By.xpath("./div[@class = 'am-thumb']"));
        int sliderWidth = firstSlider.getSize().width;
        int sliderX = firstSlider.getLocation().getX();
        int sliderY = sliderX + sliderWidth;
        int scrubberX = firstScrubber.getLocation().getX();
        int scrubberY = firstScrubber.getLocation().getY();
        actions.moveToElement(firstScrubber)
                .moveByOffset(0, 5)
                .clickAndHold()
                .moveToElement(firstSlider, sliderY, 0)
                .perform();
    }

    @Test(enabled = true)
    public void test2() {
        wd.navigate().to("http://jqueryui.com/sortable/");
        WebDriver frame = wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        List<WebElement> items = frame.findElements(By.xpath("//ul[@id = 'sortable']/li"));
        WebElement firstItem = items.get(0);
        actions.moveToElement(firstItem)
                .clickAndHold()
                .moveToElement(items.get(4))
                .release()
                .build()
                .perform();
        wait.until(ExpectedConditions.stalenessOf(items.get(0)));
        wait.until(ExpectedConditions.and(ExpectedConditions.visibilityOf(items.get(0)), ExpectedConditions.visibilityOfElementLocated(By.xpath(""))));
        wait.until(ExpectedConditions.attributeToBe(items.get(0), "value", "1234567"));
    }

    @AfterMethod
    public void after() {
        wd.quit();
        wd = null;
    }
}
