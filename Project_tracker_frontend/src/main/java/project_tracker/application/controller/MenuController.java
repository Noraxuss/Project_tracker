package project_tracker.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import project_tracker.application.scene.SceneEngine;
import project_tracker.application.scene.SceneEngineAware;

public class MenuController implements SceneEngineAware {

    private SceneEngine sceneEngine;

    @FXML
    public Button projectsButton;
    @FXML
    public Button optionsButton;
    @FXML
    public Button exitButton;

    @FXML
    public void handleProjects(ActionEvent actionEvent) {
        sceneEngine.switchScene("projects");
    }

    @FXML
    public void handleOptions(ActionEvent actionEvent) {

    }

    @FXML
    public void handleExit(ActionEvent actionEvent) {

    }

    @Override
    public void setSceneEngine(SceneEngine engine) {
        this.sceneEngine = engine;
    }
}



