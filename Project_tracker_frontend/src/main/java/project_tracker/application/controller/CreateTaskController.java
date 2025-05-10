package project_tracker.application.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project_tracker.application.domain.CreateTaskModule;
import project_tracker.application.scene.SceneEngine;
import project_tracker.application.scene.SceneEngineAware;
import project_tracker.application.service.TaskService;
import project_tracker.application.service.TaskServiceAware;
import project_tracker.application.utilities.StatusSession;

public class CreateTaskController implements SceneEngineAware, TaskServiceAware {

    public Label SystemResponseLabel;
    private SceneEngine sceneEngine;
    private TaskService taskService;

    public Label titleLabel;
    public TextField taskNameField;
    public TextField taskDescriptionField;
    public Button createTaskButton;

    public void handleCreateTask(ActionEvent actionEvent) {
        taskService.createTask(new CreateTaskModule
                (taskNameField.getText(), taskDescriptionField.getText()));
        SystemResponseLabel.setText(StatusSession.getInstance().getStatusCode());
        sceneEngine.switchScene("task_tree");
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
