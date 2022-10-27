import cart.CartPage;
import enums.Size;
import goodspage.GoodsPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import mainpage.MainPage;
import resources.Resources;
import signinpages.SignInPage;

import java.io.IOException;
import java.time.Duration;

public class GoodsPageTest {
    private static WebDriver driver;
    private static MainPage mainPage;
    private static GoodsPage goodsPage;
    private static CartPage cartPage;
    private static WebDriverWait wait;
    private static CartPage.Summary summary;
    private static SignInPage signInPage;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", Resources.chromeDriverPath);

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        mainPage = new MainPage(driver);
        goodsPage = new GoodsPage(driver, wait);
        cartPage = new CartPage(driver, wait);
        summary = cartPage.new Summary();
        signInPage = new SignInPage(driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get(Resources.mainPageUrl);
    }

    @BeforeEach
    public void precondition() {
        mainPage.pressMainPageButton();
        mainPage.chooseOneGoods((int) (Math.random()*7));
    }

    @Test
    public void case1() {
        Assertions.assertTrue(goodsPage.isSuccessPressPlusButton());
        Assertions.assertTrue(goodsPage.isSuccessPressMinusButton());
    }

    @Test
    public void case2() {
        if (cartPage.getAmountGoodsInCartButton() != 0) {
            mainPage.pressCartButton();
            summary.deleteAllGoods();
        }

        goodsPage.typeInQuantityField("5");
        goodsPage.pressAddGoodsButton();
        GoodsPage.PopUpAddGoodsInfo.pressCloseInfoWindowButton();
        Assertions.assertTrue(cartPage.getAmountGoodsInCartButton() == 5);
    }

    @Test
    public void case3() {
        goodsPage.pressAddGoodsButton();
        GoodsPage.PopUpAddGoodsInfo.pressContinueShoppingButton();
        Assertions.assertFalse(GoodsPage.PopUpAddGoodsInfo.isInfoAddPopUpExist());
    }

    @Test
    public void case4() {
        goodsPage.pressAddGoodsButton();
        GoodsPage.PopUpAddGoodsInfo.pressProceedToCheckoutButton();
        Assertions.assertEquals("http://automationpractice.com/index.php?controller=order", driver.getCurrentUrl());
    }

    @Test
    public void case5() {
        goodsPage.typeInQuantityField("0");
        goodsPage.pressAddGoodsButton();
        Assertions.assertEquals("Null quantity.", goodsPage.getInfoMessageAndClose());
    }

    @Test
    public void case6() {
        goodsPage.typeInQuantityField("Hello");
        goodsPage.pressAddGoodsButton();
        Assertions.assertEquals("Null quantity.", goodsPage.getInfoMessageAndClose());
    }

    @Test
    public void case7() {
        goodsPage.typeInQuantityField("5");
        goodsPage.setSize(Size.L);
        String color = goodsPage.setRandomColor();
        goodsPage.pressAddGoodsButton();

        Assertions.assertEquals(color + ", L, 5", GoodsPage.PopUpAddGoodsInfo.getColorSizeQuantity());
        GoodsPage.PopUpAddGoodsInfo.pressCloseInfoWindowButton();
    }

    @Test
    public void case8() {
        if (mainPage.isSignOutButtonExist()) mainPage.pressSignOutButton();

        goodsPage.pressAddToWishlistButton();
        Assertions.assertEquals("You must be logged in to manage your wishlist.", goodsPage.getInfoMessageAndClose());
    }

    @Test
    public void case9() {
        mainPage.pressSignInButton();
        signInPage.typeInEmailAutField(Resources.correctEmail);
        signInPage.typeInPasswordField(Resources.correctPassword);
        signInPage.pressSignInButton();

        mainPage.pressMainPageButton();
        mainPage.chooseOneGoods((int) (Math.random()*7));

        goodsPage.pressAddToWishlistButton();
        Assertions.assertEquals("Added to your wishlist.", goodsPage.getInfoMessageAndClose());

        mainPage.pressSignOutButton();
    }

    @Test
    public void case10() {
        goodsPage.pressSendToFriendButton();
        GoodsPage.SendToFriend.typeInFriendNameField("Dima");
        GoodsPage.SendToFriend.typeInFriendEmailField(Resources.correctEmail);
        GoodsPage.SendToFriend.pressSendButton();
        Assertions.assertEquals("Your e-mail has been sent successfully", GoodsPage.SendToFriend.getMessageAndClose());
    }

    @Test
    public void case11() {
        mainPage.pressSignInButton();
        signInPage.typeInEmailAutField(Resources.correctEmail);
        signInPage.typeInPasswordField(Resources.correctPassword);
        signInPage.pressSignInButton();

        mainPage.pressMainPageButton();
        mainPage.chooseOneGoods((int) (Math.random()*7));

        goodsPage.pressWriteReviewButton();
        GoodsPage.WriteReview.typeInTitleField("Super");
        GoodsPage.WriteReview.typeInCommentField("Wonderful");
        GoodsPage.WriteReview.pressSendMessageButton();
        Assertions.assertEquals("Your comment has been added and will be available once approved by a moderator",
                GoodsPage.WriteReview.getMessageAndClose());

        mainPage.pressSignOutButton();
    }

    @Test
    public void case12() {
        Assertions.assertTrue(goodsPage.isTwitterLinkWorkCorrect());
        Assertions.assertTrue(goodsPage.isFbLinkWorkCorrect());
        Assertions.assertTrue(goodsPage.isGoogleLinkWorkCorrect());
        Assertions.assertTrue(goodsPage.isPinterestLinkWorkCorrect());
    }

    @Test
    public void case13() {
        while (goodsPage.getCountColors() < 2) {
            mainPage.pressMainPageButton();
            mainPage.chooseOneGoods((int) (Math.random()*7));
        }

        int countPhotosBefore = goodsPage.getCountDisplayedPhotos();
        goodsPage.setRandomColor();
        int countPhotosAfter = goodsPage.getCountDisplayedPhotos();

        Assertions.assertTrue(countPhotosBefore > countPhotosAfter);
    }

    @Test
    public void case14() {
        while (goodsPage.getCountColors() < 2) {
            mainPage.pressMainPageButton();
            mainPage.chooseOneGoods((int) (Math.random()*7));
        }

        int countPhotosBefore = goodsPage.getCountDisplayedPhotos();
        goodsPage.setRandomColor();
        goodsPage.pressDisplayAllPhotosButton();
        int countPhotosAfter = goodsPage.getCountDisplayedPhotos();

        Assertions.assertTrue(countPhotosBefore == countPhotosAfter);
    }

    @Test
    public void case15() {
        goodsPage.pressMainPhotoElement();
        Assertions.assertTrue(goodsPage.isBigSizePhotoElementExist());
        goodsPage.pressCloseBigSizePhotoButton();
    }

    @Test
    public void case16() {
        goodsPage.pressOnRandomPhoto();
        Assertions.assertTrue(goodsPage.isBigSizePhotoElementExist());
        goodsPage.pressCloseBigSizePhotoButton();
    }

    @Test
    public void case17() {
        Assertions.assertTrue(goodsPage.isEveryPhotoDisplayed());
    }

    @Test
    public void case18() {
        Assertions.assertTrue(goodsPage.chooseRandomPhotoAndCompare());

        mainPage.pressMainPageButton();
        mainPage.chooseOneGoods((int) (Math.random()*7));
        Assertions.assertTrue(goodsPage.chooseRandomPhotoAndCompare());

        mainPage.pressMainPageButton();
        mainPage.chooseOneGoods((int) (Math.random()*7));
        Assertions.assertTrue(goodsPage.chooseRandomPhotoAndCompare());
    }

    @AfterAll
    public static void exit() {
        driver.quit();
    }
}
