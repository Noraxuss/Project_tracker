package project_tracker_frontend.application.domain;

public record CreateTaskModule(String name, String description) {
    public CreateTaskModule {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
    }
}
