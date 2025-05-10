package project_tracker.application.domain;

public record CreateProjectModule(String projectName, String description) {
    public CreateProjectModule {
        if (projectName == null || projectName.isBlank()) {
            throw new IllegalArgumentException("Project name cannot be null or empty");
        }
    }
}