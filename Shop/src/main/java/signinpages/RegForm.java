package signinpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class RegForm {
    private WebDriver driver;

    public RegForm(WebDriver driver) {
        this.driver = driver;
    }

    private By firstNameField = By.id("customer_firstname");
    private By lastNameField = By.id("customer_lastname");
    private By passwordField = By.id("passwd");
    private By addressField = By.id("address1");
    private By cityField = By.id("city");
    private By statesList = By.id("id_state");
    private By postalCodeField = By.id("postcode");
    private By phoneField = By.id("phone_mobile");
    private By regButton = By.id("submitAccount");

    private By errorMessage = By.xpath("//div[@class='alert alert-danger']//li");

    public void typeInFirstNameField(String firstName) {
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    public void typeInLastNameField(String lastName) {
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void typeInPasswordField(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void typeInAddressField(String address) {
        driver.findElement(addressField).sendKeys(address);
    }

    public void typeInCityField(String city) {
        driver.findElement(cityField).sendKeys(city);
    }

    public void chooseState(String state) {
        Select dropdown = new Select(driver.findElement(statesList));
        dropdown.selectByVisibleText(state);
    }

    public void typeInPostalCodeField(String postalCode) {
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }

    public void typeInPhoneField(String phoneNumber) {
        driver.findElement(phoneField).sendKeys(phoneNumber);
    }

    public void pressRegButton() {
        driver.findElement(regButton).click();
    }

    public List<WebElement> getElementsWithErrors() {
        return driver.findElements(errorMessage);
    }
}
