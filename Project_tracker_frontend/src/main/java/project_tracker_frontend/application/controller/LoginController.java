package project_tracker_frontend.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import project_tracker_frontend.application.domain.LoginModule;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.UserService;
import project_tracker_frontend.application.service.UserServiceAware;
import project_tracker_frontend.application.utilities.StatusSession;
import project_tracker_frontend.application.utilities.UserSession;

public class LoginController implements SceneEngineAware, UserServiceAware {

    private SceneEngine sceneEngine;

    private UserService userService;

    @FXML
    public Label systemResponseLabel;

    @FXML
    public Label titleLabel;

    @FXML
    public TextField usernameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Button loginButton;

    @FXML
    public Button registerButton;

    @FXML
    public void initialize() {
        titleLabel.setText("Login");
    }

    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        LoginModule loginModule = new LoginModule
                (usernameField.getText(), passwordField.getText());

        userService.loginUser(loginModule);

        if (UserSession.getInstance() != null) {
            // Handle successful login (e.g., switch to the main menu scene)
            sceneEngine.switchScene("menu");
            // Optionally, you can update a label to show the success message
            systemResponseLabel.setText(StatusSession.getInstance().getStatusCode());
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
}

