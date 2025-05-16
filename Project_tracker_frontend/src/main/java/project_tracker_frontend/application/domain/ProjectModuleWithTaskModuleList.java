package project_tracker_frontend.application.domain;

import java.util.List;

public record ProjectModuleWithTaskModuleList(Long projectId,
                                              String projectName,
                                              String projectDescription,
                                              List<TaskModule> tasksWithSubtasks) {
    @Override
    public String toString() {
        return projectName;
    }
}
