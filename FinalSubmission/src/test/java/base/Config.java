package base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reads settings from environment variables first, then falls back to
 * the project's .env file (which is git-ignored), so the tests work
 * without running "export" in every new terminal.
 */
public final class Config {

    private static final Map<String, String> DOT_ENV = loadDotEnv();

    private Config() {
    }

    public static String get(String key) {
        String value = System.getenv(key);
        if (value != null && !value.isBlank()) {
            return value;
        }
        return DOT_ENV.get(key);
    }

    private static Map<String, String> loadDotEnv() {
        Map<String, String> values = new HashMap<>();
        Path file = Path.of(".env");

        if (!Files.exists(file)) {
            return values;
        }

        try {
            List<String> lines = Files.readAllLines(file);
            for (String line : lines) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")
                        || !trimmed.contains("=")) {
                    continue;
                }
                String[] parts = trimmed.split("=", 2);
                String value = parts[1].trim();
                if (value.length() >= 2
                        && ((value.startsWith("\"") && value.endsWith("\""))
                        || (value.startsWith("'") && value.endsWith("'")))) {
                    value = value.substring(1, value.length() - 1);
                }
                values.put(parts[0].trim(), value);
            }
        } catch (IOException e) {
            System.err.println("Could not read .env: " + e.getMessage());
        }
        return values;
    }
}
