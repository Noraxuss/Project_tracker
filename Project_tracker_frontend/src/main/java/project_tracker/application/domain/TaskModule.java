package project_tracker.application.domain;

import java.util.List;

public record TaskModule(Long taskId,
                         String taskName,
                         List<TaskModule> subtaskDetails) {}
