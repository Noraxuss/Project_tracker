package project_tracker_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project_tracker_backend.dto.incoming.StatusCommandForProjects;
import project_tracker_backend.dto.incoming.StatusCommandForTasks;
import project_tracker_backend.service.StatusService;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @PostMapping("/create-task-status")
    public ResponseEntity<Void> updateStatus(StatusCommandForTasks statusCommandForTasks) {
        statusService.createStatusForTasks(statusCommandForTasks);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create-project-status")
    public ResponseEntity<Void> updateProjectStatus(StatusCommandForProjects statusCommandForProjects) {
        statusService.createStatusForProjects(statusCommandForProjects);
        return ResponseEntity.ok().build();
    }

}
