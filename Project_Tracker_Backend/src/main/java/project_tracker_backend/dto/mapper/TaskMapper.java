package project_tracker_backend.dto.mapper;

import org.springframework.stereotype.Component;
import project_tracker_backend.domain.Task;
import project_tracker_backend.dto.incoming.TaskCreationDto;
import project_tracker_backend.dto.outgoing.SubtaskDetails;
import project_tracker_backend.dto.outgoing.TaskDetails;
import project_tracker_backend.dto.outgoing.TaskDetailsWithSubtasks;

import java.util.ArrayList;

@Component
public class TaskMapper {

    public Task mapTaskCreationDtoToTask(TaskCreationDto taskCreationDto) {
        Task task = new Task();
        task.setName(taskCreationDto.getName());
        task.setDescription(taskCreationDto.getDescription());
        return task;
    }


    public TaskDetailsWithSubtasks mapTaskToTaskDetailsWithSubtasks(Task task) {
        TaskDetailsWithSubtasks taskDetailsWithSubtasks = new TaskDetailsWithSubtasks();
        taskDetailsWithSubtasks.setTaskId(task.getId());
        taskDetailsWithSubtasks.setTaskName(task.getName());
        taskDetailsWithSubtasks.setSubtaskDetails(new ArrayList<>());
        return taskDetailsWithSubtasks;
    }

    public SubtaskDetails mapTaskToSubtaskDetails(Task subTask) {
        SubtaskDetails subtaskDetails = new SubtaskDetails();
        subtaskDetails.setId(subTask.getId());
        subtaskDetails.setParentTaskId(subTask.getParentTask().getId());
        subtaskDetails.setName(subTask.getName());
        return subtaskDetails;
    }


    public TaskDetails mapTaskToTaskDetails(Task task) {
        TaskDetails taskDetails = new TaskDetails();
        taskDetails.setId(task.getId());
        taskDetails.setName(task.getName());
        taskDetails.setDescription(task.getDescription());
        return taskDetails;
    }
}
