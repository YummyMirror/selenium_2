package ru.anatoli.practice_selenium.JQueryUIActions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import static org.testng.Assert.*;

public class BrowserLogsUsing {
    private WebDriver wd;
    private WebDriverWait wait;

    @BeforeSuite
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
    }

    @Test()
    public void test(Method method) throws IOException {
        openSite("http://rutracker.org/forum/index.php");
        List<LogEntry> severeLogs = wd.manage().logs().get(LogType.BROWSER).getAll().stream().filter(l -> l.getLevel().toString().equals("SEVERE")).collect(Collectors.toList());
        File logsFile = new File("d:/" + method.getName() + "_cookies.json");
        if (severeLogs.size() > 0)
            saveLogsToJsonFile(severeLogs, logsFile);
        assertEquals(severeLogs.size(), 0);
    }

    @Test
    public void test2() {
        String testName = "test";
        openSite("http://rutracker.org/forum/index.php");
        List<LogEntry> allLogs = wd.manage().logs().get(LogType.BROWSER).getAll();
        allLogs.forEach(l -> System.out.println(l.getLevel()));
    }

    public void saveLogsToJsonFile(List<LogEntry> logEntries, File logsFile) throws IOException {
        if (logsFile.exists())
            logsFile.delete();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(logEntries);
        FileWriter writer = new FileWriter(logsFile);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    public void openSite(String url) {
        wd.navigate().to(url);
        wait.until(ExpectedConditions.urlToBe(url));
    }

    @AfterSuite
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}
