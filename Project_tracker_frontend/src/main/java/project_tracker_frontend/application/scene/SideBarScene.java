package project_tracker_frontend.application.scene;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SideBarScene extends BaseScene{

    private static SideBarScene instance;

    private SideBarScene() {}

    public static SideBarScene getInstance() {
        if (instance == null) {
            instance = new SideBarScene();
        }
        return instance;
    }
}
