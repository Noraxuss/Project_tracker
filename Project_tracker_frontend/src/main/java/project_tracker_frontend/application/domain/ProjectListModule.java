package project_tracker_frontend.application.domain;

public record ProjectListModule(Long id, String name) {

    @Override
    public String toString() {
        return name;
    }
}
