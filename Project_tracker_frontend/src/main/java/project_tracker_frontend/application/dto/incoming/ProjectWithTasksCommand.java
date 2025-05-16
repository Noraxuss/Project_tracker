package project_tracker_frontend.application.dto.incoming;

import java.util.List;

public record ProjectWithTasksCommand(Long projectId,
                                      String projectName,
                                      String projectDescription,
                                      List<TasksWithSubtasksCommand> tasksWithSubtasks) {
}
