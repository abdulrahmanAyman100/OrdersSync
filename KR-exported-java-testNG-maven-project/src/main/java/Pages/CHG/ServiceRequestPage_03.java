package Pages.CHG;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ServiceRequestPage_03 {
    private static final Logger logger = LogManager.getLogger(HomePage.class);
    WebDriver driver ;
    WebDriverWait wait ;

    public ServiceRequestPage_03(WebDriver driver) {
        this.driver = driver;
    }

    private final By speciality_DropDown = By.id("ctl00_ContentPlaceHolder1_drpSpecialty") ;
    private final By searchFacility_Button = By.id("ctl00_ContentPlaceHolder1_btn_LaunchFacilitySearch") ;
    private final By next_Button = By.id("ctl00_ContentPlaceHolder1_btnNext") ;
    private By selectFacility_Button(String facility)
    {
        return By.xpath("//td[contains(text(), '"+facility+"')]/following-sibling::td/input[@type='button']");
    }


    public ServiceRequestPage_03 selectSpeciality(String speciality)
    {
        Select select = new Select(driver.findElement(speciality_DropDown));
        select.selectByVisibleText(speciality);
        return this;
    }

    public ServiceRequestPage_03 selectFacility(String facility)
    {

        driver.findElement(searchFacility_Button).click();
        wait = new WebDriverWait(driver , Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text() = \"Search for Facility\"]")));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.findElement(selectFacility_Button(facility)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h1[text() = \"Search for Facility\"]")));

        return this;
    }

    public ServiceRequestPage_03 clickNext()
    {
        driver.findElement(next_Button).click();
        return this;
    }

}
