package Pages.CHG;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private static final Logger logger = LogManager.getLogger(LoginPage.class);
    WebDriver driver ;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage navigate()
    {
        driver.get("https://extra.chgsd.com/login.aspx");
        logger.info("Navigated to login page.");
        return this;
    }

    public HomePage login(String username , String password)
    {
        driver.findElement(By.id("txtLogin")).sendKeys(username);
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_txtPwd")).sendKeys(password);
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_btnSubmit")).click();
        logger.info("Logged in successfully.");
        return new HomePage(driver);
    }
}
