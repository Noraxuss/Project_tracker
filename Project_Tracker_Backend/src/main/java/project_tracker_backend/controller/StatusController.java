package project_tracker_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_tracker_backend.dto.incoming.StatusCommand;
import project_tracker_backend.dto.incoming.StatusUpdateCommand;
import project_tracker_backend.dto.outgoing.StatusDetails;
import project_tracker_backend.service.StatusService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    private final StatusService statusService;
    private final Logger logger = LoggerFactory.getLogger(StatusController.class);

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
        logger.info("getAllStatuses - statusPurpose: {} ", statusPurpose);
        logger.info("getAllStatuses - statusDetailsList: {} ", statusDetailsList);

        if (statusDetailsList.isEmpty()) {
            statusDetailsList = new ArrayList<>();
        }
        return ResponseEntity.ok().body(statusDetailsList);
    }

    @GetMapping("/get-status-purpose-list")
    public ResponseEntity<List<String>> getStatusPurposeList() {
        List<String> statusPurpose = statusService.getStatusPurposeList();
        return ResponseEntity.ok().body(statusPurpose);
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
