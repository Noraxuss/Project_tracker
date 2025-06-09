package project_tracker_frontend.application.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project_tracker_frontend.application.application_state.AppState;
import project_tracker_frontend.application.application_state.StatusState;
import project_tracker_frontend.application.controller.controller_utilities.ControllerUtil;
import project_tracker_frontend.application.controller.controller_utilities.ControllerUtilityAware;
import project_tracker_frontend.application.domain.CreateProjectModule;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.ProjectService;
import project_tracker_frontend.application.service.ProjectServiceAware;
import project_tracker_frontend.application.service.StatusService;
import project_tracker_frontend.application.service.StatusServiceAware;

import java.util.ResourceBundle;

public class CreateProjectController implements SceneEngineAware, ProjectServiceAware,
        StatusServiceAware, ControllerUtilityAware {

    private SceneEngine sceneEngine;
    private ProjectService projectService;
    private StatusService statusService;
    private ControllerUtil controllerUtil;

    private final Logger logger = LoggerFactory.getLogger(CreateProjectController.class);

    @FXML
    public Label titleLabel;

    @FXML
    public HBox projectNameFieldController;

    @FXML
    public VBox projectDescriptionFieldController;

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
        controllerUtil.localizeIncludedComponent(projectNameFieldController,
                "create_project.project_name", resources);
        controllerUtil.localizeIncludedComponent(projectDescriptionFieldController,
                "create_project.project_description", resources);
    }

    public void handleProjectCreation(ActionEvent actionEvent) {
        String projectName = controllerUtil.getInputStringData(projectNameFieldController);
        String projectDescription = controllerUtil.getInputStringData(projectDescriptionFieldController);
        Long userId = AppState.getInstance().getUserState().getUserId();
        Long selectedStatusId;
        if (projectStatusComboBox.getSelectionModel().getSelectedIndex() == -1) {
            selectedStatusId = 1L;
            logger.debug("No status selected, defaulting to first project status: {}", selectedStatusId);
        } else {
            selectedStatusId = AppState.getInstance().getStatusState()
                    .getStatusIdByName(projectStatusComboBox.getSelectionModel().getSelectedItem());
            logger.debug("Selected project status: {}", selectedStatusId);
        }

        CreateProjectModule createProjectModule = new CreateProjectModule
                (projectName, projectDescription, userId, selectedStatusId);

        projectService.createProject(createProjectModule);
        //systemResponseLabel.setText(StatusState.getInstance().getStatusCode());

        sceneEngine.switchScene("projects");
        logger.debug("Project created with name: {}, description: {}, userId: {}, statusId: {}",
                projectName, projectDescription, userId, selectedStatusId);

        Stage stage = (Stage) createButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onSceneLoad() {
        statusList = projectStatusComboBox.getItems();
        statusList.clear();

        logger.debug("Loading status list for project creation...");

        StatusState.getInstance();
        StatusState.getProjectStatusList()
                .forEach(statusModel -> statusList.add(statusModel.getName()));
        StatusState.getSharedStatusList()
                .forEach(statusModel -> statusList.add(statusModel.getName()));

        logger.debug("StatusListSize: {} ", statusList.size());
        logger.debug("StatusList: {} ", statusList);

        projectStatusComboBox.setItems(statusList);
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
    public void setControllerUtility(ControllerUtil controllerFactory) {
        this.controllerUtil = controllerFactory;
    }
}
