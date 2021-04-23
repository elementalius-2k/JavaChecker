package courseproject.javacheck.controller;

import courseproject.javacheck.domain.Subject;
import courseproject.javacheck.service.impl.SubjectServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class SubjectController {
    private final SubjectServiceImpl subjectService;

    private final HashMap<String, String> successAnswer = new HashMap<String, String>() {{ put("result", "success"); }};
    private final HashMap<String, String> errorAnswer = new HashMap<String, String>() {{ put("result", "error"); }};

    public SubjectController(SubjectServiceImpl subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/subject")
    public Subject addSubject(@RequestBody Subject subject) {
        return subjectService.createSubject(subject);
    }

    @PutMapping("/subject")
    public HashMap<String, String> changeSubject(@RequestBody Subject subject) {
        boolean updated = subjectService.updateSubject(subject);
        return updated ? successAnswer : errorAnswer;
    }

    @DeleteMapping("/subject/{id}")
    public HashMap<String, String> deleteSubject(@PathVariable Integer id) {
        boolean deleted = subjectService.deleteSubject(id);
        return deleted ? successAnswer : errorAnswer;
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
