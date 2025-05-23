package project_tracker_backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import project_tracker_backend.domain.Status;
import project_tracker_backend.domain.StatusPurpose;
import project_tracker_backend.dto.incoming.StatusCommand;
import project_tracker_backend.dto.incoming.StatusUpdateCommand;
import project_tracker_backend.dto.outgoing.StatusDetails;
import project_tracker_backend.repository.StatusRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class StatusService {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final StatusRepository statusRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StatusService(@Lazy ProjectService projectService, @Lazy TaskService taskService,
                         StatusRepository statusRepository, ModelMapper modelMapper) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.statusRepository = statusRepository;
        this.modelMapper = modelMapper;
    }

    public void createOrUpdateStatus(StatusCommand statusCommand) {
        // change method name
        Status status = getStatus(statusCommand);
        if (statusRepository.existsByName(statusCommand.getName())) {
            changeStatusPurpose(status);
        } else {
            statusRepository.save(status);
        }
    }

    private static void changeStatusPurpose(Status status) {
        StatusPurpose statusPurpose = status.getStatusPurpose();
        if (StatusPurpose.BOTH.equals(statusPurpose) ||
                StatusPurpose.TASK.equals(statusPurpose) ||
                StatusPurpose.PROJECT.equals(statusPurpose)) {
            return;
        }
        status.setStatusPurpose(StatusPurpose.BOTH);
    }

    private Status createStatus(StatusCommand statusCommand) {
        return modelMapper.map(statusCommand, Status.class);
    }

    public StatusDetails getStatusById(long id) {
        Status status = findStatusById(id);
        if (status == null) {
            return null;
        }
        return modelMapper.map(status, StatusDetails.class);
    }

    public List<StatusDetails> getAllStatuses(String statusPurpose) {
        StatusPurpose purpose = StatusPurpose.valueOf(statusPurpose.toUpperCase());
        List<Status> statuses = statusRepository.findAllByStatusPurpose(purpose);
        return Arrays.asList(modelMapper.map(statuses, StatusDetails[].class));
    }

    public void updateStatus(StatusUpdateCommand statusUpdateCommand) {
        Status status = findStatusById(statusUpdateCommand.getId());
        StatusPurpose statusPurpose = StatusPurpose.valueOf(statusUpdateCommand.getStatusPurpose().toUpperCase());
        if (!status.getName().equals(statusUpdateCommand.getName())) {
            status.setName(statusUpdateCommand.getName());
        }
        if (!status.getStatusPurpose().equals(statusPurpose)) {
            status.setStatusPurpose(statusPurpose);
        }
        statusRepository.save(status);
    }

    public void deleteStatus(long id) {
        statusRepository.deleteStatusById(id);
    }

    private Status getStatus(StatusCommand statusCommand) {
        return statusRepository.getStatusByName(statusCommand.getName())
                .orElse(createStatus(statusCommand));
    }

    public Status findStatusById(long id) {
        return statusRepository.findById(id).orElse(null);
    }
}
