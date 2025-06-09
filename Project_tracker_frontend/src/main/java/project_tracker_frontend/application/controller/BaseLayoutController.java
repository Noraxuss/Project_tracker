package project_tracker_frontend.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;

public class BaseLayoutController implements SceneEngineAware {

    public SplitPane mainSplitPane;
    public VBox leftSidebar;
    public StackPane contentPane;
    private SceneEngine sceneEngine;

    private boolean sidebarVisible = true;

    public void handleToggleSidebar() {
        if (sidebarVisible) {
            mainSplitPane.getItems().remove(leftSidebar);
        } else {
            mainSplitPane.getItems().addFirst(leftSidebar); // Add sidebar back at index 0
            mainSplitPane.setDividerPositions(0.25);
        }
        sidebarVisible = !sidebarVisible;
    }

    public void setContentToContentPane(Node node) {
        contentPane.getChildren().setAll(node);
    }

    public void setLeftSideBarToLeftSide(Node node) {
        leftSidebar.getChildren().setAll(node);
    }

    public void handleNewProject(ActionEvent actionEvent) {
        sceneEngine.switchScene("create_project");
    }

    public void handleNewStatus(ActionEvent actionEvent) {
        sceneEngine.switchScene("create_status");
    }

    public void handleOpen(ActionEvent actionEvent) {
    }

    public void handleExit(ActionEvent actionEvent) {
    }

    public void handleAbout(ActionEvent actionEvent) {

    }

    @FXML
    public void initialize() {
        // This method can be overridden by subclasses to perform actions when the scene is loaded
        sceneEngine.setLayoutController(this);
    }

    @Override
    public void setSceneEngine(SceneEngine engine) {
        this.sceneEngine = engine;
    }

    public void handleCreateStatus(ActionEvent actionEvent) {

    }

    public void handleEditStatus(ActionEvent actionEvent) {

    }

    public void handleDeleteStatus(ActionEvent actionEvent) {

    }
}
