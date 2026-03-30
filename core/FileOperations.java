package core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileOperations {

    // 1. List files
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

        return result.toString();
    }

    // 2. Create file
    public static String createFile(String name) {
        try {
            File file = new File(name);

            if (file.createNewFile()) {
                return name + " created successfully.";
            } else {
                return "File already exists.";
            }

        } catch (IOException e) {
            return "Error creating file: " + e.getMessage();
        }
    }

    // 3. Rename file
    public static String renameFile(String oldName, String newName) {
        File oldFile = new File(oldName);
        File newFile = new File(newName);

        if (!oldFile.exists()) {
            return "File does not exist.";
        }

        if (oldFile.renameTo(newFile)) {
            return "Renamed successfully.";
        } else {
            return "Rename failed.";
        }
    }

    // 4. Move file
    public static String moveFile(String source, String destination) {
        try {
            Path srcPath = Path.of(source);
            Path destPath = Path.of(destination);

            Files.move(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
            return "File moved successfully.";

        } catch (IOException e) {
            return "Error moving file: " + e.getMessage();
        }
    }
}