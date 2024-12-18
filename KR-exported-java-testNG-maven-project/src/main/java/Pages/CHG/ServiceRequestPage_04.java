package Pages.CHG;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ServiceRequestPage_04 {
    private static final Logger logger = LogManager.getLogger(HomePage.class);
    WebDriver driver ;

    public ServiceRequestPage_04(WebDriver driver) {
        this.driver = driver;
    }

    private final By diagnosisCodes_List = By.xpath("//*[contains(@id , \"ctl00_ContentPlaceHolder1_DiagnosisGrid_ctl00__\")]/td/a") ;
    private final By next_Button = By.id("ctl00_ContentPlaceHolder1_btnNext") ;


    public ServiceRequestPage_04 selectDiagnosisCode(int code)
    {
        driver.findElements(diagnosisCodes_List).get(code).click();
        return this;
    }

    public ServiceRequestPage_04 clickNext()
    {
        driver.findElement(next_Button).click();
        return this;
    }

}
