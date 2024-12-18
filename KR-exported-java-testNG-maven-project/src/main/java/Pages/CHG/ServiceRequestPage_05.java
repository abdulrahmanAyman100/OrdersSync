package Pages.CHG;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ServiceRequestPage_05 {
    private static final Logger logger = LogManager.getLogger(HomePage.class);
    WebDriver driver ;
    WebDriverWait wait ;

    public ServiceRequestPage_05(WebDriver driver) {
        this.driver = driver;
    }

    private final By description_TextBox = By.id("ctl00_ContentPlaceHolder1_tb_svcDescription") ;
    private final By serviceCode_TextBox = By.id("ctl00_ContentPlaceHolder1_txtCode") ;
    private final By addServiceCode_Button = By.id("ctl00_ContentPlaceHolder1_btnAdd") ;
    private final By next_Button = By.id("ctl00_ContentPlaceHolder1_btnNext") ;


    public ServiceRequestPage_05 addDescription()
    {
        driver.findElement(description_TextBox).sendKeys("Test");
        return this;
    }

    public ServiceRequestPage_05 addServiceCode(String code)
    {
        wait = new WebDriverWait(driver , Duration.ofSeconds(30));
        driver.findElement(serviceCode_TextBox).sendKeys(code);
        driver.findElement(addServiceCode_Button).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text() = \"Selected Service Codes\"]")));
        return this;
    }

    public ServiceRequestPage_05 clickNext()
    {
        driver.findElement(next_Button).click();
        return this;
    }

}
