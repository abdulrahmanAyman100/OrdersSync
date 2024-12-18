package Pages.CHG;

import Utility.BrowserActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ServiceRequestPage_01 {
    private static final Logger logger = LogManager.getLogger(HomePage.class);
    WebDriver driver ;

    public ServiceRequestPage_01(WebDriver driver) {
        this.driver = driver;
    }

    private final By contactProviderDropDown = By.id("ctl00_ContentPlaceHolder1_drpReferringProvider") ;
    private final By contactName_TextBox = By.id("ctl00_ContentPlaceHolder1_tb_ContactName") ;
    private final By phone_TextBox = By.id("ctl00_ContentPlaceHolder1_tb_ContactPhone") ;
    private final By phoneEXT_TextBox = By.id("ctl00_ContentPlaceHolder1_tb_ContactPhoneExt") ;
    private final By fax_TextBox = By.id("ctl00_ContentPlaceHolder1_tb_ContactFax") ;
    private final By faxEXT_TextBox = By.id("ctl00_ContentPlaceHolder1_tb_ContactFaxExt") ;
    private final By email_TextBox = By.id("ctl00_ContentPlaceHolder1_tb_ContactEmail") ;
    private final By next_Button = By.id("ctl00_ContentPlaceHolder1_btnNext") ;

    public ServiceRequestPage_01 selectProvider()
    {
        Select select = new Select(driver.findElement(contactProviderDropDown));
        select.selectByVisibleText("HASSAN KAFRI MD INC");
        return this;
    }

    public ServiceRequestPage_01 enterContactData(String contactName , String phone , String fax , String email)
    {
        driver.findElement(contactName_TextBox).sendKeys(contactName);
        driver.findElement(email_TextBox).sendKeys(email);
        new BrowserActions(driver).pasteText(phone_TextBox , phone);
        new BrowserActions(driver).pasteText(fax_TextBox , fax);
        return this;
    }

    public ServiceRequestPage_01 clickNext()
    {
        driver.findElement(next_Button).click();
        return this;
    }
}
