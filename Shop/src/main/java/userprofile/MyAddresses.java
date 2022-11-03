package userprofile;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class MyAddresses {
    private static WebDriver driver;

    public MyAddresses(WebDriver driver) {
        this.driver = driver;
    }

    private By addressCards = By.xpath("//div[@class='col-xs-12 col-sm-6 address']");
    private By addressFromCard = By.xpath("//span[@class='address_address1']");
    private By cityFromCard = By.xpath("//div[@class='col-xs-12 col-sm-6 address']//li[5]/span[1]");
    private By postalCodeFromCard = By.xpath("//div[@class='col-xs-12 col-sm-6 address']//li[5]/span[3]");
    private By phoneFromCard = By.xpath("//span[@class='address_phone']");

    private By updateButton = By.xpath("//a[@title='Update']");
    private By deleteButton = By.xpath("//a[@title='Delete']");
    private By addNewAddressButton = By.xpath("//a[@title='Add an address']");
    private By backToAccountButton = By.xpath("//ul[@class='footer_links clearfix']/li[1]");
    private By homeButton = By.xpath("//ul[@class='footer_links clearfix']/li[2]");

    private By infoMessage = By.xpath("//p[@class='alert alert-warning']");
    private By addNewAddressLink = By.xpath("//a[text()='Add a new address']");

    public int getCountAddressesCard() {
        return driver.findElements(addressCards).size();
    }

    public String getAddressFromCard() {
        return driver.findElement(addressFromCard).getText().trim();
    }

    public String getCityFromCard() {
        String result = driver.findElement(cityFromCard).getText();
        return result.replaceAll("\\p{Punct}", "").trim();
    }

    public String getPostalCodeFromCard() {
        return driver.findElement(postalCodeFromCard).getText().trim();
    }

    public String getPhoneFromCard() {
        return driver.findElement(phoneFromCard).getText().trim();
    }

    public void pressUpdateButton() {
        driver.findElement(updateButton).click();
    }

    public void pressDeleteButton() {
        driver.findElement(deleteButton).click();
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    public void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    public void pressAddNewAddressButton() {
        driver.findElement(addNewAddressButton).click();
    }

    public void pressBackToAccountButton() {
        driver.findElement(backToAccountButton).click();
    }

    public void pressHomeButton() {
        driver.findElement(homeButton).click();
    }

    public String getInfoMessage() {
        return driver.findElement(infoMessage).getText().trim();
    }

    public void pressAddNewAddressLink() {
        driver.findElement(addNewAddressLink).click();
    }

    public static class EditAddressForm {
        private static By firstNameField = By.id("firstname");
        private static By lastNameField = By.id("lastname");
        private static By addressField = By.id("address1");
        private static By cityField = By.id("city");
        private static By statesList = By.id("id_state");
        private static By postalCodeField = By.id("postcode");
        private static By phoneField = By.id("phone");
        private static By addressTitleField = By.id("alias");
        private static By saveButton = By.id("submitAddress");
        private static By errorList = By.xpath("//div[@class='alert alert-danger']//li");
        private static By fieldsWithErrors = By.xpath("//div[@class='required form-group form-error']");

        public static void clearFirstNameField() {
            driver.findElement(firstNameField).clear();
        }

        public static void clearLastNameField() {
            driver.findElement(lastNameField).clear();
        }

        public static void clearAddressField() {
            driver.findElement(addressField).clear();
        }

        public static void clearCityField() {
            driver.findElement(cityField).clear();
        }

        public static void typeInAddressField(String address) {
            clearAddressField();
            driver.findElement(addressField).sendKeys(address);
        }

        public static void typeInCityField(String city) {
            clearCityField();
            driver.findElement(cityField).sendKeys(city);
        }

        public static void selectState(String state) {
            Select dropDown = new Select(driver.findElement(statesList));
            dropDown.selectByVisibleText(state);
        }

        public static void typeInPostalCodeField(String postalCode) {
            WebElement element = driver.findElement(postalCodeField);
            element.clear();
            element.sendKeys(postalCode);
        }

        public static void typeInPhoneField(String phone) {
            WebElement element = driver.findElement(phoneField);
            element.clear();
            element.sendKeys(phone);
        }

        public static void typeInAddressTitleField(String title) {
            WebElement element = driver.findElement(addressTitleField);
            element.clear();
            element.sendKeys(title);
        }

        public static void pressSaveButton() {
            driver.findElement(saveButton).click();
        }

        public static void addNewAddressCard() {
            typeInAddressField("5723 Morgan Ave");
            typeInCityField("New York");
            selectState("New York");
            typeInPostalCodeField("90250");
            typeInPhoneField("8955555555");
            typeInAddressTitleField("New");
            pressSaveButton();
        }

        public static int getCountErrorsMessages() {
            return driver.findElements(errorList).size();
        }

        public static int getCountFieldsWithErrors() {
            try {
                return driver.findElements(fieldsWithErrors).size();
            } catch (NoSuchElementException e) {
                return 0;
            }
        }
    }
}
