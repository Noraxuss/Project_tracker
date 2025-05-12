package project_tracker_backend.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project_tracker_backend.config.LoggingAspect;

import java.io.File;
import java.io.IOException;

public class CreateFlag {

    private static final Logger logger = LoggerFactory.getLogger(CreateFlag.class);

    private CreateFlag() {
        // Private constructor to prevent instantiation
    }

    protected static void createFlag(String flagpath) {
        try {
            File flagFile = new File(flagpath);
            File parentDir = flagFile.getParentFile();
            if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
                    logger.error("Failed to create directories for flag file: {}", parentDir.getAbsolutePath());
                    return;
                }


            if (flagFile.createNewFile()) {
                logger.info("Created flag file: {}", flagFile.getAbsolutePath());
            } else {
                logger.warn("Flag file already exists: {}", flagFile.getAbsolutePath());
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Error creating flag file: " + e.getMessage(), e);
        }

    }
}
