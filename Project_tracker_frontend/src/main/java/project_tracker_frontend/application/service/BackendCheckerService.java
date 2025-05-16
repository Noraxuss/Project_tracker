package project_tracker_frontend.application.service;

import project_tracker_frontend.application.connectors.BackendCheckerConnector;

public class BackendCheckerService {

    public boolean checkBackend() {
        String response = BackendCheckerConnector.checkBackend();
        return response.equals("Backend is running");
    }

}
