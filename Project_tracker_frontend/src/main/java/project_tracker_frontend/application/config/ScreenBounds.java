package project_tracker_frontend.application.config;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class ScreenBounds {

    public static Rectangle2D getScreenBounds() {
        return Screen.getPrimary().getVisualBounds();
    }

}
