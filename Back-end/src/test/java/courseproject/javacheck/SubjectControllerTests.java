package courseproject.javacheck;

import com.fasterxml.jackson.databind.ObjectMapper;
import courseproject.javacheck.controllers.SubjectController;
import courseproject.javacheck.model.postgresqlModels.Subject;
import courseproject.javacheck.model.postgresqlModels.User;
import courseproject.javacheck.services.impl.SubjectServiceImpl;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubjectController.class)
public class SubjectControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SubjectServiceImpl subjectService;

    HashMap<String, String> success = new HashMap<String, String>() {{ put("result", "success"); }};

    @Test
    void givenSubject_whenAdd_thenSubjectReturned() throws Exception {
        Subject subject = createSubject();
        Mockito.when(subjectService.createSubject(Mockito.any(Subject.class))).thenReturn(subject);
        mockMvc.perform(post("/subject")
                .content(objectMapper.writeValueAsString(subject))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(subject)));
    }

    @Test
    void givenSubject_whenUpdate_thenSuccessReturned() throws Exception {
        Subject subject = createSubject();
        Mockito.when(subjectService.updateSubject(Mockito.any(Subject.class))).thenReturn(true);
        mockMvc.perform(put("/subject")
                .content(objectMapper.writeValueAsString(subject))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(success)));
    }

    @Test
    void givenId_whenGetExistingSubject_thenSubjectReturned() throws Exception {
        Subject subject = createSubject();
        Mockito.when(subjectService.getSubjectById(Mockito.anyInt())).thenReturn(subject);
        mockMvc.perform(get("/subject/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(subject)));
    }

    @Test
    void givenName_whenGetExistingSubject_thenSubjectReturned() throws Exception {
        Subject subject = createSubject();
        Mockito.when(subjectService.getSubjectByName(Mockito.anyString())).thenReturn(subject);
        mockMvc.perform(get("/subject/name/name"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(subject)));
    }

    @Test
    void givenTeacherId_whenGetExistingSubjects_thenSubjectsReturned() throws Exception {
        List<Subject> subjects = createTwoSubjects();
        Mockito.when(subjectService.getSubjectsByTeacher(Mockito.anyInt())).thenReturn(subjects);
        mockMvc.perform(get("/subject/teacher/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(subjects)));
    }

    @Test
    void givenSubjects_whenGetSubjects_thenSubjectsReturned() throws Exception {
        List<Subject> subjects = createTwoSubjects();
        Mockito.when(subjectService.getAllSubjects()).thenReturn(subjects);
        mockMvc.perform(get("/subjects"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(subjects)));
    }

    @Test
    void givenSubjectId_whenDeleteSubject_thenSuccessReturned() throws Exception {
        Mockito.when(subjectService.deleteSubject(Mockito.anyInt())).thenReturn(true);
        mockMvc.perform(delete("/subject/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(success)));
    }

    private Subject createSubject() {
        User user = new User("sOmErAnDoMlOgIn","p123","Vasiliy",1,2,true);
        user.setId(1);
        Subject subject = new Subject(user,"name","description");
        subject.setId(1);
        return subject;
    }

    private List<Subject> createTwoSubjects() {
        User user = new User("sOmErAnDoMlOgIn","p123","Vasiliy",1,2,true);
        user.setId(1);
        Subject subject1 = new Subject(user,"name","description");
        subject1.setId(1);
        Subject subject2 = new Subject(user,"new name","description");
        subject2.setId(2);
        return Arrays.asList(subject1, subject2);
    }
}
