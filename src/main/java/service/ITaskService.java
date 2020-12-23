package service;

import model.Task;
import model.TaskStatus;

import java.util.List;

public interface ITaskService {

    void createTask(Task task);

    void logHours(int taskId, int nrHours);

    void changeStatus(int taskId, TaskStatus status);

    void assignTask(int taskId, int userId);

    void update(Task task);

    void deleteTask(int taskId);

    void deleteAllProjectTasks(int projectId);

    Task findTaskById(int id);

    List<Task> findUsersTasks(int userId);

    List<Task> findAll();

    List<Task> findTasksByStatus(TaskStatus status);

}
