package project_tracker_frontend.application.application_state;

import lombok.Getter;

@Getter
public class AppState {

    public static final AppState INSTANCE = new AppState();

    private final ProjectState projectState = ProjectState.getInstance();
    private final TaskState taskState = TaskState.getInstance();
    private final StatusState statusState = StatusState.getInstance();
    private final UserState userState = UserState.getInstance();

    private AppState() {
        // Private constructor to prevent instantiation
    }

    public static AppState getInstance() {
        return INSTANCE;
    }
}
