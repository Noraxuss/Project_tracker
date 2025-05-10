package project_tracker_backend.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDetailsWithTasks {

    private Long projectId;
    private String projectName;
    private String projectDescription;
    private List<TaskDetailsWithSubtasks> tasksWithSubtasks;

}
