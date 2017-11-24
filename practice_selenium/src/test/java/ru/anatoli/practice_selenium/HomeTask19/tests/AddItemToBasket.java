package ru.anatoli.practice_selenium.HomeTask19.tests;

import org.testng.annotations.Test;

public class AddItemToBasket extends TestBase {
    @Test(enabled = true, priority = 1)
    public void testAddItemToBasket() {
        app.getMainPage().openUrl("http://localhost/litecart/public_html/en/");
        app.getProductPage().addThreeProductsToBasket();
        app.getProductPage().openBasket();
        app.getBasketPage().removeAllProductsFromBasket();
    }
}
