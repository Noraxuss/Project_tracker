package project_tracker_frontend.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project_tracker_frontend.application.domain.CreateProjectModule;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.ProjectService;
import project_tracker_frontend.application.service.ProjectServiceAware;
import project_tracker_frontend.application.utilities.StatusSession;

public class CreateProjectController implements SceneEngineAware, ProjectServiceAware {

    private SceneEngine sceneEngine;

    private ProjectService projectService;

    @FXML
    public Label titleLabel;

    @FXML
    public TextField projectNameField;

    @FXML
    public Button createButton;

    @FXML
    public TextField projectDescriptionField;

    @FXML
    public Label SystemResponseLabel;

    public void handleProjectCreation(ActionEvent actionEvent) {
        CreateProjectModule createProjectModule = new CreateProjectModule
                (projectNameField.getText(), projectDescriptionField.getText());

        projectService.createProject(createProjectModule);

        SystemResponseLabel.setText(StatusSession.getInstance().getStatusCode());

        sceneEngine.switchScene("projects");
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
