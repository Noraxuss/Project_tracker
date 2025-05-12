package project_tracker.application.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import project_tracker.application.service.BackendCheckerService;
import project_tracker.application.service.BackendCheckerServiceAware;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LoadingController implements BackendCheckerServiceAware {

    private BackendCheckerService backendCheckerService;

    @FXML private Label statusLabel;
    @FXML private ProgressIndicator progressIndicator;
    @FXML private Button showLogsButton;
    @FXML private TextArea logArea;

    private final ScheduledExecutorService executor =
            Executors.newSingleThreadScheduledExecutor();

    @FXML
    public void initialize() {
        checkBackendFlag();
    }

    private void checkBackendFlag() {
        executor.scheduleAtFixedRate(() -> {
            File flagFile = new File("flags/backend.flag");
            if (flagFile.exists()) {
                Platform.runLater(() -> {
                    statusLabel.setText("Backend is ready!");
                    progressIndicator.setVisible(false);
                    proceedToNextScene(); // You can trigger a scene change here
                });
                executor.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    @FXML
    private void onShowLogs() {
        try {
            Path logPath = Paths.get("backend.log");
            String content = Files.readString(logPath);
            logArea.setText(content);
            logArea.setVisible(true);
        } catch (IOException e) {
            logArea.setText("Failed to load logs.");
        }
    }

    public void enableLogButton() {
        showLogsButton.setVisible(true);
    }

    private void proceedToNextScene() {
        // You can use your SceneEngine here
    }

    @Override
    public void setBackendCheckerService(BackendCheckerService backendCheckerService) {
        this.backendCheckerService=backendCheckerService;
    }
}

