import mainpage.MainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import resources.Resources;
import signinpages.SignInPage;
import userprofile.OrderHistory;
import userprofile.UserProfile;

import java.time.Duration;

public class OrderHistoryPageTest {
    private static WebDriver driver;
    private static MainPage mainPage;
    private static OrderHistory orderHistory;
    private static UserProfile userProfile;
    private static SignInPage signInPage;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", Resources.chromeDriverPath);

        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        orderHistory = new OrderHistory(driver);
        userProfile = new UserProfile(driver);
        signInPage = new SignInPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        //предусловия к тест-сьюту
        driver.get(Resources.mainPageUrl);
        mainPage.pressSignInButton();
        signInPage.typeInEmailAutField(Resources.correctEmail);
        signInPage.typeInPasswordField(Resources.correctPassword);
        signInPage.pressSignInButton();
        userProfile.pressOrderHistoryLink();

        if (OrderHistory.OrderHistoryTable.getCountOrders() < 1) {
            mainPage.makeRandomOrder();
            mainPage.pressUserProfileButton();
            userProfile.pressOrderHistoryLink();
        }
    }

    @Test
    public void case1() {
        int before = OrderHistory.OrderHistoryTable.getCountOrders();
        mainPage.makeRandomOrder();
        mainPage.pressUserProfileButton();
        userProfile.pressOrderHistoryLink();
        int after = OrderHistory.OrderHistoryTable.getCountOrders();

        Assertions.assertTrue(++before == after);
    }

    @Test
    public void case2() {
        Assertions.assertTrue(OrderHistory.OrderHistoryTable.isFileDownloaded());
    }

    @Test
    public void case3() {
        //обновить страницу чтобы выполнить предусловие кейса (подробная инфомация о заказе скрыта)
        driver.navigate().refresh();

        String nameOrder = OrderHistory.OrderHistoryTable.pressRandomOrderReferenceAndGetName();
        Assertions.assertTrue(OrderHistory.OrderInfo.isInfoShowedCorrect(nameOrder));
    }

    @Test
    public void case4() {
        //обновить страницу чтобы выполнить предусловие кейса (подробная инфомация о заказе скрыта)
        driver.navigate().refresh();

        String nameOrder = OrderHistory.OrderHistoryTable.pressRandomDetailsButtonAndGetName();
        Assertions.assertTrue(OrderHistory.OrderInfo.isInfoShowedCorrect(nameOrder));
    }

    @Test
    public void case5() {
        OrderHistory.OrderHistoryTable.pressRandomReorderLink();
        Assertions.assertEquals("http://automationpractice.com/index.php?controller=order", driver.getCurrentUrl());
        driver.navigate().back();
    }

    @Test
    public void case6() {
        Assertions.assertTrue(OrderHistory.OrderHistoryTable.isDateDownSort());

        OrderHistory.OrderHistoryTable.pressDateSortButton();
        Assertions.assertTrue(OrderHistory.OrderHistoryTable.isDateUpSort());

        OrderHistory.OrderHistoryTable.pressPriceSortButton();
        OrderHistory.OrderHistoryTable.isPriceUpSort();

        OrderHistory.OrderHistoryTable.pressPriceSortButton();
        OrderHistory.OrderHistoryTable.isPriceDownSort();
    }

    @Test
    public void case7() {
        OrderHistory.OrderHistoryTable.pressBackToAccountButton();
        Assertions.assertEquals("http://automationpractice.com/index.php?controller=my-account", driver.getCurrentUrl());

        driver.navigate().back();

        OrderHistory.OrderHistoryTable.pressHomeButton();
        Assertions.assertEquals("http://automationpractice.com/index.php", driver.getCurrentUrl());

        driver.navigate().back();
    }

    @Test
    public void case8() {
        OrderHistory.OrderHistoryTable.pressRandomOrderReferenceAndGetName();
        OrderHistory.OrderInfo.pressReorderButton();
        Assertions.assertEquals("http://automationpractice.com/index.php?controller=order", driver.getCurrentUrl());

        driver.navigate().back();
    }

    @Test
    public void case9() {
        //обновить страницу чтобы выполнить предусловие кейса (подробная инфомация о заказе скрыта)
        driver.navigate().refresh();

        OrderHistory.OrderHistoryTable.pressRandomOrderReferenceAndGetName();
        Assertions.assertTrue(OrderHistory.OrderInfo.isFileDownloaded());
    }

    @Test
    public void case10() {
        //обновить страницу чтобы выполнить предусловие кейса (подробная инфомация о заказе скрыта)
        driver.navigate().refresh();

        OrderHistory.OrderHistoryTable.pressRandomOrderReferenceAndGetName();

        int countCommentBefore = OrderHistory.OrderInfo.getCountComment();
        OrderHistory.OrderInfo.typeInCommentField("Супер!");
        OrderHistory.OrderInfo.pressSendButton();
        int countCommentAfter = OrderHistory.OrderInfo.getCountComment();

        Assertions.assertEquals("Message successfully sent", OrderHistory.OrderInfo.getMessage());
        Assertions.assertTrue(countCommentBefore < countCommentAfter);
    }

    @Test
    public void case11() {
        //обновить страницу чтобы выполнить предусловие кейса (подробная инфомация о заказе скрыта)
        driver.navigate().refresh();

        OrderHistory.OrderHistoryTable.pressRandomOrderReferenceAndGetName();
        OrderHistory.OrderInfo.pressSendButton();

        Assertions.assertEquals("The message cannot be blank.", OrderHistory.OrderInfo.getMessage());
    }

    @Test
    public void case12() {
        //обновить страницу чтобы выполнить предусловие кейса (подробная инфомация о заказе скрыта)
        driver.navigate().refresh();

        OrderHistory.OrderHistoryTable.pressRandomOrderReferenceAndGetName();
        OrderHistory.OrderInfo.typeInCommentField("   ");
        OrderHistory.OrderInfo.pressSendButton();

        Assertions.assertEquals("The message cannot be blank.", OrderHistory.OrderInfo.getMessage());
    }

    @AfterAll
    public static void exit() {
        driver.quit();
    }
}
