package courseproject.javacheck.controller;

import courseproject.javacheck.domain.Subject;
import courseproject.javacheck.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/subject")
    public HashMap<String, String> test(@RequestBody Subject subject) {
        boolean isAdded = subjectService.createSubject(subject);

        System.out.println(subject.getName());

        if (isAdded) {
            return new HashMap<String, String>() {{
                put("result", "success");
            }};
        } else {
            return new HashMap<String, String>() {{
                put("result", "error");
            }};
        }
    }

    @PutMapping("/subject")
    public HashMap<String, String> change(@RequestBody Subject subject) {

        subjectService.updateSubject(subject);
        return new HashMap<String, String>() {{
            put("result", "success");
        }};
    }

    @DeleteMapping("/subject/{id}")
    public HashMap<String, String> delete(@PathVariable Integer id) {

        subjectService.deleteSubject(id);
        return new HashMap<String, String>() {{
            put("result", "success");
        }};
    }

    @GetMapping("/subject/{id}")
    public Subject test(@PathVariable Integer id) {
         return subjectService.getSubjectById(id);
    }

    @GetMapping("/subjects")
    public List<Subject> allSubjects() {
        return subjectService.getAllSubjects();
    }

}
