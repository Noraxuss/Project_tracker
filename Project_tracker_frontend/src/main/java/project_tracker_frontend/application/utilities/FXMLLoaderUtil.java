package project_tracker_frontend.application.utilities;

    import javafx.fxml.FXMLLoader;
    import project_tracker_frontend.application.controller.ControllerFactory;
    import project_tracker_frontend.application.scene.SceneEngine;
    import project_tracker_frontend.application.service.ServiceFactory;

    import java.io.IOException;
    import java.util.ResourceBundle;

    public class FXMLLoaderUtil {

        private static ControllerFactory controllerFactory;

        public static void initialize(SceneEngine sceneEngine) {
            controllerFactory = new ControllerFactory(
                    sceneEngine,
                    ServiceFactory.getProjectService(),
                    ServiceFactory.getTaskService(),
                    ServiceFactory.getUserService(),
                    ServiceFactory.getStatusService(),
                    ServiceFactory.getBackendCheckerService()
            );
        }

        public static FXMLLoader loadFXML(String fxmlPath) throws IOException {
            FXMLLoader loader = new FXMLLoader(FXMLLoaderUtil.class.getResource(fxmlPath));
            loader.setResources(ResourceBundle.getBundle("languages/messages_hu"));

            // Use the controller factory
            loader.setControllerFactory(controllerFactory::createController);

            return loader;
        }
    }