package project_tracker_frontend.application.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import project_tracker_frontend.application.AppStart;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.BackendCheckerService;
import project_tracker_frontend.application.service.BackendCheckerServiceAware;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LoadingController implements BackendCheckerServiceAware, SceneEngineAware {

    private SceneEngine sceneEngine;
    private BackendCheckerService backendCheckerService;

    @FXML
    private Label statusLabel;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Button showLogsButton;
    @FXML
    private TextArea logArea;

    private final ScheduledExecutorService executor =
            Executors.newSingleThreadScheduledExecutor();

    @FXML
    public void initialize() {
        checkBackendFlag();
    }

    private void checkBackendFlag() {
        executor.scheduleAtFixedRate(() -> {
            File readyFlag = new File("flags/backend_ready.flag");
            File failedFlag = new File("flags/backend_failed.flag");

            if (readyFlag.exists()) {
                Platform.runLater(() -> {
                    statusLabel.setText("Backend is ready!");
                    progressIndicator.setVisible(false);
                    if (backendCheckerService.checkBackend()) {
                        proceedToNextScene();
                    } else {
                        statusLabel.setText("Backend is not reachable.");
                    }
                });
                executor.shutdown(); // Stop checking
            } else if (failedFlag.exists()) {
                Platform.runLater(() -> {
                    statusLabel.setText("Backend failed to start.");
                    progressIndicator.setVisible(false);
                    enableLogButton();
                    AppStart.shutDown();
                });
                executor.shutdown(); // Stop checking
            } else {
                Platform.runLater(() -> {
                    statusLabel.setText("Waiting for backend...");
                    progressIndicator.setVisible(true);
                });
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
        sceneEngine.switchScene("login");
    }

    @Override
    public void setBackendCheckerService(BackendCheckerService backendCheckerService) {
        this.backendCheckerService = backendCheckerService;
    }

    @Override
    public void setSceneEngine(SceneEngine engine) {
        this.sceneEngine = engine;
    }
}

