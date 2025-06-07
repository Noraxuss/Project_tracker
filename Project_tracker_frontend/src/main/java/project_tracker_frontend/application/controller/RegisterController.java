package project_tracker_frontend.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import project_tracker_frontend.application.domain.UserModule;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.UserService;
import project_tracker_frontend.application.service.UserServiceAware;

import java.util.ResourceBundle;


public class RegisterController implements SceneEngineAware, UserServiceAware,
ControllerFactoryAware {

    private UserService userService;
    private SceneEngine sceneEngine;
    private ControllerFactory controllerFactory;

    @FXML
    public Label titleLabel;

    @FXML
    public HBox usernameFieldController;

    @FXML
    public HBox passwordFieldController;

    @FXML
    public HBox confirmPasswordFieldController;

    @FXML
    public HBox emailFieldController;

    @FXML
    public Button registerButton;

    @FXML
    public Label systemResponseLabel;

    @FXML
    public javafx.scene.control.Button backButton;

    @FXML
    private ResourceBundle resources;

    @FXML
    public void initialize() {
        controllerFactory.localizeIncludedComponent(usernameFieldController,
                "register.username", resources);
        controllerFactory.localizeIncludedComponent(passwordFieldController,
                "register.password", resources);
        controllerFactory.localizeIncludedComponent(confirmPasswordFieldController,
                "register.confirm_password", resources);
        controllerFactory.localizeIncludedComponent(emailFieldController,
                "register.e-mail", resources);
    }

    @FXML
    public void handleRegister(ActionEvent actionEvent) {
        String username = controllerFactory.getInputStringData(usernameFieldController);
        String password = controllerFactory.getInputStringData(passwordFieldController);
        String confirmPassword = controllerFactory.getInputStringData(confirmPasswordFieldController);
        String email = controllerFactory.getInputStringData(emailFieldController);
        // Validate the input
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
            systemResponseLabel.setText("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            systemResponseLabel.setText("Passwords do not match.");
            return;
        }

        userService.registerUser
                (new UserModule(username, password, email));

//        SystemResponseLabel.setText(StatusState.getInstance().getStatusCode());

        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    public void handleBack(ActionEvent actionEvent) {
        // Switch back to the login scene
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();
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
    public void setControllerFactory(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }
}
