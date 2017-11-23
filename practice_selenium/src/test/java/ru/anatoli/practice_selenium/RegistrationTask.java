package ru.anatoli.practice_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class RegistrationTask {
    WebDriver wd;
    WebDriverWait wait;
    private String email;
    private static final String PASSWORD = "password";

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
        wd.navigate().to("http://localhost/litecart/public_html/en/");
        email = uniqueEmail("mail.ru");
    }

    @Test
    public void registration() {
        openRegistrationForm();
        fillRegistrationForm();
        logOut();
        reLogin();
        logOut();
    }

    public void reLogin() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id = 'box-account-login']/h3[contains(text(), 'Login')]")));
        input(By.xpath("//input[@name = 'email']"), email);
        input(By.xpath("//input[@name = 'password']"), PASSWORD);
        click(By.xpath("//button[@name = 'login']"));
    }

    public void logOut() {
        click(By.xpath("//div[@class = 'content']//a[contains(@href, 'logout')]"));
    }

    public void openRegistrationForm() {
        click(By.xpath("//table//a[contains(@href, 'create_account')]"));
    }

    public void fillRegistrationForm() {
        input(By.xpath("//input[@name = 'tax_id']"), "12345");
        input(By.xpath("//input[@name = 'company']"), "Company");
        input(By.xpath("//input[@name = 'firstname']"), "Name");
        input(By.xpath("//input[@name = 'lastname']"), "Last Name");
        input(By.xpath("//input[@name = 'address1']"), "Address1");
        input(By.xpath("//input[@name = 'address2']"), "Address2");
        input(By.xpath("//input[@name = 'postcode']"), "12345");
        input(By.xpath("//input[@name = 'city']"), "Chicago");
        select(By.xpath("//select[@name = 'country_code']"), "United States");
        select(By.xpath("//select[@name = 'zone_code']"), "California");
        input(By.xpath("//input[@name = 'email']"), email);
        input(By.xpath("//input[@name = 'phone']"), "+1-888-452-1505");
        input(By.xpath("//input[@name = 'password']"), PASSWORD);
        input(By.xpath("//input[@name = 'confirmed_password']"), PASSWORD);
        click(By.xpath("//button[@name = 'create_account']"));
    }

    public String uniqueEmail(String domain) {
        List<String> lettersList = (List<String>) Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "s", "t", "u", "v", "w", "x", "z");
        int random = new SecureRandom().nextInt(lettersList.size() - 1);
        String randomLetter = lettersList.get(random);
        int randomInteger = new SecureRandom().nextInt(100);
        return randomLetter + randomInteger + "@" + domain;
    }

    public void select(By locator, String value) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        new Select(wd.findElement(locator)).selectByVisibleText(value);
    }

    public void input(By locator, String value) {
        wd.findElement(locator).click();
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(value);
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    @AfterMethod
    public void after() {
        wd.quit();
        wd = null;
    }
}
