package core;

import utils.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class FileOperations {

    public static String listFiles() {
        File dir = new File(".");
        File[] files = dir.listFiles();

        if (files == null || files.length == 0) {
            return "No files found.";
        }

        StringBuilder result = new StringBuilder("Files:\n");
        for (File file : files) {
            result.append(file.getName()).append("\n");
        }

        Logger.log("INFO", "Listed files");
        return result.toString();
    }

    public static String createFile(String name) {
        try {
            File file = new File(name);

            if (file.createNewFile()) {
                Logger.log("INFO", "Created file: " + name);
                return name + " created successfully.";
            } else {
                Logger.log("WARN", "File already exists: " + name);
                return "File already exists.";
            }

        } catch (IOException e) {
            Logger.log("ERROR", "Create failed: " + e.getMessage());
            return "Error creating file: " + e.getMessage();
        }
    }

    public static String renameFile(String oldName, String newName) {
        File oldFile = new File(oldName);
        File newFile = new File(newName);

        if (!oldFile.exists()) {
            Logger.log("ERROR", "Rename failed: file not found " + oldName);
            return "File does not exist.";
        }

        if (oldFile.renameTo(newFile)) {
            Logger.log("INFO", "Renamed " + oldName + " to " + newName);
            return "Renamed successfully.";
        } else {
            Logger.log("ERROR", "Rename failed");
            return "Rename failed.";
        }
    }

    public static String moveFile(String source, String destination) {
        try {
            Path srcPath = Path.of(source);
            Path destPath = Path.of(destination);

            if (!Files.exists(srcPath)) {
                return "Source file not found.";
            }

            if (destPath.getParent() != null) {
                Files.createDirectories(destPath.getParent());
            }

            Files.move(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);

            Logger.log("INFO", "Moved " + source + " to " + destination);
            return "File moved successfully.";

        } catch (IOException e) {
            Logger.log("ERROR", "Move failed: " + e.getMessage());
            return "Error moving file: " + e.getMessage();
        }
    }

    public static String deleteFile(String name) {
        File file = new File(name);

        if (!file.exists()) {
            return "File not found.";
        }

        if (file.delete()) {
            Logger.log("INFO", "Deleted file: " + name);
            return "File deleted.";
        } else {
            Logger.log("ERROR", "Delete failed");
            return "Delete failed.";
        }
    }
}