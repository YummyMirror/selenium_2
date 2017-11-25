package ru.anatoli.practice_selenium.HomeTask19.tests;

import org.testng.annotations.Test;

public class AddItemToBasket extends TestBase {
    @Test(enabled = true, priority = 1)
    public void testAddItemToBasket() {
        app.getProductPage().addProductsToBasket(3);
        app.getProductPage().openBasket();
        app.getBasketPage().removeAllProductsFromBasket();
    }
}
