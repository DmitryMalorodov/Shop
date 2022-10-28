package userprofile;

import mainpage.MainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.font.FontRenderContext;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderHistory {
    private static WebDriver driver;

    public OrderHistory(WebDriver driver) {
        this.driver = driver;
    }

    public static class OrderHistoryTable {
        private static By allOrders = By.xpath("//table[@id='order-list']/tbody/tr");
        private static By orderReferenceLinkList = By.xpath("//a[@class='color-myaccount']");
        private static By invoiceLinkList = By.xpath("//a[@title='Invoice']");
        private static By detailsButtonList = By.xpath("//td[@class='history_detail footable-last-column']/a[1]");
        private static By reorderLinkList = By.xpath("//td[@class='history_detail footable-last-column']/a[2]");

        private static By dateSortButton = By.xpath("//th[text()='Date']");
        private static By priceSortButton = By.xpath("//th[text()='Total price']");
        private static By datesList = By.xpath("//td[@class='history_date bold']");
        private static By pricesList = By.xpath("//td[@class='history_price']");

        private static By backToAccountButton = By.xpath("//ul[@class='footer_links clearfix']/li[1]");
        private static By homeButton = By.xpath("//ul[@class='footer_links clearfix']/li[2]");

        public static int getCountOrders() {
            List<WebElement> elements = driver.findElements(allOrders);
            return elements.size();
        }

        //метод жмет на случайный товар и возвращает его имя
        public static String pressRandomOrderReferenceAndGetName() {
            List<WebElement> elements = driver.findElements(orderReferenceLinkList);
            WebElement order = elements.get((int) (Math.random() * elements.size()));
            order.click();
            return order.getText().trim();
        }

        //метод жмет на кнопку details на случайном товаре и возвращает его имя
        public static String pressRandomDetailsButtonAndGetName() {
            //получаем список всех кнопок Details и OrderReference
            List<WebElement> allDetailsButton = driver.findElements(detailsButtonList);
            List<WebElement> allOrderReference = driver.findElements(orderReferenceLinkList);
            //получаем рандомное число и по нему вытаскиваем из обоих списков по элементу
            int randomNumber = (int) (Math.random() * allDetailsButton.size());
            WebElement detailsButton = allDetailsButton.get(randomNumber);
            WebElement orderReference = allOrderReference.get(randomNumber);
            //жмем на details и возвращаем имя товара из OrderReference
            detailsButton.click();
            return orderReference.getText().trim();
        }

        //метод проверяет папку Downloads, удаляет в ней все файлы, если они есть.
        //производит скачивание счета у случайного файла и проверет произошло ли скачивание
        public static boolean isFileDownloaded() {
            File file = new File("C:\\Users\\user\\Desktop\\Downloads\\");

            File[] files = file.listFiles();
            if (files.length != 0) {
                for (File f: files) {
                    f.delete();
                }
            }

            pressRandomInvoiceLink();
            for (int i = 0; i < 20; i++) {
                if (file.listFiles().length == 0) MainPage.waiting(1000);
                else break;
            }

            return file.listFiles().length > 0;
        }

        private static void pressRandomInvoiceLink() {
            List<WebElement> elements = driver.findElements(invoiceLinkList);
            elements.get((int) Math.random() * elements.size()).click();
        }

        public static void pressRandomReorderLink() {
            List<WebElement> elements = driver.findElements(reorderLinkList);
            elements.get((int) (Math.random() * elements.size())).click();
        }

        public static void pressDateSortButton() {
            driver.findElement(dateSortButton).click();
        }

        public static void pressPriceSortButton() {
            driver.findElement(priceSortButton).click();
        }

        //метод получает список дат всех заказов, переводит их в формат Date и возвращает их список
        private static List<Date> getAllDatesList() {
            List<WebElement> allDatesString = driver.findElements(datesList);
            List<Date> result = new ArrayList<>();

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            for (WebElement e: allDatesString) {
                try {
                    Date date = formatter.parse(e.getText().trim());
                    result.add(date);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
            return result;
        }

        //метод проверяет что сортировка в столбце дат установлена по убыванию
        public static boolean isDateDownSort() {
            List<Date> dates = getAllDatesList();
            boolean result = true;

            for (int i = 0; i < dates.size() - 1; i++) {
                if (dates.get(i).getTime() < dates.get(i + 1).getTime()) {
                    result = false;
                    break;
                }
            }
            return result;
        }

        //метод проверяет что сортировка в столбце дат установлена по возрастанию
        public static boolean isDateUpSort() {
            List<Date> dates = getAllDatesList();
            boolean result = true;

            for (int i = 0; i < dates.size() - 1; i++) {
                if (dates.get(i).getTime() > dates.get(i + 1).getTime()) {
                    result = false;
                    break;
                }
            }
            return result;
        }

        //метод получает цены всех заказов и возвращает их список
        private static List<Double> getAllPricesList() {
            List<WebElement> allPrices = driver.findElements(pricesList);
            List<Double> result = new ArrayList<>();

            for (WebElement e: allPrices) {
                Double d = Double.parseDouble(e.getAttribute("data-value"));
                result.add(d);
            }
            return result;
        }

        //метод проверяет что сортировка в столбце цен установлена по возрастанию
        public static boolean isPriceUpSort() {
            List<Double> prices = getAllPricesList();
            boolean result = true;

            for (int i = 0; i < prices.size() - 1; i++) {
                if (prices.get(i).doubleValue() > prices.get(i + 1).doubleValue()) {
                    result = false;
                    break;
                }
            }
            return result;
        }

        //метод проверяет что сортировка в столбце цен установлена по убыванию
        public static boolean isPriceDownSort() {
            List<Double> prices = getAllPricesList();
            boolean result = true;

            for (int i = 0; i < prices.size() - 1; i++) {
                if (prices.get(i).doubleValue() < prices.get(i + 1).doubleValue()) {
                    result = false;
                    break;
                }
            }
            return result;
        }

        public static void pressBackToAccountButton() {
            driver.findElement(backToAccountButton).click();
        }

        public static void pressHomeButton() {
            driver.findElement(homeButton).click();
        }
    }

    public static class OrderInfo {
        private static By reorderButton = By.xpath("//form[@id='submitReorder']/a");
        private static By invoiceLink = By.xpath("//div[@class='info-order box']//a");
        private static By commonInfo = By.xpath("//p[@class='dark']/strong");
        private static By commentField = By.xpath("//textarea[@name='msgText']");
        private static By sendButton = By.xpath("//button[@name='submitMessage']");
        private static By successMessage = By.xpath("//p[@class='alert alert-success']");
        private static By errorMessage = By.xpath("//div[@class='alert alert-danger']//li");
        private static By commentList = By.xpath("//*[@id=\"block-order-detail\"]/div[5]//tbody/tr");

        //метод ожидает пока станет видимой информация о товаре
        private static void waitCommonInfo() {
            new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.visibilityOfElementLocated(commonInfo));
        }

        public static void pressReorderButton() {
            waitCommonInfo();
            driver.findElement(reorderButton).click();
        }

        //метод проверяет папку Downloads, удаляет в ней все файлы, если они есть.
        //производит скачивание счета у выбранного файла и проверет произошло ли скачивание
        public static boolean isFileDownloaded() {
            File file = new File("C:\\Users\\user\\Desktop\\Downloads\\");

            File[] files = file.listFiles();
            if (files.length != 0) {
                for (File f: files) {
                    f.delete();
                }
            }

            waitCommonInfo();
            driver.findElement(invoiceLink).click();

            for (int i = 0; i < 20; i++) {
                if (file.listFiles().length == 0) MainPage.waiting(1000);
                else break;
            }

            return file.listFiles().length > 0;
        }

        //получаем общее инфо о товаре, берем оттуда его имя и сравниваем с именем из параметра
        public static boolean isInfoShowedCorrect(String nameOrder) {
            waitCommonInfo();
            String[] info = driver.findElement(commonInfo).getText().split(" ");
            System.out.println(nameOrder);
            System.out.println(info[2]);
            return info[2].equals(nameOrder);
        }

        public static void typeInCommentField(String comment) {
            driver.findElement(commentField).sendKeys(comment);
        }

        public static void pressSendButton() {
            driver.findElement(sendButton).click();
        }

        public static String getMessage() {
            try {
                return driver.findElement(successMessage).getText();
            } catch (NoSuchElementException e) {
                return driver.findElement(errorMessage).getText();
            }
        }

        public static int getCountComment() {
            waitCommonInfo();
            try {
                return driver.findElements(commentList).size();
            } catch (NoSuchElementException e) {
                return 0;
            }
        }
    }
}
