package ru.anatoli.practice_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ComparisonLitecartTask {
    WebDriver wd;
    WebDriverWait wait;
    String browser;

    @BeforeMethod
    public void before() {
        browser = BrowserType.CHROME;

        if (browser.equals(BrowserType.IE)) {
            wd = new InternetExplorerDriver();
        } else if (browser.equals(BrowserType.CHROME)) {
            wd = new ChromeDriver();
        } else if (browser.equals(BrowserType.FIREFOX)) {
            System.setProperty("webdriver.gecko.driver", "F:\\Private\\Programs\\geckodriver\\geckodriver.exe");
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
            wd = new FirefoxDriver(firefoxOptions);
        }
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
        wd.navigate().to("http://localhost/litecart/public_html/en/");
    }

    @Test
    public void test() {
        List<WebElement> getCampaignsDucks = wd.findElements(By.xpath("//div[@id = 'box-campaigns']//ul/li"));
        WebElement main = getCampaignsDucks.get(0);
        String mainItemTitle = main.findElement(By.xpath(".//div[@class = 'name']")).getText();
        int mainRegularPrice = Integer.parseInt(main.findElement(By.xpath(".//*[@class = 'regular-price']")).getText().substring(1));
        int mainSalePrice = Integer.parseInt(main.findElement(By.xpath(".//*[@class = 'campaign-price']")).getText().substring(1));
        String mainRegularPriceTagName = main.findElement(By.xpath(".//*[@class = 'regular-price']")).getTagName();
        String mainRegularPriceColor = main.findElement(By.xpath(".//*[@class = 'regular-price']")).getCssValue("color");
        int[] mainRegularPriceColorRgb = getRgb(mainRegularPriceColor);
        int mainRegularPriceColorR = mainRegularPriceColorRgb[0];
        int mainRegularPriceColorG = mainRegularPriceColorRgb[1];
        int mainRegularPriceColorB = mainRegularPriceColorRgb[2];

        String mainSalePriceTagName = main.findElement(By.xpath(".//*[@class = 'campaign-price']")).getTagName();
        String mainSalePriceColor = main.findElement(By.xpath(".//*[@class = 'campaign-price']")).getCssValue("color");
        int[] mainSalePriceColorRgb = getRgb(mainSalePriceColor);
        int mainSalePriceColorR = mainSalePriceColorRgb[0];
        int mainSalePriceColorG = mainSalePriceColorRgb[1];
        int mainSalePriceColorB = mainSalePriceColorRgb[2];

        String mainRegularPriceSizeString = main.findElement(By.xpath(".//*[@class = 'regular-price']")).getCssValue("font-size");
        Double mainRegularPriceSizeDigit = Double.valueOf(mainRegularPriceSizeString.substring(0, mainRegularPriceSizeString.length() - 2));
        String mainSalePriceSizeString = main.findElement(By.xpath(".//*[@class = 'campaign-price']")).getCssValue("font-size");
        Double mainSalePriceSizeDigit = Double.valueOf(mainSalePriceSizeString.substring(0, mainSalePriceSizeString.length() - 2));

        ((JavascriptExecutor) wd).executeScript("arguments[0].scrollIntoView(true);", main);
        main.findElement(By.xpath("./a[@class = 'link']")).click();
        String minorItemTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class = 'title']"))).getText();
        int minorRegularPrice = Integer.parseInt(wd.findElement(By.xpath("//*[@class = 'regular-price']")).getText().substring(1));
        int minorSalePrice = Integer.parseInt(wd.findElement(By.xpath("//*[@class = 'campaign-price']")).getText().substring(1));
        String minorRegularPriceTagName = wd.findElement(By.xpath("//*[@class = 'regular-price']")).getTagName();
        String minorRegularPriceColor = wd.findElement(By.xpath("//*[@class = 'regular-price']")).getCssValue("color");
        int[] minorRegularPriceColorRgb = getRgb(minorRegularPriceColor);
        int minorRegularPriceColorR = minorRegularPriceColorRgb[0];
        int minorRegularPriceColorG = minorRegularPriceColorRgb[1];
        int minorRegularPriceColorB = minorRegularPriceColorRgb[2];

        String minorSalePriceTagName = wd.findElement(By.xpath("//*[@class = 'campaign-price']")).getTagName();
        String minorSalePriceColor = wd.findElement(By.xpath("//*[@class = 'campaign-price']")).getCssValue("color");
        int[] minorSalePriceColorRgb = getRgb(minorSalePriceColor);
        int minorSalePriceColorR = minorSalePriceColorRgb[0];
        int minorSalePriceColorG = minorSalePriceColorRgb[1];
        int minorSalePriceColorB = minorSalePriceColorRgb[2];

        String minorRegularPriceSizeString = wd.findElement(By.xpath("//*[@class = 'regular-price']")).getCssValue("font-size");
        Double minorRegularPriceSizeDigit = Double.valueOf(minorRegularPriceSizeString.substring(0, minorRegularPriceSizeString.length() - 2));
        String minorSalePriceSizeString = wd.findElement(By.xpath("//*[@class = 'campaign-price']")).getCssValue("font-size");
        Double minorSalePriceSizeDigit = Double.valueOf(minorSalePriceSizeString.substring(0, minorSalePriceSizeString.length() - 2));

        //Asserting by TITLES
        assertEquals(mainItemTitle, minorItemTitle);

        //Asserting by PRICES
        assertEquals(mainRegularPrice, minorRegularPrice);
        assertEquals(mainSalePrice, minorSalePrice);

        //Asserting by REGULAR PRICES STYLES
        assertTrue(mainRegularPriceTagName.equals("s"));
        assertTrue(mainRegularPriceColorR == mainRegularPriceColorG && mainRegularPriceColorG == mainRegularPriceColorB);

        assertTrue(minorRegularPriceTagName.equals("s"));
        assertTrue(minorRegularPriceColorR == minorRegularPriceColorG && minorRegularPriceColorG == minorRegularPriceColorB);

        //Asserting by SALE PRICES STYLES
        assertTrue(mainSalePriceTagName.equals("strong"));
        assertTrue(mainSalePriceColorR == 204);
        assertTrue(mainSalePriceColorG == 0);
        assertTrue(mainSalePriceColorB == 0);

        assertTrue(minorSalePriceTagName.equals("strong"));
        assertTrue(minorSalePriceColorR == 204);
        assertTrue(minorSalePriceColorG == 0);
        assertTrue(minorSalePriceColorB == 0);

        //Asserting by PRICE SIZE
        assertTrue(mainRegularPriceSizeDigit < mainSalePriceSizeDigit);
        assertTrue(minorRegularPriceSizeDigit < minorSalePriceSizeDigit);
    }

    public int[] getRgb(String value) {
        String s1 = value.replaceAll("[a-z()\\s]", "");
        String[] split = s1.split(",");
        int r = Integer.parseInt(split[0].replaceAll(",", ""));
        int g = Integer.parseInt(split[1].replaceAll(",", ""));
        int b = Integer.parseInt(split[2].replaceAll(",", ""));
        return new int[]{r, g, b};
    }

    @AfterMethod
    public void after() {
        wd.quit();
        wd = null;
    }
}
