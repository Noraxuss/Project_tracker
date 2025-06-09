package project_tracker_frontend.application.utilities;

    import javafx.fxml.FXMLLoader;
    import project_tracker_frontend.application.controller.controller_utilities.ControllerFactory;
    import project_tracker_frontend.application.controller.controller_utilities.ControllerUtil;
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
                    ServiceFactory.getBackendCheckerService(),
                    new ControllerUtil()
            );
        }

        public static FXMLLoader loadFXML(String fxmlPath, String name) throws IOException {
            FXMLLoader loader = new FXMLLoader(FXMLLoaderUtil.class.getResource(fxmlPath));
            ResourceBundle resourceBundle = ResourceBundle.getBundle("languages/messages_hu");
            loader.setResources(resourceBundle);

            // Use the controller factory
            loader.setControllerFactory(controllerFactory::createController);


            return loader;
        }
    }