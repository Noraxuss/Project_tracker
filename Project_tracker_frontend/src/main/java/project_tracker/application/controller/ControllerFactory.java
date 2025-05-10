package project_tracker.application.controller;

import project_tracker.application.scene.SceneEngine;
import project_tracker.application.scene.SceneEngineAware;
import project_tracker.application.service.*;

public class ControllerFactory {

    private final SceneEngine sceneEngine;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserService userService;

    public ControllerFactory(SceneEngine sceneEngine,
                             ProjectService projectService,
                             TaskService taskService,
                             UserService userService) {
        this.sceneEngine = sceneEngine;
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
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
            return controller;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create controller: " + controllerClass.getName(), e);
        }
    }

}
