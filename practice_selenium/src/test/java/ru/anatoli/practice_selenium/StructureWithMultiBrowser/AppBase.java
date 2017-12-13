package ru.anatoli.practice_selenium.StructureWithMultiBrowser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.plugin.dom.exception.BrowserNotSupportedException;
import static java.lang.System.setProperty;

public class AppBase {
    private WebDriver wd;
    private WebDriverWait wait;

    public WebDriver getBrowser(String browser) {
        switch (browser) {
            case "CHROME":
                wd = new ChromeDriver();
                break;
            case "FIREFOX":
                setProperty("webdriver.gecko.driver", "F:\\Private\\Programs\\geckodriver.exe");
                wd = new FirefoxDriver();
                break;
            case "IE":
                setProperty("webdriver.ie.driver", "F:\\Private\\Programs\\IEDriverServer.exe");
                wd = new InternetExplorerDriver();
                break;
            default:
                throw new BrowserNotSupportedException("Incorrect browser type!");
        }
        return wd;
    }

    public void openUrl(String url) {
        wd.navigate().to("https://google.by");
    }
}
