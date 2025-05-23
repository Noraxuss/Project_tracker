package project_tracker_frontend.application.application_state;

import lombok.Getter;
import lombok.Setter;
import project_tracker_frontend.application.service.ServiceFactory;
import project_tracker_frontend.application.service.StatusService;
import project_tracker_frontend.application.utilities.StatusModel;

import java.util.List;

@Getter
@Setter
public class StatusState {

    private static StatusState instance;

    @Getter
    @Setter
    private static List<StatusModel> taskStatusList;

    @Getter
    @Setter
    private static List<StatusModel> projectStatusList;

    @Getter
    @Setter
    private static List<StatusModel> sharedStatusList;

    private static StatusService statusService;

    private StatusState() {
        // Private constructor to prevent instantiation
    }

    public static StatusState getInstance() {
        if (instance == null) {
            instance = new StatusState();
        }
        setStatusService();
        initialize();
        return instance;
    }

    private static void initialize() {
        requestTaskStatusList();
        requestProjectStatusList();
        requestSharedStatusList();
    }

    private static void requestTaskStatusList() {
        taskStatusList = statusService.getApproppriateStatusList("task");
    }
    private static void requestProjectStatusList() {
        projectStatusList = statusService.getApproppriateStatusList("project");
    }
    private static void requestSharedStatusList() {
        sharedStatusList = statusService.getApproppriateStatusList("both");
    }

    public static void setStatusService() {
        statusService = ServiceFactory.getStatusService();
    }

}
