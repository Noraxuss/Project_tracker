package project_tracker_backend.dto.mapper;

import org.springframework.stereotype.Component;
import project_tracker_backend.domain.Project;
import project_tracker_backend.dto.incoming.ProjectCreationCommand;
import project_tracker_backend.dto.outgoing.ProjectDetails;
import project_tracker_backend.dto.outgoing.ProjectDetailsWithTasks;
import project_tracker_backend.dto.outgoing.ProjectTaskListDto;

import java.util.ArrayList;

@Component
public class ProjectMapper {

    private final TaskMapper taskMapper;

    public ProjectMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public Project mapProjectCreationDtoToProject(ProjectCreationCommand projectCreationCommand) {
        Project project = new Project();
        project.setName(projectCreationCommand.getName());
        project.setDescription(projectCreationCommand.getDescription());

        return project;
    }

    public ProjectTaskListDto mapProjectToProjectTaskListDto(Project project) {
        ProjectTaskListDto projectTaskListDto = new ProjectTaskListDto();
        projectTaskListDto.setId(project.getId());
        projectTaskListDto.setName(project.getName());
        projectTaskListDto.setDescription(project.getDescription());
        return projectTaskListDto;
    }

    public ProjectDetails mapProjectToProjectDetails(Project project) {
        ProjectDetails projectDetails = new ProjectDetails();
        projectDetails.setId(project.getId());
        projectDetails.setName(project.getName());
        return projectDetails;
    }

    public ProjectDetailsWithTasks mapProjectToProjectDetailsWithTasks(Project project) {
        ProjectDetailsWithTasks projectDetailsWithTasks = new ProjectDetailsWithTasks();
        projectDetailsWithTasks.setProjectId(project.getId());
        projectDetailsWithTasks.setProjectName(project.getName());
        projectDetailsWithTasks.setProjectDescription(project.getDescription());
        projectDetailsWithTasks.setTasksWithSubtasks(new ArrayList<>());
        return projectDetailsWithTasks;
    }
}
