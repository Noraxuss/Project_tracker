package project_tracker_frontend.application.dto.incoming;

import java.util.List;

public record TasksWithSubtasksCommand(Long taskId,
                                       String taskName,
                                       List<TasksWithSubtasksCommand> subtaskDetails)  {
}
