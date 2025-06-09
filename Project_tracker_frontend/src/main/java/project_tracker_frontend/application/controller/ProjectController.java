package project_tracker_frontend.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import project_tracker_frontend.application.application_state.AppState;
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
    public void handleDeleteProject(ActionEvent actionEvent) {
        ProjectListModule selectedItem = projectListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            projectService.deleteProject(selectedItem.id());
            projectListView.getItems().remove(selectedItem);
            systemResponseLabel.setText("Project deleted successfully.");
        } else {
            systemResponseLabel.setText("No project selected to delete.");
        }
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

        mouseClickEvent();
    }

    private void mouseClickEvent() {
        // Add a double-click event to open the selected item
        projectListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Detect double-click
                ProjectListModule selectedItem = projectListView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    projectService.selectProject(selectedItem.id());
                    AppState.getInstance().getProjectState().setCurrentProjectId(selectedItem.id());
//                    handleViewProject(selectedItem.id());
                    sceneEngine.switchScene("task_tree");
                }
            } else if (event.getClickCount() == 1) { // Detect single-click
                ProjectListModule selectedItem = projectListView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    systemResponseLabel.setText("Selected project: " + selectedItem.name());
                } else {
                    systemResponseLabel.setText("No project selected.");
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
