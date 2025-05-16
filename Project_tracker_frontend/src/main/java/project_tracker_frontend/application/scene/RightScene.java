package project_tracker_frontend.application.scene;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RightScene extends BaseScene{

    private static RightScene instance;

    private RightScene() {}

    public static RightScene getInstance() {
        if (instance == null) {
            instance = new RightScene();
        }
        return instance;
    }
}
