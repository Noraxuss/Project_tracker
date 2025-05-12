package my_projects.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FlagUtils {

    private static final String READY_FLAG = "flags/backend_ready.flag";
    private static final String FAILED_FLAG = "flags/backend_failed.flag";

    private FlagUtils() {
        // prevent instantiation
    }

    public static void deleteFlagFiles() {
        deleteIfExists(READY_FLAG);
        deleteIfExists(FAILED_FLAG);
    }

    public static void deleteIfExists(String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            System.err.println("Failed to delete flag: " + path + " - " + e.getMessage());
        }
    }

    public static void createFlag(String path) {
        try {
            Files.createDirectories(Paths.get(path).getParent());
            Files.createFile(Paths.get(path));
        } catch (IOException e) {
            System.err.println("Failed to create flag: " + path + " - " + e.getMessage());
        }
    }

}
