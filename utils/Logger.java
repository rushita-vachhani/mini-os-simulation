package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {

    private static final String LOG_FILE = "logs/log.txt";

    public static void log(String level, String message) {
        try {
            java.io.File dir = new java.io.File("logs");
            if (!dir.exists()) {
                dir.mkdir();
            }

            FileWriter writer = new FileWriter(LOG_FILE, true);
            writer.write(LocalDateTime.now() + " [" + level + "] " + message + "\n");
            writer.close();

        } catch (IOException e) {
            System.out.println("Logging failed: " + e.getMessage());
        }
    }
}