package Pages.MPM;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MPM_HomePage {

    private static final Logger logger = LogManager.getLogger(MPM_HomePage.class);
    WebDriver driver ;

    // Locators
    private final By authorizations_DropDownMenu = By.xpath("/html/body/app/mat-sidenav-container/mat-sidenav-content/content/app-navbar/mat-toolbar[1]/a[3]") ;
    private final By recentAttachments_Button = By.xpath("//a[@href=\"/my-data/recent-authorization-attachments\"]") ;
    private final By recentlyUpdatedAttachments_Button = By.xpath("//a[@href=\"/my-data/recently-updated-authorizations\"]") ;
    private final By IPADropDown = By.xpath("//*[@id=\"mat-select-0\"]/div/div[2]");
    private final By communityCareIPA_Option = By.xpath("//mat-option[@id=\"mat-option-0\"]/span[contains(text(), \"COMMUNITY CARE\")]");
    private final By integratedHealthIPA_Option = By.xpath("//mat-option[@id=\"mat-option-1\"]/span[contains(text(), \"INTEGRATED HEALTH\")]");

    public MPM_HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public MPM_RecentlyUpdatedPage navigateToRecentlyUpdatedAttachments()
    {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.findElement(authorizations_DropDownMenu).click();
        driver.findElement(recentlyUpdatedAttachments_Button).click();
        return new MPM_RecentlyUpdatedPage(driver);
    }

    public MPM_RecentAttachPage navigateToRecentAttachments()
    {
        driver.findElement(authorizations_DropDownMenu).click();
        driver.findElement(recentAttachments_Button).click();
        return new MPM_RecentAttachPage(driver);
    }

    public MPM_HomePage switchToIntegratedHealthIPA()
    {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.findElement(IPADropDown).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.findElement(integratedHealthIPA_Option).click();
        return this;
    }

    public MPM_HomePage switchToCommunityCareIPA()
    {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.findElement(IPADropDown).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.findElement(communityCareIPA_Option).click();
        return this;
    }
}
