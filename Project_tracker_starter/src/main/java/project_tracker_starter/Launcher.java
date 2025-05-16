package project_tracker_starter;

import javafx.application.Application;
import logging.LogFileCleanup;
import project_tracker_starter.utilities.FlagUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import project_tracker_frontend.application.AppStart;
import project_tracker_backend.ProjectTrackerBackendApplication;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Launcher {

    private static ConfigurableApplicationContext springContext;
    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        ExecutorService executorService;

        // Clean up leftover flags before startup
        FlagUtils.deleteFlagFiles();

        executorService = Executors.newFixedThreadPool(2);

        // Start backend
        executorService.submit(() -> {
            try {
                springContext = SpringApplication.run(ProjectTrackerBackendApplication.class, args);
            } catch (Exception e) {
                logger.error("Error starting backend: " + e.getMessage());
                e.printStackTrace();
                FlagUtils.createFlag("flags/backend_failed.flag");
            }
        });

        // Start frontend (JavaFX)
        executorService.submit(() -> Application.launch(AppStart.class, args));

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                logger.warn("Executor did not terminate in the specified time.");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            logger.error("Thread interrupted while waiting for termination.", e);
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // Shutdown logic
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (springContext != null) springContext.close();
            if (!executorService.isShutdown()) executorService.shutdown();

            FlagUtils.deleteFlagFiles(); // Clean up again on exit

            List<String> logPaths = Arrays.asList(
                    "logs/backend.log",
                    "logs/frontend.log",
                    "logs/starter.log"
            );
            LogFileCleanup.cleanupLogsIfEmpty(logPaths);
        }));
    }

}
