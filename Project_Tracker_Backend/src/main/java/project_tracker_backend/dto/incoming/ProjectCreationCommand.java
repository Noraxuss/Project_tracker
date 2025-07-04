package project_tracker_backend.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreationCommand {

    private String name;
    private String description;
    private Long userId;
    private Long statusId;


}
