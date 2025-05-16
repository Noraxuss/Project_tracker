package project_tracker_frontend.application.utilities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskSession {

    private static TaskSession instance;
    private Long taskId;

    private TaskSession() {
        // Private constructor to prevent instantiation
    }

    public static TaskSession getInstance() {
        if (instance == null) {
            instance = new TaskSession();
        }
        return instance;
    }
}
