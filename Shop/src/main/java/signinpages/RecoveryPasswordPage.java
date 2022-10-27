package signinpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RecoveryPasswordPage {
    private WebDriver driver;

    public RecoveryPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    private By emailField = By.id("email");
    private By retrieveButton = By.xpath("//form[@id='form_forgotpassword']//button");
    private By successMessage = By.xpath("//p[@class='alert alert-success']");

    public void typeInEmailField(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void pressRetrieveButton() {
        driver.findElement(retrieveButton).click();
    }

    public String getSuccessMessage() {
        return driver.findElement(successMessage).getText();
    }
}
