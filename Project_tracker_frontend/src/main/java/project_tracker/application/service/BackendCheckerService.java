package project_tracker.application.service;

import project_tracker.application.connectors.BackendCheckerConnector;

public class BackendCheckerService {

    public String checkBackend() {
        return BackendCheckerConnector.checkBackend();
    }

}
