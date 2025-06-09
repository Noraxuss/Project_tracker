package project_tracker_frontend.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project_tracker_frontend.application.application_state.UserState;
import project_tracker_frontend.application.controller.controller_utilities.ControllerFactory;
import project_tracker_frontend.application.controller.controller_utilities.ControllerFactoryAware;
import project_tracker_frontend.application.controller.controller_utilities.ControllerUtil;
import project_tracker_frontend.application.controller.controller_utilities.ControllerUtilityAware;
import project_tracker_frontend.application.domain.LoginModule;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.UserService;
import project_tracker_frontend.application.service.UserServiceAware;

import java.util.ResourceBundle;

public class LoginController implements SceneEngineAware, UserServiceAware,
        ControllerUtilityAware {

    private SceneEngine sceneEngine;
    private UserService userService;
    private ControllerUtil controllerUtility;

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @FXML
    public Label systemResponseLabel;

    @FXML
    public Label titleLabel;

    @FXML
    public HBox usernameFieldController;

    @FXML
    public HBox passwordFieldController;

    @FXML
    public Button loginButton;

    @FXML
    public Button guestUser;

    @FXML
    public Button registerButton;

    @FXML
    public ResourceBundle resources;

    @FXML
    public void initialize() {
        controllerUtility.localizeIncludedComponent(usernameFieldController,
                "login.username", resources);
        controllerUtility.localizeIncludedComponent(passwordFieldController,
                "login.password", resources);
    }


    @FXML
    public void handleGuest(ActionEvent actionEvent) {

    }

    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        // Create a LoginModule with the input from the username and password fields
        String username = controllerUtility.getInputStringData(usernameFieldController);
        String password = controllerUtility.getInputStringData(passwordFieldController);
        LoginModule loginModule = new LoginModule(username, password);

        userService.loginUser(loginModule);

        if (UserState.getInstance() != null) {

            // Handle successful login (e.g., switch to the main menu scene)

            sceneEngine.switchScene("projects");
            sceneEngine.switchScene("empty_center");
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.close();
            // Optionally, you can update a label to show the success message
//            systemResponseLabel.setText(StatusState.getInstance().getStatusCode());
        } else {
            // Optionally, you can update a label to show the error message
            systemResponseLabel.setText("Invalid username or password");
        }
    }

    @FXML
    public void handleRegister(ActionEvent actionEvent) {
        sceneEngine.switchScene("register");
    }

    @Override
    public void setSceneEngine(SceneEngine engine) {
        this.sceneEngine = engine;
    }

    @Override
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setControllerUtility(ControllerUtil controllerUtility) {
        this.controllerUtility = controllerUtility;
    }
}

