import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import resources.Resources;
import signinpages.RegForm;
import signinpages.SignInPage;
import mainpage.MainPage;

import java.time.Duration;

public class RegFormTest {
    private static WebDriver driver;
    private static MainPage mainPage;
    private static SignInPage signInPage;
    private static RegForm regForm;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", Resources.chromeDriverPath);

        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        signInPage = new SignInPage(driver);
        regForm = new RegForm(driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get(Resources.mainPageUrl);
    }

    @BeforeEach
    public void precondition() {
        if (mainPage.isSignOutButtonExist()) {
            mainPage.pressSignOutButton();
        }
        mainPage.pressSignInButton();
    }

    @Test
    public void case1() {
        //ввести адрес неиспользованный ранее при регистрации на данном сайте
        signInPage.typeInEmailRegField(Resources.correctEmail);
        signInPage.pressCreateAccountButton();
        Assertions.assertFalse(signInPage.isErrorExist(), "Некорректный или уже использованный email");

        regForm.typeInFirstNameField("Dmitriy");
        regForm.typeInLastNameField("Ivanov");
        regForm.typeInPasswordField("12345");
        regForm.typeInAddressField("Address");
        regForm.typeInCityField("LA");
        regForm.chooseState("California");
        regForm.typeInPostalCodeField("55555");
        regForm.typeInPhoneField("123456789");
        regForm.pressRegButton();

        Assertions.assertTrue(mainPage.isSignOutButtonExist());
    }

    @Test
    public void case2() {
        signInPage.typeInEmailRegField(Resources.correctEmail);
        signInPage.pressCreateAccountButton();
        Assertions.assertEquals( "An account using this email address has already been registered." +
                " Please enter a valid password or request a new one.", signInPage.getErrorMessage());
    }

    @Test
    public void case3() {
        signInPage.typeInEmailRegField(Resources.incorrectEmail);
        signInPage.pressCreateAccountButton();
        Assertions.assertEquals("Invalid email address.", signInPage.getErrorMessage());
    }

    @Test
    public void case4() {
        signInPage.pressCreateAccountButton();
        Assertions.assertEquals("Invalid email address.", signInPage.getErrorMessage());
    }

    @Test
    public void case5() {
        signInPage.typeInEmailRegField(Resources.correctEmailWithoutAcc);
        signInPage.pressCreateAccountButton();
        regForm.pressRegButton();
        Assertions.assertEquals(8, regForm.getElementsWithErrors().size(), "Неверное количество сообщений об ошибках");
    }

    @Test
    public void case6() {
        signInPage.typeInEmailRegField(Resources.correctEmailWithoutAcc);
        signInPage.pressCreateAccountButton();

        regForm.typeInLastNameField("Ivanov");
        regForm.typeInPhoneField("123456789");
        regForm.typeInPostalCodeField("55555");
        regForm.chooseState("California");
        regForm.pressRegButton();

        Assertions.assertEquals(4, regForm.getElementsWithErrors().size(), "Неверное количество сообщений об ошибках");
    }

    @Test
    public void case7() {
        signInPage.typeInEmailRegField(Resources.correctEmailWithoutAcc);
        signInPage.pressCreateAccountButton();
        Assertions.assertFalse(signInPage.isErrorExist(), "Некорректный или уже использованный email");

        regForm.typeInFirstNameField("Dmitriy");
        regForm.typeInLastNameField("Ivanov");
        regForm.typeInPasswordField("123");
        regForm.typeInAddressField("Address");
        regForm.typeInCityField("LA");
        regForm.chooseState("California");
        regForm.typeInPostalCodeField("55555");
        regForm.typeInPhoneField("123456789");
        regForm.pressRegButton();

        Assertions.assertEquals("passwd is invalid.", signInPage.getErrorMessage());
    }

    @AfterAll
    public static void exit() {
        driver.quit();
    }
}
