package goodspage;

import enums.Size;
import mainpage.MainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PipedReader;
import java.net.URL;
import java.util.List;

public class GoodsPage {
    private static WebDriver driver;
    private static WebDriverWait wait;

    public GoodsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private By addGoodsButton = By.xpath("//button[@class='exclusive']");
    private By plusButton = By.xpath("//p[@id='quantity_wanted_p']/a[2]");
    private By minusButton = By.xpath("//p[@id='quantity_wanted_p']/a[1]");
    private By quantityField = By.id("quantity_wanted");
    private By sizeList = By.id("group_1");
    private By colorList = By.xpath("//ul[@id='color_to_pick_list']//a");
    private By addToWishlistButton = By.id("wishlist_button");
    private By closeInfoPopUp = By.xpath("//a[@title='Close']");
    private By writeReviewButton = By.xpath("//a[@class='open-comment-form']");
    private By sendToFriendButton = By.id("send_friend_button");
    private By infoMessage = By.xpath("//p[@class='fancybox-error']");

    private By twitterLink = By.xpath("//button[@class='btn btn-default btn-twitter']");
    private By fbLink = By.xpath("//button[@class='btn btn-default btn-facebook']");
    private By googleLink = By.xpath("//button[@class='btn btn-default btn-google-plus']");
    private By pinterestLink = By.xpath("//button[@class='btn btn-default btn-pinterest']");

    private By photoList = By.xpath("//ul[@id='thumbs_list_frame']//a");
    private By displayAllPhotosButton = By.id("wrapResetImages");
    private By mainPhotoElement = By.id("bigpic");
    private By bigSizePhotoElement = By.xpath("//img[@class='fancybox-image']");
    private By closeBigSizePhotoButton = By.xpath("//a[@title='Close']");

    public void pressAddGoodsButton() {
        driver.findElement(addGoodsButton).click();
    }

    public void pressPlusButton() {
        driver.findElement(plusButton).click();
    }

    //метод нажимает на элемент страницы "плюс" и проверяет увеличилось ли значение
    public boolean isSuccessPressPlusButton() {
        Integer quantityBeforePlus = getQuantityGoods();
        Integer quantityAfterPlus = getQuantityGoods();
        pressPlusButton();
        for (int i = 0; i < 10; i++) {
            quantityAfterPlus = getQuantityGoods();
            if (quantityAfterPlus > quantityBeforePlus) break;
            MainPage.waiting(1000);
        }
        return quantityAfterPlus > quantityBeforePlus;
    }

    public void pressMinusButton() {
        driver.findElement(minusButton).click();
    }

    //метод нажимает на элемент страницы "минус" и проверяет уменьшилось ли значение
    public boolean isSuccessPressMinusButton() {
        Integer quantityBeforeMinus = getQuantityGoods();
        Integer quantityAfterMinus = getQuantityGoods();
        pressMinusButton();
        for (int i = 0; i < 10; i++) {
            quantityAfterMinus = getQuantityGoods();
            if (quantityAfterMinus < quantityBeforeMinus) break;
            MainPage.waiting(1000);
        }
        return quantityAfterMinus < quantityBeforeMinus;
    }

    private Integer getQuantityGoods() {
        return Integer.parseInt(driver.findElement(quantityField).getAttribute("value"));
    }

    public void typeInQuantityField(String quantity) {
        WebElement element = driver.findElement(quantityField);
        element.clear();
        element.sendKeys(quantity);
    }

    public void setSize(Size size) {
        Select select = new Select(driver.findElement(sizeList));
        select.selectByVisibleText(size.getSize());
    }

    //метод выбирает и устанавливает случайный цвет из доступных вариантов и возвращает название цвета
    public String setRandomColor() {
        List<WebElement> allColors = driver.findElements(colorList);
        WebElement choseColor = allColors.get((int) Math.random() * allColors.size());
        choseColor.click();
        return choseColor.getAttribute("name");
    }

    public int getCountColors() {
        return driver.findElements(colorList).size();
    }

    //метод нажимает на кнопку добавить в список желаний и закрывает появляющийся информационный попап
    public void pressAddToWishlistButton() {
        driver.findElement(addToWishlistButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(closeInfoPopUp));
        driver.findElement(closeInfoPopUp).click();
    }

    public String getInfoMessageAndClose() {
        String result = driver.findElement(infoMessage).getText();
        driver.findElement(By.xpath("//a[@title='Close']")).click();
        return result;
    }

    public void pressSendToFriendButton() {
        driver.findElement(sendToFriendButton).click();
    }

    public void pressWriteReviewButton() {
        driver.findElement(writeReviewButton).click();
    }

    //метод переходит по ссылке на новое окно, переключается между окнами, проверяет корректность ссылки, закрывает новое окно
    public boolean isTwitterLinkWorkCorrect() {
        String windowBefore = driver.getWindowHandle();
        driver.findElement(twitterLink).click();
        for (String w: driver.getWindowHandles()) {
            if (!w.equals(windowBefore)) {
                driver.switchTo().window(w);
            }
        }
        boolean result = driver.getCurrentUrl().contains("twitter.com");

        driver.close();
        driver.switchTo().window(windowBefore);

        return result;
    }

    //метод переходит по ссылке на новое окно, переключается между окнами, проверяет корректность ссылки, закрывает новое окно
    public boolean isFbLinkWorkCorrect() {
        String windowBefore = driver.getWindowHandle();
        driver.findElement(fbLink).click();
        for (String w: driver.getWindowHandles()) {
            if (!w.equals(windowBefore)) {
                driver.switchTo().window(w);
            }
        }
        boolean result = driver.getCurrentUrl().contains("www.facebook.com");

        driver.close();
        driver.switchTo().window(windowBefore);

        return result;
    }

    //метод переходит по ссылке на новое окно, переключается между окнами, проверяет корректность ссылки, закрывает новое окно
    public boolean isGoogleLinkWorkCorrect() {
        String windowBefore = driver.getWindowHandle();
        driver.findElement(googleLink).click();
        for (String w: driver.getWindowHandles()) {
            if (!w.equals(windowBefore)) {
                driver.switchTo().window(w);
            }
        }
        boolean result = driver.getCurrentUrl().contains("accounts.google.com");

        driver.close();
        driver.switchTo().window(windowBefore);

        return result;
    }

    //метод переходит по ссылке на новое окно, переключается между окнами, проверяет корректность ссылки, закрывает новое окно
    public boolean isPinterestLinkWorkCorrect() {
        String windowBefore = driver.getWindowHandle();
        driver.findElement(pinterestLink).click();
        for (String w: driver.getWindowHandles()) {
            if (!w.equals(windowBefore)) {
                driver.switchTo().window(w);
            }
        }
        boolean result = driver.getCurrentUrl().contains("pinterest.com");

        driver.close();
        driver.switchTo().window(windowBefore);

        return result;
    }

    public int getCountDisplayedPhotos() {
        List<WebElement> allPhotos = driver.findElements(photoList);
        int result = 0;

        for (WebElement e: allPhotos) {
            if (e.isDisplayed()) result++;
        }
        return result;
    }

    public void pressDisplayAllPhotosButton() {
        driver.findElement(displayAllPhotosButton).click();
    }

    public void pressMainPhotoElement() {
        driver.findElement(mainPhotoElement).click();
    }

    public boolean isBigSizePhotoElementExist() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(bigSizePhotoElement));
            return driver.findElement(bigSizePhotoElement).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void pressCloseBigSizePhotoButton() {
        driver.findElement(closeBigSizePhotoButton).click();
    }

    public void pressOnRandomPhoto() {
        List<WebElement> allPhotos = driver.findElements(photoList);
        allPhotos.get((int) Math.random() * allPhotos.size()).click();
    }

    //метод наводит мышь на каждое фото и проверяет отображается ли оно в поле главного фото
    public boolean isEveryPhotoDisplayed() {
        List<WebElement> allPhotos = driver.findElements(photoList);
        Actions actions = new Actions(driver);
        boolean result = true;

        for (WebElement e: allPhotos) {
            actions.moveToElement(e).build().perform();
            if (!e.getAttribute("class").equals("fancybox shown")) {
                result = false;
            }
        }
        return result;
    }

    public boolean chooseRandomPhotoAndCompare() {
        //выбираем рандомное фото и нажимаем на него мышью
        List<WebElement> allPhotos = driver.findElements(photoList);
        allPhotos.get((int) Math.random() * allPhotos.size()).click();

        //получили текущий урл страницы и номер раздела товара из него
        String currentUrl = driver.getCurrentUrl();
        String numberOfGoods = String.valueOf(currentUrl.charAt(currentUrl.lastIndexOf("&") - 1));

        //получили урл фото, которое нужно проверить
        String urlPhotoToCheck = driver.findElement(bigSizePhotoElement).getAttribute("src");
        //получили имя товара и из имени его номер
        String namePhoto = urlPhotoToCheck.substring(urlPhotoToCheck.lastIndexOf("/") + 1);
        String numberPhoto = namePhoto.replaceAll("[^0-9]", "");

        //создали путь к исходному файлу на основе полученных номера раздела товара и номера из имени файла
        File file = new File(String.format("C:\\Users\\Photos\\popular\\%s\\%s-thickbox_default.jpg", numberOfGoods, numberPhoto));

        //получаем оригинальное фото с локального диска и фото для сравнения по урлу
        //проводим попиксельное сравнение двух фото
        boolean result = true;
        try {
            BufferedImage origImage = ImageIO.read(file);
            BufferedImage imageForCheck = ImageIO.read(new URL(urlPhotoToCheck));

            int columnsOrig = origImage.getHeight();
            int rowsOrig = origImage.getWidth();
            int columns = imageForCheck.getHeight();
            int rows = imageForCheck.getWidth();
            if (columnsOrig != columns || rowsOrig != rows) return result = false;

            for (int i = 0; i < columnsOrig; i++) {
                for (int j = 0; j < rowsOrig; j++) {
                    int rgbOrig = origImage.getRGB(i, j);
                    int rgbForCheck = imageForCheck.getRGB(i, j);
                    if (rgbOrig != rgbForCheck) {
                        result = false;
                        break;
                    }
                }
                if (result == false) break;
            }

        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        }
        pressCloseBigSizePhotoButton();
        return result;
    }

    public static class PopUpAddGoodsInfo {
        private static By infoAddPopUp = By.id("layer_cart");
        private static By closeInfoWindowButton = By.xpath("//span[@class='cross']");
        private static By continueShoppingButton = By.xpath("//span[@title='Continue shopping']");
        private static By proceedToCheckoutButton = By.xpath("//a[@title='Proceed to checkout']");
        private static By colorAndSizeGoods = By.id("layer_cart_product_attributes");
        private static By quantityGoods = By.id("layer_cart_product_quantity");

        public static void pressCloseInfoWindowButton() {
            driver.findElement(closeInfoWindowButton).click();
        }

        public static void pressContinueShoppingButton() {
            driver.findElement(continueShoppingButton).click();
        }

        public static boolean isInfoAddPopUpExist() {
            MainPage.waiting(1500);
            return driver.findElement(infoAddPopUp).isDisplayed();
        }

        public static void pressProceedToCheckoutButton() {
            driver.findElement(proceedToCheckoutButton).click();
        }

        //метод возвращает установленные у товара цвет, размер, количество
        public static String getColorSizeQuantity() {
            wait.until(ExpectedConditions.visibilityOfElementLocated(colorAndSizeGoods));
            String result = driver.findElement(colorAndSizeGoods).getText();
            return result + ", " + driver.findElement(quantityGoods).getText();
        }
    }

    public static class SendToFriend {
        private static By friendNameField = By.id("friend_name");
        private static By friendEmailField = By.id("friend_email");
        private static By sendButton = By.id("sendEmail");
        private static By message = By.xpath("//div[@class='fancybox-inner']/p[1]");

        public static void typeInFriendNameField(String friendName) {
            driver.findElement(friendNameField).sendKeys(friendName);
        }

        public static void typeInFriendEmailField(String friendEmail) {
            driver.findElement(friendEmailField).sendKeys(friendEmail);
        }

        public static void pressSendButton() {
            driver.findElement(sendButton).click();
        }

        public static String getMessageAndClose() {
            String result = driver.findElement(message).getText();
            driver.findElement(By.xpath("//a[@title='Close']")).click();
            return result;
        }
    }

    public static class WriteReview {
        private static By titleField = By.id("comment_title");
        private static By commentField = By.id("content");
        private static By sendMessageButton = By.id("submitNewMessage");
        private static By message = By.xpath("//div[@class='fancybox-inner']/p[1]");

        public static void typeInTitleField(String title) {
            driver.findElement(titleField).sendKeys(title);
        }

        public static void typeInCommentField(String comment) {
            driver.findElement(commentField).sendKeys(comment);
        }

        public static void pressSendMessageButton() {
            driver.findElement(sendMessageButton).click();
        }

        public static String getMessageAndClose() {
            String result = driver.findElement(message).getText();
            driver.findElement(By.xpath("//a[@title='Close']")).click();
            return result;
        }
    }
}
