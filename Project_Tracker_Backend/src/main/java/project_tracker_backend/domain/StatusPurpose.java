package project_tracker_backend.domain;

import lombok.Getter;

@Getter
public enum StatusPurpose {

    TASK("Task"),
    PROJECT("Project"),
    Both("Both");

    private final String purpose;

    StatusPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPurpose() {
        return purpose;
    }
}
