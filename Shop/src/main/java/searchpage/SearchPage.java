package searchpage;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class SearchPage {
    private WebDriver driver;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
    }

    private By listGoods = By.xpath("//div[@class='product-container']");
    private By errorMessage = By.xpath("//p[@class='alert alert-warning']");

    public boolean isGoodsExist() {
        try {
            return driver.findElement(listGoods).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
}
