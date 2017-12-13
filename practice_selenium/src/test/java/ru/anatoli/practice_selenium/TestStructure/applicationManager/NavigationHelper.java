package ru.anatoli.practice_selenium.TestStructure.applicationManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class NavigationHelper extends HelperBase {
    //Constructor
    public NavigationHelper(WebDriver wd, WebDriverWait wait, Actions actions) {
        super(wd, wait, actions);
    }

    public List<WebElement> getMenuItems() {
        return wd.findElements(By.xpath("//ul[@id = 'box-apps-menu']/li[@id = 'app-']/a"));
    }

    public List<WebElement> getSubMenuItems(WebElement parent) {
        return parent.findElements(By.xpath("./../ul[@class = 'docs']/li/a"));
    }

    public void openNeededMenuItem(String menuItem, String subMenuItem) {
        for (int i = 0; i < getMenuItems().size(); i++) {
            List<WebElement> menus = getMenuItems();
            if (menus.get(i).getText().equals(menuItem)) {
                menus.get(i).click();
                waitForVisibilityOfElement(By.xpath("//td[@id = 'content']/h1"));
            }
            List<WebElement> subItems = getSubMenuItems(getMenuItems().get(i));
            for (int j = 0; j < subItems.size(); j++) {
                if (subItems.get(j).getText().equals(subMenuItem)) {
                    subItems.get(j).click();
                    waitForVisibilityOfElement(By.xpath("//td[@id = 'content']/h1"));
                }
            }
        }
    }

    public void showAlert(String text) {
        js.executeScript("alert('" + text + "');");
    }
}
