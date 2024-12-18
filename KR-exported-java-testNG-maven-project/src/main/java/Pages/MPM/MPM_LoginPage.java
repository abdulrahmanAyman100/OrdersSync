package Pages.MPM;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MPM_LoginPage {

    private static final Logger logger = LogManager.getLogger(MPM_LoginPage.class);
    WebDriver driver ;


    // Locators
    private final By username_TextBox = By.id("mat-input-0") ;
    private final By password_textBox = By.id("mat-input-1") ;
    private final By login_Button = By.xpath("//button[@class=\"mat-button\" and @type=\"submit\"]") ;


    public MPM_LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public MPM_LoginPage navigate()
    {
        driver.get("https://portal.medpointmanagement.com/sign-in");
        logger.info("Navigated to login page.");
        return this;
    }

    public MPM_HomePage login(String username , String password)
    {
        driver.findElement(username_TextBox).sendKeys(username);
        driver.findElement(password_textBox).sendKeys(password);
        driver.findElement(login_Button).click();
        logger.info("Logged in successfully.");
        return new MPM_HomePage(driver);
    }
}
