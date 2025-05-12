package project_tracker.application.utilities;

    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import project_tracker.application.controller.ControllerFactory;
    import project_tracker.application.scene.SceneEngine;
    import project_tracker.application.service.ServiceFactory;

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