package ru.anatoli.practice_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

public class LeftPanelMenuItems {
    WebDriver wd;
    WebDriverWait wait;

    @BeforeMethod
    public  void before() {
        System.setProperty("webdriver.gecko.driver", "F:\\Private\\Programs\\geckodriver\\geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        options.setLegacy(false);
        options.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
        wd = new FirefoxDriver(options);
        wd.manage().window().maximize();
        wait = new WebDriverWait(wd, 10);
        wd.navigate().to("http://localhost/litecart/public_html/admin/login.php");
    }

    @Test
    public void test() {
        //Login
        login("admin", "admin");
        assertTrue(getText(By.xpath("//div[@class = 'notice success']")).equals("You are now logged in as admin"));

        //Transition through the sections
        appearenceSection();
        catalogSection();
        countriesSection();
        currenciesSection();
        customersSection();
        geoZoneSection();
        languagesSection();
        modulesSection();
        ordersSection();
        pagesSections();
        reportsSection();
        settingsSection();
        slidesSection();
        taxSection();
        translationSection();
        usersSection();
        vQmodsSection();
    }

    public void vQmodsSection() {
        //vQModes
        click(By.xpath("//a[contains(@href, 'app=vqmods&doc=vqmods')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'vQmods')]"));
    }

    public void usersSection() {
        //Users
        click(By.xpath("//a[contains(@href, 'app=users&doc=users')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Users')]"));
    }

    public void translationSection() {
        //Translations
        click(By.xpath("//a[contains(@href, 'app=translations&doc=search')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Search Translations')]"));

        //Scan files
        click(By.xpath("//a[contains(@href, 'app=translations&doc=scan')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Scan Files For Translations')]"));

        //CSV
        click(By.xpath("//a[contains(@href, 'app=translations&doc=csv')]"));
        isElementPresent(By.xpath("//h2[text() = 'Translations']"));
    }

    public void taxSection() {
        //Tax classes
        click(By.xpath("//a[contains(@href, 'app=tax&doc=tax_classes')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Tax Classes')]"));

        //Tax rates
        click(By.xpath("//a[contains(@href, 'app=tax&doc=tax_rates')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Tax Rates')]"));
    }

    public void slidesSection() {
        //Slides
        click(By.xpath("//a[contains(@href, 'app=slides&doc=slides')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Slides')]"));
    }

    public void settingsSection() {
        //Store info
        click(By.xpath("//a[contains(@href, 'app=settings&doc=store_info')]"));
        isElementPresent(By.xpath("//table[@class = 'dataTable']//tr[@class = 'row']/td[contains(text(), 'Store Name')]"));

        //Defaults
        click(By.xpath("//a[contains(@href, 'app=settings&doc=defaults')]"));
        isElementPresent(By.xpath("//table[@class = 'dataTable']//tr[@class = 'row']/td[contains(text(), 'Default Language')]"));

        //General
        click(By.xpath("//a[contains(@href, 'app=settings&doc=general')]"));
        isElementPresent(By.xpath("//table[@class = 'dataTable']//tr[@class = 'odd']/td[contains(text(), 'There are no entries in the database.')]"));

        //Listings
        click(By.xpath("//a[contains(@href, 'app=settings&doc=listings')]"));
        isElementPresent(By.xpath("//table[@class = 'dataTable']//tr[@class = 'row']/td[contains(text(), 'Catalog Only Mode')]"));

        //Images
        click(By.xpath("//a[contains(@href, 'app=settings&doc=images')]"));
        isElementPresent(By.xpath("//table[@class = 'dataTable']//tr[@class = 'row']/td[contains(text(), 'Category Images: Aspect Ratio')]"));

        //Checkout
        click(By.xpath("//a[contains(@href, 'app=settings&doc=checkout')]"));
        isElementPresent(By.xpath("//table[@class = 'dataTable']//tr[@class = 'row']/td[contains(text(), 'Password Field')]"));

        //Security
        click(By.xpath("//a[contains(@href, 'app=settings&doc=security')]"));
        isElementPresent(By.xpath("//table[@class = 'dataTable']//tr[@class = 'row']/td[contains(text(), 'Blacklist')]"));
    }

    public void reportsSection() {
        //Monthly sales
        click(By.xpath("//a[contains(@href, 'app=reports&doc=monthly_sales')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Monthly Sales')]"));

        //Most sold products
        click(By.xpath("//a[contains(@href, 'app=reports&doc=most_sold_products')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Most Sold Products')]"));

        //Most shipping customers
        click(By.xpath("//a[contains(@href, 'app=reports&doc=most_shopping_customers')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Most Shopping Customers')]"));
    }

    public void pagesSections() {
        //Pages
        click(By.xpath("//a[contains(@href, 'app=pages&doc=pages')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Page')]"));
    }

    public void ordersSection() {
        //Orders
        click(By.xpath("//a[contains(@href, 'app=orders&doc=orders')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Orders')]"));

        //Order statuses
        click(By.xpath("//a[contains(@href, 'app=orders&doc=order_statuses')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Order Statuses')]"));
    }

    public void modulesSection() {
        //Modules
        click(By.xpath("//a[contains(@href, 'app=modules&doc=jobs')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Job Modules')]"));

        //Customer
        click(By.xpath("//a[contains(@href, 'app=modules&doc=customer')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Customer Modules')]"));

        //Shipping
        click(By.xpath("//a[contains(@href, 'app=modules&doc=shipping')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Shipping Modules')]"));

        //Payment
        click(By.xpath("//a[contains(@href, 'app=modules&doc=payment')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Payment Modules')]"));

        //Order total
        click(By.xpath("//a[contains(@href, 'app=modules&doc=order_total')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Order Total Modules')]"));

        //Order success
        click(By.xpath("//a[contains(@href, 'app=modules&doc=order_success')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Order Success Modules')]"));

        //Order action
        click(By.xpath("//a[contains(@href, 'app=modules&doc=order_action')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Order Action Modules')]"));
    }

    public void languagesSection() {
        //Languages
        click(By.xpath("//a[contains(@href, 'app=languages&doc=languages')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Languages')]"));

        //Storage encoding
        click(By.xpath("//a[contains(@href, 'app=languages&doc=storage_encoding')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Storage Encoding')]"));
    }

    public void geoZoneSection() {
        //Geo zones
        click(By.xpath("//a[contains(@href, 'app=geo_zones&doc=geo_zones')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Geo Zones')]"));
    }

    public void customersSection() {
        //Customers
        click(By.xpath("//a[contains(@href, 'app=customers&doc=customers')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Customers')]"));

        //CSV
        click(By.xpath("//a[contains(@href, 'app=customers&doc=csv')]"));
        isElementPresent(By.xpath("//h2[text() = 'Import From CSV']"));

        //Newsletter
        click(By.xpath("//a[contains(@href, 'app=customers&doc=newsletter')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Newsletter')]"));
    }

    public void currenciesSection() {
        //Currencies
        click(By.xpath("//a[contains(@href, 'app=currencies&doc=currencies')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Currencies')]"));
    }

    public void countriesSection() {
        //Countries
        click(By.xpath("//a[contains(@href, 'app=countries&doc=countries')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Countries')]"));
    }

    public void catalogSection() {
        //Catalog
        click(By.xpath("//a[contains(@href, 'app=catalog&doc=catalog')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Catalog')]"));

        //Product groups
        click(By.xpath("//a[contains(@href, 'app=catalog&doc=product_groups')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Product Groups')]"));

        //Option groups
        click(By.xpath("//a[contains(@href, 'app=catalog&doc=option_groups')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Option Groups')]"));

        //Manufacturers
        click(By.xpath("//a[contains(@href, 'app=catalog&doc=manufacturers')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Manufacturers')]"));

        //Suppliers
        click(By.xpath("//a[contains(@href, 'app=catalog&doc=suppliers')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Suppliers')]"));

        //Delivery statuses
        click(By.xpath("//a[contains(@href, 'app=catalog&doc=delivery_statuses')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Delivery Statuses')]"));

        //Sold out statuses
        click(By.xpath("//a[contains(@href, 'app=catalog&doc=sold_out_statuses')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Sold Out Statuses')]"));

        //Quantity units
        click(By.xpath("//a[contains(@href, 'app=catalog&doc=quantity_units')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Quantity Units')]"));

        //CSV
        click(By.xpath("//a[contains(@href, 'app=catalog&doc=csv')]"));
        isElementPresent(By.xpath("//h2[text() = 'Categories']"));
    }

    public void appearenceSection() {
        //Template
        click(By.xpath("//a[contains(@href, 'app=appearance&doc=template')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Template')]"));

        //Logotype
        click(By.xpath("//a[contains(@href, 'app=appearance&doc=logotype')]"));
        isElementPresent(By.xpath("//h1[contains(text(), 'Logotype')]"));
    }

    public void login(String username, String password) {
        input(By.xpath("//input[@name = 'username']"), username);
        input(By.xpath("//input[@name = 'password']"), password);
        click(By.xpath("//input[@name = 'remember_me']"));
        click(By.xpath("//button[@name = 'login']"));
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
    }

    public void input(By locator, String value) {
        click(locator);
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(value);
    }

    public boolean isElementPresent(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean areElementsPresent(By locator) {
        return wd.findElements(locator).size() > 0;
    }

    public String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    @AfterMethod
    public void after() {
        wd.quit();
        wd = null;
    }
}
