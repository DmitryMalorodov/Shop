import goodspage.GoodsPage;
import mainpage.MainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import resources.Resources;
import signinpages.SignInPage;
import userprofile.MyAddresses;
import userprofile.UserProfile;
import userprofile.WishList;

import java.time.Duration;

public class WishListPageTest {
    private static WebDriver driver;
    private static WishList wishList;
    private static MainPage mainPage;
    private static UserProfile userProfile;
    private static SignInPage signInPage;
    private static GoodsPage goodsPage;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", Resources.chromeDriverPath);

        driver = new ChromeDriver();
        wishList = new WishList(driver);
        mainPage = new MainPage(driver);
        userProfile = new UserProfile(driver);
        signInPage = new SignInPage(driver);
        goodsPage = new GoodsPage(driver, new WebDriverWait(driver, Duration.ofSeconds(10)));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        //предусловия к тест-сьюту
        driver.get(Resources.mainPageUrl);
        mainPage.pressSignInButton();
        signInPage.typeInEmailAutField(Resources.correctEmail);
        signInPage.typeInPasswordField(Resources.correctPassword);
        signInPage.pressSignInButton();
        userProfile.pressWishListLink();
    }

    @BeforeEach
    public void precondition() {
        if (wishList.getCountWishLists() < 1) {
            wishList.typeInNameField("New");
            wishList.pressSaveWishListButton();
        } else if (wishList.getCountWishLists() > 1) {
            while (wishList.getCountWishLists() > 1) {
                wishList.pressDeleteWishListButton();
                wishList.acceptAlert();
                MainPage.waiting(5000);
            }
        }
        wishList.makeQuantityGoodsEquals_1();
    }

    @Test
    public void case1() {
        wishList.pressDeleteWishListButton();
        wishList.acceptAlert();

        wishList.typeInNameField("New");
        wishList.pressSaveWishListButton();
        Assertions.assertEquals(1, wishList.getCountWishLists());
        Assertions.assertTrue(wishList.isCorrectDateCreatedList());

        mainPage.pressMainPageButton();
        mainPage.chooseOneGoods(0);
        goodsPage.pressAddToWishlistButton();
        mainPage.pressUserProfileButton();
        userProfile.pressWishListLink();
        Assertions.assertEquals("1", wishList.getQuantityGoods());
    }

    @Test
    public void case2() {
        driver.navigate().refresh();
        wishList.pressNameLink();
        Assertions.assertTrue(wishList.isInfoWindowShowed());

        wishList.pressCloseInfoWindowButton();
        MainPage.waiting(1000);
        Assertions.assertFalse(wishList.isInfoWindowShowed());
    }

    @Test
    public void case3() {
        driver.navigate().refresh();
        wishList.pressViewLink();
        Assertions.assertTrue(wishList.isInfoWindowShowed());

        wishList.pressCloseInfoWindowButton();
        MainPage.waiting(1000);
        Assertions.assertFalse(wishList.isInfoWindowShowed());
    }

    @AfterAll
    public static void exit() {
        driver.quit();
    }
}
