package Utility;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Set;

public class BrowserActions {

    WebDriver driver ;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
    }

    public void pasteText(By textBox , String value)
    {
        driver.findElement(textBox).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        
        StringSelection selection = new StringSelection(value);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        robot.keyPress(KeyEvent.VK_CONTROL); // Press Ctrl
        robot.keyPress(KeyEvent.VK_V);       // Press V
        robot.keyRelease(KeyEvent.VK_V);     // Release V
        robot.keyRelease(KeyEvent.VK_CONTROL); // Release Ctrl
    }

    public void switchTab(int tab) {

        Set<String> windowHandles = driver.getWindowHandles();
        ArrayList<String> tabs = new ArrayList<>(windowHandles);
        // Switch to the second tab (index 1)
        driver.switchTo().window(tabs.get(tab));
    }

    public static void print() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_P);
        Thread.sleep(2000);
        robot.keyRelease(KeyEvent.VK_P);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    public static void enter() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public static void typeText(Robot robot, String text) {
        // Simulate typing the file name
        for (char c : text.toCharArray()) {
            robot.keyPress(Character.toUpperCase(c));
            robot.keyRelease(Character.toUpperCase(c));
            try {
                Thread.sleep(100); // Small delay for realistic typing
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
