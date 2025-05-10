package project_tracker.application.domain;

public record ProjectListModule(Long id, String name) {

    @Override
    public String toString() {
        return name;
    }
}
