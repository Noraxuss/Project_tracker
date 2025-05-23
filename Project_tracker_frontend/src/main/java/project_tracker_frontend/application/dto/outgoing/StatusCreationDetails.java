package project_tracker_frontend.application.dto.outgoing;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusCreationDetails extends OutgoingParentDetails {

    private String name;
    private String statusPurpose;

}
