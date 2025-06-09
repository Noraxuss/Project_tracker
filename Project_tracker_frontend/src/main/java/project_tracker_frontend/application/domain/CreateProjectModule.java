package project_tracker_frontend.application.domain;

public record CreateProjectModule(String name, String description, Long userId, Long statusId) {
    public CreateProjectModule {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Project name cannot be null or empty");
        }
    }
}