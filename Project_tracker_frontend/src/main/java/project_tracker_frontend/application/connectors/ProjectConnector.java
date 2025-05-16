package project_tracker_frontend.application.connectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import project_tracker_frontend.application.dto.incoming.ProjectCommand;
import project_tracker_frontend.application.dto.incoming.ProjectWithTasksCommand;
import project_tracker_frontend.application.dto.outgoing.ProjectDetails;
import project_tracker_frontend.application.dto.outgoing.ProjectIdDetails;
import project_tracker_frontend.application.service.ProjectService;
import project_tracker_frontend.application.service.ServiceFactory;
import project_tracker_frontend.application.utilities.UserSession;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.List;

public class ProjectConnector {

    private static final String PROJECTS = "projects";
    private final ProjectService projectService;

    public ProjectConnector() {
        this.projectService = ServiceFactory.getProjectService();
    }

    public static void createProject(ProjectDetails projectDetails) {
        String response;
        try {
            HttpURLConnection conn = ConnectorUtilities.getHttpURLConnection
                    (ConnectorUtilities.BASE_URL + PROJECTS + "/create",
                            "POST");

            // Dynamically create JSON payload
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonPayload = objectMapper.createObjectNode();
            jsonPayload.put("name", projectDetails.projectName());
            jsonPayload.put("description", projectDetails.projectDescription());
            jsonPayload.put("userId", UserSession.getInstance().getUserId());

            // Convert JSON object to string
            String jsonInputString = objectMapper.writeValueAsString(jsonPayload);

            // Write JSON payload to output stream
            ConnectorUtilities.sendPostRequest(conn, jsonInputString);

            ConnectorUtilities.getStatusCode(conn);

            // Read response
            response = ConnectorUtilities.getResponse(conn);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ProjectCommand> getAllProjects(Long id) {
        List<ProjectCommand> response;
        try {
            HttpURLConnection conn = ConnectorUtilities.getHttpURLConnection
                    (ConnectorUtilities.BASE_URL + PROJECTS + "/user-projects/" + id,
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

    public static ProjectWithTasksCommand getProjectWithTasksAndSubtasks(ProjectIdDetails projectIdDetails) {
        ProjectWithTasksCommand response;
        System.out.println(projectIdDetails.id());
        try {
            HttpURLConnection conn = ConnectorUtilities.getHttpURLConnection
                    (ConnectorUtilities.BASE_URL + PROJECTS + "/project-tasks-and-subtasks/" + projectIdDetails.id(),
                            "GET");
            String jsonResponse = ConnectorUtilities.getResponse(conn);
            ObjectMapper objectMapper = new ObjectMapper();

            ConnectorUtilities.getStatusCode(conn);

            response = objectMapper.readValue(jsonResponse,
                    objectMapper.getTypeFactory().
                            constructType(ProjectWithTasksCommand.class));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

//    public static void createProject(ProjectDetails projectDetails) {
//        String response;
//        try {
//            HttpsURLConnection conn = ConnectorUtilities.getHttpsURLConnection
//                    (ConnectorUtilities.BASE_URL + "projects/create",
//                            "POST");
//
//            // Dynamically create JSON payload
//            ObjectMapper objectMapper = new ObjectMapper();
//            ObjectNode jsonPayload = objectMapper.createObjectNode();
//            jsonPayload.put("name", projectDetails.projectName());
//            jsonPayload.put("description", projectDetails.projectDescription());
//            jsonPayload.put("userId", UserSession.getInstance().getUserId());
//
//            // Convert JSON object to string
//            String jsonInputString = objectMapper.writeValueAsString(jsonPayload);
//
//
//            // Write JSON payload to output stream
//            ConnectorUtilities.sendPostRequest(conn, jsonInputString);
//
//            // Read response
//            response = ConnectorUtilities.getResponse(conn);
//        } catch (URISyntaxException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static List<ProjectCommand> getAllProjects(Long id) {
//        List<ProjectCommand> response;
//        try {
//            HttpsURLConnection conn = ConnectorUtilities.getHttpsURLConnection
//                    (ConnectorUtilities.BASE_URL + "projects/user-projects/" + id,
//                            "GET");
//
//            // Read response
//            String jsonResponse = ConnectorUtilities.getResponse(conn);
//
//            // Deserialize JSON response into a list of ProjectDetails
//            ObjectMapper objectMapper = new ObjectMapper();
//            response = objectMapper.readValue(jsonResponse,
//                    objectMapper.getTypeFactory().constructCollectionType
//                            (List.class, ProjectCommand.class));
//        } catch (URISyntaxException | IOException e) {
//            throw new RuntimeException(e);
//        }
//        return response;
//    }
}
