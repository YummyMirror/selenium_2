package ru.anatoli.practice_selenium.OtherTestingNeeds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CheckBrowserLogs {
    private WebDriver wd;
    private WebDriverWait wait;

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
    }

    @Test
    public void test() {
        openSite("http://rutracker.org/forum/index.php");
        Set<String> availableLogTypes = wd.manage().logs().getAvailableLogTypes();
        List<LogEntry> allLogs = wd.manage().logs().get(LogType.BROWSER).getAll();
        allLogs.stream().forEach(l -> System.out.println("Level: " + l.getLevel() + "; Message: " + l.getMessage() + "; TimeStamp: " + l.getTimestamp()));
        if (allLogs.stream()
                   .filter(l -> l.getLevel()
                                 .toString()
                                 .equals("SEVERE"))
                   .collect(Collectors.toList()).size() > 0) {
            System.out.println("There is at least one SEVERE problem in logs!!!");
        }
    }

    public void openSite(String url) {
        wd.navigate().to(url);
        wait.until(ExpectedConditions.titleIs("BitTorrent трекер RuTracker.org"));
    }

    @AfterMethod
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}
