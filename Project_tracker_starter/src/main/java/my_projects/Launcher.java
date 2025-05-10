package my_projects;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import javafx.application.Application;
import project_tracker.application.AppStart;
import project_tracker_backend.ProjectTrackerBackendApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {

    private static ConfigurableApplicationContext springContext;
    private static ExecutorService executorService;

    public static void main(String[] args) {

        // Create a thread pool with two threads: one for the backend and one for the frontend
        executorService = Executors.newFixedThreadPool(2);

        // Run backend in a separate thread
        executorService.submit(() -> {
            try {
                springContext = SpringApplication.run(ProjectTrackerBackendApplication.class, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Run frontend (JavaFX) in the main thread
        executorService.submit(() -> {
            Application.launch(AppStart.class, args);
        });

        // Graceful shutdown: shut down executor service once both frontend and backend are done
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (springContext != null) {
                springContext.close();
            }
            if (executorService != null && !executorService.isShutdown()) {
                executorService.shutdown();
            }
        }));
    }
}
