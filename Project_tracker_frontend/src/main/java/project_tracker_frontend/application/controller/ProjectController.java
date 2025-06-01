package project_tracker_frontend.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import project_tracker_frontend.application.domain.ProjectListModule;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.ProjectService;
import project_tracker_frontend.application.service.ProjectServiceAware;

import java.util.List;

public class ProjectController implements SceneEngineAware, ProjectServiceAware {

    private SceneEngine sceneEngine;

    private ProjectService projectService;

    @FXML
    public Button createProjectButton;

    @FXML
    public Button viewProjectButton;

    @FXML
    public Button editProjectButton;

    @FXML
    public Button pauseProjectButton;

    @FXML
    public Button resumeProjectButton;

    @FXML
    public Button completeProjectButton;

    @FXML
    public Button deleteProjectButton;

    @FXML
    public Button backButton;

    @FXML
    public Label systemResponseLabel;

    @FXML
    public ListView<ProjectListModule> projectListView;

    @FXML
    public void handleCreateProject(ActionEvent actionEvent) {
        sceneEngine.switchScene("create_project");
    }
    @FXML
    public void handleViewProject(ActionEvent actionEvent) {
//        projectService.setProjectSession(projectListView.)
    }
    @FXML
    public void handleEditProject(ActionEvent actionEvent) {

    }
    @FXML
    public void handlePauseProject(ActionEvent actionEvent) {

    }
    @FXML
    public void handleResumeProject(ActionEvent actionEvent) {

    }
    @FXML
    public void handleCompleteProject(ActionEvent actionEvent) {

    }
    @FXML
    public void handleDeleteProject(ActionEvent actionEvent) {

    }
    @FXML
    public void handleBack(ActionEvent actionEvent) {
        sceneEngine.switchScene("menu");
    }

    public void onSceneLoad() {
        projectListView.getItems().clear(); // Clear the list view before populating it
        // Initialize the project list view with some sample data
        List<ProjectListModule> projectList = projectService.getProjectList();

        projectListView.getItems().addAll(projectList);

        // Add a double-click event to open the selected item
        projectListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Detect double-click
                ProjectListModule selectedItem = projectListView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    projectService.selectProject(selectedItem.id());
//                    handleViewProject(selectedItem.id());
                    sceneEngine.switchScene("task_tree");
                }
            }
        });
    }

    @Override
    public void setSceneEngine(SceneEngine engine) {
        this.sceneEngine = engine;
    }

    @Override
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
}
