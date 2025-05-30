package project_tracker_frontend.application.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project_tracker_frontend.application.application_state.StatusState;
import project_tracker_frontend.application.domain.CreateTaskModule;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.TaskService;
import project_tracker_frontend.application.service.TaskServiceAware;

public class CreateTaskController implements SceneEngineAware, TaskServiceAware {

    private SceneEngine sceneEngine;
    private TaskService taskService;

    @FXML
    public Label systemResponseLabel;

    @FXML
    public ComboBox<String> taskStatusComboBox;

    @FXML
    public Label titleLabel;

    @FXML
    public TextField taskNameField;

    @FXML
    public TextField taskDescriptionField;

    @FXML
    public Button createTaskButton;

    private ObservableList<String> statusList;

    public void handleCreateTask(ActionEvent actionEvent) {
        taskService.createTask(new CreateTaskModule
                (taskNameField.getText(), taskDescriptionField.getText()));
//        SystemResponseLabel.setText(StatusState.getInstance().getStatusCode());
        sceneEngine.switchScene("task_tree");
    }

    public void onSceneLoad() {
        statusList = taskStatusComboBox.getItems();
        statusList.clear();

        // Load the status list from the StatusState
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
}
