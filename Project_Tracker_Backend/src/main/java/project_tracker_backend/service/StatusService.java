package project_tracker_backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_tracker_backend.domain.Status;
import project_tracker_backend.domain.StatusPurpose;
import project_tracker_backend.dto.incoming.StatusCommandForTasks;
import project_tracker_backend.repository.StatusRepository;

@Service
public class StatusService {

    private final StatusRepository statusRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StatusService(StatusRepository statusRepository, ModelMapper modelMapper) {
        this.statusRepository = statusRepository;
        this.modelMapper = modelMapper;
    }

    public Status findStatusById(long id) {
        return statusRepository.findById(id).orElse(null);
    }

    public void createStatusForTasks(StatusCommandForTasks statusCommandForTasks) {
        Status status;
        if (statusRepository.existsByName(statusCommandForTasks.getName())) {
            // TODO Optional
            status = statusRepository.getStatusByName(statusCommandForTasks.getName());
            if (status.getStatusPurpose().equals(StatusPurpose.PROJECT)) {
                status.setStatusPurpose(StatusPurpose.BOTH);
            }
        } else {
            status = modelMapper.map(statusCommandForTasks, Status.class);
            status.setStatusPurpose(StatusPurpose.TASK);
        }
    }

    private void createStatus(String name, Long statusId,
                              Long projectId, StatusPurpose statusPurpose) {

    }


    public void createStatusForProjects(StatusCommandForTasks statusCommandForTasks) {

    }
}
