package project_tracker_frontend.application.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project_tracker_frontend.application.application_state.StatusState;

public class CreateStatusController {
    public Label titleLabel;
    public TextField statusNameField;
    public ComboBox<String> statusPurposeComboBox;
    public Button createButton;
    public Label systemResponseLabel;
    public ObservableList<String> statusPurposeList;

    public void handleProjectCreation(ActionEvent actionEvent) {
    }

    public void onSceneLoad(){
        statusPurposeList = statusPurposeComboBox.getItems();
        statusPurposeList.clear();

        StatusState.getInstance();

    }
}
