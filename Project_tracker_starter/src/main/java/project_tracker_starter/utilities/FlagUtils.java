package project_tracker_starter.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FlagUtils {

    private static final String READY_FLAG = "flags/backend_ready.flag";
    private static final String FAILED_FLAG = "flags/backend_failed.flag";

    private static final Logger logger = LoggerFactory.getLogger(FlagUtils.class);

    private FlagUtils() {
        // prevent instantiation
    }

    public static void deleteFlagFiles() {
        deleteIfExists(READY_FLAG);
        deleteIfExists(FAILED_FLAG);
    }

    private static void deleteIfExists(String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            logger.error("Failed to delete flag: " + path + " - " + e.getMessage());
        }
    }

    public static void createFlag(String path) {
        try {
            Files.createDirectories(Paths.get(path).getParent());
            Files.createFile(Paths.get(path));
        } catch (IOException e) {
            logger.error("Failed to create flag: " + path + " - " + e.getMessage());
        }
    }

}
