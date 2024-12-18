package org.example;

import API.Login_API;
import API.UploadMedicalAuthFile_API;
import API.UploadMedicalAuthRecord_API;
import API_Models.UploadMedicalAuthFileResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Utility.FileMover;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class CHG_DownloadPatientsDocumentsLogs {
    private static final Logger logger = LogManager.getLogger(CHG_DownloadPatientsDocumentsLogs.class);
    private WebDriver driver;
    private String filePath;

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        String folderName = "NewFolder_" + System.currentTimeMillis();
        String downloadsPath = System.getProperty("user.home") + "/Downloads";

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
    public void testUntitledTestCase() throws Exception {
        driver.get("https://extra.chgsd.com/login.aspx");
        logger.info("Navigated to login page.");

        // Login
        driver.findElement(By.id("txtLogin")).sendKeys("Nwasily");
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_txtPwd")).sendKeys("Welcome1");
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_btnSubmit")).click();
        logger.info("Logged in successfully.");

        // Expand Referrals Accordion
        driver.findElement(By.xpath("//h3[@id='ui-id-1']/span")).click();
        driver.findElement(By.linkText("Download 90 day report")).click();
        Thread.sleep(1000);
        Select select = new Select(driver.findElement(By.name("referrals-table_length")));
        select.selectByValue("100");
        logger.info("Referrals report download initiated.");

        // API Login
        String token = new Login_API().login("mohamed.wagdy@medai.com", "Mo@2081997");
        logger.info("API login successful, token retrieved.");

        new FileMover().moveFile("Referrals_Last_90_Days");
        File file = new File("90Reports/Referrals_Last_90_Days.csv");

        Response response = new UploadMedicalAuthFile_API().uploadMedicalAuthFile(token, file,"CHG");
        logger.info("File upload response: {}", response.asString());
        Assert.assertEquals(response.statusCode(), 200, "Verify status code is 200");

        List<UploadMedicalAuthFileResponse> apiResponseList = response.body().as(new TypeReference<List<UploadMedicalAuthFileResponse>>() {}.getType());
        String originalTab = driver.getWindowHandle();

        for (UploadMedicalAuthFileResponse apiResponse : apiResponseList) {
            if (apiResponse.isNew || apiResponse.statusChanged) {
                try {
                    Thread.sleep(2000);
                    driver.findElement(By.xpath("//tr[td[contains(text(), '" + apiResponse.authId + "')]]/td/a[text() = 'View']")).click();
                    switchTab(originalTab);
                    Thread.sleep(2000);
                    driver.findElement(By.id("ctl00_ContentPlaceHolder1_btnPrintToPdf")).click();
                    Thread.sleep(5000);
                    driver.close();
                    driver.switchTo().window(originalTab);
                    Thread.sleep(2000);

                    new FileMover().downloadSummaryFile();

                    file = new File("files/summary.pdf");
                    response = new UploadMedicalAuthRecord_API().uploadMedicalAuthRecord(token, file, apiResponse.authId);
                    logger.info("Summary upload response for authId {}: {}", apiResponse.authId, response.asString());
                    Assert.assertEquals(response.statusCode(), 200, "Verify status code is 200");

                    new FileMover().clearFolder("files");
                } catch (NoSuchElementException e) {
                    logger.warn("Element not found for authId {}: {}", apiResponse.authId, e.getMessage());
                } catch (Exception e) {
                    logger.error("An error occurred for authId {}: {}", apiResponse.authId, e.getMessage());
                }
            }
            logger.info("Processed Member ID: {}", apiResponse.authId);
        }
    }

    private void switchTab(String originalTab) {
        Set<String> allTabs = driver.getWindowHandles();
        for (String tab : allTabs) {
            if (!tab.equals(originalTab)) {
                driver.switchTo().window(tab);
                logger.info("Switched to new tab.");
                break;
            }
        }
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
