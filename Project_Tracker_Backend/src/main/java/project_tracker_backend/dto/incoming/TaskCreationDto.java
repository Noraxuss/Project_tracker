package project_tracker_backend.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreationDto {

    private String name;
    private String description;
    private Long projectId;
    private Long taskId;
    private Long statusId;
}
