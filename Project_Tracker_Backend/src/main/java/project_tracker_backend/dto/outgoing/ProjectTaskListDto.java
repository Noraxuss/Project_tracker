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
public class ProjectTaskListDto {

    private Long id;
    private String name;
    private String description;
    private boolean status;
    private List<TaskSubtaskListDto> tasks;
}
