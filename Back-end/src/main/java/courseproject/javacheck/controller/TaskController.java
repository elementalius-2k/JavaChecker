package courseproject.javacheck.controller;

import courseproject.javacheck.domain.Task;
import courseproject.javacheck.service.impl.TaskServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class TaskController {
    private final TaskServiceImpl taskService;

    private final HashMap<String, String> successAnswer = new HashMap<String, String>() {{ put("result", "success"); }};
    private final HashMap<String, String> errorAnswer = new HashMap<String, String>() {{ put("result", "error"); }};

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/task")
    public Task addTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/task")
    public HashMap<String, String> updateTask(@RequestBody Task task) {
        boolean updated = taskService.updateTask(task);
        return updated ?  successAnswer : errorAnswer;
    }

    @DeleteMapping("/task/{id}")
    public HashMap<String, String> deleteTask(@PathVariable Integer id) {
        boolean deleted = taskService.deleteTask(id);
        return deleted ?  successAnswer : errorAnswer;
    }

    @GetMapping("/task/{id}")
    public Task getTaskById(@PathVariable Integer id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/task/name/{name}")
    public Task getTaskByName(@PathVariable String name) {
        return taskService.getTaskByName(name);
    }

    @GetMapping("/task/subject/{subjectId}")
    public List<Task> getTasksBySubject(@PathVariable Integer subjectId) {
        return taskService.getTasksBySubject(subjectId);
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
}
