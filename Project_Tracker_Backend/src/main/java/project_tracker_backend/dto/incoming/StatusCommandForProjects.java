package project_tracker_backend.dto.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusCommandForProjects {

    private String name;
    private Long projectId;
}
