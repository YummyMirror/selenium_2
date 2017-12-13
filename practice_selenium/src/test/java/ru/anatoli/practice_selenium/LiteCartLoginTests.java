package ru.anatoli.practice_selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import static org.testng.Assert.assertTrue;

public class LiteCartLoginTests {
    WebDriver wd;
    WebDriverWait wait;

    @BeforeMethod
    public void init() {
        //New Method
        System.setProperty("webdriver.gecko.driver", "F:\\Private\\Programs\\geckodriver\\geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions().setLegacy(false);
        //Run Nightly
        //options.setBinary(new FirefoxBinary(new File("C:\\Program Files\\Nightly\\firefox.exe")));
        //Old Method
        //options.setLegacy(true)
        options.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
        wd = new FirefoxDriver(options);
        //wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wd.manage().window().maximize();
        wait = new WebDriverWait(wd, 10);
        wd.navigate().to("http://localhost/litecart/public_html/admin/loginAs.php");
        System.out.println(((HasCapabilities) wd).getCapabilities());
    }

    @Test(enabled = true)
    public void loginTests() throws IOException {
        login("admin", "admin");
        //Asserting by Login text
        String loginText = wd.findElement(By.xpath("//div[@class = 'notice success']")).getText();
        assertTrue(loginText.equals("You are now logged in as admin"));
        //Getting Cookies
        Set<Cookie> cookiesSet = wd.manage().getCookies();
        List<Cookie> cookieList = new ArrayList<Cookie>(cookiesSet);

        cookieList.remove(0);
        cookieList.remove(cookieList.size() - 1);

        for (Cookie cookie : cookieList) {
            System.out.println("Name: " + cookie.getName() + "\n" +
                                "Path: " + cookie.getPath() + "\n" +
                                "Domain: " + cookie.getDomain() + "\n" +
                                "Expiry: " + cookie.getExpiry() + "\n" +
                                "Value: " + cookie.getValue() + "\n");
        }

        File cookiesFile = new File("d:/cookies.csv");
        FileWriter writer = new FileWriter(cookiesFile);

        for (Cookie cookie : cookieList) {
            writer.write(String.format("%s;%s\n", cookie.getName(),
                                                           //cookie.getPath(),
                                                           //cookie.getDomain(),
                                                           //cookie.getExpiry(),
                                                  cookie.getValue()));
        }
        writer.flush();
        writer.close();
    }

    @Test(enabled = true)
    public void appearanceTests() {
        login("admin", "admin");

        openTemplatePage();
        openLogotypePage();

        assertTrue(getPageTitle().equals("Logotype"));

        attach(By.xpath("//input[@name = 'image']"), "D:\\WallPapers\\3L3VP6GnoB4.jpg");
        click(By.xpath("//button[@name = 'save']"));

        assertTrue(getSuccessText().equals("Changes were successfully saved."));
    }

    @Test(enabled = true)
    public void templateTest() {
        login("admin", "admin");

        openTemplatePage();
        openTemplateSettingsPage();
        openEditTemplatesSettingsPage();

        assertTrue(getHeaderTitle().equals("Fixed Header"));

        selectYesValueForFixHeader(getYesButtonStatus());
        click(By.xpath("//button[@name = 'save']"));

        assertTrue(getSuccessText().equals("Changes were successfully saved."));
    }

    @Test(enabled = true)
    public void justTest() {
        login("admin", "admin");
        wd.navigate().to("http://localhost/litecart/public_html/admin/?app=catalog&doc=edit_category&category_id=1");
        wd.findElement(By.xpath("//input[@name = 'priority']")).clear();
        wd.findElement(By.xpath("//input[@name = 'priority']")).sendKeys("5");
        wd.findElement(By.xpath("//a[@href = '#tab-information']")).click();

    }

    @Test(enabled = true)
    public void testingMethods() {
        login("admin", "admin");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt = 'My Store' and @title = 'My Store']")));
        Boolean title = isElementPresent(By.xpath("//img[@alt = 'My Store' and @title = 'My Store']"));
        Boolean title2 = areElementsPresent(By.xpath("//img[@alt = 'My Store' and @title = 'My Store']"));
    }

    public boolean isElementPresent(By locator) {
        try {
            //wd.findElement(locator);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            throw e;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean areElementsPresent(By locator) {
        return wd.findElements(locator).size() > 0;
    }

    public String getPageTitle() {
        return wd.findElement(By.xpath("//td[@id = 'content']/h1")).getText();
    }

    public void openLogotypePage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'app=appearance&doc=logotype')]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@src, '1480b4f58154b1812108cdf1ab5de7bf928c4830500x250_fob.png')]")));
    }

    public String getSuccessText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'notice success']"))).getText();
    }

    public String getYesButtonStatus() {
        return wd.findElement(By.xpath("//input[@name = 'fixed_header' and @value = '1']/parent::label")).getAttribute("checked");
    }

    public String getHeaderTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class = 'dataTable']//tr[@class = 'row']//u"))).getText();
    }

    public void selectYesValueForFixHeader(String radio) {
        if (radio == null) {
            wd.findElement(By.xpath("//input[@name = 'fixed_header' and @value = '1']/parent::label")).click();
        }
    }

    public void openEditTemplatesSettingsPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'app=appearance&doc=template_settings&action=edit&key=fixed_header')]"))).click();
    }

    public void openTemplateSettingsPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'app=appearance&doc=template_settings')]"))).click();
    }

    public void openTemplatePage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'app=appearance&doc=template')]"))).click();
    }

    public void click(By locator) {
        wd.findElement(locator).click();
    }

    public void attach(By locator, String path) {
        if (!path.isEmpty()) {
            wd.findElement(locator).sendKeys(path);
        }
    }

    public void login(String login, String password) {
        input(By.xpath("//input[@name = 'username']"), login);
        input(By.xpath("//input[@name = 'password']"), password);
        checkBox(By.xpath("//input[@name = 'remember_me']"));
        submitForm(By.xpath("//button[@name = 'loginAs']"));
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
    public void stop() {
        wd.quit();
        wd = null;
    }
}
