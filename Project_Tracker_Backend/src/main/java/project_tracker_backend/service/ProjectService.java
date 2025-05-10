package project_tracker_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import project_tracker_backend.domain.Project;
import project_tracker_backend.domain.Status;
import project_tracker_backend.domain.User;
import project_tracker_backend.dto.incoming.ProjectCreationDto;
import project_tracker_backend.dto.mapper.ProjectMapper;
import project_tracker_backend.dto.outgoing.ProjectDetails;
import project_tracker_backend.dto.outgoing.ProjectDetailsWithTasks;
import project_tracker_backend.dto.outgoing.TaskDetailsWithSubtasks;
import project_tracker_backend.repository.ProjectRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Lazy
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    private final TaskService taskService;

    private final UserService userService;

    private final StatusService statusService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper,
                          @Lazy TaskService taskService, @Lazy UserService userService, StatusService statusService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.taskService = taskService;
        this.userService = userService;
        this.statusService = statusService;
    }

    public void createProject(ProjectCreationDto projectCreationDto) {
        Project project = projectMapper.mapProjectCreationDtoToProject(projectCreationDto);
        User user = userService.findUserById(projectCreationDto.getUserId());

        Status status = null;
        if (projectCreationDto.getStatusId() < 0L) {
            status = statusService.findStatusById(projectCreationDto.getStatusId());
        }

        project.setStatus(status);
        project.setUser(user);
        user.getProject().add(project);
        projectRepository.save(project);
    }

    public Project findProjectById(Long projectId) {
        //TODO throw Exception
        return projectRepository.findById(projectId).orElse(null);
    }

    public List<ProjectDetails> getUserProjects(Long userId) {
        List<Project> projectList = findAllUserProjects(userId);
        List<ProjectDetails> projectDetailsList = new ArrayList<>();

        for (Project project : projectList) {
            ProjectDetails projectDetails = projectMapper.mapProjectToProjectDetails(project);
            projectDetailsList.add(projectDetails);
        }
        return projectDetailsList;
    }

    private List<Project> findAllUserProjects(Long userId) {
        return projectRepository.findAllProjectsByUser_Id(userId);
    }

    public ProjectDetailsWithTasks getProjectTasksAndSubtasks(Long projectId) {
        Project project = findProjectById(projectId);
        ProjectDetailsWithTasks projectDetailsWithTasks = projectMapper.mapProjectToProjectDetailsWithTasks(project);

        List<TaskDetailsWithSubtasks> taskDetailsWithSubtasks = taskService.getTasksAndSubtasks(projectId);
        projectDetailsWithTasks.getTasksWithSubtasks().addAll(taskDetailsWithSubtasks);

        return projectDetailsWithTasks;

    }

    public ProjectDetails getProject(Long projectId) {
        Project project = findProjectById(projectId);
        return projectMapper.mapProjectToProjectDetails(project);
    }
}
