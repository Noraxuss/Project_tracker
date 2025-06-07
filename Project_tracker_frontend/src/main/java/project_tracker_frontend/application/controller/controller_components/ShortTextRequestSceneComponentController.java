package project_tracker_frontend.application.controller.controller_components;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
public class ShortTextRequestSceneComponentController extends BaseComponentController {

    private Logger logger = LoggerFactory.getLogger(ShortTextRequestSceneComponentController.class);

    public Label labelName;
    public TextField inputText;

    public void initialize() {
        logger.info("ShortTextRequestSceneComponentController initialized.");
    }
}
