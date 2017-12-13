package ru.anatoli.practice_selenium.JQueryUIActions;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import sun.plugin.dom.exception.BrowserNotSupportedException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.in;
import static java.lang.System.setProperty;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ActionsTests {
    private WebDriver wd;
    private WebDriverWait wait;
    private Actions actions;

    @BeforeSuite
    public void before() {
        String browser = BrowserType.CHROME;
        switch (browser) {
            case BrowserType.CHROME:
                wd = new ChromeDriver();
                break;
            case BrowserType.FIREFOX:
                setProperty("webdriver.gecko.driver", "F:\\Private\\Programs\\geckodriver\\geckodriver.exe");
                FirefoxOptions optionsFF = new FirefoxOptions();
                optionsFF.setLegacy(false);
                optionsFF.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
                wd = new FirefoxDriver(optionsFF);
                break;
            case BrowserType.IE:
                InternetExplorerOptions optionsIE = new InternetExplorerOptions();
                optionsIE.ignoreZoomSettings();
                optionsIE.introduceFlakinessByIgnoringSecurityDomains();
                optionsIE.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
                wd = new InternetExplorerDriver(optionsIE);
                break;
            default:
                throw new BrowserNotSupportedException("Incorrect browser type input!");
        }
        wait = new WebDriverWait(wd, 10);
        actions = new Actions(wd);
        wd.manage().window().maximize();
    }

    @Test
    public void test() {
        openPage("http://jqueryui.com/menu/", "Menu");
        wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        List<WebElement> menuItems = wait.until(visibilityOfAllElementsLocatedBy(By.xpath("//ul[@id = 'menu']/li[not(@aria-disabled)]")));
        List<WebElement> itemsWithSubMenus = menuItems.stream().filter(m -> m.findElements(By.xpath("./ul")).size() > 0).collect(Collectors.toList());
        for (int i = 0; i < itemsWithSubMenus.size(); i++) {
            actions.moveToElement(itemsWithSubMenus.get(i))
                    .build()
                    .perform();
            if (areElementsPresent(itemsWithSubMenus.get(i), By.xpath("./ul"))) {
                WebElement subSection = itemsWithSubMenus.get(i).findElement(By.xpath("./ul"));
                wait.until(attributeToBe(subSection, "aria-expanded", "true"));
                List<WebElement> subMenus = itemsWithSubMenus.get(i).findElements(By.xpath("./ul/li[not(@aria-disabled)]"));
                for (int j = 0; j < subMenus.size(); j++) {
                    actions.moveToElement(subMenus.get(j))
                            .build()
                            .perform();
                    if (areElementsPresent(subMenus.get(j), By.xpath("./ul"))) {
                        WebElement subSection2 = subMenus.get(j).findElement(By.xpath("./ul"));
                        wait.until(attributeToBe(subSection2, "aria-expanded", "true"));
                        List<WebElement> subSubMenus = subMenus.get(j).findElements(By.xpath("./ul/li[not(@aria-disabled)]"));
                        for (int s = 0; s < subSubMenus.size(); s++) {
                            actions.moveToElement(subSubMenus.get(s))
                                    .build()
                                    .perform();
                        }
                    }
                }
            }
        }
    }

    @Test
    public void dragAndDrop() {
        openPage("http://jqueryui.com/draggable/", "Draggable");
        wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        WebElement figure = wd.findElement(By.xpath("//div[@id = 'draggable']"));
        actions.dragAndDropBy(figure, 50, 50)
                .build()
                .perform();
        actions.dragAndDropBy(figure, 100, 100)
                .build()
                .perform();
        actions.moveToElement(figure)
                .clickAndHold()
                .moveByOffset(150, 10)
                .release()
                .build()
                .perform();
    }

    @Test
    public void dragAndDrop2() {
        openPage("http://jqueryui.com/droppable/", "Droppable");
        wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        WebElement figure = wd.findElement(By.xpath("//div[@id = 'draggable']"));
        WebElement area = wd.findElement(By.xpath("//div[@id = 'droppable']"));
        actions.dragAndDrop(figure, area)
                .build()
                .perform();
        wait.until(attributeContains(area, "class", "highlight"));
    }

    @Test
    public void resizing() {
        openPage("http://jqueryui.com/resizable/", "Resizable");
        wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        WebElement figure = wd.findElement(By.xpath("//div[@id = 'resizable']"));
        WebElement resizeButton = figure.findElement(By.xpath("./div[contains(@class, 'diagonal')]"));
        actions.moveToElement(resizeButton)
                .dragAndDropBy(resizeButton, 100, 100)
                .build()
                .perform();
    }

    @Test
    public void selecting() {
        openPage("http://jqueryui.com/selectable/", "Selectable");
        wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        List<WebElement> items = wd.findElements(By.xpath("//ol[@id = 'selectable']/li"));
        actions.sendKeys(Keys.CONTROL).build().perform();
        for (int i = 0; i < items.size(); i++) {
            List<WebElement> list = wd.findElements(By.xpath("//ol[@id = 'selectable']/li"));
            actions.click(list.get(i))
                    .build()
                    .perform();
            wait.until(attributeContains(list.get(i), "class", "selected"));
        }
    }

    @Test
    public void dragAndDrop3() {
        openPage("http://jqueryui.com/sortable/", "Sortable");
        wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        List<WebElement> elements = wd.findElements(By.xpath("//ul[@id = 'sortable']/li"));
        for (int i = 0; i < elements.size(); i++) {
            List<WebElement> els = wd.findElements(By.xpath("//ul[@id = 'sortable']/li"));
            actions.clickAndHold(els.get(i))
                    .moveByOffset(0, 50)
                    .release()
                    .build()
                    .perform();
        }
    }

    @Test
    public void contextClick() {
        openPage("http://jqueryui.com/tooltip/", "Tooltip");
        wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        WebElement field = wd.findElement(By.xpath("//input[@id = 'age']"));
        actions.click(field)
                .sendKeys("abcde")
                .doubleClick(field)
                .sendKeys(Keys.chord(Keys.CONTROL, "C"))
                .build()
                .perform();
        field.clear();
        actions.click(field)
                .sendKeys(Keys.chord(Keys.CONTROL, "V"))
                .build()
                .perform();
    }

    @Test
    public void slider() {
        openPage("http://jqueryui.com/slider/", "Slider");
        wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        WebElement slider = wd.findElement(By.xpath("//div[@id = 'slider']"));
        WebElement scrubber = slider.findElement(By.xpath("./span"));
        int sliderWidth = slider.getSize().width; //571
        int scrubberWidth = scrubber.getSize().width; //22
        int scrubberX = scrubber.getLocation().getX(); //0
        int scrubberY = scrubber.getLocation().getY(); //4
        for (int i = 0; i < 12; i++) {
            actions.dragAndDropBy(scrubber, 450, 0)
                    .build()
                    .perform();
            actions.dragAndDropBy(scrubber, -400, 0)
                    .build()
                    .perform();
        }
    }

    public void openPage(String url, String pageTitle) {
        wd.navigate().to(url);
        wait.until(textToBe(By.xpath("//h1[@class = 'entry-title']"), pageTitle));
    }

    public boolean areElementsPresent(WebElement parent, By locator) {
        return parent.findElements(locator).size() > 0;
    }

    @AfterSuite
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}
