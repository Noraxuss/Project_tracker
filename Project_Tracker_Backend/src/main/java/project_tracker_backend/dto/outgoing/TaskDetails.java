package project_tracker_backend.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDetails {

    private Long id;
    private String name;
    private String description;
    private boolean status;

}
