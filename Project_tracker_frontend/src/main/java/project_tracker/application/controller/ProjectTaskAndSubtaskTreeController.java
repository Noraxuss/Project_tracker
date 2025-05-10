package project_tracker.application.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import project_tracker.application.controller.controller_utilities.CustomTreeCell;
import project_tracker.application.domain.ProjectModuleWithTaskModuleList;
import project_tracker.application.domain.TaskModule;
import project_tracker.application.dto.outgoing.TaskIdDetails;
import project_tracker.application.scene.SceneEngine;
import project_tracker.application.scene.SceneEngineAware;
import project_tracker.application.service.TaskService;
import project_tracker.application.service.TaskServiceAware;

public class ProjectTaskAndSubtaskTreeController
        implements SceneEngineAware, TaskServiceAware {

    private SceneEngine sceneEngine;

    private TaskService taskService;

    @FXML
    public Button expandAllButton;

    @FXML
    public Button collapseAllButton;

    @FXML
    public TreeView<Object> taskTreeView;

    @FXML
    public Button createTaskButton;

    public void onSceneLoad() {
        // Load the single instance from the service
        ProjectModuleWithTaskModuleList rootProject = taskService.getProjectWithTasks();

        TreeItem<Object> rootItem = new TreeItem<>(rootProject);
        populateTree(rootItem, rootProject);
        taskTreeView.setRoot(rootItem);

        // Set custom cell factory
        taskTreeView.setCellFactory(treeView -> new CustomTreeCell());

        // Bind the buttons' disable property to the state of the TreeView
        expandAllButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> allExpanded(taskTreeView.getRoot()),
                taskTreeView.rootProperty()
        ));

        collapseAllButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> allCollapsed(taskTreeView.getRoot()),
                taskTreeView.rootProperty()
        ));
    }

    private void populateTree(TreeItem<Object> parentItem, Object parentData) {
        if (parentData instanceof ProjectModuleWithTaskModuleList project) {
            for (TaskModule task : project.tasksWithSubtasks()) {
                TreeItem<Object> taskItem = new TreeItem<>(task);
                parentItem.getChildren().add(taskItem);
                populateTree(taskItem, task);
            }
        } else if (parentData instanceof TaskModule task) {
            for (TaskModule subTask : task.subtaskDetails()) {
                TreeItem<Object> subTaskItem = new TreeItem<>(subTask);
                parentItem.getChildren().add(subTaskItem);
                populateTree(subTaskItem, subTask);
            }
        }
    }

    @FXML
    public void handleExpendAllButtonAction(ActionEvent actionEvent) {
        TreeItem<Object> root = taskTreeView.getRoot();
        if (root != null) {
            expandAll(root);
        }
    }

    @FXML
    public void handleCollapseAllButtonAction(ActionEvent actionEvent) {
        TreeItem<Object> root = taskTreeView.getRoot();
        if (root != null) {
            collapseAll(root);
        }
    }

    private void expandAll(TreeItem<?> item) {
        item.setExpanded(true);
        for (TreeItem<?> child : item.getChildren()) {
            expandAll(child);
        }
    }

    private void collapseAll(TreeItem<?> item) {
        item.setExpanded(false);
        for (TreeItem<?> child : item.getChildren()) {
            collapseAll(child);
        }
    }

    private boolean allExpanded(TreeItem<?> item) {
        if (item == null) return true;
        if (!item.isExpanded()) return false;
        for (TreeItem<?> child : item.getChildren()) {
            if (!allExpanded(child)) return false;
        }
        return true;
    }

    private boolean allCollapsed(TreeItem<?> item) {
        if (item == null) return true;
        if (item.isExpanded()) return false;
        for (TreeItem<?> child : item.getChildren()) {
            if (!allCollapsed(child)) return false;
        }
        return true;
    }

    public void handleCreateTask(ActionEvent actionEvent) {
        sceneEngine.switchScene("create_task");
    }

    public void handleTaskTreeViewClick(MouseEvent mouseEvent) {
        taskTreeView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TaskModule selection = (TaskModule) taskTreeView
                        .getSelectionModel().getSelectedItem().getValue();
                TaskIdDetails selectedItem = new TaskIdDetails(selection.taskId());
                taskService.setSelectedTaskSession(selectedItem.taskId());
                sceneEngine.switchScene("task_viewer");
            }
        });
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
