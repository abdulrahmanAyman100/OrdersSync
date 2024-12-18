package Tests.CHG;

import Pages.CHG.*;
import Utility.BrowserActions;
import Utility.FileMover;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class SubmitAuthorization {
    private static final Logger logger = LogManager.getLogger(SubmitAuthorization.class);
    private WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {

        // Initialize WebDriver
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        logger.info("WebDriver initialized successfully.");

    }

    @Test
    public void testUntitledTestCase() throws Exception {

        new LoginPage(driver)
                .navigate()
                .login("Nwasily" , "Welcome1")
                .expandProviders()
                .submitAuthorizationLink();

        new BrowserActions(driver)
                .switchTab(1);

        new ServiceRequestPage_01(driver)
                .selectProvider()
                .enterContactData("Apex" , "(760) 822-9560" , "(111) 111-1111" , "Apex@apex.com")
                .clickNext();


        new ServiceRequestPage_02(driver)
                .searchForPatient("A")
                .selectPatient(0)
                .confirmEligibility()
                .clickNext();

        new ServiceRequestPage_03(driver)
                .selectSpeciality("Acupuncture")
                .selectFacility("180 MEDICAL")
                .clickNext();

        new ServiceRequestPage_04(driver)
                .selectDiagnosisCode(0)
                .clickNext();

        new ServiceRequestPage_05(driver)
                .addDescription()
                .addServiceCode("22222")
                .clickNext();
    }


    @AfterClass(alwaysRun = true)
    public void tearDown() throws InterruptedException {
        new FileMover().clearFolder("90Reports");
        Thread.sleep(2000);
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver closed successfully.");
        }
    }
}
