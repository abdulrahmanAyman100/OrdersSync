package org.example;

import Listener.TestListener;
import org.testng.TestNG;

public class MainClass {
    public static void main(String[] args) {
        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[] { CHG_DownloadPatientsDocuments_NEW.class });
        testng.addListener(new TestListener());
        testng.run();
    }
}