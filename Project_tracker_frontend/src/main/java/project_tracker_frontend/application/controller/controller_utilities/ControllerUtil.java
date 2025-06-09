package project_tracker_frontend.application.controller.controller_utilities;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

public class ControllerUtil {

    private final Logger logger = LoggerFactory.getLogger(ControllerUtil.class);

    public void localizeIncludedComponent(Pane includedRoot, String resourceKeyBase, ResourceBundle resources) {
        logger.debug("Localizing included component with base key: {}", resourceKeyBase);
        logger.debug("Check if includedRoot is null: {}", includedRoot == null);

        for (Node node : includedRoot.getChildren()) { // Iterate through all child nodes
            if (node instanceof Label label) { // If the node is a Label
                String key = resourceKeyBase + "_label"; // Construct the key for the label
                if (resources.containsKey(key)) { // Check if the key exists in the ResourceBundle
                    String labelText = resources.getString(key);
                    label.setText(labelText); // Set the label text from the ResourceBundle
                    label.setPrefWidth((labelText.length() * 7)); // Adjust width based on text length
                }
            }
            // You can add Button, Tooltip, etc., here as needed
        }
    }

    // This method retrieves the first TextInputControl from the inputPane
    public String getInputStringData(Pane inputPane) {
        return inputPane.getChildren().stream()
                .filter(TextInputControl.class::isInstance) // Filter for TextInputControl instances
                .findFirst()// Get the first instance
                .map(TextInputControl.class::cast) // Cast to TextInputControl
                .map(TextInputControl::getText)// Get the text from the TextInputControl
                .orElseThrow(() -> new IllegalArgumentException("Field is empty")); // Throw an exception if no TextInputControl is found
    }
}
