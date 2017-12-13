package ru.anatoli.practice_selenium;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.HarEntry;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import java.util.stream.Collectors;

public class ProxyUsing {
    private WebDriver wd;
    private WebDriverWait wait;
    private BrowserMobProxy proxy;

    @BeforeMethod
    public void before() {
        proxy = new BrowserMobProxyServer();
        proxy.start(0);
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.PROXY, seleniumProxy);
        wd = new ChromeDriver(options);
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
    }

    @Test
    public void test() {
        proxy.newHar();
        openSite("https:yandex.by");
        List<HarEntry> entries = proxy.endHar().getLog().getEntries();
        entries.stream().forEach(e -> System.out.println("Response Status: " + e.getResponse().getStatus() + "; Request URL: " + e.getRequest().getUrl()));

        if (entries.stream().filter(e -> e.getResponse().getStatus() == 404 || e.getResponse().getStatus() == 500).collect(Collectors.toList()).size() > 0) {
            System.out.println("There are serious problems exist");
        } else if (entries.stream().filter(e -> e.getResponse().getStatus() == 302 || e.getResponse().getStatus() == 200).collect(Collectors.toList()).size() > 0){
            System.out.println("All is fine! ;)");
        } else {
            System.out.println("GOOD");
        }
    }

    public void openSite(String url) {
        wd.navigate().to(url);
        wait.until(ExpectedConditions.titleIs("Яндекс"));
    }

    @AfterMethod
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}
