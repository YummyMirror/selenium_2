package ru.anatoli.practice_selenium.OtherTestingNeeds;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class STLoginWithCookies {
    private WebDriver wd;
    private WebDriverWait wait;
    private File file;
    private FileReader reader;
    private BufferedReader bufferedReader;

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
    }

    @Test
    public void test() throws IOException {
        List<Cookie> cookieJSON = getCookieJSON();
        openUrl("http://software-testing.ru/lms/login/index.php");
        cookieJSON.stream().forEach(c -> wd.manage().addCookie(c));
        openUrl("http://software-testing.ru/lms/my/");
    }

    public void openUrl(String url) {
        wd.navigate().to(url);
    }

    private List<Cookie> getCookieJSON() throws IOException {
        file = new File("d:/cookies.json");
        reader = new FileReader(file);
        bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        String json = "";
        while (line != null) {
            json += line;
            line = bufferedReader.readLine();
        }
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Cookie>>(){}.getType();
        bufferedReader.close();
        reader.close();
        return gson.fromJson(json, collectionType);
    }

    public List<Cookie> getCookieCSV() throws IOException {
        List<Cookie> list = new ArrayList<Cookie>(0);
        file = new File("d:/cookies.csv");
        reader = new FileReader(file);
        bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        while (line != null) {
            String[] split = line.split(";");
            list.add(new Cookie(split[0], split[1]));
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        reader.close();
        return list;
    }

    @AfterMethod
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}
