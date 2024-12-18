package Pages.CHG;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class HomePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);
    WebDriver driver ;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public HomePage expandProviders()
    {
        driver.findElement(By.id("providerSpanImg")).click();
        return this;
    }

    public HomePage submitAuthorizationLink()
    {
        driver.findElement(By.xpath("//*[@id=\"providerSpan\"]/a[4]")).click();
        return this;
    }

    public HomePage expandReferralsAccordion()
    {
        driver.findElement(By.xpath("//h3[@id='ui-id-1']/span")).click();
        return this;
    }

    public HomePage download90DayReport()
    {
        driver.findElement(By.linkText("Download 90 day report")).click();
        return this ;
    }

    public HomePage adjustReferralTableLength(String length)
    {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Select select = new Select(driver.findElement(By.name("referrals-table_length")));
        select.selectByValue(length);
        return this ;
    }

}
