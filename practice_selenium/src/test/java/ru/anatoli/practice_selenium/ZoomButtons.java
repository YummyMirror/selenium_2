package ru.anatoli.practice_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import java.util.stream.Collectors;

public class ZoomButtons {
    WebDriver wd;
    WebDriverWait wait;

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wd.manage().window().maximize();
        wait = new WebDriverWait(wd, 10);
        wd.navigate().to("http://localhost/litecart/public_html/en/");
    }

    @Test
    public void test() {
        List<WebElement> ducks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//li[starts-with(@class, 'product')]")));
        List<WebElement> ducksWithPriceNotZero = ducks.stream()
                                                      .filter((duck) -> getPrice(duck, By.xpath(".//div[@class = 'price-wrapper']/*")) > 0)
                                                      .collect(Collectors.toList());
        List<WebElement> ducksWithPrice20 = ducks.stream()
                                                 .filter((duck) -> getPrice(duck, By.xpath(".//div[@class = 'price-wrapper']/*")) == 20)
                                                 .collect(Collectors.toList());
        List<WebElement> ducksWithPrice0 = ducks.stream()
                                                .filter((duck) -> getPrice(duck, By.xpath(".//div[@class = 'price-wrapper']/*")) == 0)
                                                .collect(Collectors.toList());
    }

    public int getPrice(WebElement element, By locator) {
        List<WebElement> priceElements = element.findElements(locator);
        if (priceElements.size() > 1) {
            return Integer.parseInt(priceElements.get(1).getText().substring(1));
        }
        return Integer.parseInt(priceElements.get(0).getText().substring(1));
    }

    @AfterMethod
    public void after() {
        wd.quit();
        wd = null;
    }
}
