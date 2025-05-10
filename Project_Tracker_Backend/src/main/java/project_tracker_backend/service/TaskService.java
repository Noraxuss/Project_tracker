package project_tracker_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import project_tracker_backend.domain.Project;
import project_tracker_backend.domain.Status;
import project_tracker_backend.domain.Task;
import project_tracker_backend.dto.incoming.TaskCreationDto;
import project_tracker_backend.dto.mapper.TaskMapper;
import project_tracker_backend.dto.outgoing.SubtaskDetails;
import project_tracker_backend.dto.outgoing.TaskDetails;
import project_tracker_backend.dto.outgoing.TaskDetailsWithSubtasks;
import project_tracker_backend.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectService projectService;
    private final StatusService statusService;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, @Lazy ProjectService projectService, StatusService statusService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectService = projectService;
        this.statusService = statusService;
    }

    public void createTask(TaskCreationDto taskCreationDto) {
        Task task = taskMapper.mapTaskCreationDtoToTask(taskCreationDto);
        Project project = null;
        if (taskCreationDto.getProjectId() < 0L) {
            project = projectService.findProjectById(taskCreationDto.getProjectId());
        }
        Task parentTask = null;
        if (taskCreationDto.getTaskId() < 0L) {
            parentTask = findTaskById(taskCreationDto.getTaskId());
        }
        Status status = null;
        if (taskCreationDto.getStatusId() < 0L) {
            status = statusService.findStatusById(taskCreationDto.getStatusId());
        }
        task.setProject(project);
        task.setParentTask(parentTask);
        task.setStatus(status);
        taskRepository.save(task);
    }

    private Task findTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    public void autoAssignSubTasks(Task task) {
        List<Task> autoSubTaskList = new ArrayList<>();
        // TODO when autolist is introduced this method will slightly change
        task.setSubTask(autoSubTaskList);
    }

    public List<TaskDetailsWithSubtasks> getTasksAndSubtasks(Long projectId) {
        List<Task> taskList = findTasksInRepository(projectId);
        List<TaskDetailsWithSubtasks> taskDetailsWithSubtasks = new ArrayList<>();
        for (Task task : taskList) {
            TaskDetailsWithSubtasks taskDto = taskMapper.mapTaskToTaskDetailsWithSubtasks(task);
            addSubtasks(task, taskDto);
            taskDetailsWithSubtasks.add(taskDto);
        }

        return taskDetailsWithSubtasks;
    }

    private void addSubtasks(Task task, TaskDetailsWithSubtasks taskDto) {
        for (Task subtask : task.getSubTask()) {
            SubtaskDetails subtaskDto = taskMapper.mapTaskToSubtaskDetails(subtask);
            taskDto.getSubtaskDetails().add(subtaskDto);
        }
    }

    private List<Task> findTasksInRepository(Long projectId) {
        return taskRepository.findAllTasksByProject(projectId);
    }

    public TaskDetails getTaskDetails(Long taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        TaskDetails taskDetails = taskMapper.mapTaskToTaskDetails(task);
        return taskDetails;
    }
}
