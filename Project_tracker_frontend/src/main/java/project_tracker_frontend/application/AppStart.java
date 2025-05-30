package project_tracker_frontend.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import project_tracker_frontend.application.scene.SceneEngine;

public class AppStart extends Application {

    @Override
    public void init() {
    }

    @Override
    public void start(Stage primaryStage) {
        SceneEngine sceneEngine = new SceneEngine(primaryStage);
        primaryStage.setTitle("Project Tracker");

        sceneEngine.initializeStage("base", "loading");
    }

    @Override
    public void stop() {
        Platform.exit();
    }

    public static void shutDown() {
        Platform.exit();
    }
}
