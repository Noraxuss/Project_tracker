package project_tracker.application.connectors;

import project_tracker.application.utilities.StatusSession;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ConnectorUtilities {

    public static final String BASE_URL = "http://localhost:3306/api/";

    protected static String getResponse(HttpURLConnection conn) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = reader.readLine()) != null) {
                response.append(responseLine.trim());
            }
            reader.close();
            return response.toString();
        }
    }

    protected static HttpURLConnection getHttpURLConnection(String urlToBackend, String crudMethod)
            throws URISyntaxException, IOException {
        URI uri = new URI(urlToBackend);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(crudMethod);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        return conn;
    }

    protected static void sendPostRequest(HttpURLConnection conn, String jsonInputString) throws IOException {
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
    }

    protected static void getStatusCode(HttpURLConnection conn) throws IOException {
        String statusCode = String.valueOf(conn.getResponseCode());
        StatusSession.getInstance().setStatusCode(statusCode);
    }

//    public static final String BASE_URL = "https://localhost:3306/api/";
//
//    protected static String getResponse(HttpsURLConnection conn) throws IOException {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),
//                StandardCharsets.UTF_8))) {
//            StringBuilder response = new StringBuilder();
//            String responseLine;
//            while ((responseLine = reader.readLine()) != null) {
//                response.append(responseLine.trim());
//            }
//            reader.close();
//            return response.toString();
//        }
//    }
//
//    protected static HttpsURLConnection getHttpsURLConnection(String urlToBackend, String crudMethod)
//            throws URISyntaxException, IOException {
//        // Temporarily replace "https" with "http" in the URL
//        if (urlToBackend.startsWith("https://")) {
//            urlToBackend = urlToBackend.replaceFirst("https://", "http://");
//        }N
//
//        URI uri = new URI(urlToBackend);
//        URL url = uri.toURL();
//        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//        conn.setRequestMethod(crudMethod);
//        conn.setRequestProperty("Content-Type", "application/json");
//        conn.setDoOutput(true);
//
//        return conn;
//    }
//
//    protected static void sendPostRequest(HttpsURLConnection conn, String jsonInputString) throws IOException {
//        try (OutputStream os = conn.getOutputStream()) {
//            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
//            os.write(input, 0, input.length);
//        }
//    }

}
