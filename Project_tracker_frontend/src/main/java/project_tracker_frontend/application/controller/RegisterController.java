package project_tracker_frontend.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import project_tracker_frontend.application.domain.UserModule;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.UserService;
import project_tracker_frontend.application.service.UserServiceAware;
import project_tracker_frontend.application.application_state.StatusState;


public class RegisterController implements SceneEngineAware, UserServiceAware {

    private UserService userService;
    private SceneEngine sceneEngine;

    @FXML
    public Label SystemResponseLabel;

    @FXML
    public javafx.scene.control.Label titleLabel;

    @FXML
    public javafx.scene.control.TextField usernameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Button registerButton;

    @FXML
    public javafx.scene.control.Button backButton;

    @FXML
    public TextField emailField;

    @FXML
    public void initialize() {
        titleLabel.setText("Register");
    }

    @FXML
    public void handleRegister(ActionEvent actionEvent) {
        userService.registerUser
                (new UserModule(usernameField.getText(),
                        passwordField.getText(), emailField.getText()));

//        SystemResponseLabel.setText(StatusState.getInstance().getStatusCode());

        sceneEngine.switchScene("login");

    }

    @FXML
    public void handleBack(ActionEvent actionEvent) {
        // Switch back to the login scene
        sceneEngine.switchScene("login");
    }

    @Override
    public void setSceneEngine(SceneEngine engine) {
        this.sceneEngine = engine;
    }
    @Override
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
