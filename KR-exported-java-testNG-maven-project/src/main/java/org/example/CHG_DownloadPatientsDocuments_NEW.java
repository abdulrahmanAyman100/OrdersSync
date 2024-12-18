package org.example;

import API.Login_API;
import API.UploadMedicalAuthFile_API;
import API.UploadMedicalAuthRecord_API;
import API_Models.UploadMedicalAuthFileResponse;
import Pages.CHG.HomePage;
import Pages.CHG.LoginPage;
import Utility.BrowserActions;
import Utility.FileMover;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class CHG_DownloadPatientsDocuments_NEW {
    private static final Logger logger = LogManager.getLogger(CHG_DownloadPatientsDocuments_NEW.class);
    private WebDriver driver;
    private String filePath;

    @BeforeClass(alwaysRun = true)
    public void setUp()  {
        String downloadsPath = System.getProperty("user.home") + File.separator + "Downloads";

        String fileName = "Referrals_Last_90_Days.csv";
        filePath = downloadsPath + File.separator + fileName;
        logger.info("File path for download: {}", filePath);

        // Initialize WebDriver
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        logger.info("WebDriver initialized successfully.");
    }

    @Test
    public void testUntitledTestCase()  {

        new LoginPage(driver)
                .navigate()
                .login("Nwasily" , "Welcome1");

        logger.info("Navigated to login page.");
        logger.info("Logged in successfully.");

        // Expand Referrals Accordion

        new HomePage(driver)
                .expandReferralsAccordion()
                .download90DayReport()
                .adjustReferralTableLength("100");

        logger.info("Referrals report download initiated.");

        // API Login
        String token = new Login_API().login("drkafri@kafri.com", "Apex@#1200");
        logger.info("API login successful, token retrieved.");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new FileMover().moveFile("90");
        File file = new File("90Reports/Referrals_Last_90_Days.csv");

        Response response = new UploadMedicalAuthFile_API().uploadMedicalAuthFile(token, file,"CHG");
        logger.info("File upload response: {}", response.asString());

        List<UploadMedicalAuthFileResponse> apiResponseList = response.body().as(new TypeReference<List<UploadMedicalAuthFileResponse>>() {}.getType());

        for (UploadMedicalAuthFileResponse apiResponse : apiResponseList) {
            if (apiResponse.isNew || apiResponse.statusChanged) {
                try {
                    Thread.sleep(2000);
                    driver.findElement(By.xpath("//tr[td[contains(text(), '" + apiResponse.authId + "')]]/td/a[text() = 'View']")).click();
                    new BrowserActions(driver).switchTab(1);
                    Thread.sleep(2000);
                    driver.findElement(By.id("ctl00_ContentPlaceHolder1_btnPrintToPdf")).click();
                    Thread.sleep(5000);
                    driver.close();
                    new BrowserActions(driver).switchTab(0);
                    Thread.sleep(2000);

                    new FileMover().downloadSummaryFile();

                    file = new File("files/summary.pdf");
                    response = new UploadMedicalAuthRecord_API().uploadMedicalAuthRecord(token, file, apiResponse.authId);
                    logger.info("Summary upload response for authId {}: {}", apiResponse.authId, response.asString());

                    new FileMover().clearFolder("files");
                } catch (NoSuchElementException e) {
                    logger.warn("Element not found for authId {}: {}", apiResponse.authId, e.getMessage());
                } catch (Exception e) {
                    logger.error("An error occurred for authId {}: {}", apiResponse.authId, e.getMessage());
                }
                logger.info("Processed Member ID: {}", apiResponse.authId);
            }
        }

        logger.info("Flow has Passed !!");

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
