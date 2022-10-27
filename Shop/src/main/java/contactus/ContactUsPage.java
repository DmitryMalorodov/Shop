package contactus;

import enums.Subject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ContactUsPage {
    private WebDriver driver;

    public ContactUsPage(WebDriver driver) {
        this.driver = driver;
    }

    private By subjectDropDown = By.id("id_contact");
    private By infoAboutSubject = By.xpath("//p[@class='desc_contact contact-title']");
    private By emailField = By.id("email");
    private By orderReferenceField = By.id("id_order");
    private By attachField = By.id("fileUpload");
    private By messageField = By.id("message");
    private By sendButton = By.id("submitMessage");
    private By successMessage = By.xpath("//p[@class='alert alert-success']");
    private By errorMessage = By.xpath("//div[@class='alert alert-danger']//li");
    private By homeButton = By.xpath("//ul[@class='footer_links clearfix']/li");

    //варианты ввода в параметр метода: "Customer service", "Webmaster", "-- Choose --"
    public void selectSubject(Subject subject) {
        Select select = new Select(driver.findElement(subjectDropDown));
        select.selectByVisibleText(subject.getSubject());
    }

    //метод определяет какой выбран subject и возвращает выводимый на экран текст
    public String getInfoAboutSubject() {
        List<WebElement> elements = driver.findElements(infoAboutSubject);
        String result = "";

        for (WebElement e: elements) {
            if (e.isDisplayed()) result = e.getText().trim();
        }
        return result;
    }

    //метод проверяет отображается ли какое либо сообщение, если да, то возвращает true
    public boolean isInfoAboutSubjectExist() {
        List<WebElement> elements = driver.findElements(infoAboutSubject);
        for (WebElement e: elements) {
            if (e.isDisplayed()) return true;
        }
        return false;
    }

    public void typeInEmailField(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void typeInOrderReferenceField(String orderReference) {
        driver.findElement(orderReferenceField).sendKeys(orderReference);
    }

    public void uploadFile(String filePath) {
        driver.findElement(attachField).sendKeys(filePath);
    }

    public void typeInMessageField(String message) {
        driver.findElement(messageField).sendKeys(message);
    }

    public void pressSendButton() {
        driver.findElement(sendButton).click();
    }

    public String getInfoMessage() {
        try {
            return driver.findElement(errorMessage).getText();
        }catch (NoSuchElementException e) {
            return driver.findElement(successMessage).getText();
        }
    }

    //метод нажимает в пустую область, затем проверяет выдает ли поле ошибку
    public boolean isEmailFieldShowError() {
        driver.findElement(By.xpath("//html")).click();
        WebElement element = driver.findElement(By.xpath("//input[@id='email']/.."));
        return element.getAttribute("class").equals("form-group form-error");
    }

    public void pressHomeButton() {
        driver.findElement(homeButton).click();
    }
}
