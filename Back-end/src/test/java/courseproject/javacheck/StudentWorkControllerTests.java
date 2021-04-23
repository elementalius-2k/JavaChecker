package courseproject.javacheck;

import com.fasterxml.jackson.databind.ObjectMapper;
import courseproject.javacheck.controller.StudentWorkController;
import courseproject.javacheck.domain.StudentWork;
import courseproject.javacheck.domain.Subject;
import courseproject.javacheck.domain.Task;
import courseproject.javacheck.domain.User;
import courseproject.javacheck.service.impl.StudentWorkServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentWorkController.class)
public class StudentWorkControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StudentWorkServiceImpl studentWorkService;

    HashMap<String, String> success = new HashMap<String, String>() {{ put("result", "success"); }};

    @Test
    void givenStudentWork_whenAdd_thenStudentWorkReturned() throws Exception {
        StudentWork studentWork = createStudentWork();
        Mockito.when(studentWorkService.createStudentWork(Mockito.any(StudentWork.class))).thenReturn(studentWork);
        mockMvc.perform(post("/work")
                .content(objectMapper.writeValueAsString(studentWork))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(studentWork)));
    }

    @Test
    void givenStudentWork_whenUpdate_thenSuccessReturned() throws Exception {
        StudentWork studentWork = createStudentWork();
        Mockito.when(studentWorkService.updateStudentWork(Mockito.any(StudentWork.class))).thenReturn(true);
        mockMvc.perform(put("/work")
                .content(objectMapper.writeValueAsString(studentWork))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(success)));
    }

    @Test
    void givenId_whenGetExistingStudentWork_thenStudentWorkReturned() throws Exception {
        StudentWork studentWork = createStudentWork();
        Mockito.when(studentWorkService.getStudentWorkById(Mockito.anyInt())).thenReturn(studentWork);
        mockMvc.perform(get("/work/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(studentWork)));
    }

    @Test
    void givenTaskId_whenGetExistingStudentWorks_thenStudentWorksReturned() throws Exception {
        List<StudentWork> studentWorks = createTwoStudentWorks();
        Mockito.when(studentWorkService.getStudentWorkByTask(Mockito.anyInt())).thenReturn(studentWorks);
        mockMvc.perform(get("/work/task/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(studentWorks)));
    }

    @Test
    void givenUserId_whenGetExistingStudentWorks_thenStudentWorksReturned() throws Exception {
        List<StudentWork> studentWorks = createTwoStudentWorks();
        Mockito.when(studentWorkService.getStudentWorksByUser(Mockito.anyInt())).thenReturn(studentWorks);
        mockMvc.perform(get("/work/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(studentWorks)));
    }

    @Test
    void givenTaskIdAndUserId_whenGetExistingStudentWorks_thenStudentWorksReturned() throws Exception {
        List<StudentWork> studentWorks = createTwoStudentWorks();
        Mockito.when(studentWorkService.getStudentWorksByTaskAndUser(Mockito.anyInt(), Mockito.anyInt())).thenReturn(studentWorks);
        mockMvc.perform(get("/work/task/1/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(studentWorks)));
    }

    @Test
    void givenStudentWorks_whenGetStudentWorks_thenStudentWorksReturned() throws Exception {
        List<StudentWork> studentWorks = createTwoStudentWorks();
        Mockito.when(studentWorkService.getAllStudentWorks()).thenReturn(studentWorks);
        mockMvc.perform(get("/works"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(studentWorks)));
    }

    @Test
    void givenStudentWorkId_whenDeleteStudentWork_thenSuccessReturned() throws Exception {
        Mockito.when(studentWorkService.deleteStudentWork(Mockito.anyInt())).thenReturn(true);
        mockMvc.perform(delete("/work/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(success)));
    }

    private StudentWork createStudentWork() {
        User user = new User("sOmErAnDoMlOgIn","p123","Vasiliy",1,2,true);
        user.setId(1);
        Subject subject = new Subject(user,"name","description");
        subject.setId(1);
        Task task = new Task(subject,"name","desc",100,5,20);
        task.setId(1);
        StudentWork studentWork = new StudentWork(task, user,"path","rev","rev",
                10,100,LocalDateTime.now(), "local path");
        studentWork.setId(1);
        return studentWork;
    }

    private List<StudentWork> createTwoStudentWorks() {
        User user = new User("sOmErAnDoMlOgIn","p123","Vasiliy",1,2,true);
        user.setId(1);
        Subject subject = new Subject(user,"name","description");
        subject.setId(1);
        Task task = new Task(subject,"name","desc",100,5,20);
        task.setId(1);
        StudentWork studentWork1 = new StudentWork(task, user,"path","rev","rev",
                10,100, LocalDateTime.now(), "local path");
        studentWork1.setId(1);
        StudentWork studentWork2 = new StudentWork(task, user,"path","rev","rev",
                10,100,LocalDateTime.now(), "local path");
        studentWork1.setId(2);
        return Arrays.asList(studentWork1, studentWork2);
    }
}
