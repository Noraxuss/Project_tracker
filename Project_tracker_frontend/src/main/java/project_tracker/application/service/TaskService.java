package project_tracker.application.service;

import javafx.scene.control.TreeItem;
import project_tracker.application.connectors.TaskConnector;
import project_tracker.application.domain.CreateTaskModule;
import project_tracker.application.domain.ProjectModuleWithTaskModuleList;
import project_tracker.application.domain.TaskDataModule;
import project_tracker.application.domain.TaskModule;
import project_tracker.application.dto.incoming.TaskDataCommand;
import project_tracker.application.dto.outgoing.CreateTaskDetails;
import project_tracker.application.utilities.ProjectSession;
import project_tracker.application.utilities.TaskSession;

import java.util.ArrayList;

public class TaskService {

    public ProjectModuleWithTaskModuleList getProjectWithTasks() {
        return ServiceFactory.getProjectService().getSelectedProjectWithTasksAndSubtasks
                (ProjectSession.getInstance().getCurrentProjectId());
    }

    public void createTask(CreateTaskModule createTaskModule) {
        CreateTaskDetails createTaskDetails = new CreateTaskDetails(
                createTaskModule.name(), createTaskModule.description());
        TaskConnector.createTask(createTaskDetails);
    }

    public void setSelectedTaskSession(Long selectedTaskId) {
        TaskSession.getInstance().setTaskId(selectedTaskId);
    }

    public TaskDataModule getTaskData() {
        TaskDataCommand taskDataCommand = TaskConnector.getTaskData();
        TaskDataModule taskDataModule = new TaskDataModule();
        taskDataModule.setId(taskDataCommand.id());
        taskDataModule.setName(taskDataCommand.name());
        taskDataModule.setDescription(taskDataCommand.description());
        return taskDataModule;
    }
}
