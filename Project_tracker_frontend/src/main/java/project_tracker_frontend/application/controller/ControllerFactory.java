package project_tracker_frontend.application.controller;

import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.*;

public class ControllerFactory {

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
            return controller;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create controller: " + controllerClass.getName(), e);
        }
    }

}
