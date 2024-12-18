package Pages.CHG;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ServiceRequestPage_06 {
    private static final Logger logger = LogManager.getLogger(HomePage.class);
    WebDriver driver ;
    WebDriverWait wait ;

    public ServiceRequestPage_06(WebDriver driver) {
        this.driver = driver;
    }

    private final By memberContactPhone_TextBox = By.id("ctl00_ContentPlaceHolder1_txtMemberContactPhone") ;
    private final By submitAuthRequest_Button = By.id("ctl00_ContentPlaceHolder1_btnNextAutoAuth") ;


    public ServiceRequestPage_06 addMemberContactPhone(String phone)
    {
        driver.findElement(memberContactPhone_TextBox).sendKeys(phone);
        return this;
    }

    public ServiceRequestPage_06 submitAuthRequest()
    {
        driver.findElement(submitAuthRequest_Button).click();
        return this;
    }

}
