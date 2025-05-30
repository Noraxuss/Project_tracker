package project_tracker_frontend.application.scene;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import project_tracker_frontend.application.config.ScreenBounds;

@Getter
@Setter
public class ExtraScene extends BaseScene{

    private Stage extraStage;

    public ExtraScene() {
        this.extraStage = new Stage();
    }

    public void initializeExtraStage(Parent scene) {
        this.extraStage.setTitle(getName());
        this.extraStage.setScene(new Scene(scene));
        this.extraStage.setResizable(true);
        this.extraStage.sizeToScene();
        this.extraStage.setMaxHeight(ScreenBounds.getScreenBounds().getHeight());
        this.extraStage.setMaxWidth(ScreenBounds.getScreenBounds().getWidth());
        this.extraStage.centerOnScreen();

    }

    public void closeExtraScene() {
        if (extraStage != null) {
            extraStage.close();
            extraStage = null;
        }
    }
}
