package ru.anatoli.practice_selenium.HomeTask19.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.anatoli.practice_selenium.HomeTask19.pages.BasketPage;
import ru.anatoli.practice_selenium.HomeTask19.pages.MainPage;
import ru.anatoli.practice_selenium.HomeTask19.pages.ProductPage;

public class Application {
    private WebDriver wd;
    private WebDriverWait wait;
    private BasketPage basketPage;
    private MainPage mainPage;
    private ProductPage productPage;

    public void setUp() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();

        //Delegates
        mainPage = new MainPage(wd, wait);
        productPage = new ProductPage(wd, wait);
        basketPage = new BasketPage(wd, wait);
    }

    public void tearDown() {
        wd.quit();
        if (wd != null)
            wd = null;
    }

    //Getters of Delegates
    public MainPage getMainPage() {
        return mainPage;
    }

    public ProductPage getProductPage() {
        return productPage;
    }

    public BasketPage getBasketPage() {
        return basketPage;
    }
}
