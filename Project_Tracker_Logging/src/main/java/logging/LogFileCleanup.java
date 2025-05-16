package logging;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class LogFileCleanup {

    private LogFileCleanup() {
        // Prevent instantiation
    }

    public static void cleanupLogsIfEmpty(List<String> logFilePath) {
        try {
            List<Path> paths = logFilePath.stream()
                    .map(Path::of)
                    .toList();

            AtomicBoolean thereWasAnError = new AtomicBoolean(false);
            paths.forEach(path -> {
                if (checkForErrors(path)) {
                    thereWasAnError.set(true);
                }
            });

            if (thereWasAnError.get()) {
                permanentlySaveLogFiles(paths);
            }

            System.out.println("Deleting original log files.");
            paths.forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (Exception e) {
                    System.err.println("Error deleting log file: " + e.getMessage());
                }
            });

        } catch (Exception e) {
            System.err.println("Error checking log files: " + e.getMessage());
        }
    }

    private static void permanentlySaveLogFiles(List<Path> paths) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        Path directoryPath = createDirectory(timestamp);

        paths.forEach(path -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
                List<String> lines = reader.lines().toList();

                // Determine suffix based on original filename
                String originalName = path.getFileName().toString().toLowerCase();
                String suffix;
                if (originalName.contains("backend")) {
                    suffix = "backend";
                } else if (originalName.contains("frontend")) {
                    suffix = "frontend";
                } else if (originalName.contains("starter")) {
                    suffix = "starter";
                } else {
                    suffix = "log";
                }

                // Create new file name: timestamp_suffix.log
                String newFileName = timestamp + "_" + suffix + ".log";
                Path newLogFile = directoryPath.resolve(newFileName);

                try (BufferedWriter writer = Files.newBufferedWriter(newLogFile)) {
                    for (String line : lines) {
                        // Wrap long lines before writing
                        List<String> wrappedLines = wrapLine(line, 100);
                        for (String wrappedLine : wrappedLines) {
                            writer.write(wrappedLine);
                            writer.newLine();
                        }
                    }
                }

                System.out.println("Saved log file: " + newLogFile);

            } catch (FileNotFoundException e) {
                System.out.println("Log file not found: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error copying log file: " + e.getMessage());
            }
        });
    }

    private static List<String> wrapLine(String line, int maxLength) {
        List<String> wrappedLines = new ArrayList<>();
        int start = 0;

        while (start < line.length()) {
            int end = Math.min(start + maxLength, line.length());
            if (end < line.length() && line.charAt(end) != ' ') {
                int lastSpace = line.lastIndexOf(' ', end);
                if (lastSpace > start) {
                    end = lastSpace;
                }
            }
            wrappedLines.add(line.substring(start, end).trim());
            start = end + 1; // Skip the space
        }

        return wrappedLines;
    }

    private static Path createDirectory(String timestamp) {
        try {
            Path logDir = Paths.get("logs", timestamp);
            Files.createDirectories(logDir);
            return logDir;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create log directory", e);
        }
    }

    private static boolean checkForErrors(Path path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            return reader.lines()
                    .map(line -> line.split(" - ")[1]) // assumes format is always valid
                    .anyMatch(level -> level.contains("ERROR") ||
                            level.contains("WARN"));
        } catch (FileNotFoundException e) {
            System.out.println("Log file not found: " + e.getMessage());
            return true; // treat as error
        } catch (Exception e) {
            System.out.println("Error reading log file: " + e.getMessage());
            return true; // treat as error
        }
    }
}
