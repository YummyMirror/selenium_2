package ru.anatoli.practice_selenium.OtherTestingNeeds;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatePicker {
    private WebDriver wd;
    private WebDriverWait wait;

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
        wd.navigate().to("http://jqueryui.com/datepicker/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id = 'content']/h1[contains(text(), 'Datepicker')]")));
    }

    @Test
    public void test() {
        wd.switchTo().frame(wd.findElement(By.xpath("//iframe[@class = 'demo-frame']")));
        setDateUsingPicker(By.xpath("//input[@id = 'datepicker']"), By.xpath("//div[@id = 'ui-datepicker-div']"), 2017, 11, 1);
    }

    public void setDateUsingPicker(By fieldLocator, By widgetLocator, int year, int month, int date) {
        wd.findElement(fieldLocator).click();
        WebElement dateWidget = wd.findElement(widgetLocator);
        String currentMonth = dateWidget.findElement(By.xpath(".//span[contains(@class, 'month')]")).getText();
        List<String> months = new ArrayList<String>(Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
        int count = 1;
        for (int j = 0; j < months.size(); j++) {
            if (months.get(j).equals(currentMonth))
                break;
            count++;
        }
        int currentMonthValue = count;
        int currentYear = Integer.parseInt(dateWidget.findElement(By.xpath(".//span[contains(@class, 'year')]")).getText());
        //Choosing Year and Month
        if (year > currentYear) {
            int yearDifference = year - currentYear;
            if (month > currentMonthValue) {
                int monthDifference = month - currentMonthValue;
                for (int i = 0; i < (yearDifference * 12) + monthDifference; i++) {
                    dateWidget.findElement(By.xpath(".//a[@title = 'Next']")).click();
                }
            } else if (month < currentMonthValue) {
                int monthDifference = currentMonthValue - month;
                for (int i = 0; i < (yearDifference * 12) - monthDifference; i++) {
                    dateWidget.findElement(By.xpath(".//a[@title = 'Next']")).click();
                }
            } else {
                for (int i = 0; i < yearDifference * 12; i++) {
                    dateWidget.findElement(By.xpath(".//a[@title = 'Next']")).click();
                }
            }
        } else if (year < currentYear){
            int yearDifference = currentYear - year;
            if (month > currentMonthValue) {
                int monthDifference = month - currentMonthValue;
                for (int i = 0; i < (yearDifference * 12) - monthDifference; i++) {
                    dateWidget.findElement(By.xpath(".//a[@title = 'Prev']")).click();
                }
            } else if (month < currentMonthValue) {
                int monthDifference = currentMonthValue - month;
                for (int i = 0; i < (yearDifference * 12) + monthDifference; i++) {
                    dateWidget.findElement(By.xpath(".//a[@title = 'Prev']")).click();
                }
            } else {
                for (int i = 0; i < yearDifference * 12; i++) {
                    dateWidget.findElement(By.xpath(".//a[@title = 'Prev']")).click();
                }
            }
        } else {
            if (month > currentMonthValue) {
                int monthDifference = month - currentMonthValue;
                for (int i = 0; i < monthDifference; i++) {
                    dateWidget.findElement(By.xpath(".//a[@title = 'Next']")).click();
                }
            } else if (month < currentMonthValue) {
                int monthDifference = currentMonthValue - month;
                for (int i = 0; i < monthDifference; i++) {
                    dateWidget.findElement(By.xpath(".//a[@title = 'Prev']")).click();
                }
            }
        }
        //Choosing Date
        List<WebElement> currentD = dateWidget.findElements(By.xpath(".//td[@data-handler = 'selectDay']/a"));
        for (WebElement day : currentD) {
            if (date >= 1 && date <= currentD.size()) {
                if (Integer.parseInt(day.getText()) == date) {
                    day.click();
                    break;
                }
            }
        }
    }

    @AfterMethod
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}
