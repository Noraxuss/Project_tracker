package project_tracker_frontend.application.service;

import project_tracker_frontend.application.connectors.UserConnector;
import project_tracker_frontend.application.domain.LoginModule;
import project_tracker_frontend.application.domain.UserModule;
import project_tracker_frontend.application.dto.incoming.LoginCommand;
import project_tracker_frontend.application.dto.outgoing.LoginDetails;
import project_tracker_frontend.application.dto.outgoing.RegisterUserDetails;
import project_tracker_frontend.application.application_state.UserState;

public class UserService {

    public void registerUser(UserModule user) {
        // Logic to register a new user
        RegisterUserDetails registerUserDetails = new RegisterUserDetails(
                user.userName(),
                user.password(),
                user.email()
        );

        UserConnector.postRegister(registerUserDetails);
    }

    public void loginUser(LoginModule loginModule) {
        LoginDetails loginDetails = new LoginDetails(loginModule.userName(),
                loginModule.password());

        LoginCommand loginCommand = UserConnector.getLogin(loginDetails);

        UserState userState = UserState.getInstance();
        userState.setUserId(loginCommand.id());
        userState.setUserName(loginCommand.userName());
    }
}
