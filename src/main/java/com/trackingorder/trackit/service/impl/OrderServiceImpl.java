package com.trackingorder.trackit.service.impl;

import com.trackingorder.trackit.dto.*;
import com.trackingorder.trackit.service.OrderService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {
    private static void wait(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void moveCursor(WebDriver driver, WebElement element) {
        new Actions(driver).moveToElement(element).perform();
        wait(1);
    }

    public static float parseCurrencyToFloat(String currencyString) {
        String cleanedString = currencyString.replace("₫", "").trim();
        cleanedString = cleanedString.replace(".", "");
        return Float.parseFloat(cleanedString);
    }

    public static Date parseStringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static WebDriver initDriver() {
        String driverPath = "D:\\ChromeDriver\\chromedriver-win64\\chromedriver.exe";
        String chromeTestingBinaryPath = "D:\\Chrome\\chrome-win64\\chrome.exe";

        // Set the path for the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", driverPath);

        // Create ChromeOptions to customize Chrome browser settings
        ChromeOptions options = new ChromeOptions();
        options.setBinary(chromeTestingBinaryPath); // Set the Chrome Testing binary path
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.6422.60 Safari/537.36");
        options.addArguments("--disable-infobars");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

        // Initialize WebDriver with ChromeOptions
        WebDriver driver = new ChromeDriver(options);

        // Maximize the browser window
        driver.manage().window().maximize();

        // Optionally, remove the navigator.webdriver property to reduce bot detection
        ((JavascriptExecutor) driver).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");

        return driver;
    }

    @Override
    public String getOrderTiki(AccountDTO accountDTO) {
        WebDriver driver = initDriver();
        driver.get("https://tiki.vn/");
        wait(2);
        String pageSource = driver.getPageSource();

        // Open login dialog
        WebElement accountButton = driver.findElement(By.xpath("//div[span[text()='Tài khoản']]"));
        accountButton.click();
        wait(2);

        // Enter phone num
        pageSource = driver.getPageSource();
        WebElement usernameInput = driver.findElement(By.xpath("//input[@placeholder='Số điện thoại']"));
        usernameInput.sendKeys(accountDTO.getUsername());
        WebElement continueButton = driver.findElement(By.xpath("//button[text()='Tiếp Tục']"));
        continueButton.click();
        wait(2);

        // Enter password
        pageSource = driver.getPageSource();
        WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='Mật khẩu']"));
        passwordInput.sendKeys(accountDTO.getPassword());
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Đăng Nhập']"));
        loginButton.click();
        wait(2);

        // Solve CAPTCHA
        wait(0);

        // Move to order
        driver.get("https://tiki.vn/sales/order/history");
        wait(5);

        List<WebElement> viewDetailButtons = driver.findElements(By.xpath("//div[text()='Xem chi tiết']"));
        List<Map<String, Object>> orderMaps = new ArrayList<>();

        for (int i = 0; i < viewDetailButtons.size(); i++) {
            viewDetailButtons.get(i).click();
            wait(5);
            Map<String, Object> orderPage = new HashMap<>();
            orderPage.put("id", i);
            orderPage.put("page source", driver.getPageSource());
            orderMaps.add(orderPage);
            WebElement returnButton = driver.findElement(By.xpath("//a[text()='<< Quay lại đơn hàng của tôi']"));
            returnButton.click();
            wait(5);
            viewDetailButtons = driver.findElements(By.xpath("//div[text()='Xem chi tiết']"));
        }

        pageSource = driver.getPageSource();
        return orderMaps.toString();
    }

    @Override
    public MessageDTO getOrderShopee(AccountDTO accountDTO) {
        // Init chrome driver
        WebDriver driver = initDriver();

        // Access login page
        driver.get("https://shopee.vn/buyer/login");
        wait(5);

        // Enter phone num and pass
        WebElement usernameInput = driver.findElement(By.xpath("//input[@placeholder='Email/Số điện thoại/Tên đăng nhập']"));
        usernameInput.sendKeys(accountDTO.getUsername());
        WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='Mật khẩu']"));
        passwordInput.sendKeys(accountDTO.getPassword());
        wait(1);
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Đăng nhập']"));
        loginButton.click();
        wait(10);

        // Verify by email (if have)
        try {
            WebElement verifyButton = driver.findElement(By.xpath("//button[div[text()='Xác minh bằng liên kết Email']]"));
            verifyButton.click();
            wait(60);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Move to order
        driver.get("https://shopee.vn/user/purchase");
        wait(5);

        // Get order info
        List<WebElement> purchaseOrderLinks;
        List<OrderDTO> orderList = new ArrayList<OrderDTO>();
        int orderListSize = 0;

        while (true) {
            // Show full order
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            wait(1);
            int newLen = driver.findElements(By.xpath("//a[contains(@href, '/user/purchase/order/')]")).size();
            if (newLen == orderListSize || orderListSize >= 10)
                break;
            else orderListSize = newLen;
        }

        // Get order link
        purchaseOrderLinks = driver.findElements(By.xpath("//a[contains(@href, '/user/purchase/order/')]"));
        List<String> orderHrefs = new ArrayList<>();
        for (WebElement link : purchaseOrderLinks) {
            String href = link.getAttribute("href").toString();
            if (!orderHrefs.contains(href))
                orderHrefs.add(href);
        }

        for (String orderHref : orderHrefs) {
            // Access order detail page
            driver.get(orderHref);
            wait(2);

            // Init order list
            OrderDTO order = new OrderDTO();
            order.setOrderDetailList(new ArrayList<OrderDetailDTO>());

            // Get order detail info
            List<WebElement> orderDetailElements = driver.findElements(By.xpath("//div[@class='FNHV0p']"));
            for(int i = 0; i < orderDetailElements.size(); i++) {

                // Product info
                List<WebElement> productInfoElements = orderDetailElements.get(i).findElements(By.xpath(".//a[@class='mZ1OWk']"));
                OrderDetailDTO orderDetail = new OrderDetailDTO();
                orderDetail.setProductList(new ArrayList<ProductDTO>());
                for (WebElement productInfoElement : productInfoElements) {
                    ProductDTO product = new ProductDTO();

                    // Handle gift product
                    try {
                        product.setImageUrl(productInfoElement.findElement(By.xpath(".//img[@class='gQuHsZ']")).getAttribute("src").toString());
                        product.setName(productInfoElement.findElement(By.xpath(".//span[@class='DWVWOJ']")).getText());
                    } catch (Exception e) {
//                    e.printStackTrace();
                        continue;
                    }

                    product.setProvidedBy(driver.findElement(By.xpath(".//div[@class='UDaMW3']")).getText().toString());

                    // Product option
                    try {
                        String productOption = productInfoElement.findElement(By.xpath(".//div[@class='rsautk']")).getText().replaceAll("^Phân loại hàng: ","");
                        product.setProductOption(productOption.substring(0,1).toUpperCase() + productOption.substring(1));
                    }
                    catch (Exception e) {
//                    e.printStackTrace();
                    }

                    // Product quantity
                    WebElement quantityElement = productInfoElement.findElement(By.xpath(".//div[@class='j3I_Nh']"));
                    product.setQuantity(Integer.parseInt(quantityElement.getText().replaceAll("^x", "")));

                    orderDetail.getProductList().add(product);
                }

                // Order detail price
                WebElement priceElement;
                try {
                    priceElement = orderDetailElements.get(i).findElement(By.xpath(".//span[contains(@class, 'nW_6Oi')]"));
                }
                catch (Exception e) {
                    priceElement = orderDetailElements.get(i).findElement(By.xpath(".//div[contains(@class, 'nW_6Oi')]"));
                }
                orderDetail.setTotalPrice(parseCurrencyToFloat(priceElement.getText()));
                order.getOrderDetailList().add(orderDetail);
            }

            // Order status
            order.setStatus("Success");

            // Order created date
            WebElement createdDateElement = driver.findElement(By.xpath("//div[@class='stepper__step-date']"));
            order.setCreatedDate(parseStringToDate(createdDateElement.getText()));

            // Coin
            try {
                WebElement coinElement = driver.findElement(By.xpath("//div[contains(@class, 'kW3VDc') and .//span[contains(text(), 'Shopee xu')]]"));
                order.setCoin(parseCurrencyToFloat(coinElement.findElement(By.xpath(".//div[contains(@class, 'Tfejtu')]"))
                        .findElement(By.xpath(".//div")).getText().substring(1)));
            }
            catch (Exception e) {}

            // Payment method
            try {
                WebElement paymentMethodElement = driver.findElement(By.xpath("//div[contains(@class, 'kW3VDc') and .//span[text()='Phương thức Thanh toán']]"));
                order.setPaymentMethod(paymentMethodElement.findElement(By.xpath(".//div[contains(@class, 'Tfejtu')]"))
                        .findElement(By.xpath(".//div")).getText());
            }
            catch (Exception e) {}

            // Shipping cost
            try {
                WebElement shippingCostElement = driver.findElement(By.xpath("//div[contains(@class, 'kW3VDc') and .//span[text()='Phí vận chuyển']]"));
                order.setShipCost(parseCurrencyToFloat(shippingCostElement.findElement(By.xpath(".//div[contains(@class, 'Tfejtu')]"))
                        .findElement(By.xpath(".//div")).getText()));
            }
            catch (Exception e) {}

            // Shop discount
            try {
                WebElement shopDiscountElement = driver.findElement(By.xpath("//div[contains(@class, 'kW3VDc') and .//span[text()='Voucher từ Shop']]"));
                order.setShopDiscount(parseCurrencyToFloat(shopDiscountElement.findElement(By.xpath(".//div[contains(@class, 'Tfejtu')]"))
                        .findElement(By.xpath(".//div")).getText().substring(1)));
            }
            catch (Exception e) {}

            // Voucher shopee discount
            try {
                WebElement voucherDiscountElement = driver.findElement(By.xpath("//div[contains(@class, 'kW3VDc') and .//span[text()='Voucher từ Shopee']]"));
                order.setVoucherDiscount(parseCurrencyToFloat(voucherDiscountElement.findElement(By.xpath(".//div[contains(@class, 'Tfejtu')]"))
                        .findElement(By.xpath(".//div")).getText().substring(1)));
            }
            catch (Exception e) {}

            // Ship discount
            try {
                WebElement shipDiscountElement = driver.findElement(By.xpath("//div[contains(@class, 'kW3VDc') and .//span[text()='Giảm giá phí vận chuyển']]"));
                order.setShipDiscount(parseCurrencyToFloat(shipDiscountElement.findElement(By.xpath(".//div[contains(@class, 'Tfejtu')]"))
                        .findElement(By.xpath(".//div")).getText().substring(1)));
            }
            catch (Exception e) {}

            // Total cost
            try {
                WebElement totalCostElement = driver.findElement(By.xpath("//div[contains(@class, 'kW3VDc') and .//span[text()='Tổng tiền hàng']]"));
                order.setTotalCost(parseCurrencyToFloat(totalCostElement.findElement(By.xpath(".//div[contains(@class, 'Tfejtu')]"))
                        .findElement(By.xpath(".//div")).getText()));
            }
            catch (Exception e) {}

            // Final cost
            order.setFinalCost(order.getTotalCost() + order.getShipCost() - order.getShipDiscount() - order.getVoucherDiscount() - order.getShopDiscount() - order.getCoin());

            // Shipping method
            order.setShippingMethod(new ShippingMethodDTO());
            order.getShippingMethod().setPrice(order.getShipCost());
            order.getShippingMethod().setDiscount(order.getShipDiscount());
            try {
                WebElement shippedByElement = driver.findElement(By.xpath("//div[@class='Azh6RS']"))
                        .findElement(By.xpath(".//div"))
                        .findElement(By.xpath(".//div"));
                order.getShippingMethod().setShippedBy(shippedByElement.getText());
            } catch (Exception e) {}

            // User address
            order.setUserAddress(new UserAddressDTO());
            WebElement nameElement = driver.findElement(By.xpath("//div[@class='BWtzco']"));
            List<WebElement> phoneAndAddress = driver.findElement(By.xpath("//div[@class='rRE7pF']")).findElements(By.xpath(".//span"));
            order.getUserAddress().setName(nameElement.getText());
            order.getUserAddress().setPhone(phoneAndAddress.get(0).getText());
            order.getUserAddress().setAddress(phoneAndAddress.get(1).getText());

            // Order status detail list
            WebElement openFullStatusListButton = driver.findElement(By.xpath("//span[@class='fQNud3']"));
            openFullStatusListButton.click();
            wait(1);
            order.setStatusDetailList(new ArrayList<StatusDetailDTO>());
            List<WebElement> statusDetailElements = driver.findElements(By.xpath("//div[@class='qhDYac']"));
            for (WebElement statusDetailElement : statusDetailElements) {
                StatusDetailDTO statusDetail = new StatusDetailDTO();
                statusDetail.setDate(parseStringToDate(statusDetailElement.findElement(By.xpath(".//div[@class='e73NiD']")).getText().replaceAll("Hôm nay", "00:00")));
                List<WebElement> statusDetailContentElement = statusDetailElement.findElement(By.xpath(".//div[@class='LKrsme']"))
                        .findElements(By.xpath(".//p"));
                statusDetail.setTitle(statusDetailContentElement.get(0).getText().toString());
                String contentText = statusDetailContentElement.get(1).getText().toString();
                int index = contentText.indexOf('\n');
                if (index != -1) {
                    contentText = contentText.substring(0,index);
                }
                statusDetail.setContent(contentText);
                order.getStatusDetailList().add(statusDetail);
            }

            orderList.add(order);

            // Back to order list
            try {
                WebElement backButton = driver.findElement(By.xpath("//button[@class='yMANId']"));
                backButton.click();
            }
            catch (Exception e) {break;}
            wait(2);
        }

        driver.quit();
        MessageDTO messageDTO = new MessageDTO("Get order successfully", orderList);
        return messageDTO;
    }

    @Override
    public String getOrderLazada(AccountDTO accountDTO) {
        WebDriver driver = initDriver();
        driver.get("https://member.lazada.vn/user/login");
        wait(5);
        String pageSource = driver.getPageSource();

        // Enter phone num and pass
        WebElement usernameInput = driver.findElement(By.xpath("//input[@placeholder='Vui lòng nhập số điện thoại hoặc email của bạn']"));
        usernameInput.sendKeys(accountDTO.getUsername());
        WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='Vui lòng nhập mật khẩu của bạn']"));
        passwordInput.sendKeys(accountDTO.getPassword());
        wait(1);
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='ĐĂNG NHẬP']"));
        loginButton.click();
        wait(5);

        // Solve CAPTCHA
        wait(10);

        // Move to order

        wait(5);

        pageSource = driver.getPageSource();
        driver.quit();
        return pageSource.toString();
    }
}
