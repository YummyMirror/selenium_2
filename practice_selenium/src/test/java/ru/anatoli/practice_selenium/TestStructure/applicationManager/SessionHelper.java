package ru.anatoli.practice_selenium.TestStructure.applicationManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class SessionHelper extends HelperBase {
    //Constructor
    public SessionHelper(WebDriver wd, WebDriverWait wait) {
        super(wd, wait);
    }

    public void login(String login, String password) {
        input(By.xpath("//input[@name = 'username']"), login);
        input(By.xpath("//input[@name = 'password']"), password);
        check(By.xpath("//input[@name = 'remember_me']"));
        click(By.xpath("//button[@name = 'login']"));
        wait.until(visibilityOfElementLocated(By.xpath("//a[contains(@href, 'logout.php')]")));
    }

    public String getLoginSuccessMessage() {
        return wait.until(visibilityOfElementLocated(By.xpath("//div[@class = 'notice success']"))).getText();
    }
}
