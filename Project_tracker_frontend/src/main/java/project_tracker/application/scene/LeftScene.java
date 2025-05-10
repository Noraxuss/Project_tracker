package project_tracker.application.scene;

import javafx.scene.Parent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeftScene extends BaseScene {

    private static LeftScene instance;

    private LeftScene() {}

    public static LeftScene getInstance() {
        if (instance == null) {
            instance = new LeftScene();
        }
        return instance;
    }


}
