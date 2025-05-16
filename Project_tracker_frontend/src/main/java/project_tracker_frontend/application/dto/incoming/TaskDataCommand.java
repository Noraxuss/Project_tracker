package project_tracker_frontend.application.dto.incoming;

public record TaskDataCommand(Long id, String name, String description, boolean status) {
}
