package project_tracker_frontend.application.connectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import project_tracker_frontend.application.dto.incoming.StatusCommand;
import project_tracker_frontend.application.dto.outgoing.StatusCreationDetails;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.List;

public class StatusConnector {

    private static final String STATUS = "status";

    public static void createStatus(StatusCreationDetails statusCreationDetails) {
        String response;
        try {
            HttpURLConnection conn = ConnectorUtilities.getHttpURLConnection
                    (ConnectorUtilities.BASE_URL + STATUS + "/create-status",
                            "POST");

            // Convert JSON object to string
            String jsonInputString = JsonPayloadFactory.createJsonPayload(statusCreationDetails);

            // Write JSON payload to output stream
            ConnectorUtilities.sendPostRequest(conn, jsonInputString);

            // Read response
            response = ConnectorUtilities.getResponse(conn);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static StatusCommand getStatus(Long statusId) {
        String response;
        try {
            HttpURLConnection conn = ConnectorUtilities.getHttpURLConnection
                    (ConnectorUtilities.BASE_URL + STATUS + "/get-status/"
                                    + statusId,
                            "GET");

            // Read response
            response = ConnectorUtilities.getResponse(conn);

            // Convert JSON string to StatusCommand object
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, StatusCommand.class);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<StatusCommand> getStatusList(String StatusPurpose) {
        String response;
        try {
            HttpURLConnection conn = ConnectorUtilities.getHttpURLConnection
                    (ConnectorUtilities.BASE_URL + STATUS +
                                    "/get-all-statuses-by-status-purpose/" + StatusPurpose,
                            "GET");

            // Read response
            response = ConnectorUtilities.getResponse(conn);

            // Convert JSON string to List<StatusCommand> object
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, StatusCommand.class));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteStatus(Long statusId) {
        String response;
        try {
            HttpURLConnection conn = ConnectorUtilities.getHttpURLConnection
                    (ConnectorUtilities.BASE_URL + STATUS + "/delete-status/" + statusId,
                            "DELETE");

            // Read response
            response = ConnectorUtilities.getResponse(conn);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
