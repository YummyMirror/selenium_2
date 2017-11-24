package ru.anatoli.practice_selenium.HomeTask19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class MainPage extends PageBase {
    //Constructor
    public MainPage(WebDriver wd, WebDriverWait wait) {
        super(wd, wait);
    }

    public void openUrl(String url) {
        wd.navigate().to(url);
        wait.until(visibilityOfElementLocated(By.xpath("//div[@id = 'region']")));
    }

    public List<WebElement> getAllProducts() {
        return wd.findElements(By.xpath("//li/a[@class = 'link']"));
    }
}
