package ru.anatoli.practice_selenium.HomeTask19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBe;

public class BasketPage extends PageBase {
    //Constructor
    public BasketPage(WebDriver wd, WebDriverWait wait) {
        super(wd, wait);
    }

    public void removeAllProductsFromBasket() {
        List<WebElement> productsInTable = getProductsFromOrderTable();
        for (int i = 0; i < productsInTable.size(); i++) {
            wait.until(elementToBeClickable(By.xpath("//button[@name = 'remove_cart_item']"))).click();
            wait.until(numberOfElementsToBe(By.xpath("//table[@class = 'dataTable rounded-corners']//td[@class = 'item']"), productsInTable.size() - 1));
        }
    }

    public List<WebElement> getProductsFromOrderTable() {
        return wd.findElements(By.xpath("//table[@class = 'dataTable rounded-corners']//td[@class = 'item']"));
    }
}
