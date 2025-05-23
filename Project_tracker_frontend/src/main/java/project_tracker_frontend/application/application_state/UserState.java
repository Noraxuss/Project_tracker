package project_tracker_frontend.application.application_state;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserState {
    private static UserState instance;
    private Long userId;
    private String userName;

    private UserState() {
        // Private constructor to prevent instantiation
    }

    public static UserState getInstance() {
        if (instance == null) {
            instance = new UserState();
        }
        return instance;
    }
}