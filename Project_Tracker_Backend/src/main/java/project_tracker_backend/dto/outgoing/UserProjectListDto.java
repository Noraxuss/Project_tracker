package project_tracker_backend.dto.outgoing;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project_tracker_backend.domain.Project;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProjectListDto {

    private Long id;
    private String name;
    private List<ProjectTaskListDto> projects;

}
