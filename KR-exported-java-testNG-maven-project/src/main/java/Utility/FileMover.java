package Utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileMover {
    public void downloadSummaryFile()
    {
        // Set the Downloads folder path
        String downloadsPath = System.getProperty("user.home") + File.separator + "Downloads";
        File downloadsFolder = new File(downloadsPath);

        // Get the current timestamp and format it
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        File destinationFolder = new File("files");

        // Create a folder with the timestamp in the current directory
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }

        // Get the current time
        long currentTimeMillis = System.currentTimeMillis();

        // Filter files downloaded in the last 2 minutes and containing "summary" in the name
        if (downloadsFolder.exists() && downloadsFolder.isDirectory()) {
            File[] recentFiles = downloadsFolder.listFiles(file -> {
                return file.isFile() &&
                        (currentTimeMillis - file.lastModified()) <= (2 * 60 * 1000) &&
                        file.getName().toLowerCase().contains("summary");
            });

            if (recentFiles != null && recentFiles.length > 0) {
                for (File file : recentFiles) {
                    try {
                        // Move each file to the destination folder
                        Path sourcePath = file.toPath();
                        Path destinationPath = destinationFolder.toPath().resolve(file.getName());
                        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Moved: " + file.getName());
                    } catch (IOException e) {
                        System.err.println("Failed to move file: " + file.getName());
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("No files found within the last 2 minutes containing 'summary'.");
            }
        } else {
            System.out.println("Downloads folder not found.");
        }
    }

    public void moveFile(String fileName)
    {
        // Set the Downloads folder path
        String downloadsPath = System.getProperty("user.home") + File.separator + "Downloads";
        File downloadsFolder = new File(downloadsPath);

        // Get the current timestamp and format it
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        File destinationFolder = new File("90Reports");

        // Create a folder with the timestamp in the current directory
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }

        // Get the current time
        long currentTimeMillis = System.currentTimeMillis();

        // Filter files downloaded in the last 2 minutes and containing "summary" in the name
        if (downloadsFolder.exists() && downloadsFolder.isDirectory()) {
            File[] recentFiles = downloadsFolder.listFiles(file -> {
                return file.isFile() &&
                        (currentTimeMillis - file.lastModified()) <= (2 * 60 * 1000) &&
                        file.getName().toLowerCase().contains(fileName);
            });

            if (recentFiles != null && recentFiles.length > 0) {
                for (File file : recentFiles) {
                    try {
                        // Move each file to the destination folder
                        Path sourcePath = file.toPath();
                        Path destinationPath = destinationFolder.toPath().resolve(file.getName());
                        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Moved: " + file.getName());
                    } catch (IOException e) {
                        System.err.println("Failed to move file: " + file.getName());
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("No files found within the last 2 minutes containing ." + fileName);
            }
        } else {
            System.out.println("Downloads folder not found.");
        }
    }


    public void clearFolder(String folderName) {
        File folder = new File(folderName);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        if (file.delete()) {
                            System.out.println("Deleted: " + file.getName());
                        } else {
                            System.err.println("Failed to delete: " + file.getName());
                        }
                    }
                }
            }
        } else {
            System.out.println("Folder does not exist or is not a directory: " + folder.getPath());
        }
    }
}
