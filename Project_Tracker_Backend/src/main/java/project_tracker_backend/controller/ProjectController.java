package project_tracker_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_tracker_backend.dto.incoming.ProjectCreationDto;
import project_tracker_backend.dto.outgoing.ProjectDetails;
import project_tracker_backend.dto.outgoing.ProjectDetailsWithTasks;
import project_tracker_backend.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(@RequestBody ProjectCreationDto projectCreationDto) {
        projectService.createProject(projectCreationDto);
    }

    @GetMapping("/user-projects/{userId}")
    public ResponseEntity<List<ProjectDetails>> getUserProjects(@PathVariable("userId") Long userId) {
        List<ProjectDetails> projectList = projectService.getUserProjects(userId);
        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }

    @GetMapping("/project-tasks-and-subtasks/{projectId}")
    public ResponseEntity<ProjectDetailsWithTasks> getProjectTasksAndSubtasks(@PathVariable("projectId") Long projectId) {
        ProjectDetailsWithTasks projectDetailsWithTasks = projectService.getProjectTasksAndSubtasks(projectId);
        return new ResponseEntity<>(projectDetailsWithTasks, HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ProjectDetails> getProject(@PathVariable("projectId") Long projectId) {
        ProjectDetails projectDetails = projectService.getProject(projectId);
        return new ResponseEntity<>(projectDetails, HttpStatus.OK);
    }

}
