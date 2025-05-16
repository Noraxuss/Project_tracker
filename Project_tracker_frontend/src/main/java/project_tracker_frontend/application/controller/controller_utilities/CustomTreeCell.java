package project_tracker_frontend.application.controller.controller_utilities;

import javafx.scene.control.TreeCell;
import project_tracker_frontend.application.domain.ProjectModuleWithTaskModuleList;
import project_tracker_frontend.application.domain.TaskModule;

public class CustomTreeCell extends TreeCell<Object> {

    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else if (item instanceof ProjectModuleWithTaskModuleList project) {
            setText("Project: " + project.projectName());
        } else if (item instanceof TaskModule task) {
            setText("Task: " + task.taskName());
        } else {
            setText(item.toString());
        }
    }
}