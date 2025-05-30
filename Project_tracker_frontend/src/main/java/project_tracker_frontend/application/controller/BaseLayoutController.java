package project_tracker_frontend.application.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import lombok.Getter;
import org.controlsfx.control.HiddenSidesPane;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;

public class BaseLayoutController implements SceneEngineAware {

    private SceneEngine sceneEngine;

    @Getter
    @FXML
    public HiddenSidesPane contentPane;

    public void setContentToContentPane(Node node) {
        contentPane.setContent(node);
    }

    public void setLeftSideBarToLeftSide(Node node) {
        contentPane.setLeft(node);
    }

    public void handleNewProject(ActionEvent actionEvent) {

    }

    public void handleOpen(ActionEvent actionEvent) {

    }

    public void handleExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void handleAbout(ActionEvent actionEvent) {

    }

    public void setContentPane() {
        // This method can be overridden by subclasses to perform actions when the scene is loaded
        this.contentPane = new HiddenSidesPane();
    }

    @FXML
    public void initialize() {
        // This method can be overridden by subclasses to perform actions when the scene is loaded
        sceneEngine.setHiddenSidesPane(contentPane);
        sceneEngine.setLayoutController(this);
    }

    @Override
    public void setSceneEngine(SceneEngine engine) {
        this.sceneEngine = engine;
    }
}
