package project_tracker_frontend.application.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import project_tracker_frontend.application.application_state.AppState;
import project_tracker_frontend.application.controller.controller_utilities.CustomTreeCell;
import project_tracker_frontend.application.domain.ProjectModuleWithTaskModuleList;
import project_tracker_frontend.application.domain.TaskModule;
import project_tracker_frontend.application.dto.outgoing.TaskIdDetails;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.TaskService;
import project_tracker_frontend.application.service.TaskServiceAware;

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

        // Create the root TreeItem and populate the TreeView
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

        // Bind the collapse button to the state of the TreeView
        collapseAllButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> allCollapsed(taskTreeView.getRoot()),
                taskTreeView.rootProperty()
        ));
    }

    private void populateTree(TreeItem<Object> parentItem, Object parentData) {
        // Check if the parentData is an instance of ProjectModuleWithTaskModuleList
        if (parentData instanceof ProjectModuleWithTaskModuleList project) {
            for (TaskModule task : project.tasksWithSubtasks()) {
                TreeItem<Object> taskItem = new TreeItem<>(task);
                parentItem.getChildren().add(taskItem);
                populateTree(taskItem, task);
            }
            // Add subtasks if any
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
        // Expand all items in the TreeView
        TreeItem<Object> root = taskTreeView.getRoot();
        if (root != null) {
            expandAll(root);
        }
    }

    @FXML
    public void handleCollapseAllButtonAction(ActionEvent actionEvent) {
        // Collapse all items in the TreeView
        TreeItem<Object> root = taskTreeView.getRoot();
        if (root != null) {
            collapseAll(root);
        }
    }

    private void expandAll(TreeItem<?> item) {
        // Expand the current item and recursively expand all its children
        item.setExpanded(true);
        for (TreeItem<?> child : item.getChildren()) {
            expandAll(child);
        }
    }

    private void collapseAll(TreeItem<?> item) {
        // Collapse the current item and recursively collapse all its children
        item.setExpanded(false);
        for (TreeItem<?> child : item.getChildren()) {
            collapseAll(child);
        }
    }

    private boolean allExpanded(TreeItem<?> item) {
        // Check if all items in the tree are expanded
        if (item == null) return true;
        if (!item.isExpanded()) return false;
        for (TreeItem<?> child : item.getChildren()) {
            if (!allExpanded(child)) return false;
        }
        return true;
    }

    private boolean allCollapsed(TreeItem<?> item) {
        // Check if all items in the tree are collapsed
        if (item == null) return true;
        if (item.isExpanded()) return false;
        for (TreeItem<?> child : item.getChildren()) {
            if (!allCollapsed(child)) return false;
        }
        return true;
    }

    public void handleCreateTask(ActionEvent actionEvent) {
    // Switch to the create task scene
        sceneEngine.switchScene("create_task");
    }

    public void handleTaskTreeViewClick(MouseEvent mouseEvent) {
        taskTreeView.setOnMouseClicked(event -> {
            // Get the currently selected item from the TreeView
            TreeItem<?> selectedItem = taskTreeView.getSelectionModel().getSelectedItem();

            // Safety check: if nothing is selected, or it's not a TaskModule, skip handling
            if (selectedItem == null || !(selectedItem.getValue() instanceof TaskModule)) {
                return;
            }

            // Cast the selected item's value to TaskModule
            TaskModule selection = (TaskModule) selectedItem.getValue();

            // Handle left mouse button events
            if (event.getButton() == MouseButton.PRIMARY) {
                if (event.getClickCount() == 2) {
                    // Double-click with left button:
                    // - Set selected task ID in taskService
                    // - Switch to the task viewer scene
                    TaskIdDetails selectedTask = new TaskIdDetails(selection.taskId());
                    taskService.setSelectedTaskSession(selectedTask.taskId());
                    sceneEngine.switchScene("task_viewer");

                } else if (event.getClickCount() == 1) {
                    // Single-click with left button:
                    // - Store selected task ID in AppState
                    AppState.getInstance().getTaskState().setTaskId(selection.taskId());
                }
            }

            // Handle right mouse button click (context menu)
            if (event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
                // Create a context menu with options specific to the selected task
                ContextMenu contextMenu = new ContextMenu();

                // Option 1: View Task
                MenuItem viewTask = new MenuItem("View Task");
                viewTask.setOnAction(e -> {
                    // Same logic as double-click: open task viewer
                    taskService.setSelectedTaskSession(selection.taskId());
                    sceneEngine.switchScene("task_viewer");
                });

                // Option 2: Create Subtask
                MenuItem createSubtask = new MenuItem("Create Subtask");
                createSubtask.setOnAction(e -> {
                    AppState.getInstance().getTaskState().setTaskId(selection.taskId());
                });
                    // Switch to the Create Task scene
                    sceneEngine.switchScene("create_task");

                // Add both items to the context menu
                contextMenu.getItems().addAll(viewTask, createSubtask);

                // Show the context menu at the current mouse screen position
                contextMenu.show(taskTreeView, event.getScreenX(), event.getScreenY());
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
