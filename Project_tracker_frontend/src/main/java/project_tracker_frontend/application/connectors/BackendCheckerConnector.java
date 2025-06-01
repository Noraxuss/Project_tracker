package project_tracker_frontend.application.connectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;


public class BackendCheckerConnector {

    private static final String PROJECTS = "health";

    private static final Logger logger = LoggerFactory.getLogger(BackendCheckerConnector.class);

    private BackendCheckerConnector() {
        // Private constructor to prevent instantiation
    }

    public static String checkBackend() {
        String response = null;
        String jsonResponse = null;
        try {
            HttpURLConnection conn = ConnectorUtilities.getHttpURLConnection
                    (ConnectorUtilities.BASE_URL + PROJECTS + "/check",
                            "GET");

            // Read response
            jsonResponse = ConnectorUtilities.getResponse(conn);

//            ConnectorUtilities.getStatusCode(conn);

            // Deserialize JSON response into a list of ProjectDetails
//            ObjectMapper objectMapper = new ObjectMapper();
//            response = objectMapper.readValue(jsonResponse,
//                    objectMapper.getTypeFactory().constructCollectionType
//                            (String.class, String.class));
        } catch (URISyntaxException | IOException | RuntimeException e) {
            logger.error(e.getMessage());
            logger.error(jsonResponse);
            logger.error(response);
            throw new RuntimeException(e);
        }
        return jsonResponse;
    }

}
