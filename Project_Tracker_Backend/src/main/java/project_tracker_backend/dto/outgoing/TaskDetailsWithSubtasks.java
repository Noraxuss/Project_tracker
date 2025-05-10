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
public class TaskDetailsWithSubtasks {

    private Long taskId;
    private String taskName;
    private List<SubtaskDetails> subtaskDetails;

}
