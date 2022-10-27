package signinpages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignInPage {
    private WebDriver driver;

    public SignInPage(WebDriver driver) {
        this.driver = driver;
    }
    //элементы формы регистрации
    private By emailRegField = By.id("email_create");
    private By createAccountButton = By.id("SubmitCreate");
    //элементы формы авторизации
    private By emailAutField = By.id("email");
    private By passwordField = By.id("passwd");
    private By forgotPasswordLink = By.xpath("//a[text()='Forgot your password?']");
    private By signInButton = By.id("SubmitLogin");
    //сообщения об ошибках
    private By errorMessage = By.xpath("//div[@class='alert alert-danger']//li");

    public void typeInEmailRegField(String email) {
        driver.findElement(emailRegField).sendKeys(email);
    }

    public void pressCreateAccountButton() {
        driver.findElement(createAccountButton).click();
    }

    public void typeInEmailAutField(String email) {
        driver.findElement(emailAutField).sendKeys(email);
    }

    public void typeInPasswordField(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void pressForgotPasswordLink() {
        driver.findElement(forgotPasswordLink).click();
    }

    public void pressSignInButton() {
        driver.findElement(signInButton).click();
    }

    public boolean isErrorExist() {
        try {
            return driver.findElement(errorMessage).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
}
