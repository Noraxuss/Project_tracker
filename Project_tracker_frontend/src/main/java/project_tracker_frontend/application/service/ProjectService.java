package project_tracker_frontend.application.service;

import project_tracker_frontend.application.connectors.ProjectConnector;
import project_tracker_frontend.application.domain.CreateProjectModule;
import project_tracker_frontend.application.domain.ProjectListModule;
import project_tracker_frontend.application.domain.ProjectModuleWithTaskModuleList;
import project_tracker_frontend.application.domain.TaskModule;
import project_tracker_frontend.application.dto.incoming.ProjectCommand;
import project_tracker_frontend.application.dto.incoming.ProjectWithTasksCommand;
import project_tracker_frontend.application.dto.outgoing.ProjectDetails;
import project_tracker_frontend.application.dto.outgoing.ProjectIdDetails;
import project_tracker_frontend.application.utilities.ProjectSession;
import project_tracker_frontend.application.utilities.UserSession;

import java.util.ArrayList;
import java.util.List;

public class ProjectService {

    public List<ProjectListModule> getProjectList() {
        List<ProjectCommand> projectCommandList = ProjectConnector
                .getAllProjects(UserSession.getInstance().getUserId());
        List<ProjectListModule> projectListModules = new ArrayList<>();
        for (ProjectCommand projectCommand : projectCommandList) {
            ProjectListModule projectListModule = new ProjectListModule
                    (projectCommand.id(), projectCommand.name());
            projectListModules.add(projectListModule);
        }
        return projectListModules;
    }

    public void createProject(CreateProjectModule createProjectModule) {
        ProjectDetails projectDetails = new ProjectDetails(
                createProjectModule.projectName(),
                createProjectModule.description()
        );
        ProjectConnector.createProject(projectDetails);
    }

    public void selectProject(Long id) {
        ProjectSession.getInstance();
        ProjectSession.getInstance().setCurrentProjectId(id);
    }

    public ProjectModuleWithTaskModuleList getSelectedProjectWithTasksAndSubtasks(Long projectId) {
        ProjectWithTasksCommand projectWithTasksCommand = ProjectConnector
                .getProjectWithTasksAndSubtasks(new ProjectIdDetails(projectId));
        List<TaskModule> taskModules = new ArrayList<>();
        projectWithTasksCommand.tasksWithSubtasks().forEach(task -> {
            TaskModule taskModule = new TaskModule(
                    task.taskId(),
                    task.taskName(),
                    new ArrayList<>()
            );
            taskModules.add(taskModule);
        });
        return new ProjectModuleWithTaskModuleList(
                projectWithTasksCommand.projectId(),
                projectWithTasksCommand.projectName(),
                projectWithTasksCommand.projectDescription(),
                taskModules
                );
    }
}

