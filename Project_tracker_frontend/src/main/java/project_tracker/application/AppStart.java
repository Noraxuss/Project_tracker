package project_tracker.application;

import javafx.application.Application;
import javafx.stage.Stage;
import project_tracker.application.scene.SceneEngine;

public class AppStart extends Application {

    @Override
    public void init() {
    }

    @Override
    public void start(Stage primaryStage) {
        SceneEngine sceneEngine = new SceneEngine(primaryStage);

        primaryStage.setTitle("Project Tracker");
        sceneEngine.initializeStage("login");
    }

    @Override
    public void stop() {
    }
}
