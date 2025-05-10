package project_tracker.application.service;

import project_tracker.application.connectors.UserConnector;
import project_tracker.application.domain.LoginModule;
import project_tracker.application.domain.UserModule;
import project_tracker.application.dto.incoming.LoginCommand;
import project_tracker.application.dto.outgoing.LoginDetails;
import project_tracker.application.dto.outgoing.RegisterUserDetails;
import project_tracker.application.utilities.UserSession;

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

        UserSession userSession = UserSession.getInstance();
        userSession.setUserId(loginCommand.id());
        userSession.setUserName(loginCommand.userName());
    }
}
