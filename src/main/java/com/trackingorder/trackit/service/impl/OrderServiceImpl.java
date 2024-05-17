package com.trackingorder.trackit.service.impl;

import com.trackingorder.trackit.service.OrderService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public String getOrderTiki() {
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

//        String driver_home = "D:\\ChromeDriver\\chromedriver-win64\\chromedriver.exe";
//
//        ChromeOptions chrome_options = new ChromeOptions();
//        chrome_options.addArguments("--window-size=1920,1080");
//        //chrome_options.addArguments("--headless=new"); when chromedriver > 108.x.x.x
//        //chrome_options.addArguments("--headless=chrome"); when chromedriver <= 108.x.x.x
//
//        ChromeDriverService service = new ChromeDriverService.Builder()
//                .usingDriverExecutable(new File(driver_home))
//                .usingAnyFreePort()
//                .build();
//
//        //ChromeDriver chromeDriver1 = new ChromeDriver(service);
//        ChromeDriver driver = new ChromeDriverBuilder()
//                .build(chrome_options,driver_home);


        driver.get("https://tiki.vn/");
        wait(5);
        String pageSource = driver.getPageSource();

        // Open login dialog
        WebElement accountButton = driver.findElement(By.xpath("//div[span[text()='Tài khoản']]"));
        accountButton.click();
        wait(5);

        // Enter phone num
        pageSource = driver.getPageSource();
        WebElement usernameInput = driver.findElement(By.xpath("//input[@placeholder='Số điện thoại']"));
        usernameInput.sendKeys("0971669507");
        WebElement continueButton = driver.findElement(By.xpath("//button[text()='Tiếp Tục']"));
        continueButton.click();
        wait(5);

        // Enter password
        pageSource = driver.getPageSource();
        WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='Mật khẩu']"));
        passwordInput.sendKeys("06082003DinhTam");
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Đăng Nhập']"));
        loginButton.click();
        wait(5);

        // Solve CAPTCHA
        wait(10);

        // Move to order
        accountButton = driver.findElement(By.xpath("//div[span[text()='Tài khoản']]"));
        moveCursor(driver, accountButton);
        WebElement orderLinkTag = driver.findElement(By.xpath("//div[a[p[text()='Đơn hàng của tôi']]]"));
        orderLinkTag.click();
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
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
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
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
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
