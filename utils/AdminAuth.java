package utils;

import java.util.Set;

public class AdminAuth {

    private static final String ADMIN_PASSWORD = "admin123";

    private static final Set<String> PROTECTED = Set.of("gui", "cli", "core", "utils", "logs");

    public static boolean isProtected(String name) {
        String baseName = name.replaceAll("[/\\\\].*", "").toLowerCase();
        return PROTECTED.contains(baseName);
    }

    public static boolean checkPassword(String input) {
        return ADMIN_PASSWORD.equals(input);
    }
}
