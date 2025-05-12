package my_projects;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import project_tracker.application.AppStart;
import project_tracker_backend.ProjectTrackerBackendApplication;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {

    private static ConfigurableApplicationContext springContext;


    public static void main(String[] args) {
        ExecutorService executorService;

        // Clean up leftover flags before startup
        cleanFlagFiles();

        executorService = Executors.newFixedThreadPool(2);

        // Start backend
        executorService.submit(() -> {
            try {
                springContext = SpringApplication.run(ProjectTrackerBackendApplication.class, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Start frontend (JavaFX)
        executorService.submit(() -> {
            Application.launch(AppStart.class, args);
        });

        // Shutdown logic
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (springContext != null) springContext.close();
            if (!executorService.isShutdown()) executorService.shutdown();

            cleanFlagFiles(); // Clean up again on exit
        }));
    }

    private static void cleanFlagFiles() {
        File readyFlag = new File("flags/backend_ready.flag");
        File errorFlag = new File("flags/backend_failed.flag");
        if (readyFlag.exists()) {
            readyFlag.delete();
        }
        if (errorFlag.exists()) {
            errorFlag.delete();
        }
    }
}
