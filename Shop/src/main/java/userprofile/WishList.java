package userprofile;

import enums.Priority;
import goodspage.GoodsPage;
import mainpage.MainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class WishList {
    private WebDriver driver;

    public WishList(WebDriver driver) {
        this.driver = driver;
    }

    private By nameField = By.id("name");
    private By saveWishListButton = By.id("submitWishlist");
    private By backToAccountButton = By.xpath("//ul[@class='footer_links clearfix']/li[1]");
    private By homeButton = By.xpath("//ul[@class='footer_links clearfix']/li[2]");

    //элементы таблицы списков желаний
    private By listOfWishLists = By.xpath("//table[@class='table table-bordered']/tbody/tr");
    private By nameLink = By.xpath("//td[@style='width:200px;']/a");
    private By quantityGoods = By.xpath("//td[@class='bold align_center']");
    private By dateCreatedList = By.xpath("//table[@class='table table-bordered']//td[4]");
    private By viewLink = By.xpath("//table[@class='table table-bordered']//td[5]/a");
    private By deleteWishListButton = By.xpath("//td[@class='wishlist_delete']/a");

    //элементы информации о товаре
    private By infoWindow = By.xpath("//div[@class='wishlistLinkTop']");
    private By photoOfGoods = By.xpath("//div[@class='product_image']");
    private By sizeAndColorLink = By.xpath("//p[@id='s_title']//a");
    private By deleteGoodsButton = By.xpath("//a[@title='Delete']");
    private By quantityField = By.xpath("//input[@class='form-control grey']");
    private By dropDownPriority = By.xpath("//select[@class='form-control grey']");
    private By saveButton = By.xpath("//a[@title='Save']");

    //метод проверяет количество карточек товаров, если значение не равно 1, то уравнивает (добавляет или удаляет)
    //далее проверяет указано ли у товара в поле количество значение отличное от 1, если значение отличается, то уравнивает
    public void makeQuantityGoodsEquals_1() {
        MainPage mainPage = new MainPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        GoodsPage goodsPage = new GoodsPage(driver, wait);
        UserProfile userProfile = new UserProfile(driver);

        //если в поле количество указано значение 0, то добавляем один товар
        if (getQuantityGoods().equals("0")) {
            mainPage.pressMainPageButton();

            mainPage.chooseOneGoods(0);
            goodsPage.pressAddToWishlistButton();

            mainPage.pressUserProfileButton();
            userProfile.pressWishListLink();
        }

        pressNameLink();
        wait.until(ExpectedConditions.visibilityOfElementLocated(infoWindow));

        //если карточек товаров больше чем один, то удаляем лишние
        if (getCountGoodsCards() > 1) {
            List<WebElement> allDeleteButtons = driver.findElements(deleteGoodsButton);
            for (int i = 0; i < getCountGoodsCards() - 1; i ++) {
                pressDeleteGoodsButton(allDeleteButtons.get(i));
                allDeleteButtons.remove(i);
                MainPage.waiting(3000);
            }
        }
        //проверяем количество товаров, если не равно 1, то вписываем в поле 1
        if (!getQuantityGoods().equals("1")) {
            driver.navigate().refresh();
            pressNameLink();
            wait.until(ExpectedConditions.visibilityOfElementLocated(infoWindow));

            typeInQuantityField("1");
            pressSaveButton();
        }
    }

    public void typeInNameField(String nameGoods) {
        driver.findElement(nameField).sendKeys(nameGoods);
    }

    public void pressSaveWishListButton() {
        driver.findElement(saveWishListButton).click();
    }

    public void pressBackToAccountButton() {
        driver.findElement(backToAccountButton).click();
    }

    public void pressHomeButton() {
        driver.findElement(homeButton).click();
    }

    public int getCountWishLists() {
        try {
            return driver.findElements(listOfWishLists).size();
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    public void pressNameLink() {
        driver.findElement(nameLink).click();
    }

    public String getQuantityGoods() {
        return driver.findElement(quantityGoods).getText().trim();
    }

    public String getDateCreatedList() {
        return driver.findElement(dateCreatedList).getText().trim();
    }

    public void pressViewLink() {
        driver.findElement(viewLink).click();
    }

    public void pressDeleteWishListButton() {
        driver.findElement(deleteWishListButton).click();
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    public void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    //возвращает количество карточек товаров
    public int getCountGoodsCards() {
        return driver.findElements(photoOfGoods).size();
    }

    public void pressPhotoOfGoods() {
        driver.findElement(photoOfGoods).click();
    }

    public void pressSizeAndColorLink() {
        driver.findElement(sizeAndColorLink).click();
    }

    public void pressDeleteGoodsButton() {
        driver.findElement(deleteGoodsButton).click();
    }

    private void pressDeleteGoodsButton(WebElement element) {
        element.click();
    }

    public void typeInQuantityField(String quantity) {
        WebElement element = driver.findElement(quantityField);
        element.clear();
        element.sendKeys(quantity);
    }

    public void selectPriority(Priority priority) {
        Select select = new Select(driver.findElement(dropDownPriority));
        select.selectByVisibleText(priority.getPriority());
    }

    public void pressSaveButton() {
        driver.findElement(saveButton).click();
    }
}
