package project_tracker_frontend.application.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project_tracker_frontend.application.domain.CreateProjectModule;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.ProjectService;
import project_tracker_frontend.application.service.ProjectServiceAware;
import project_tracker_frontend.application.service.StatusService;
import project_tracker_frontend.application.service.StatusServiceAware;
import project_tracker_frontend.application.application_state.StatusState;

public class CreateProjectController implements SceneEngineAware, ProjectServiceAware, StatusServiceAware {

    private SceneEngine sceneEngine;
    private ProjectService projectService;
    private StatusService statusService;

    @FXML
    public Label titleLabel;

    @FXML
    public TextField projectNameField;

    @FXML
    public Button createButton;

    @FXML
    public ComboBox<String> projectStatusComboBox;

    @FXML
    public TextField projectDescriptionField;

    @FXML
    public Label systemResponseLabel;

    private ObservableList<String> statusList;

    public void handleProjectCreation(ActionEvent actionEvent) {
        CreateProjectModule createProjectModule = new CreateProjectModule
                (projectNameField.getText(), projectDescriptionField.getText());

        projectService.createProject(createProjectModule);
        //systemResponseLabel.setText(StatusState.getInstance().getStatusCode());

        sceneEngine.switchScene("projects");
    }

    public void onSceneLoad() {
        statusList = projectStatusComboBox.getItems();
        statusList.clear();

        StatusState.getInstance();
        StatusState.getProjectStatusList()
                .forEach(statusModel -> statusList.add(statusModel.getName()));
        StatusState.getSharedStatusList()
                .forEach(statusModel -> statusList.add(statusModel.getName()));
    }

    @Override
    public void setSceneEngine(SceneEngine engine) {
        this.sceneEngine = engine;
    }

    @Override
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public void setStatusService(StatusService statusService) {
        this.statusService = statusService;
    }
}
