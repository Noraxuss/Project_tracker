package project_tracker_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_tracker_backend.dto.incoming.TaskCreationDto;
import project_tracker_backend.dto.outgoing.ProjectDetailsWithTasks;
import project_tracker_backend.dto.outgoing.TaskDetails;
import project_tracker_backend.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@RequestBody TaskCreationDto taskCreationDto) {
        taskService.createTask(taskCreationDto);
    }

    @GetMapping("/task-details/{taskId}")
    public ResponseEntity<TaskDetails> getTaskDetails(@PathVariable("taskId") Long taskId) {
        TaskDetails taskDetails = taskService.getTaskDetails(taskId);
        return new ResponseEntity<>(taskDetails, HttpStatus.OK);
    }


}
