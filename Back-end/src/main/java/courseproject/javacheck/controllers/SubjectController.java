package courseproject.javacheck.controllers;

import courseproject.javacheck.model.postgresqlModels.Subject;
import courseproject.javacheck.services.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class SubjectController {
    private final SubjectService subjectService;

    private final HashMap<String, String> successAnswer = new HashMap<>() {{
        put("result", "success");
    }};
    private final HashMap<String, String> errorAnswer = new HashMap<>() {{
        put("result", "error");
    }};

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/subject")
    public Subject addSubject(@RequestBody Subject subject) {
        return subjectService.createSubject(subject);
    }

    @PutMapping("/subject")
    public HashMap<String, String> changeSubject(@RequestBody Subject subject) {
        return subjectService.updateSubject(subject) ? successAnswer : errorAnswer;
    }

    @DeleteMapping("/subject/{id}")
    public HashMap<String, String> deleteSubject(@PathVariable Integer id) {
        return subjectService.deleteSubject(id) ? successAnswer : errorAnswer;
    }

    @GetMapping("/subject/{id}")
    public Subject getSubjectById(@PathVariable Integer id) {
         return subjectService.getSubjectById(id);
    }

    @GetMapping("/subject/name/{name}")
    public Subject getSubjectByName(@PathVariable String name) {
        return subjectService.getSubjectByName(name);
    }

    @GetMapping("/subject/teacher/{teacherId}")
    public List<Subject> getSubjectsByTeacher(@PathVariable Integer teacherId) {
        return subjectService.getSubjectsByTeacher(teacherId);
    }

    @GetMapping("/subjects")
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }
}
