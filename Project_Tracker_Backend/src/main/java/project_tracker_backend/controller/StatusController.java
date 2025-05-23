package project_tracker_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_tracker_backend.dto.incoming.StatusCommand;
import project_tracker_backend.dto.incoming.StatusUpdateCommand;
import project_tracker_backend.dto.outgoing.StatusDetails;
import project_tracker_backend.service.StatusService;

import java.util.List;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    private StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @PostMapping("/create-status")
    public ResponseEntity<Void> updateStatus(StatusCommand statusCommand) {
        statusService.createOrUpdateStatus(statusCommand);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-status/{id}")
    public ResponseEntity<StatusDetails> getStatus(@PathVariable("id") long id) {
        StatusDetails statusDetails = statusService.getStatusById(id);
        return ResponseEntity.ok().body(statusDetails);
    }

    @GetMapping("/get-all-statuses-by-status-purpose/{statusPurpose}")
    public ResponseEntity<List<StatusDetails>> getAllStatuses
            (@PathVariable("statusPurpose") String statusPurpose) {
        List<StatusDetails> statusDetailsList = statusService.getAllStatuses(statusPurpose);
        return ResponseEntity.ok().body(statusDetailsList);
    }

    @PutMapping("/update-status")
    public ResponseEntity<Void> updateStatus(@RequestBody StatusUpdateCommand statusUpdateCommand) {
        statusService.updateStatus(statusUpdateCommand);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-status/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable("id") long id) {
        statusService.deleteStatus(id);
        return ResponseEntity.ok().build();
    }
}
