package project_tracker_frontend.application.service;

public class ServiceFactory {

    private static ProjectService projectService;
    private static TaskService taskService;
    private static UserService userService;
    private static StatusService statusService;
    private static BackendCheckerService backendCheckerService;

    private ServiceFactory() {

    }

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

    public static StatusService getStatusService() {
        if (statusService == null) {
            statusService = new StatusService();
        }
        return statusService;
    }

    public static BackendCheckerService getBackendCheckerService() {
        if (backendCheckerService == null) {
            backendCheckerService = new BackendCheckerService();
        }
        return backendCheckerService;
    }
}
