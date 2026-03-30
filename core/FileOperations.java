package core;

import utils.AdminAuth;
import utils.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileOperations {

    public static String getCurrentDirectory() {
        return new File(".").getAbsolutePath().replaceAll("\\.$", "");
    }

    public static String listFiles() {
        File dir = new File(".");
        File[] files = dir.listFiles();

        if (files == null || files.length == 0) {
            return "No files found.";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        StringBuilder result = new StringBuilder(
            String.format("%-30s %-10s %-8s %s%n", "Name", "Type", "Size", "Last Modified")
        );
        result.append("-".repeat(65)).append("\n");

        for (File file : files) {
            String type = file.isDirectory() ? "[DIR]" : "[FILE]";
            String size = file.isDirectory() ? "-" : file.length() + " B";
            String modified = sdf.format(new Date(file.lastModified()));
            result.append(String.format("%-30s %-10s %-8s %s%n",
                file.getName(), type, size, modified));
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

    public static boolean isProtected(String name) {
        return AdminAuth.isProtected(name);
    }

    public static String deleteFile(String name, String password) {
        if (AdminAuth.isProtected(name)) {
            if (!AdminAuth.checkPassword(password)) {
                Logger.log("WARN", "Unauthorized delete attempt on protected path: " + name);
                return "Access denied. Incorrect admin password.";
            }
        }

        File file = new File(name);
        if (!file.exists()) {
            return "File not found.";
        }

        try {
            File trashDir = new File("trash");
            if (!trashDir.exists()) trashDir.mkdir();

            Path src  = file.toPath();
            Path dest = Path.of("trash/" + file.getName());
            Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);

            Logger.log("INFO", "Moved to trash: " + name);
            return "\"" + name + "\" moved to trash. Use 'restore' to recover or 'emptytrash' to delete permanently.";
        } catch (IOException e) {
            Logger.log("ERROR", "Move to trash failed: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }

    public static String listTrash() {
        File trashDir = new File("trash");
        if (!trashDir.exists() || trashDir.listFiles() == null || trashDir.listFiles().length == 0) {
            return "Trash is empty.";
        }

        StringBuilder result = new StringBuilder("Trash:\n");
        for (File f : trashDir.listFiles()) {
            result.append("  ").append(f.getName()).append("\n");
        }
        return result.toString();
    }

    public static String restoreFile(String name) {
        File trashFile = new File("trash/" + name);
        if (!trashFile.exists()) {
            return "\"" + name + "\" not found in trash.";
        }

        try {
            Files.move(trashFile.toPath(), Path.of(name), StandardCopyOption.REPLACE_EXISTING);
            Logger.log("INFO", "Restored from trash: " + name);
            return "\"" + name + "\" restored successfully.";
        } catch (IOException e) {
            Logger.log("ERROR", "Restore failed: " + e.getMessage());
            return "Error restoring file: " + e.getMessage();
        }
    }

    public static String emptyTrash() {
        File trashDir = new File("trash");
        if (!trashDir.exists() || trashDir.listFiles() == null || trashDir.listFiles().length == 0) {
            return "Trash is already empty.";
        }

        int count = 0;
        for (File f : trashDir.listFiles()) {
            if (f.delete()) count++;
        }

        Logger.log("INFO", "Emptied trash: " + count + " file(s) permanently deleted");
        return count + " file(s) permanently deleted from trash.";
    }


    public static String makeDirectory(String name) {
        File dir = new File(name);
        if (dir.exists()) {
            Logger.log("WARN", "Directory already exists: " + name);
            return "Directory already exists: " + name;
        }
        if (dir.mkdirs()) {
            Logger.log("INFO", "Created directory: " + name);
            return "Directory created: " + name;
        } else {
            Logger.log("ERROR", "Failed to create directory: " + name);
            return "Failed to create directory: " + name;
        }
    }

    public static String copyFile(String source, String destination) {
        try {
            Path srcPath = Path.of(source);
            Path destPath = Path.of(destination);

            if (!Files.exists(srcPath)) {
                return "Source file not found: " + source;
            }

            if (destPath.getParent() != null) {
                Files.createDirectories(destPath.getParent());
            }

            Files.copy(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);

            Logger.log("INFO", "Copied " + source + " to " + destination);
            return "File copied: " + source + " → " + destination;

        } catch (IOException e) {
            Logger.log("ERROR", "Copy failed: " + e.getMessage());
            return "Error copying file: " + e.getMessage();
        }
    }

    public static String readLogs() {
        File logFile = new File("logs/log.txt");
        if (!logFile.exists()) return "No logs found.";

        try {
            String content = new String(Files.readAllBytes(logFile.toPath()));
            return content.isEmpty() ? "Log file is empty." : content;
        } catch (IOException e) {
            return "Error reading logs: " + e.getMessage();
        }
    }

    public static String searchFile(String keyword) {
        StringBuilder result = new StringBuilder("Search Results:\n");
        searchRecursive(new File("."), keyword, result);

        if (result.toString().equals("Search Results:\n")) {
            return "No matching files found.";
        }

        Logger.log("INFO", "Searched for: " + keyword);
        return result.toString();
    }

    private static void searchRecursive(File dir, String keyword, StringBuilder result) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.getName().contains(keyword)) {
                // Show relative path from working directory
                String relativePath = new File(".").toURI().relativize(file.toURI()).getPath();
                result.append(relativePath).append("\n");
            }
            if (file.isDirectory()) {
                searchRecursive(file, keyword, result);
            }
        }
    }
}