package project_tracker.application.utilities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusSession {

    private static StatusSession instance;
    private String statusCode;

    private StatusSession() {
        // Private constructor to prevent instantiation
    }

    public static StatusSession getInstance() {
        if (instance == null) {
            instance = new StatusSession();
        }
        return instance;
    }

}
