package project_tracker.application.connectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import project_tracker.application.dto.incoming.ProjectCommand;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.List;

public class BackendCheckerConnector {

    private static final String PROJECTS = "health";

    public static String checkBackend() {
        String response;
        try {
            HttpURLConnection conn = ConnectorUtilities.getHttpURLConnection
                    (ConnectorUtilities.BASE_URL + PROJECTS + "/check",
                            "GET");

            // Read response
            String jsonResponse = ConnectorUtilities.getResponse(conn);

            ConnectorUtilities.getStatusCode(conn);

            // Deserialize JSON response into a list of ProjectDetails
            ObjectMapper objectMapper = new ObjectMapper();
            response = objectMapper.readValue(jsonResponse,
                    objectMapper.getTypeFactory().constructCollectionType
                            (List.class, ProjectCommand.class));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

}
