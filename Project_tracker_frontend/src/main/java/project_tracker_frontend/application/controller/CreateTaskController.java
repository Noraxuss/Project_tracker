package project_tracker_frontend.application.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project_tracker_frontend.application.application_state.AppState;
import project_tracker_frontend.application.application_state.StatusState;
import project_tracker_frontend.application.controller.controller_utilities.ControllerUtil;
import project_tracker_frontend.application.controller.controller_utilities.ControllerUtilityAware;
import project_tracker_frontend.application.domain.CreateTaskModule;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.TaskService;
import project_tracker_frontend.application.service.TaskServiceAware;

import java.util.ResourceBundle;

public class CreateTaskController implements SceneEngineAware, TaskServiceAware,
        ControllerUtilityAware {

    private SceneEngine sceneEngine;
    private TaskService taskService;
    private ControllerUtil controllerUtil;

    @FXML
    public Label titleLabel;

    @FXML
    public HBox taskNameFieldController;

    @FXML
    public VBox taskDescriptionFieldController;

    @FXML
    public Button createTaskButton;

    @FXML
    public ComboBox<String> taskStatusComboBox;

    @FXML
    public Label systemResponseLabel;

    private ObservableList<String> statusList;

    @FXML
    public ResourceBundle resources;

    @FXML
    public void initialize() {
        controllerUtil.localizeIncludedComponent(taskNameFieldController,
                "create_task.task_name", resources);
        controllerUtil.localizeIncludedComponent(taskDescriptionFieldController,
                "create_task.task_description", resources);
    }

    public void handleCreateTask(ActionEvent actionEvent) {
        String taskName = controllerUtil.getInputStringData(taskNameFieldController);
        String taskDescription = controllerUtil.getInputStringData(taskDescriptionFieldController);
        Long taskStatusId = AppState.getInstance().getStatusState()
                .getStatusIdByName(taskStatusComboBox.getSelectionModel().getSelectedItem());

        taskService.createTask(new CreateTaskModule
                (taskName, taskDescription, taskStatusId));
//        SystemResponseLabel.setText(StatusState.getInstance().getStatusCode());
        sceneEngine.switchScene("task_tree");
    }

    public void onSceneLoad() {
        statusList = taskStatusComboBox.getItems();
        statusList.clear();

        // Load the status list from the StatusState
        StatusState.getInstance();
        StatusState.getTaskStatusList()
                .forEach(statusModel -> statusList.add(statusModel.getName()));
        StatusState.getSharedStatusList()
                .forEach(statusModel -> statusList.add(statusModel.getName()));
    }

    @Override
    public void setSceneEngine(SceneEngine engine) {
        this.sceneEngine = engine;
    }
    @Override
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }
    @Override
    public void setControllerUtility(ControllerUtil controllerUtil) {
        this.controllerUtil = controllerUtil;
    }
}
