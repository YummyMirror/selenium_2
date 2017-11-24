package ru.anatoli.practice_selenium.TestStructure.applicationManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.anatoli.practice_selenium.TestStructure.models.CountryData;
import java.util.List;

public class CountriesHelper extends HelperBase {
    //Constructor
    public CountriesHelper(WebDriver wd, WebDriverWait wait) {
        super(wd, wait);
    }

    public List<WebElement> getCountries() {
        return wd.findElements(By.xpath("//table[@class = 'dataTable']//tr[@class = 'row']"));
    }

    public void openAddNewCountryPage() {
        click(By.xpath("//a[contains(@href, 'app=countries&doc=edit_country')]"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@id = 'content']/h1[contains(text(), 'Add New Country')]")));
    }

    public void createNewCountry(CountryData country) {
        radio(country.getStatus());
        input(By.xpath("//input[@name = 'iso_code_2']"), country.getCode());
        input(By.xpath("//input[@name = 'iso_code_3']"), country.getCode2());
        input(By.xpath("//input[@name = 'name']"), country.getName());
        input(By.xpath("//input[@name = 'domestic_name']"), country.getDomesticName());
        input(By.xpath("//input[@name = 'tax_id_format']"), country.getTaxIDFormat());
        input(By.xpath("//textarea[@name = 'address_format']"), country.getAddressFormat());
        input(By.xpath("//input[@name = 'postcode_format']"), country.getPostcodeFormat());
        input(By.xpath("//input[@name = 'currency_code']"), country.getCurrencyCode());
        input(By.xpath("//input[@name = 'phone_code']"), country.getPhoneCountryCode());
        click(By.xpath("//button[@name = 'save']"));
    }

    public void radio(Boolean value) {
        WebElement enabled = wd.findElement(By.xpath("//input[@name = 'status' and @value = '1']"));
        WebElement disabled = wd.findElement(By.xpath("//input[@name = 'status' and @value = '0']"));
        if (value != null) {
            if (value) {
                if (enabled.getAttribute("checked") == null)
                    enabled.click();
            } else {
                if (disabled.getAttribute("checked") == null)
                    disabled.click();
            }
        }
    }
}
