package project_tracker_frontend.application.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import project_tracker_frontend.application.application_state.StatusState;
import project_tracker_frontend.application.domain.CreateStatusModule;
import project_tracker_frontend.application.scene.SceneEngine;
import project_tracker_frontend.application.scene.SceneEngineAware;
import project_tracker_frontend.application.service.StatusService;
import project_tracker_frontend.application.service.StatusServiceAware;

import java.util.ResourceBundle;

public class CreateStatusController implements SceneEngineAware, ControllerFactoryAware,
        StatusServiceAware {

    private ControllerFactory controllerFactory;
    private SceneEngine sceneEngine;
    private StatusService statusService;

    @FXML
    public Label titleLabel;

    @FXML
    public HBox statusNameFieldController;

    @FXML
    public ComboBox<String> statusPurposeComboBox;

    @FXML
    public Button createButton;

    @FXML
    public Label systemResponseLabel;

    public ObservableList<String> statusPurposeList;

    @FXML
    public ResourceBundle resources;

    public void initialize() {
        controllerFactory.localizeIncludedComponent(statusNameFieldController,
                "status_name", resources);
    }

    public void handleStatusCreation(ActionEvent actionEvent) {
        String statusName = controllerFactory.getInputStringData(statusNameFieldController);
        String statusPurpose = statusPurposeComboBox.getValue();

        CreateStatusModule statusModule = new CreateStatusModule(statusName, statusPurpose);

        statusService.createNewStatus(statusModule);
    }

    public void onSceneLoad(){
        statusPurposeList = statusPurposeComboBox.getItems();
        statusPurposeList.clear();
        StatusState.getInstance();
    }

    @Override
    public void setControllerFactory(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    @Override
    public void setSceneEngine(SceneEngine engine) {
        this.sceneEngine = engine;
    }

    @Override
    public void setStatusService(StatusService statusService) {
        this.statusService = statusService;
    }
}
