package courseproject.javacheck;

import com.fasterxml.jackson.databind.ObjectMapper;
import courseproject.javacheck.controllers.TaskController;
import courseproject.javacheck.model.postgresqlModels.Subject;
import courseproject.javacheck.model.postgresqlModels.Task;
import courseproject.javacheck.model.postgresqlModels.User;
import courseproject.javacheck.services.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TaskServiceImpl taskService;

    HashMap<String, String> success = new HashMap<String, String>() {{ put("result", "success"); }};

    @Test
    void givenTask_whenAdd_thenTaskReturned() throws Exception {
        Task task = createTask();
        Mockito.when(taskService.createTask(Mockito.any(Task.class))).thenReturn(task);
        mockMvc.perform(post("/task")
                .content(objectMapper.writeValueAsString(task))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(task)));
    }

    @Test
    void givenTask_whenUpdate_thenSuccessReturned() throws Exception {
        Task task = createTask();
        Mockito.when(taskService.updateTask(Mockito.any(Task.class))).thenReturn(true);
        mockMvc.perform(put("/task")
                .content(objectMapper.writeValueAsString(task))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(success)));
    }

    @Test
    void givenId_whenGetExistingTask_thenTaskReturned() throws Exception {
        Task task = createTask();
        Mockito.when(taskService.getTaskById(Mockito.anyInt())).thenReturn(task);
        mockMvc.perform(get("/task/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(task)));
    }

    @Test
    void givenName_whenGetExistingTask_thenTaskReturned() throws Exception {
        Task task = createTask();
        Mockito.when(taskService.getTaskByName(Mockito.anyString())).thenReturn(task);
        mockMvc.perform(get("/task/name/name"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(task)));
    }

    @Test
    void givenTasks_whenGetTasks_thenTasksReturned() throws Exception {
        List<Task> tasks = createTwoTasks();
        Mockito.when(taskService.getAllTasks()).thenReturn(tasks);
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(tasks)));
    }

    @Test
    void givenSubjectId_whenGetExistingTasks_thenTasksReturned() throws Exception {
        List<Task> tasks = createTwoTasks();
        Mockito.when(taskService.getTasksBySubject(Mockito.anyInt())).thenReturn(tasks);
        mockMvc.perform(get("/task/subject/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(tasks)));
    }

    @Test
    void givenTaskId_whenDeleteTask_thenSuccessReturned() throws Exception {
        Mockito.when(taskService.deleteTask(Mockito.anyInt())).thenReturn(true);
        mockMvc.perform(delete("/task/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(success)));
    }

    private Task createTask() {
        User user = new User("sOmErAnDoMlOgIn","p123","Vasiliy",1,2,true);
        user.setId(1);
        Subject subject = new Subject(user,"name","description");
        subject.setId(1);
        Task task = new Task(subject,"name","desc",100,5,20);
        task.setId(1);
        return task;
    }

    private List<Task> createTwoTasks() {
        User user = new User("sOmErAnDoMlOgIn","p123","Vasiliy",1,2,true);
        user.setId(1);
        Subject subject = new Subject(user,"name","description");
        subject.setId(1);
        Task task1 = new Task(subject,"name","desc",100,5,20);
        task1.setId(1);
        Task task2 = new Task(subject,"another name","desc",100,5,20);
        task2.setId(2);
        return Arrays.asList(task1, task2);
    }
}
