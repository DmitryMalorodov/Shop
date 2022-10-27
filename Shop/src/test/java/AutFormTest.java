import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import resources.Resources;
import signinpages.RecoveryPasswordPage;
import signinpages.SignInPage;
import mainpage.MainPage;

import java.time.Duration;

public class AutFormTest {
    private static WebDriver driver;
    private static MainPage mainPage;
    private static SignInPage signInPage;
    private static RecoveryPasswordPage recoveryPasswordPage;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", Resources.chromeDriverPath);

        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        signInPage = new SignInPage(driver);
        recoveryPasswordPage = new RecoveryPasswordPage(driver);

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
        signInPage.typeInEmailAutField(Resources.correctEmail);
        signInPage.typeInPasswordField(Resources.correctPassword);
        signInPage.pressSignInButton();
        Assertions.assertTrue(mainPage.isSignOutButtonExist());
    }

    @Test
    public void case2() {
        signInPage.typeInEmailAutField(Resources.correctEmail);
        signInPage.typeInPasswordField(Resources.incorrectPassword);
        signInPage.pressSignInButton();
        Assertions.assertEquals("Invalid password.", signInPage.getErrorMessage());
    }

    @Test
    public void case3() {
        signInPage.typeInEmailAutField(Resources.incorrectEmail);
        signInPage.typeInPasswordField(Resources.correctPassword);
        signInPage.pressSignInButton();
        Assertions.assertEquals( "Invalid email address.", signInPage.getErrorMessage());
    }

    @Test
    public void case4() {
        signInPage.typeInEmailAutField(Resources.incorrectEmail);
        signInPage.typeInPasswordField(Resources.incorrectPassword);
        signInPage.pressSignInButton();
        Assertions.assertEquals( "Invalid email address.", signInPage.getErrorMessage());
    }

    @Test
    public void case5() {
        signInPage.pressSignInButton();
        Assertions.assertEquals( "An email address required.", signInPage.getErrorMessage());
    }

    @Test
    public void case6() {
        signInPage.pressForgotPasswordLink();
        recoveryPasswordPage.typeInEmailField(Resources.correctEmail);
        recoveryPasswordPage.pressRetrieveButton();
        Assertions.assertEquals( "A confirmation email has been sent to your address: whenigro@mail.ru", recoveryPasswordPage.getSuccessMessage());
    }

    @Test
    public void case7() {
        signInPage.typeInEmailAutField(Resources.correctEmail);
        signInPage.typeInPasswordField(Resources.correctPassword);
        signInPage.pressSignInButton();
        mainPage.pressSignOutButton();
        Assertions.assertTrue(!mainPage.isSignOutButtonExist());
    }

    @AfterAll
    public static void exit() {
        driver.quit();
    }
}
