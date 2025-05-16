package project_tracker_frontend.application.domain;

public record ProjectNameModule(String projectName) {
    public ProjectNameModule {
        if (projectName == null || projectName.isBlank()) {
            throw new IllegalArgumentException("Project name cannot be null or empty");
        }
    }
}
