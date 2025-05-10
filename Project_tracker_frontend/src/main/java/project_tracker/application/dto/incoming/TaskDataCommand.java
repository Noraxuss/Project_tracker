package project_tracker.application.dto.incoming;

public record TaskDataCommand(Long id, String name, String description, boolean status) {
}
