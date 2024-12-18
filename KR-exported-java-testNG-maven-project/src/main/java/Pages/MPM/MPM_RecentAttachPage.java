package Pages.MPM;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MPM_RecentAttachPage {
    private static final Logger logger = LogManager.getLogger(MPM_RecentAttachPage.class);
    WebDriver driver ;

    // Locators
    private final By exportCsv_Button = By.xpath("/html/body/app/mat-sidenav-container/mat-sidenav-content/content/app-report-page/section/app-report/section/div[1]/mat-card/mat-card-title/div[2]/button[1]") ;

    public MPM_RecentAttachPage(WebDriver driver) {
        this.driver = driver;
    }

    public void exportCSVFile()
    {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.findElement(exportCsv_Button).click();
    }
}
