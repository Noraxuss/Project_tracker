package project_tracker_backend.dto.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusCommandForTasks {

    private String name;
    private Long taskId;
}
