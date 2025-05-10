package project_tracker.application.dto.incoming;

import project_tracker.application.domain.TaskModule;

import java.util.List;

public record TasksWithSubtasksCommand(Long taskId,
                                       String taskName,
                                       List<TasksWithSubtasksCommand> subtaskDetails)  {
}
