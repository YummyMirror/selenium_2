package ru.anatoli.practice_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

public class homeTask13 {
    WebDriver wd;
    WebDriverWait wait;

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
        wd.navigate().to("http://localhost/litecart/public_html/en/");
    }

    @Test
    public void test() {
        for (int i = 0; i < 3; i++) {
            List<WebElement> ducks = getAllDucks();
            openFirstDuck(ducks);
            int basketCountBefore = getBasketCount();
            addDuckToBasket(basketCountBefore);
            goToMainPage();
        }
        openBasket();
        List<WebElement> ducksInBasket = getAllDucksInBasket();
        for (int j = 0; j < ducksInBasket.size(); j++) {
            List<WebElement> elementsInTable = getDucksFromTable();
            removeItemFromBasket(elementsInTable);
        }
    }

    public List<WebElement> getDucksFromTable() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[contains(@class, 'dataTable')]//tr/td[1][contains(@style, 'center')]")));
    }

    public List<WebElement> getAllDucksInBasket() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@class = 'shortcuts']/li")));
    }

    public void removeItemFromBasket(List<WebElement> elementsInTable) {
        click(By.xpath("//button[@name = 'remove_cart_item']"));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//table[contains(@class, 'dataTable')]//tr/td[1][contains(@style, 'center')]"), elementsInTable.size() - 1));
    }

    public void openBasket() {
        click(By.xpath("//div[@id = 'cart-wrapper']//a[@class = 'link']"));
    }

    public void goToMainPage() {
        click(By.xpath("//div[@id = 'logotype-wrapper']/a"));
    }

    public void addDuckToBasket(int basketCountBefore) {
        if (areElementsPresent(By.xpath("//select[@name = 'options[Size]']"))) {
            new Select(wd.findElement(By.xpath("//select[@name = 'options[Size]']"))).selectByVisibleText("Large +$5");
        }
        click(By.xpath("//button[@name = 'add_cart_product']"));
        wait.until(ExpectedConditions.textToBe(By.xpath("//div[@id = 'cart']//span[@class = 'quantity']"), String.valueOf(basketCountBefore + 1)));
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public int getBasketCount() {
        return Integer.parseInt(wd.findElement(By.xpath("//div[@id = 'cart']//span[@class = 'quantity']")).getAttribute("textContent"));
    }

    public void openFirstDuck(List<WebElement> ducks) {
        ducks.get(0).click();
    }

    public List<WebElement> getAllDucks() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//li[starts-with(@class, 'product')]")));
    }

    public boolean areElementsPresent(By locator) {
        return wd.findElements(locator).size() > 0;
    }

    @AfterMethod
    public void after() {
        wd.quit();
        wd = null;
    }
}
