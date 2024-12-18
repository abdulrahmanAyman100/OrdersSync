package Tests.MPM;

import API.Login_API;
import API.UploadMedicalAuthFile_API;
import API.UploadMedicalAuthRecord_API;
import API_Models.UploadMedicalAuthFileResponse;
import Pages.CHG.HomePage;
import Pages.MPM.MPM_HomePage;
import Pages.MPM.MPM_LoginPage;
import Pages.MPM.MPM_RecentlyUpdatedPage;
import Tests.CHG.DownloadPatientsDocumentsLogs;
import Utility.BrowserActions;
import Utility.FileMover;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.List;

public class MPM_DownloadPatientsDocuments {
    private static final Logger logger = LogManager.getLogger(DownloadPatientsDocumentsLogs.class);
    private WebDriver driver;
    private String filePath;


    @BeforeClass(alwaysRun = true)
    public void setUp() {
        String downloadsPath = System.getProperty("user.home") + "/Downloads";

        String fileName = "recent-authorization-attachments.csv";
        filePath = downloadsPath + File.separator + fileName;
        logger.info("File path for download: {}", filePath);

        // Initialize WebDriver
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        logger.info("WebDriver initialized successfully.");
    }

    @Test
    public void downloadPatientsFiles() throws InterruptedException, AWTException {
        Robot robot = new Robot();

        new MPM_LoginPage(driver)
                .navigate()
                .login("nabil.wasily01" , "Apex!23$")
                .switchToCommunityCareIPA()
                .navigateToRecentlyUpdatedAttachments()
                .enterBackDays("7")
                .search()
                .exportCSVFile();

        Thread.sleep(5000);

        String token = new Login_API().login("mohamed.wagdy@medai.com", "Mo@2081997");
        logger.info("API login successful, token retrieved.");

        new FileMover().moveFile("recently-updated-authorizations");
        File file = new File("90Reports/recently-updated-authorizations.csv");

        Response response = new UploadMedicalAuthFile_API().uploadMedicalAuthFile(token, file , "CCIPA");
        logger.info("File upload response: {}", response.asString());
        java.util.List<UploadMedicalAuthFileResponse> apiResponseList = response.body().as(new TypeReference<List<UploadMedicalAuthFileResponse>>() {}.getType());


        Thread.sleep(5000);


        for (UploadMedicalAuthFileResponse apiResponse : apiResponseList) {
            if (apiResponse.isNew || apiResponse.statusChanged) {
                try {
                    By fileLocator = By.xpath("//a[@href=\"/authorizations/ccipa/" + apiResponse.authId+"\"]");
                    driver.findElement(fileLocator).click();
                    Thread.sleep(5000);
                    BrowserActions.print();
                    BrowserActions.enter();
                    Thread.sleep(2000);
                    BrowserActions.typeText(robot, "summary.pdf");
                    Thread.sleep(2000);
                    BrowserActions.enter();
                    System.out.println("File saved successfully.");
                    driver.navigate().back();
                    Thread.sleep(5000);
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
            }
            logger.info("Processed Member ID: {}", apiResponse.authId);
        }




//        Thread.sleep(5000);
//        new FileMover().downloadSummaryFile();

//        file = new File("files/summary.pdf");
//        response = new UploadMedicalAuthRecord_API().uploadMedicalAuthRecord(token, file, apiResponse.authId);
//        logger.info("Summary upload response for authId {}: {}", apiResponse.authId, response.asString());

//        new FileMover().clearFolder("files");

//
//        // Simulate pressing Enter to save the file
//        robot.keyPress(KeyEvent.VK_ENTER);
//        robot.keyRelease(KeyEvent.VK_ENTER);
//
        new FileMover().clearFolder("files");
        new FileMover().clearFolder("90Reports");


        new MPM_HomePage(driver)
                .switchToIntegratedHealthIPA();

        new MPM_RecentlyUpdatedPage(driver)
                .enterBackDays("7")
                .search()
                .exportCSVFile();

        Thread.sleep(5000);

        new FileMover().moveFile("recently-updated-authorizations");
        file = new File("90Reports/recently-updated-authorizations.csv");

        response = new UploadMedicalAuthFile_API().uploadMedicalAuthFile(token, file , "IHP");
        logger.info("File upload response: {}", response.asString());
        apiResponseList = response.body().as(new TypeReference<List<UploadMedicalAuthFileResponse>>() {}.getType());


        Thread.sleep(5000);


        for (UploadMedicalAuthFileResponse apiResponse : apiResponseList) {
            if (apiResponse.isNew || apiResponse.statusChanged) {
                try {
                    By fileLocator = By.xpath("//a[@href=\"/authorizations/ihp/" + apiResponse.authId + "\"]");
                    driver.findElement(fileLocator).click();
                    Thread.sleep(5000);
                    BrowserActions.print();
                    BrowserActions.enter();
                    Thread.sleep(2000);
                    BrowserActions.typeText(robot, "summary.pdf");
                    Thread.sleep(2000);
                    BrowserActions.enter();
                    System.out.println("File saved successfully.");
                    driver.navigate().back();
                    Thread.sleep(5000);
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
            }
            logger.info("Processed Member ID: {}", apiResponse.authId);
        }



    }






    @AfterClass(alwaysRun = true)
    public void tearDown() throws InterruptedException {
        Thread.sleep(5000);
        new FileMover().clearFolder("files");
        new FileMover().clearFolder("90Reports");
        Thread.sleep(2000);
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver closed successfully.");
        }
    }
}
