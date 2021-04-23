package courseproject.javacheck.service;

import courseproject.javacheck.domain.Task;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    boolean updateTask(Task task);
    boolean deleteTask(Integer id);
    Task getTaskById(Integer id);
    List<Task> getTasksBySubject(Integer id);
    Task getTaskByName(String name);
    List<Task> getAllTasks();
}
