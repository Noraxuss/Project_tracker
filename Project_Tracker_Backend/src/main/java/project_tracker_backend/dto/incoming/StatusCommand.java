package project_tracker_backend.dto.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusCommand {

    private String name;
    private String statusPurpose;
}
