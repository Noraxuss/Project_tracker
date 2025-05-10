package project_tracker.application.connectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import project_tracker.application.dto.incoming.TaskDataCommand;
import project_tracker.application.dto.outgoing.CreateTaskDetails;
import project_tracker.application.service.ServiceFactory;
import project_tracker.application.service.TaskService;
import project_tracker.application.utilities.ProjectSession;
import project_tracker.application.utilities.TaskSession;
import project_tracker.application.utilities.UserSession;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class TaskConnector {

    private final TaskService taskService;

    public TaskConnector(TaskService taskService) {
        this.taskService = ServiceFactory.getTaskService();
    }

    public static void createTask(CreateTaskDetails createTaskDetails) {
        String response;
        try {
            HttpURLConnection conn = ConnectorUtilities.getHttpURLConnection
                    (ConnectorUtilities.BASE_URL + "tasks/create",
                            "POST");

            // Dynamically create JSON payload
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonPayload = objectMapper.createObjectNode();
            jsonPayload.put("name", createTaskDetails.name());
            jsonPayload.put("description", createTaskDetails.description());
            jsonPayload.put("projectId", ProjectSession.getInstance().getCurrentProjectId());

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

    public static TaskDataCommand getTaskData() {
        TaskDataCommand response;
        try {
            HttpURLConnection conn = ConnectorUtilities.getHttpURLConnection
                    (ConnectorUtilities.BASE_URL + "tasks/task-details/" +
                                    TaskSession.getInstance().getTaskId(), "GET");

            ConnectorUtilities.getStatusCode(conn);

            // Read response
            response = new ObjectMapper().readValue(ConnectorUtilities.getResponse(conn),
                    TaskDataCommand.class);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
