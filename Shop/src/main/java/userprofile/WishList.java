package userprofile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WishList {
    private WebDriver driver;

    public WishList(WebDriver driver) {
        this.driver = driver;
    }

    private By nameWishListField = By.id("name");
    private By saveButton = By.id("submitWishlist");
    private By deleteWishListButton = By.xpath("//a[@class='icon']");

    public void typeInNameWishListField(String nameGoods) {
        driver.findElement(nameWishListField).sendKeys(nameGoods);
    }

    public void pressSaveButton() {
        driver.findElement(saveButton).click();
    }
    //доработать
    public void pressDeleteGoodsButton() {
        driver.findElement(deleteWishListButton).click();
    }
}
