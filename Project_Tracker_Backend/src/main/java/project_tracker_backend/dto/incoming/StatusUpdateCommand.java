package project_tracker_backend.dto.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusUpdateCommand {

    private Long id;
    private String name;
    private String statusPurpose;

}
