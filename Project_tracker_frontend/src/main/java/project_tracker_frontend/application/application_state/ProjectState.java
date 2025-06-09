package project_tracker_frontend.application.application_state;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectState {

    private static ProjectState instance;
    private Long currentProjectId;

    private ProjectState() {
        // Private constructor to prevent instantiation
    }

    public static ProjectState getInstance() {
        if (instance == null) {
            instance = new ProjectState();
        }
        return instance;
    }
}
