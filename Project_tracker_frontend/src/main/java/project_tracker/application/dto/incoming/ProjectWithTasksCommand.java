package project_tracker.application.dto.incoming;

import project_tracker.application.domain.TaskModule;

import java.util.List;

public record ProjectWithTasksCommand(Long projectId,
                                      String projectName,
                                      String projectDescription,
                                      List<TasksWithSubtasksCommand> tasksWithSubtasks) {
}
