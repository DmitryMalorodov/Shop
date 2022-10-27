import contactus.ContactUsPage;
import enums.Subject;
import mainpage.MainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import resources.Resources;

import java.time.Duration;

public class ContactUsPageTest {
    private static WebDriver driver;
    private static MainPage mainPage;
    private static ContactUsPage contactUsPage;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", Resources.chromeDriverPath);

        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        contactUsPage = new ContactUsPage(driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get(Resources.mainPageUrl);
    }

    @BeforeEach
    public void precondition() {
        mainPage.pressContactUsButton();
    }

    @Test
    public void case1() {
        contactUsPage.selectSubject(Subject.WEBMASTER);
        contactUsPage.typeInEmailField(Resources.correctEmail);
        contactUsPage.typeInOrderReferenceField("5555");
        contactUsPage.typeInMessageField("Hello!");
        contactUsPage.uploadFile("C:\\Users\\user\\Desktop\\Test-Suits\\Тест-Сьют - Тестирование формы регистрации.rtf");
        contactUsPage.pressSendButton();

        Assertions.assertEquals("Your message has been successfully sent to our team.", contactUsPage.getInfoMessage());
    }

    @Test
    public void case2() {
        contactUsPage.selectSubject(Subject.WEBMASTER);
        contactUsPage.typeInEmailField(Resources.correctEmail);
        contactUsPage.typeInMessageField("Hello!");
        contactUsPage.pressSendButton();

        Assertions.assertEquals("Your message has been successfully sent to our team.", contactUsPage.getInfoMessage());
    }

    @Test
    public void case3() {
        contactUsPage.selectSubject(Subject.WEBMASTER);
        contactUsPage.typeInEmailField(Resources.correctEmail);
        contactUsPage.pressSendButton();

        Assertions.assertEquals("The message cannot be blank.", contactUsPage.getInfoMessage());
    }

    @Test
    public void case4() {
        contactUsPage.selectSubject(Subject.WEBMASTER);
        contactUsPage.typeInMessageField("Hello!");
        contactUsPage.pressSendButton();

        Assertions.assertEquals("Invalid email address.", contactUsPage.getInfoMessage());
    }

    @Test
    public void case5() {
        contactUsPage.typeInEmailField(Resources.correctEmail);
        contactUsPage.typeInMessageField("Hello!");
        contactUsPage.pressSendButton();

        Assertions.assertEquals("Please select a subject from the list provided.", contactUsPage.getInfoMessage());
    }

    @Test
    public void case6() {
        contactUsPage.typeInEmailField(Resources.incorrectEmail);
        Assertions.assertTrue(contactUsPage.isEmailFieldShowError());
    }

    @Test
    public void case7() {
        contactUsPage.pressSendButton();
        Assertions.assertEquals("Invalid email address.", contactUsPage.getInfoMessage());
    }

    @Test
    public void case8() {
        contactUsPage.selectSubject(Subject.WEBMASTER);
        contactUsPage.typeInEmailField(Resources.correctEmail);
        contactUsPage.typeInMessageField("   ");
        contactUsPage.pressSendButton();

        Assertions.assertEquals("The message cannot be blank.", contactUsPage.getInfoMessage());
    }

    @Test
    public void case9() {
        contactUsPage.selectSubject(Subject.WEBMASTER);
        contactUsPage.typeInEmailField(Resources.correctEmail);
        contactUsPage.typeInOrderReferenceField("5555");
        contactUsPage.typeInMessageField("Hello!");
        contactUsPage.uploadFile("C:\\Users\\user\\Desktop\\Test-Suits\\Тест-Сьют - Тестирование формы регистрации.rtf");
        contactUsPage.pressSendButton();
        contactUsPage.pressHomeButton();

        Assertions.assertEquals("http://automationpractice.com/index.php", driver.getCurrentUrl());
    }

    @Test
    public void case10() {
        contactUsPage.selectSubject(Subject.CUSTOMER_SERVICE);
        Assertions.assertEquals("For any question about a product, an order", contactUsPage.getInfoAboutSubject());

        contactUsPage.selectSubject(Subject.WEBMASTER);
        Assertions.assertEquals("If a technical problem occurs on this website", contactUsPage.getInfoAboutSubject());

        contactUsPage.selectSubject(Subject.CHOOSE);
        Assertions.assertFalse(contactUsPage.isInfoAboutSubjectExist());
    }

    @AfterAll
    public static void exit() {
        driver.quit();
    }
}
