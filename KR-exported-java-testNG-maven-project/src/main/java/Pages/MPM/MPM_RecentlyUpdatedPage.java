package Pages.MPM;

import Utility.BrowserActions;
import Utility.FileMover;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.util.List;

public class MPM_RecentlyUpdatedPage {
    private static final Logger logger = LogManager.getLogger(MPM_RecentlyUpdatedPage.class);
    WebDriver driver ;

    // Locators
    private final By backDays_TextBox = By.xpath("/html/body/app/mat-sidenav-container/mat-sidenav-content/content/app-report-page/section/app-report/section/section/mat-card/mat-card-content/form/div/div[2]/mat-form-field/div/div[1]/div/input") ;
    private final By search_Button = By.xpath("/html/body/app/mat-sidenav-container/mat-sidenav-content/content/app-report-page/section/app-report/section/section/mat-card/mat-card-content/form/mat-card-actions/button") ;
    private final By exportCsv_Button = By.xpath("/html/body/app/mat-sidenav-container/mat-sidenav-content/content/app-report-page/section/app-report/section/div[1]/mat-card/mat-card-title/div[2]/button[1]") ;
    private final By filesList = By.xpath("/html/body/app/mat-sidenav-container/mat-sidenav-content/content/app-report-page/section/app-report/section/div[2]/mat-card/table/tbody/tr/td[2]");
    public MPM_RecentlyUpdatedPage(WebDriver driver) {
        this.driver = driver;
    }

    public MPM_RecentlyUpdatedPage enterBackDays(String days)
    {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.findElement(backDays_TextBox).clear();
        driver.findElement(backDays_TextBox).sendKeys(days);
        return this;
    }

    public MPM_RecentlyUpdatedPage search()
    {
        driver.findElement(search_Button).click();
        return this;
    }


    public MPM_RecentlyUpdatedPage exportCSVFile()
    {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.findElement(exportCsv_Button).click();
        return this;
    }

}
