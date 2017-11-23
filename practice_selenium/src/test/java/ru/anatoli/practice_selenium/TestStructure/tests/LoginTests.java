package ru.anatoli.practice_selenium.TestStructure.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import java.util.List;
import static org.testng.Assert.assertTrue;

public class LoginTests extends TestBase {
    @Test(enabled = true)
    public void loginTest() {
        applicationManager.getSessionHelper().login("admin", "admin");
        assertTrue(applicationManager.getSessionHelper().getLoginSuccessMessage().equals("You are now logged in as admin"));

        List<WebElement> menuItems = applicationManager.getNavigationHelper().getMenuItems();
        for (int i = 0; i < menuItems.size(); i++) {
            applicationManager.getNavigationHelper().getMenuItems().get(i).click();
            applicationManager.getNavigationHelper().waitForVisibilityOfElement(By.xpath("//td[@id = 'content']/h1"));

            WebElement item = applicationManager.getNavigationHelper().getMenuItems().get(i);
            List<WebElement> subMenuItems = applicationManager.getNavigationHelper().getSubMenuItems(item);
            for (int j = 0; j < subMenuItems.size(); j++) {
                WebElement menuItem = applicationManager.getNavigationHelper().getMenuItems().get(i);
                applicationManager.getNavigationHelper().getSubMenuItems(menuItem).get(j).click();
                applicationManager.getNavigationHelper().waitForVisibilityOfElement(By.xpath("//td[@id = 'content']/h1"));
            }
        }
        assertTrue(menuItems.size() == 17);
    }

    @Test(enabled = true)
    public void findNecessayMenuItem() {
        applicationManager.getSessionHelper().login("admin", "admin");
        assertTrue(applicationManager.getSessionHelper().getLoginSuccessMessage().equals("You are now logged in as admin"));

        applicationManager.getNavigationHelper().openNeededMenuItem("Countries", "");
    }
}