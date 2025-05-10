package project_tracker_backend.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubtaskDetails {

    private Long id;
    private Long parentTaskId;
    private String name;
}
