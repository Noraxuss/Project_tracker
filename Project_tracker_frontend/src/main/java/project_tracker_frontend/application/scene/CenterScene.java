package project_tracker_frontend.application.scene;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CenterScene extends BaseScene {

    private static CenterScene instance;

    private CenterScene() {}

    public static CenterScene getInstance() {
        if (instance == null) {
            instance = new CenterScene();
        }
        return instance;
    }


}
