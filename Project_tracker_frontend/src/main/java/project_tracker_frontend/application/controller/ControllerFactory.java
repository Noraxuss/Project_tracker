package project_tracker_frontend.application.controller;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project_tracker_frontend.application.controller.controller_components.BaseComponentController;
import project_tracker_frontend.application.controller.controller_utilities.ResourceBundleName;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.*;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerFactory {

    private final Logger logger = LoggerFactory.getLogger(ControllerFactory.class.getName());

    private final SceneEngine sceneEngine;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserService userService;
    private final StatusService statusService;
    private final BackendCheckerService backendCheckerService;

    public ControllerFactory(SceneEngine sceneEngine,
                             ProjectService projectService,
                             TaskService taskService,
                             UserService userService, StatusService statusService, BackendCheckerService backendCheckerservice) {
        this.sceneEngine = sceneEngine;
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
        this.statusService = statusService;
        this.backendCheckerService = backendCheckerservice;
    }

    public Object createController(Class<?> controllerClass) {
        try {
            Object controller = controllerClass.getDeclaredConstructor().newInstance();
            if (controller instanceof SceneEngineAware) {
                ((SceneEngineAware) controller).setSceneEngine(sceneEngine);
            }
            if (controller instanceof ProjectServiceAware) {
                ((ProjectServiceAware) controller).setProjectService(projectService);
            }
            if (controller instanceof TaskServiceAware) {
                ((TaskServiceAware) controller).setTaskService(taskService);
            }
            if (controller instanceof UserServiceAware) {
                ((UserServiceAware) controller).setUserService(userService);
            }
            if (controller instanceof BackendCheckerServiceAware) {
                ((BackendCheckerServiceAware) controller).setBackendCheckerService(backendCheckerService);
            }
            if (controller instanceof StatusServiceAware) {
                ((StatusServiceAware) controller).setStatusService(statusService);
            }
            if (controller instanceof ControllerFactoryAware) {
                ((ControllerFactoryAware) controller).setControllerFactory(this);
            }
            return controller;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create controller: " + controllerClass.getName(), e);
        }
    }

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
