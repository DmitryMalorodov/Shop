package cart;

import mainpage.MainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CartPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private By amountGoodsInCartButton = By.xpath("//a[@title='View my shopping cart']/span[1]");

    public Integer getAmountGoodsInCartButton() {
        WebElement element = driver.findElement(amountGoodsInCartButton);
        if (element.getText().equals("")) return 0;
        else return Integer.parseInt(element.getText());
    }

    public class Summary {
        private By plusOneGoodsButton = By.xpath("//a[@title='Add']");
        private By minusOneGoodsButton = By.xpath("//a[@title='Subtract']");
        private By deleteGoodsButton = By.xpath("//a[@title='Delete']");
        private By proceed = By.xpath("//p[@class='cart_navigation clearfix']/a[@title='Proceed to checkout']");
        private By nameGoodsButton = By.xpath("//td[@class='cart_description']/p");
        private By photoGoods = By.xpath("//td[@class='cart_product']");

        private By errorMessage = By.xpath("//p[@class='alert alert-warning']");
        private By amountGoods = By.xpath("//input[@class='cart_quantity_input form-control grey']");
        private By totalPrice = By.xpath("//td[@class='cart_total']/span");
        private By unitPrice = By.xpath("//td[@class='cart_unit']/span");

        //метод нажимает на элемент страницы "плюс" и проверяет увеличилось ли значение
        public boolean isSuccessPressPlusOneGoodsButton() {
            Integer amountBeforePlus = getAmountGoods();
            Integer amountAfterPlus = getAmountGoods();
            driver.findElement(plusOneGoodsButton).click();
            for (int i = 0; i < 20; i++) {
                amountAfterPlus = getAmountGoods();
                if (amountAfterPlus > amountBeforePlus) break;
                MainPage.waiting(1000);
            }
            return amountAfterPlus > amountBeforePlus;
        }

        //метод нажимает на элемент страницы "минус" и проверяет уменьшилось ли значение
        public boolean isSuccessPressMinusOneGoodsButton() {
            Integer amountBeforeMinus = getAmountGoods();
            Integer amountAfterMinus = getAmountGoods();
            driver.findElement(minusOneGoodsButton).click();
            for (int i = 0; i < 20; i++) {
                amountAfterMinus = getAmountGoods();
                if (amountAfterMinus < amountBeforeMinus) break;
                MainPage.waiting(1000);
            }
            return amountAfterMinus < amountBeforeMinus;
        }

        public void pressDeleteGoodsButton() {
            driver.findElement(deleteGoodsButton).click();
        }

        public void deleteAllGoods() {
            List<WebElement> elements = driver.findElements(deleteGoodsButton);
            for (WebElement e: elements) {
                e.click();
            }
        }

        public void pressProceed() {
            driver.findElement(proceed).click();
        }

        public String getErrorMessage() {
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return driver.findElement(errorMessage).getText();
        }

        private Integer getAmountGoods() {
            return Integer.parseInt(driver.findElement(amountGoods).getAttribute("value"));
        }

        public void typeInAmountGoods(String amountGoods) {
            WebElement element = driver.findElement(this.amountGoods);
            element.clear();
            element.sendKeys(amountGoods);
        }

        private Double getTotalPrice() {
            return Double.parseDouble(driver.findElement(totalPrice).getText().substring(1));
        }

        private Double getUnitPrice() {
            return Double.parseDouble(driver.findElement(unitPrice).getText().substring(1));
        }

        //метод прописывает в поле количества товаров число и проверяет увеличилась ли соответственно общая сумма заказа
        public boolean isTotalPriceEqualsUnitPrice(String amountGoods) {
            typeInAmountGoods(amountGoods);
            Double unitPrice = getUnitPrice() * Double.parseDouble(amountGoods);
            Double totalPrice = 0.0;
            for (int i = 0; i < 20; i++) {
                totalPrice = getTotalPrice();
                if (totalPrice.equals(unitPrice)) break;
                MainPage.waiting(1000);
            }
            return totalPrice.equals(unitPrice);
        }

        public void pressNameGoodsButton() {
            driver.findElement(nameGoodsButton).click();
        }

        public void pressPhotoGoods() {
            driver.findElement(photoGoods).click();
        }
    }

    public class Address {
        private By billAddressCheckBox = By.id("addressesAreEquals");
        private By proceed = By.xpath("//button[@name='processAddress']");
        private By newAddressButton = By.id("address_invoice_form");

        public void selectBillAddressCheckBox() {
            WebElement element = driver.findElement(billAddressCheckBox);
            if (!element.isSelected()) {
                element.click();
            }
        }

        public void unselectBillAddressCheckBox() {
            WebElement element = driver.findElement(billAddressCheckBox);
            if (element.isSelected()) {
                element.click();
            }
        }

        public void pressProceed() {
            driver.findElement(proceed).click();
        }

        public boolean isNewAddressButtonExist() {
            wait.until(ExpectedConditions.visibilityOfElementLocated(newAddressButton));
            return driver.findElement(newAddressButton).isDisplayed();
        }

        public boolean isNewAddressButtonNotExist() {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(newAddressButton));
            return driver.findElement(newAddressButton).isDisplayed();
        }
    }

    public class Shipping {
        private By myDeliveryRadioButton = By.xpath("//td[@class='delivery_option_radio']");
        private By agreementCheckBox = By.id("cgv");
        private By proceed = By.xpath("//button[@name='processCarrier']");
        private By errorMessage = By.xpath("//p[@class='fancybox-error']");
        private By termsLink = By.xpath("//a[@class='iframe']");
        private By closeTermsWindowButton = By.xpath("//a[@title='Close']");

        public void selectMyDeliveryRadioButton() {
            WebElement element = driver.findElement(myDeliveryRadioButton);
            if (!element.isSelected()) {
                element.click();
            }
        }

        public void selectAgreementCheckBox() {
            WebElement element = driver.findElement(agreementCheckBox);
            if (!element.isSelected()) {
                element.click();
            }
        }

        public void unselectAgreementCheckBox() {
            WebElement element = driver.findElement(agreementCheckBox);
            if (element.isSelected()) {
                element.click();
            }
        }

        public void pressProceed() {
            driver.findElement(proceed).click();
        }

        public String getErrorMessage() {
            return driver.findElement(errorMessage).getText();
        }

        public void pressTermsLink() {
            driver.findElement(termsLink).click();
        }

        public void pressCloseTermsWindowButton() {
            driver.findElement(closeTermsWindowButton).click();
        }
    }

    public class Payment {
        private By payByBankWireLink = By.xpath("//a[@class='bankwire']");
        private By payByCheckLink = By.xpath("//a[@class='cheque']");
        private By confirmOrderButton = By.xpath("//p[@id='cart_navigation']/button");
        private By successMessage1 = By.xpath("//p[@class='cheque-indent']/strong");
        private By successMessage2 = By.xpath("//p[@class='alert alert-success']");

        public void pressPayByBankWireLink() {
            driver.findElement(payByBankWireLink).click();
        }

        public void pressPayByCheckLink() {
            driver.findElement(payByCheckLink).click();
        }

        public void pressConfirmOrderButton() {
            driver.findElement(confirmOrderButton).click();
        }

        public String getSuccessMessage() {
            try {
                return driver.findElement(successMessage1).getText();
            } catch (NoSuchElementException e) {
                return driver.findElement(successMessage2).getText();
            }
        }
    }
}
