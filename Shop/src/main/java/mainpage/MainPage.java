package mainpage;

import cart.CartPage;
import enums.Number;
import goodspage.GoodsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Calendar;
import java.util.List;

public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    private By mainPageButton = By.xpath("//a[@title='My Store']");

    private By signInButton = By.xpath("//a[@class='login']");
    private By signOutButton = By.xpath("//a[@class='logout']");
    private By userProfileButton = By.xpath("//a[@class='account']");
    private By contactUsButton = By.id("contact-link");

    private By searchField = By.xpath("//input[@name='search_query']");
    private By startSearchButton = By.xpath("//form[@id='searchbox']/button");

    private By cartButton = By.xpath("//div[@class='shopping_cart']/a");

    private By listPopularGoods = By.xpath("//ul[@id='homefeatured']/li");

    public void pressMainPageButton() {
        driver.findElement(mainPageButton).click();
    }

    public void pressSignInButton() {
        driver.findElement(signInButton).click();
    }

    public void pressSignOutButton() {
        driver.findElement(signOutButton).click();
    }

    public boolean isSignOutButtonExist() {
        if (driver.findElements(signOutButton).size() == 0) return false;
        else return true;
    }

    public void pressUserProfileButton() {
        driver.findElement(userProfileButton).click();
    }

    public void pressContactUsButton() {
        driver.findElement(contactUsButton).click();
    }

    public void typeInSearchField(String searchQuery) {
        driver.findElement(searchField).clear();
        driver.findElement(searchField).sendKeys(searchQuery);
    }

    public void pressStartSearchButton() {
        driver.findElement(startSearchButton).click();
    }

    public void pressCartButton() {
        driver.findElement(cartButton).click();
    }

    //метод добавляет в корзину товары из популярных. Для выбора одного товара вводить в параметр - 0, для двух товаров - 1 и т д
    public void selectGoods(Number amountOfGoods) {
        List<WebElement> allGoods;
        GoodsPage goodsPage = new GoodsPage(driver, new WebDriverWait(driver, Duration.ofSeconds(10)));

        for (int i = 0; i <= amountOfGoods.getNumber(); i++) {
            allGoods = driver.findElements(listPopularGoods);
            allGoods.get(i).click();
            goodsPage.pressAddGoodsButton();
            GoodsPage.PopUpAddGoodsInfo.pressCloseInfoWindowButton();
            pressMainPageButton();
        }
    }

    //Метод нажимает на выбранный товар на главной странице. Вводить в параметр число от 0 до 6 включительно!
    public void chooseOneGoods(int numberOfGoods) {
        List<WebElement> allGoods = driver.findElements(listPopularGoods);
        allGoods.get(numberOfGoods).click();
    }

    //метод выбирает один товар и оформляет заказ
    public void makeRandomOrder() {
        pressMainPageButton();
        selectGoods(Number.NULL);
        pressCartButton();

        CartPage cartPage = new CartPage(driver, new WebDriverWait(driver, Duration.ofSeconds(10)));
        CartPage.Summary summary = cartPage.new Summary();
        CartPage.Address address = cartPage.new Address();
        CartPage.Shipping shipping = cartPage.new Shipping();
        CartPage.Payment payment = cartPage.new Payment();

        summary.pressProceed();
        address.selectBillAddressCheckBox();
        address.pressProceed();
        shipping.selectMyDeliveryRadioButton();
        shipping.selectAgreementCheckBox();
        shipping.pressProceed();
        payment.pressPayByBankWireLink();
        payment.pressConfirmOrderButton();
    }

    public static void waiting(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
