package ru.anatoli.practice_selenium.HomeTask19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class PageBase {
    protected WebDriver wd;
    protected WebDriverWait wait;

    //Constructor
    public PageBase(WebDriver wd, WebDriverWait wait) {
        this.wd = wd;
        this.wait = wait;
    }

    public void click(By locator) {
        wait.until(elementToBeClickable(locator)).click();
    }

    public void select(By locator, String value) {
        if (value != null)
            new Select(wd.findElement(locator)).selectByVisibleText(value);
    }

    public boolean areElementsPresent(By locator) {
        return wd.findElements(locator).size() > 0;
    }

    public int getBasketCount() {
        return Integer.parseInt(wd.findElement(By.xpath("//div[@id = 'cart']//span[@class = 'quantity']")).getText());
    }

    public void openBasket() {
        click(By.xpath("//a[contains(@href, 'checkout')]"));
        wait.until(visibilityOfElementLocated(By.xpath("//button[@name = 'confirm_order']")));
    }

    public void back() {
        wd.navigate().back();
    }
}
