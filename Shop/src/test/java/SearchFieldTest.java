import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import resources.Resources;
import searchpage.SearchPage;
import mainpage.MainPage;

import java.time.Duration;

public class SearchFieldTest {
    private static WebDriver driver;
    private static MainPage mainPage;
    private static SearchPage searchPage;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", Resources.chromeDriverPath);

        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        searchPage = new SearchPage(driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get(Resources.mainPageUrl);
    }

    @Test
    public void case1() {
        mainPage.typeInSearchField("Dress");
        mainPage.pressStartSearchButton();
        Assertions.assertTrue(searchPage.isGoodsExist());
    }

    @Test
    public void case2() {
        mainPage.typeInSearchField("Faded comfortable ");
        mainPage.pressStartSearchButton();
        Assertions.assertTrue(searchPage.isGoodsExist());
    }

    @Test
    public void case3() {
        mainPage.typeInSearchField("Printed Chiffon Deep v-neckline");
        mainPage.pressStartSearchButton();
        Assertions.assertTrue(searchPage.isGoodsExist());
    }

    @Test
    public void case4() {
        mainPage.typeInSearchField("Printed Chiffon Dress Printed chiffon knee length dress with tank straps. Deep v-neckline.");
        mainPage.pressStartSearchButton();
        Assertions.assertTrue(searchPage.isGoodsExist());
    }

    @Test
    public void case5() {
        mainPage.typeInSearchField("Привет");
        mainPage.pressStartSearchButton();
        Assertions.assertEquals("No results were found for your search \"Привет\"", searchPage.getErrorMessage());
    }

    @Test
    public void case6() {
        mainPage.typeInSearchField("");
        mainPage.pressStartSearchButton();
        Assertions.assertEquals("Please enter a search keyword", searchPage.getErrorMessage());
    }

    @Test
    public void case7() {
        mainPage.typeInSearchField("   ");
        mainPage.pressStartSearchButton();
        Assertions.assertEquals("No results were found for your search \" \"", searchPage.getErrorMessage());
    }

    @AfterAll
    public static void exit() {
        driver.quit();
    }
}
