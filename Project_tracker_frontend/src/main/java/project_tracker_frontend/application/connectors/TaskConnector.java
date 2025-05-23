package project_tracker_frontend.application.connectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import project_tracker_frontend.application.dto.incoming.TaskDataCommand;
import project_tracker_frontend.application.dto.outgoing.CreateTaskDetails;
import project_tracker_frontend.application.service.ServiceFactory;
import project_tracker_frontend.application.service.TaskService;
import project_tracker_frontend.application.application_state.ProjectState;
import project_tracker_frontend.application.application_state.TaskState;

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
            jsonPayload.put("projectId", ProjectState.getInstance().getCurrentProjectId());

            // Convert JSON object to string
            String jsonInputString = objectMapper.writeValueAsString(jsonPayload);

            // Write JSON payload to output stream
            ConnectorUtilities.sendPostRequest(conn, jsonInputString);

//            ConnectorUtilities.getStatusCode(conn);

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
                                    TaskState.getInstance().getTaskId(), "GET");

//            ConnectorUtilities.getStatusCode(conn);

            // Read response
            response = new ObjectMapper().readValue(ConnectorUtilities.getResponse(conn),
                    TaskDataCommand.class);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
