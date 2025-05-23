package project_tracker_frontend.application.connectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import project_tracker_frontend.application.dto.outgoing.OutgoingParentDetails;

import java.lang.reflect.Field;

public class JsonPayloadFactory {

    private JsonPayloadFactory() {
        // Private constructor to prevent instantiation
    }

    public static String createJsonPayload(OutgoingParentDetails details) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonPayload = objectMapper.createObjectNode();

        Class<?> detailsClass = details.getClass();
        Field[] fields = detailsClass.getDeclaredFields();

        for (Field field : fields) {
            // Make the field accessible for reflection
            field.setAccessible(true); // Allow access to private fields
            try {
                Object value = field.get(details);
                if (value != null) {
                    // Convert the field value to JSON and add it to the JSON payload
                    jsonPayload.set(field.getName(), objectMapper.valueToTree(value));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing field: " + field.getName(), e);
            } finally {
                field.setAccessible(false); // Reset accessibility after use
            }
        }

        // Convert JSON object to string
        return jsonPayload.toString();

    }

}
