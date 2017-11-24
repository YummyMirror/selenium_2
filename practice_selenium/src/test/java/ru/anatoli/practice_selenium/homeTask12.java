package ru.anatoli.practice_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.util.List;
import static org.testng.Assert.assertEquals;

public class homeTask12 {
    WebDriver wd;
    WebDriverWait wait;
    private String name = "TestName";
    private String photo = "src/test/resources/1.jpg";

    @BeforeMethod
    public void before() {
        System.setProperty("webdriver.gecko.driver", "F:\\Private\\Programs\\geckodriver\\geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        options.setLegacy(false);
        options.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
        wd = new FirefoxDriver(options);
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
        wd.navigate().to("http://localhost/litecart/public_html/admin/login.php");
    }

    @Test
    public void test() {
        login("admin", "admin");
        openCatalogPage();
        List<WebElement> productsBefore = getAllProducts();
        openAddNewProductPage();
        fillingGeneralTab();
        fillingInformationTab();
        fillingPricesTab();
        savingProduct();
        //Asserting
        List<WebElement> productsAfter = getAllProducts();
        String title = productsAfter.get(productsAfter.size() - 1).findElement(By.xpath("./td[3]/a")).getText();
        assertEquals(name, title);
        assertEquals(productsBefore.size() + 1, productsAfter.size());
    }

    public List<WebElement> getAllProducts() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@id = 'content']/h1")));
        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class = 'dataTable']//tr[@class = 'row']")));
        products.remove(products.get(0));
        return products;
    }

    public void fillingGeneralTab() {
        radio(By.xpath("//input[@name = 'status']"));
        input(By.xpath("//input[@name = 'name[en]']"), name);
        input(By.xpath("//input[@name = 'code']"), "12345");
        check(By.xpath("//input[@name = 'categories[]' and @value = '1']"));
        check(By.xpath("//input[@name = 'product_groups[]' and @value = '1-1']"));
        input(By.xpath("//input[@name = 'quantity']"), "1");
        attach(By.xpath("//input[@name = 'new_images[]']"), new File(photo));
        date(By.xpath("//input[@name = 'date_valid_from']"), "2017-01-01");
        date(By.xpath("//input[@name = 'date_valid_to']"), "2017-01-12");
    }

    public void date(By locator, String value) {
        wd.findElement(locator).click();
        wd.findElement(locator).sendKeys(value);
    }

    public void fillingInformationTab() {
        click(By.xpath("//a[@href = '#tab-information']"));
        select(By.xpath("//select[@name = 'manufacturer_id']"), "ACME Corp.");
        input(By.xpath("//input[@name = 'keywords']"), "keyword");
        input(By.xpath("//input[@name = 'short_description[en]']"), "short description");
        input(By.xpath("//div[@class = 'trumbowyg-editor']"), "Description");
        input(By.xpath("//input[@name = 'head_title[en]']"), "Title");
        input(By.xpath("//input[@name = 'meta_description[en]']"), "meta description");
    }

    public void savingProduct() {
        click(By.xpath("//button[@name = 'save']"));
    }

    public void fillingPricesTab() {
        click(By.xpath("//a[@href = '#tab-prices']"));
        input(By.xpath("//input[@name = 'purchase_price']"), "1200");
        select(By.xpath("//select[@name = 'purchase_price_currency_code']"), "US Dollars");
        input(By.xpath("//input[@name = 'prices[USD]']"), "2500");
        input(By.xpath("//input[@name = 'gross_prices[USD]']"), "2500");
        input(By.xpath("//input[@name = 'prices[EUR]']"), "2200");
        input(By.xpath("//input[@name = 'gross_prices[EUR]']"), "2200");
    }

    public void setDatePicker(WebDriver driver, String cssSelector, String date) {
        new WebDriverWait(driver, 30).until((d) -> driver.findElement(By.cssSelector(cssSelector)).isDisplayed());
        ((JavascriptExecutor) wd).executeScript(
            String.format("$('{0}').datepicker('setDateUsingPicker', '{1}')", cssSelector, date));
    }

    public void select(By locator, String value) {
        new Select(wait.until(ExpectedConditions.elementToBeClickable(locator))).selectByVisibleText(value);
    }

    public void attach(By locator, File value) {
        wd.findElement(locator).sendKeys(value.getAbsolutePath());
    }

    public void check(By locator) {
        wd.findElement(locator).click();
    }

    public void radio(By locator) {
        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        if (!elements.get(0).isSelected()) {
            elements.get(0).click();
        }
    }

    public void openAddNewProductPage() {
        click(By.xpath("//a[contains(@href, '&app=catalog&doc=edit_product')]"));
    }

    public void openCatalogPage() {
        click(By.xpath("//ul//a[contains(@href, '?app=catalog&doc=catalog')]"));
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void login(String login, String password) {
        input(By.xpath("//input[@name = 'username']"), login);
        input(By.xpath("//input[@name = 'password']"), password);
        checkBox(By.xpath("//input[@name = 'remember_me']"));
        submitForm(By.xpath("//button[@name = 'login']"));
    }

    public void submitForm(By locator) {
        wd.findElement(locator).click();
    }

    public void checkBox(By locator) {
        wd.findElement(locator).click();
    }

    public void input(By locator, String value) {
        wd.findElement(locator).click();
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(value);
    }

    @AfterMethod
    public void after() {
        wd.quit();
        wd = null;
    }
}
