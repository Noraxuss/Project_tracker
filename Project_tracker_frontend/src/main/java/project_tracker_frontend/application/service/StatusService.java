package project_tracker_frontend.application.service;

import project_tracker_frontend.application.connectors.StatusConnector;
import project_tracker_frontend.application.domain.CreateStatusModule;
import project_tracker_frontend.application.dto.incoming.StatusCommand;
import project_tracker_frontend.application.dto.outgoing.StatusCreationDetails;
import project_tracker_frontend.application.utilities.StatusModel;

import java.util.List;

public class StatusService {

    public List<StatusModel> getApproppriateStatusList(String statusPurpose) {
        List<StatusCommand> statusCommands = StatusConnector.getStatusList(statusPurpose);
        return statusCommands.stream()
                .map(statusCommand -> {
                    StatusModel statusModel = new StatusModel();
                    statusModel.setId(statusCommand.id());
                    statusModel.setName(statusCommand.name());
                    return statusModel;
                })
                .toList();
    }

    public List<String> getStatusPurposeList() {
        return StatusConnector.getStatusPurposeList();
    }

    public void createNewStatus(CreateStatusModule statusModule) {
        StatusCreationDetails statusCreationDetails = new StatusCreationDetails();
        statusCreationDetails.setName(statusModule.statusName());
        statusCreationDetails.setStatusPurpose(statusCreationDetails.getStatusPurpose());
        StatusConnector.createStatus(statusCreationDetails);
    }
}
