/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selenium;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Frank
 */
public class ChromeExample {

    public static String driverPath = "C:/Users/Frank/Downloads/chromedriver_win32/";

    public static WebDriver driver;

    public static void main(String[] args) {

        System.out.println("launching chrome browser");
        System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
        driver = new ChromeDriver();
        driver.navigate().to("http://facebook.com/");
        WebElement email = driver.findElement(By.id("email"));
        WebElement senha = driver.findElement(By.id("pass"));
        WebElement btn = driver.findElement(By.id("u_0_2"));
        email.sendKeys("frankdonascimentojr@hotmail.com");
        senha.sendKeys("123123");
        btn.click();
    }
}
