package project_tracker.application.utilities;

import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectSession {
    private static ProjectSession instance;
    private Long currentProjectId;

    private ProjectSession() {

    }

    public static ProjectSession getInstance() {
        if (instance == null) {
            instance = new ProjectSession();
        }
        return instance;
    }
}
