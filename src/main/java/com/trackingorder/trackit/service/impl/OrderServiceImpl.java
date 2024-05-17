package com.trackingorder.trackit.service.impl;

import com.trackingorder.trackit.chromedriver.ChromeDriverBuilder;
import com.trackingorder.trackit.dto.AccountDTO;
import com.trackingorder.trackit.service.OrderService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.File;
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

    public static WebDriver initDriver(String driverPath, String chromeTestingBinaryPath) {
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
        String driverPath = "D:\\ChromeDriver\\chromedriver-win64\\chromedriver.exe";
        String chromeTestingBinaryPath = "D:\\Chrome\\chrome-win64\\chrome.exe";

        WebDriver driver = initDriver(driverPath, chromeTestingBinaryPath);
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
        usernameInput.sendKeys("0971669507");
        WebElement continueButton = driver.findElement(By.xpath("//button[text()='Tiếp Tục']"));
        continueButton.click();
        wait(2);

        // Enter password
        pageSource = driver.getPageSource();
        WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='Mật khẩu']"));
        passwordInput.sendKeys("06082003DinhTam");
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
    public String getOrderShopee() {
        String driverPath = "D:\\ChromeDriver\\chromedriver-win64\\chromedriver.exe";
        String chromeTestingBinaryPath = "D:\\Chrome\\chrome-win64\\chrome.exe";

        WebDriver driver = initDriver(driverPath, chromeTestingBinaryPath);
        driver.get("https://shopee.vn/buyer/login");
        wait(5);
        String pageSource = driver.getPageSource();

        // Enter phone num and pass
        WebElement usernameInput = driver.findElement(By.xpath("//input[@placeholder='Email/Số điện thoại/Tên đăng nhập']"));
        usernameInput.sendKeys("0971669507");
        WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='Mật khẩu']"));
        passwordInput.sendKeys("Dinhtam080803");
        wait(1);
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Đăng nhập']"));
        loginButton.click();
        wait(5);

        // Solve CAPTCHA
        wait(10);

        // Move to order

        wait(5);

        pageSource = driver.getPageSource();
        return pageSource.toString();
    }

    @Override
    public String getOrderLazada() {
        String driverPath = "D:\\ChromeDriver\\chromedriver-win64\\chromedriver.exe";
        String chromeTestingBinaryPath = "D:\\Chrome\\chrome-win64\\chrome.exe";

        WebDriver driver = initDriver(driverPath, chromeTestingBinaryPath);
        driver.get("https://member.lazada.vn/user/login");
        wait(5);
        String pageSource = driver.getPageSource();

        // Enter phone num and pass
        WebElement usernameInput = driver.findElement(By.xpath("//input[@placeholder='Vui lòng nhập số điện thoại hoặc email của bạn']"));
        usernameInput.sendKeys("trandinhtam8a5@gmail.com");
        WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='Vui lòng nhập mật khẩu của bạn']"));
        passwordInput.sendKeys("Dinhtam080803@");
        wait(1);
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='ĐĂNG NHẬP']"));
        loginButton.click();
        wait(5);

        // Solve CAPTCHA
        wait(10);

        // Move to order

        wait(5);

        pageSource = driver.getPageSource();
        return pageSource.toString();
    }
}
