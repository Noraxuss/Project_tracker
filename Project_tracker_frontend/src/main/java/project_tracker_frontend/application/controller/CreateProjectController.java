package project_tracker_frontend.application.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import project_tracker_frontend.application.application_state.StatusState;
import project_tracker_frontend.application.domain.CreateProjectModule;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.ProjectService;
import project_tracker_frontend.application.service.ProjectServiceAware;
import project_tracker_frontend.application.service.StatusService;
import project_tracker_frontend.application.service.StatusServiceAware;

import java.util.ResourceBundle;

public class CreateProjectController implements SceneEngineAware, ProjectServiceAware,
        StatusServiceAware, ControllerFactoryAware {

    private SceneEngine sceneEngine;
    private ProjectService projectService;
    private StatusService statusService;
    private ControllerFactory controllerFactory;

    @FXML
    public Label titleLabel;

    @FXML
    public HBox projectNameFieldController;

    @FXML
    public HBox projectDescriptionFieldController;

    @FXML
    public ComboBox<String> projectStatusComboBox;

    @FXML
    public Button createButton;

    @FXML
    public Label systemResponseLabel;

    @FXML
    private ResourceBundle resources;

    private ObservableList<String> statusList;

    @FXML
    public void initialize() {
        controllerFactory.localizeIncludedComponent(projectNameFieldController,
                "create_project.project_name", resources);
        controllerFactory.localizeIncludedComponent(projectDescriptionFieldController,
                "create_project.project_description", resources);
    }

    public void handleProjectCreation(ActionEvent actionEvent) {
        String projectName = controllerFactory.getInputStringData(projectNameFieldController);
        String projectDescription = controllerFactory.getInputStringData(projectDescriptionFieldController);

        CreateProjectModule createProjectModule = new CreateProjectModule
                (projectName, projectDescription);

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

    @Override
    public void setControllerFactory(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }
}
