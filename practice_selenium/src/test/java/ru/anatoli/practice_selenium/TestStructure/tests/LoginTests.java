package ru.anatoli.practice_selenium.TestStructure.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.testng.annotations.Test;
import ru.anatoli.practice_selenium.TestStructure.applicationManager.HelperBase;
import ru.anatoli.practice_selenium.TestStructure.models.CountryData;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import static org.testng.Assert.*;

public class LoginTests extends TestBase {
    @Test(enabled = true, priority = 1)
    public void loginTest(Method method) {
        List<WebElement> menuItems = applicationManager.getNavigationHelper().getMenuItems();
        for (int i = 0; i < menuItems.size(); i++) {
            applicationManager.getNavigationHelper().getMenuItems().get(i).click();
            applicationManager.getNavigationHelper().waitForVisibilityOfElement(By.xpath("//td[@id = 'content']/h1"));
            List<LogEntry> logsSevere = applicationManager.getNavigationHelper().getLogs().stream().filter(l -> l.getLevel().toString().equals("SEVERE")).collect(Collectors.toList());
            assertEquals(logsSevere.size(), 0);

            WebElement item = applicationManager.getNavigationHelper().getMenuItems().get(i);
            List<WebElement> subMenuItems = applicationManager.getNavigationHelper().getSubMenuItems(item);
            for (int j = 0; j < subMenuItems.size(); j++) {
                WebElement menuItem = applicationManager.getNavigationHelper().getMenuItems().get(i);
                applicationManager.getNavigationHelper().getSubMenuItems(menuItem).get(j).click();
                applicationManager.getNavigationHelper().waitForVisibilityOfElement(By.xpath("//td[@id = 'content']/h1"));
                List<LogEntry> logs = applicationManager.getNavigationHelper().getLogs().stream().filter(l -> l.getLevel().toString().equals("SEVERE")).collect(Collectors.toList());
                assertEquals(logs.size(), 0);
            }
        }
        assertEquals(menuItems.size(),17);
        //applicationManager.getNavigationHelper().showAlert("Test alert message");
        HelperBase.LOGGER.info(method.getName() + " method is good!!!");
        applicationManager.getNavigationHelper().openUrl("http://localhost/litecart/public_html/admin/");
    }

    @Test(enabled = false, priority = 2)
    public void findNecessaryMenuItem() {
        //applicationManager.getNavigationHelper().openUrl("http://localhost/litecart/public_html/admin/");
        //Open Menu Item
        applicationManager.getNavigationHelper().openNeededMenuItem("Countries", "");

        List<WebElement> countriesBefore = applicationManager.getCountriesHelper().getCountries();

        //applicationManager.getCountriesHelper().openAddNewCountryPage();
        CountryData country = new CountryData().setStatus(true)
                                               .setCode("AA")
                                               .setCode2("ABW")
                                               .setName("Name")
                                               .setDomesticName("Domestic name")
                                               .setTaxIDFormat("11111")
                                               .setAddressFormat("Address format")
                                               .setPostcodeFormat("55555")
                                               .setCurrencyCode("8")
                                               .setPhoneCountryCode("+375");
        //applicationManager.getCountriesHelper().createNewCountry(country);

        List<WebElement> countriesAfter = applicationManager.getCountriesHelper().getCountries();
        //Asserting
        assertEquals(countriesBefore.size(), countriesAfter.size());
        assertEquals(countriesBefore, countriesAfter);
    }
}