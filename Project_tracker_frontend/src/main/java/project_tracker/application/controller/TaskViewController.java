package project_tracker.application.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import project_tracker.application.domain.TaskDataModule;
import project_tracker.application.domain.TaskModule;
import project_tracker.application.scene.SceneEngine;
import project_tracker.application.scene.SceneEngineAware;
import project_tracker.application.service.TaskService;
import project_tracker.application.service.TaskServiceAware;

public class TaskViewController
        implements SceneEngineAware, TaskServiceAware {

    private SceneEngine sceneEngine;
    private TaskService taskService;

    public Label taskIdField;
    public Label taskNameField;
    public TextField editableTaskNameField;
    public Label taskDescriptionField;
    public TextField editableTaskDescriptionField;


    public void onSceneLoad() {
        TaskDataModule taskDataModule = taskService.getTaskData();
        taskIdField.setText(taskDataModule.getId().toString());
        taskNameField.setText(taskDataModule.getName());
        editableTaskNameField.setText(taskDataModule.getName());
        taskDescriptionField.setText(taskDataModule.getDescription());
        editableTaskDescriptionField.setText(taskDataModule.getDescription());
    }

    public void handleMouseClickOnTaskTitle(MouseEvent mouseEvent) {
    }

    public void handleMouseClickOnTaskName(MouseEvent mouseEvent) {
    }

    public void handleTaskNameEditAction(ActionEvent actionEvent) {
    }

    public void handleMouseClickOnTaskDescription(MouseEvent mouseEvent) {
    }

    public void handleTaskDescriptionEditAction(ActionEvent actionEvent) {
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
