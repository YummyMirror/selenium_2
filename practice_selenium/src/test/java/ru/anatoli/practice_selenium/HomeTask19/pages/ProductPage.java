package ru.anatoli.practice_selenium.HomeTask19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ProductPage extends PageBase {
    MainPage mainPage = new MainPage(wd, wait);

    //Constructor
    public ProductPage(WebDriver wd, WebDriverWait wait) {
        super(wd, wait);
    }

    public void addThreeProductsToBasket() {
        for (int i = 0; i < 3; i++) {
            mainPage.getAllProducts().get(0).click();
            wait.until(visibilityOfElementLocated(By.xpath("//h1[@class = 'title']")));

            if (areElementsPresent(By.xpath("//select[contains(@name, 'options')]"))) {
                select(By.xpath("//select[contains(@name, 'options')]"), "Large +$5");
            }
            addProductToBasket(getBasketCount());
            back();
        }
    }

    public void addProductToBasket(int basketCount) {
        click(By.xpath("//button[@name = 'add_cart_product']"));
        wait.until(textToBe(By.xpath("//div[@id = 'cart']//span[@class = 'quantity']"), String.valueOf(basketCount + 1)));
    }
}
