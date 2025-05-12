package project_tracker.application.service;

public class ServiceFactory {

    private static ProjectService projectService;
    private static TaskService taskService;
    private static UserService userService;
    private static BackendCheckerService backendCheckerService;

    public static ProjectService getProjectService() {
        if (projectService == null) {
            projectService = new ProjectService();
        }
        return projectService;
    }

    public static TaskService getTaskService() {
        if (taskService == null) {
            taskService = new TaskService();
        }
        return taskService;
    }

    public static UserService getUserService() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public static BackendCheckerService getBackendCheckerService() {
        if (backendCheckerService == null) {
            backendCheckerService = new BackendCheckerService();
        }
        return backendCheckerService;
    }
}
