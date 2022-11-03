package userprofile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserProfile {
    private WebDriver driver;

    public UserProfile(WebDriver driver) {
        this.driver = driver;
    }

    private By orderHistoryLink = By.xpath("//a[@title='Orders']");
    private By myAddressesLink = By.xpath("//a[@title='Addresses']");
    private By wishListLink = By.xpath("//a[@title='My wishlists']");

    public void pressOrderHistoryLink() {
        driver.findElement(orderHistoryLink).click();
    }

    public void pressMyAddressesLink() {
        driver.findElement(myAddressesLink).click();
    }

    public void pressWishListLink() {
        driver.findElement(wishListLink).click();
    }
}
