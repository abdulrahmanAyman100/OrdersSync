package Pages.CHG;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ServiceRequestPage_02 {
    private static final Logger logger = LogManager.getLogger(HomePage.class);
    WebDriver driver ;

    public ServiceRequestPage_02(WebDriver driver) {
        this.driver = driver;
    }

    private final By patientSearch_TextBox = By.id("ctl00_ContentPlaceHolder1_txtSearch") ;
    private final By patientSearch_Button = By.id("ctl00_ContentPlaceHolder1_btnSearch") ;
    private final By searchResults = By.xpath("//*[contains(@id, 'ctl00_ContentPlaceHolder1_mbrSearchResults_ctl00__')]/td/input") ;
    private final By confirmEligibility = By.id("ctl00_ContentPlaceHolder1_rblDMEProsOrFacility_0") ;
    private final By next_Button = By.id("ctl00_ContentPlaceHolder1_btnNext") ;


    public ServiceRequestPage_02 searchForPatient(String searchKeyWord)
    {
        driver.findElement(patientSearch_TextBox).sendKeys(searchKeyWord);
        driver.findElement(patientSearch_Button).click();
        return this;
    }

    public ServiceRequestPage_02 selectPatient(int patientOrder)
    {
        driver.findElements(searchResults).get(patientOrder).click();
        return this;
    }

    public ServiceRequestPage_02 confirmEligibility()
    {
        driver.findElement(confirmEligibility).click();
        return this;
    }

    public ServiceRequestPage_02 clickNext()
    {
        driver.findElement(next_Button).click();
        return this;
    }
}
