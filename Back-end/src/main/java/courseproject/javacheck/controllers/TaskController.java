package courseproject.javacheck.controllers;

import courseproject.javacheck.model.postgresqlModels.Task;
import courseproject.javacheck.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    private final HashMap<String, String> successAnswer = new HashMap<>() {{
        put("result", "success");
    }};
    private final HashMap<String, String> errorAnswer = new HashMap<>() {{
        put("result", "error");
    }};

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/task")
    public Task addTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/task")
    public HashMap<String, String> updateTask(@RequestBody Task task) {
        return taskService.updateTask(task) ?  successAnswer : errorAnswer;
    }

    @DeleteMapping("/task/{id}")
    public HashMap<String, String> deleteTask(@PathVariable Integer id) {
        return taskService.deleteTask(id) ?  successAnswer : errorAnswer;
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
