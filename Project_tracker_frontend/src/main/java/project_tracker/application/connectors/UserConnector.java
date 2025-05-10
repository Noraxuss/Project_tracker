package project_tracker.application.connectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import project_tracker.application.dto.incoming.LoginCommand;
import project_tracker.application.dto.outgoing.LoginDetails;
import project_tracker.application.dto.outgoing.RegisterUserDetails;
import project_tracker.application.service.ServiceFactory;
import project_tracker.application.service.UserService;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class UserConnector {

    private final UserService userService;

    public UserConnector(UserService userService) {
        this.userService = ServiceFactory.getUserService();
    }

    public static String postRegister(RegisterUserDetails userData) {
        String response = "";
        try {
            HttpURLConnection conn = ConnectorUtilities.getHttpURLConnection
                    (ConnectorUtilities.BASE_URL + "users/register",
                            "POST");

            // Dynamically create JSON payload
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonPayload = objectMapper.createObjectNode();
            jsonPayload.put("username", userData.userName());
            jsonPayload.put("password", userData.password());
            jsonPayload.put("email", userData.email());

            // Convert JSON object to string
            String jsonInputString = objectMapper.writeValueAsString(jsonPayload);

            // Write JSON payload to output stream
            ConnectorUtilities.sendPostRequest(conn, jsonInputString);

            ConnectorUtilities.getStatusCode(conn);

            // Read response
            response = ConnectorUtilities.getResponse(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static LoginCommand getLogin(LoginDetails loginDetails) {
        try {
            HttpURLConnection conn = ConnectorUtilities.getHttpURLConnection
                    (ConnectorUtilities.BASE_URL + "users/login/" +
                                    loginDetails.userName() + "/" + loginDetails.password(),
                            "GET");

            String response = ConnectorUtilities.getResponse(conn);
            ObjectMapper objectMapper = new ObjectMapper();

            ConnectorUtilities.getStatusCode(conn);

            return objectMapper.readValue(response, LoginCommand.class);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public static String postRegister(RegisterUserDetails userData) {
//        String response = "";
//        try {
//            HttpsURLConnection conn = ConnectorUtilities.getHttpsURLConnection
//                    (ConnectorUtilities.BASE_URL + "users/register",
//                    "POST");
//
//            // Dynamically create JSON payload
//            ObjectMapper objectMapper = new ObjectMapper();
//            ObjectNode jsonPayload = objectMapper.createObjectNode();
//            jsonPayload.put("username", userData.userName());
//            jsonPayload.put("password", userData.password());
//            jsonPayload.put("email", userData.email());
//
//            // Convert JSON object to string
//            String jsonInputString = objectMapper.writeValueAsString(jsonPayload);
//
//            // Write JSON payload to output stream
//            ConnectorUtilities.sendPostRequest(conn, jsonInputString);
//
//            // Read response
//            response = ConnectorUtilities.getResponse(conn);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    public static LoginCommand getLogin(LoginDetails loginDetails) {
//        try {
//            HttpsURLConnection conn = ConnectorUtilities.getHttpsURLConnection
//                    (ConnectorUtilities.BASE_URL + "users/login/" +
//                            loginDetails.userName() + "/" + loginDetails.password(),
//                    "GET");
//
//            String response = ConnectorUtilities.getResponse(conn);
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.readValue(response, LoginCommand.class);
//        } catch (URISyntaxException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
