package ru.anatoli.practice_selenium.JQueryUIActions;

import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import sun.plugin.dom.exception.BrowserNotSupportedException;
import java.util.List;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static org.testng.Assert.assertTrue;

public class Manipulations {
    private WebDriver wd;
    private WebDriverWait wait;
    private String browser = BrowserType.CHROME;

    @BeforeSuite
    public void before() {
        switch (browser) {
            case BrowserType.CHROME:
                wd = new ChromeDriver();
                break;
            case BrowserType.FIREFOX:
                System.setProperty("webdriver.gecko.driver", "F:\\Private\\Programs\\geckodriver\\geckodriver.exe");
                FirefoxOptions optionsFF = new FirefoxOptions();
                optionsFF.setLegacy(false);
                optionsFF.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
                wd = new FirefoxDriver(optionsFF);
                break;
            case BrowserType.IE:
                InternetExplorerOptions optionsIE = new InternetExplorerOptions();
                optionsIE.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
                optionsIE.ignoreZoomSettings();
                optionsIE.introduceFlakinessByIgnoringSecurityDomains();
                wd = new InternetExplorerDriver(optionsIE);
                break;
            default:
                throw new BrowserNotSupportedException("Incorrect browser type input!!!");
        }
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
        System.out.println("Browser's capabilities: " + ((HasCapabilities) wd).getCapabilities());
    }

    @Test
    public void test1() {
        openPage("http://jqueryui.com/selectable/", "Selectable");
        wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        List<WebElement> items = wd.findElements(By.xpath("//ol[@id = 'selectable']/li"));
        for (int i = 0; i < items.size(); i++) {
            List<WebElement> elements = wd.findElements(By.xpath("//ol[@id = 'selectable']/li"));
            elements.get(i).click();
            wait.until(attributeContains(elements.get(i), "class", "selected"));
        }
    }

    @Test
    public void test2() {
        openPage("http://jqueryui.com/accordion/", "Accordion");
        wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        List<WebElement> itemsAll = wait.until(visibilityOfAllElementsLocatedBy(By.xpath("//div[@id = 'accordion']/h3[contains(@id, 'ui-id')]")));
        List<WebElement> selectedItem = wd.findElements(By.xpath("//div[@id = 'accordion']/h3[@aria-expanded = 'true']"));
        itemsAll.removeAll(selectedItem);
        for (int i = 0; i < itemsAll.size(); i++) {
            List<WebElement> all = wait.until(visibilityOfAllElementsLocatedBy(By.xpath("//div[@id = 'accordion']/h3[contains(@id, 'ui-id')]")));
            List<WebElement> selected = wd.findElements(By.xpath("//div[@id = 'accordion']/h3[@aria-expanded = 'true']"));
            all.removeAll(selected);
            all.get(i).click();
            wait.until(attributeToBe(all.get(i), "aria-expanded", "true"));
        }
    }

    @Test
    public void test3() {
        openPage("http://jqueryui.com/datepicker/", "Datepicker");
        wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        setDate(12);
        wd.switchTo().defaultContent();
        String buttonName = wd.findElement(By.xpath("//a[contains(@href, '/demos/')]")).getText();
        assertTrue(buttonName.equals("Demos"));
    }

    @Test
    public void test4() {
        openPage("http://jqueryui.com/dialog/", "Dialog");
        wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        String title = wd.findElement(By.xpath("//span[@class = 'ui-dialog-title']")).getText();
        String body = wd.findElement(By.xpath("//div[@id = 'dialog']")).getText();
        wd.findElement(By.xpath("//button[@title = 'Close']")).click();
    }

    @Test
    public void test5() {
        openPage("http://jqueryui.com/spinner/", "Spinner");
        wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        WebElement spinner = wd.findElement(By.xpath("//span[contains(@class, 'ui-spinner')]"));
        WebElement spinnerUp = spinner.findElement(By.xpath("./a[contains(@class, 'spinner-up')]"));
        WebElement spinnerDown = spinner.findElement(By.xpath("./a[contains(@class, 'spinner-down')]"));
        for (int i = 0; i < 10; i++) {
            spinnerUp.click();
        }
        for (int i = 0; i < 3; i++) {
            spinnerDown.click();
        }
        int currentValue = Integer.parseInt(spinner.findElement(By.xpath("./input[@id = 'spinner']")).getAttribute("aria-valuenow"));
        assertTrue(currentValue == 7);
    }

    @Test
    public void test6() {
        openPage("http://jqueryui.com/tabs/", "Tabs");
        wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        List<WebElement> allTabs = wd.findElements(By.xpath("//div[@id = 'tabs']//li"));
        List<WebElement> selectedTabs = wd.findElements(By.xpath("//div[@id = 'tabs']//li[@aria-selected = 'true']"));
        allTabs.removeAll(selectedTabs);
        for (int i = 0; i < allTabs.size(); i++) {
            List<WebElement> all = wd.findElements(By.xpath("//div[@id = 'tabs']//li"));
            List<WebElement> selected = wd.findElements(By.xpath("//div[@id = 'tabs']//li[@aria-selected = 'true']"));
            all.removeAll(selected);
            all.get(i).click();
            wait.until(attributeToBe(all.get(i), "aria-selected", "true"));
        }
    }

    @Test
    public void test7() {
        wd.navigate().to("https://www.w3schools.com/jquery/tryit.asp?filename=tryjquery_lib_google");
        wait.until(visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Run')]")));
        wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@id = 'iframeResult']")));
        String title = wd.findElement(By.xpath("//h2")).getText();
        List<WebElement> paragraphs = wd.findElements(By.xpath("//p"));
        wait.until(visibilityOfElementLocated(By.xpath("//button"))).click();
    }

    public void setDate(int date) {
        WebElement datePicker = wd.findElement(By.xpath("//input[@id = 'datepicker']"));
        datePicker.click();
        WebElement dateWidget = wd.findElement(By.xpath("//div[@id = 'ui-datepicker-div']"));
        List<WebElement> daysInMonth = dateWidget.findElements(By.xpath(".//tr//a[contains(@class, 'ui-state')]"));
        if (date >= 1 && date <= daysInMonth.size()) {
            for (int i = 0; i < daysInMonth.size(); i++) {
                if (date == Integer.parseInt(daysInMonth.get(i).getText())) {
                    daysInMonth.get(i).click();
                    break;
                }
            }
        }
    }

    public void openPage(String url, String pageTitle) {
        wd.navigate().to(url);
        wait.until(textToBe(By.xpath("//h1[@class = 'entry-title']"), pageTitle));
    }

    @AfterSuite
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}
