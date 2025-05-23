package project_tracker_frontend.application.application_state;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskState {

    private static TaskState instance;
    private Long taskId;

    private TaskState() {
        // Private constructor to prevent instantiation
    }

    public static TaskState getInstance() {
        if (instance == null) {
            instance = new TaskState();
        }
        return instance;
    }
}
