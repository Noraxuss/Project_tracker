package project_tracker_frontend.application.controller.controller_utilities;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.*;

import java.util.ResourceBundle;

public class ControllerFactory {

    private final Logger logger = LoggerFactory.getLogger(ControllerFactory.class.getName());

    private final SceneEngine sceneEngine;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserService userService;
    private final StatusService statusService;
    private final BackendCheckerService backendCheckerService;
    private final ControllerUtil controllerUtility;

    public ControllerFactory(SceneEngine sceneEngine,
                             ProjectService projectService,
                             TaskService taskService,
                             UserService userService, StatusService statusService, BackendCheckerService backendCheckerservice, ControllerUtil controllerUtility) {
        this.sceneEngine = sceneEngine;
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
        this.statusService = statusService;
        this.backendCheckerService = backendCheckerservice;
        this.controllerUtility = controllerUtility;
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
            if (controller instanceof ControllerUtilityAware) {
                ((ControllerUtilityAware) controller).setControllerUtility(controllerUtility);
            }
            return controller;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create controller: " + controllerClass.getName(), e);
        }
    }
}
