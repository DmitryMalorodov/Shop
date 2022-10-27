import cart.CartPage;
import enums.Number;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import resources.Resources;
import signinpages.SignInPage;
import mainpage.MainPage;

import java.time.Duration;

public class CartPageTest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static MainPage mainPage;
    private static SignInPage signInPage;

    private static CartPage cartPage;
    private static CartPage.Summary summary;
    private static CartPage.Address address;
    private static CartPage.Shipping shipping;
    private static CartPage.Payment payment;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", Resources.chromeDriverPath);

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        mainPage = new MainPage(driver);
        signInPage = new SignInPage(driver);

        cartPage = new CartPage(driver, wait);
        summary = cartPage.new Summary();
        address = cartPage.new Address();
        shipping = cartPage.new Shipping();
        payment = cartPage.new Payment();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get(Resources.mainPageUrl);

        mainPage.pressSignInButton();
        signInPage.typeInEmailAutField(Resources.correctEmail);
        signInPage.typeInPasswordField(Resources.correctPassword);
        signInPage.pressSignInButton();
        mainPage.pressMainPageButton();
        mainPage.selectGoods(Number.ONE);
    }

    @Test
    public void case1() {
        Assertions.assertEquals(1, cartPage.getAmountGoodsInCartButton());
    }

    @Test
    public void case2() {
        mainPage.pressCartButton();
        summary.pressDeleteGoodsButton();
        Assertions.assertEquals("Your shopping cart is empty.", summary.getErrorMessage());

        mainPage.pressMainPageButton();
        mainPage.selectGoods(Number.ONE);
    }

    @Test
    public void case3() {
        mainPage.pressCartButton();
        Assertions.assertTrue(summary.isSuccessPressPlusOneGoodsButton());
        Assertions.assertTrue(summary.isSuccessPressMinusOneGoodsButton());
    }

    @Test
    public void case4() {
        mainPage.pressCartButton();
        Assertions.assertTrue(summary.isTotalPriceEqualsUnitPrice("3"));
    }

    @Test
    public void case5() {
        mainPage.pressCartButton();
        summary.pressNameGoodsButton();
        Assertions.assertTrue(driver.getCurrentUrl().contains("http://automationpractice.com/index.php?id_product="));
    }

    @Test
    public void case6() {
        mainPage.pressCartButton();
        summary.pressPhotoGoods();
        Assertions.assertTrue(driver.getCurrentUrl().contains("http://automationpractice.com/index.php?id_product="));
    }

    @Test
    public void case7() {
        mainPage.pressCartButton();
        summary.pressProceed();

        address.selectBillAddressCheckBox();
        address.pressProceed();

        shipping.selectMyDeliveryRadioButton();
        shipping.selectAgreementCheckBox();
        shipping.pressProceed();

        payment.pressPayByBankWireLink();
        payment.pressConfirmOrderButton();
        Assertions.assertEquals("Your order on My Store is complete.", payment.getSuccessMessage());

        mainPage.pressMainPageButton();
        mainPage.selectGoods(Number.ONE);
    }

    @Test
    public void case8() {
        mainPage.pressCartButton();
        summary.pressProceed();

        address.selectBillAddressCheckBox();
        address.pressProceed();

        shipping.selectMyDeliveryRadioButton();
        shipping.selectAgreementCheckBox();
        shipping.pressProceed();

        payment.pressPayByCheckLink();
        payment.pressConfirmOrderButton();
        Assertions.assertEquals("Your order on My Store is complete.", payment.getSuccessMessage());

        mainPage.pressMainPageButton();
        mainPage.selectGoods(Number.ONE);
    }

    @Test
    public void case9() {
        mainPage.pressCartButton();
        summary.pressProceed();

        address.selectBillAddressCheckBox();
        address.pressProceed();

        shipping.selectMyDeliveryRadioButton();
        shipping.unselectAgreementCheckBox();
        shipping.pressProceed();
        Assertions.assertEquals("You must agree to the terms of service before continuing.", shipping.getErrorMessage());
    }

    @Test
    public void case10() {
        mainPage.pressCartButton();
        summary.pressProceed();

        address.selectBillAddressCheckBox();
        address.pressProceed();

        shipping.pressTermsLink();
        shipping.pressCloseTermsWindowButton();
    }

    @Test
    public void case11() {
        mainPage.pressCartButton();
        summary.pressProceed();

        address.unselectBillAddressCheckBox();
        Assertions.assertTrue(address.isNewAddressButtonExist());

        address.selectBillAddressCheckBox();
        Assertions.assertFalse(address.isNewAddressButtonNotExist());
    }

    @AfterAll
    public static void exit() {
        driver.quit();
    }
}
