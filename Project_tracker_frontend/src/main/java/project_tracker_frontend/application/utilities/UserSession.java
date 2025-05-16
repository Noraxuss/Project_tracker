package project_tracker_frontend.application.utilities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSession {
    private static UserSession instance;
    private Long userId;
    private String userName;

    private UserSession() {
        // Private constructor to prevent instantiation
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
}