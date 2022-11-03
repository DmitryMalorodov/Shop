import mainpage.MainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import resources.Resources;
import signinpages.SignInPage;
import userprofile.MyAddresses;
import userprofile.UserProfile;

import java.time.Duration;

public class MyAddressesPageTest {
    private static WebDriver driver;
    private static MainPage mainPage;
    private static MyAddresses myAddresses;
    private static UserProfile userProfile;
    private static SignInPage signInPage;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", Resources.chromeDriverPath);

        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        myAddresses = new MyAddresses(driver);
        userProfile = new UserProfile(driver);
        signInPage = new SignInPage(driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        //предусловия к тест-сьюту
        driver.get(Resources.mainPageUrl);
        mainPage.pressSignInButton();
        signInPage.typeInEmailAutField(Resources.correctEmail);
        signInPage.typeInPasswordField(Resources.correctPassword);
        signInPage.pressSignInButton();
        userProfile.pressMyAddressesLink();
    }

    //если нет карточки адреса, то создаем. Если больше одной, то удаляем пока не останется одна
    @BeforeEach
    public void precondition() {
        if (myAddresses.getCountAddressesCard() < 1) {
            myAddresses.pressAddNewAddressButton();
            MyAddresses.EditAddressForm.addNewAddressCard();
        } else if (myAddresses.getCountAddressesCard() > 1) {
            while (myAddresses.getCountAddressesCard() > 1) {
                myAddresses.pressDeleteButton();
                myAddresses.acceptAlert();
            }
        }
    }

    @Test
    public void case1() {
        myAddresses.pressUpdateButton();
        MyAddresses.EditAddressForm.typeInCityField("Los Angeles");
        MyAddresses.EditAddressForm.typeInPhoneField("555555555");
        MyAddresses.EditAddressForm.pressSaveButton();

        Assertions.assertEquals("Los Angeles", myAddresses.getCityFromCard());
        Assertions.assertEquals("555555555", myAddresses.getPhoneFromCard());
    }

    @Test
    public void case2() {
        myAddresses.pressUpdateButton();
        MyAddresses.EditAddressForm.typeInPostalCodeField("15532");
        MyAddresses.EditAddressForm.typeInAddressField("100 Wall Street");
        MyAddresses.EditAddressForm.pressSaveButton();

        Assertions.assertEquals("15532", myAddresses.getPostalCodeFromCard());
        Assertions.assertEquals("100 Wall Street", myAddresses.getAddressFromCard());
    }

    @Test
    public void case3() {
        myAddresses.pressUpdateButton();
        MyAddresses.EditAddressForm.clearFirstNameField();
        MyAddresses.EditAddressForm.clearLastNameField();
        MyAddresses.EditAddressForm.pressSaveButton();

        Assertions.assertEquals(2, MyAddresses.EditAddressForm.getCountErrorsMessages());
    }

    @Test
    public void case4() {
        myAddresses.pressUpdateButton();
        MyAddresses.EditAddressForm.clearFirstNameField();
        MyAddresses.EditAddressForm.clearLastNameField();
        MyAddresses.EditAddressForm.clearAddressField();
        MyAddresses.EditAddressForm.clearCityField();
        MyAddresses.EditAddressForm.pressSaveButton();

        Assertions.assertEquals(4, MyAddresses.EditAddressForm.getCountErrorsMessages());
    }

    @Test
    public void case5() {
        myAddresses.pressUpdateButton();
        MyAddresses.EditAddressForm.clearFirstNameField();
        MyAddresses.EditAddressForm.clearLastNameField();
        MyAddresses.EditAddressForm.clearAddressField();

        Assertions.assertEquals(3, MyAddresses.EditAddressForm.getCountFieldsWithErrors());
    }

    @Test
    public void case6() {
        int countCardBefore = myAddresses.getCountAddressesCard();
        myAddresses.pressDeleteButton();
        myAddresses.acceptAlert();
        int countCardAfter = myAddresses.getCountAddressesCard();

        Assertions.assertTrue(countCardBefore > countCardAfter);
    }

    @Test
    public void case7() {
        int countCardBefore = myAddresses.getCountAddressesCard();
        myAddresses.pressDeleteButton();
        myAddresses.dismissAlert();
        int countCardAfter = myAddresses.getCountAddressesCard();

        Assertions.assertTrue(countCardBefore == countCardAfter);
    }

    @Test
    public void case8() {
        myAddresses.pressAddNewAddressButton();
        Assertions.assertEquals("http://automationpractice.com/index.php?controller=address", driver.getCurrentUrl());
    }

    @Test
    public void case9() {
        myAddresses.pressDeleteButton();
        myAddresses.acceptAlert();
        Assertions.assertEquals("No addresses are available. Add a new address", myAddresses.getInfoMessage());

        myAddresses.pressAddNewAddressLink();
        Assertions.assertEquals("http://automationpractice.com/index.php?controller=address", driver.getCurrentUrl());
    }

    @Test
    public void case10() {
        myAddresses.pressBackToAccountButton();
        Assertions.assertEquals("http://automationpractice.com/index.php?controller=my-account", driver.getCurrentUrl());
        driver.navigate().back();

        myAddresses.pressHomeButton();
        Assertions.assertEquals("http://automationpractice.com/index.php", driver.getCurrentUrl());
        driver.navigate().back();
    }

    @AfterEach
    public void postCondition() {
        if (!driver.getCurrentUrl().equals("http://automationpractice.com/index.php?controller=addresses")) {
            mainPage.pressUserProfileButton();
            userProfile.pressMyAddressesLink();
        }
    }

    @AfterAll
    public static void exit() {
        driver.quit();
    }
}
