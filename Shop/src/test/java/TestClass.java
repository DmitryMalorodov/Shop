import cart.CartPage;
import contactus.ContactUsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import resources.Resources;
import signinpages.RegForm;
import signinpages.SignInPage;
import mainpage.MainPage;
import userprofile.MyAddresses;
import userprofile.UserProfile;
import userprofile.WishList;

import java.time.Duration;

public class TestClass {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", Resources.chromeDriverPath);
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        MainPage mainPage = new MainPage(driver);
        SignInPage signInPage = new SignInPage(driver);
        RegForm regForm = new RegForm(driver);
        UserProfile userProfile = new UserProfile(driver);
        WishList wishList = new WishList(driver);
        ContactUsPage contactUsPage = new ContactUsPage(driver);
        MyAddresses myAddresses = new MyAddresses(driver);

        CartPage cartPage = new CartPage(driver, wait);
        CartPage.Summary summary = cartPage.new Summary();
        CartPage.Address address = cartPage.new Address();
        CartPage.Shipping shipping = cartPage.new Shipping();
        CartPage.Payment payment = cartPage.new Payment();

        driver.get(Resources.mainPageUrl);
        mainPage.pressSignInButton();
        signInPage.typeInEmailAutField(Resources.correctEmail);
        signInPage.typeInPasswordField(Resources.correctPassword);
        signInPage.pressSignInButton();
        userProfile.pressWishListLink();

        System.out.println(wishList.getQuantityGoods());
    }

//    public static boolean chooseRandomPhotoAndCompare() {
//        File file = new File("C:\\Users\\user\\Desktop\\Downloads\\169887-gorod-zdanie-purpur-tsvetnoy-liniya_gorizonta-7680x4320.jpg");
//
//        boolean result = true;
//        try {
//            BufferedImage origImage = ImageIO.read(file);
//            BufferedImage imageForCheck = ImageIO.read(new URL("http://inetnovichok.ru/wp-content/uploads/2013/03/kartinka-ssilka.jpg"));
//
//            int columnsOrig = origImage.getHeight();
//            int rowsOrig = origImage.getWidth();
//            int columns = imageForCheck.getHeight();
//            int rows = imageForCheck.getWidth();
//            if (columnsOrig != columns || rowsOrig != rows) return result = false;
//
//            for (int i = 0; i < columnsOrig; i++) {
//                for (int j = 0; j < rowsOrig; j++) {
//                    int rgbOrig = origImage.getRGB(i, j);
//                    int rgbForCheck = imageForCheck.getRGB(i, j);
//                    if (rgbOrig !=(rgbForCheck)) {
//                        result = false;
//                        break;
//                    }
//                }
//                if (result == false) break;
//            }
//
//        } catch (IOException e) {
//            result = false;
//            e.printStackTrace();
//        }
//        return result;
//    }
}
