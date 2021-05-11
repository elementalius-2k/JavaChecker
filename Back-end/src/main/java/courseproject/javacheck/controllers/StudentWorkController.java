package courseproject.javacheck.controllers;

import courseproject.javacheck.model.postgresqlModels.StudentWork;
import courseproject.javacheck.model.elasticsearchModels.Work;
import courseproject.javacheck.services.StudentWorkService;
import courseproject.javacheck.services.WorkService;
import courseproject.javacheck.utils.executor.ParserExecutor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

@RestController
public class StudentWorkController {
    private final StudentWorkService studentWorkService;
    private final WorkService workService;

    private final HashMap<String, String> successAnswer = new HashMap<>() {{
        put("result", "success");
    }};
    private final HashMap<String, String> errorAnswer = new HashMap<>() {{
        put("result", "error");
    }};

    public StudentWorkController(StudentWorkService studentWorkService, WorkService workService) {
        this.studentWorkService = studentWorkService;
        this.workService = workService;
    }

    @PostMapping("/work")
    public HashMap<String, String> addStudentWork(@RequestBody StudentWork studentWork) {
        ParserExecutor executor = ParserExecutor.getInstance(studentWorkService, workService);
        executor.execute(studentWork);
        return successAnswer;
    }

    @DeleteMapping("/works")
    public HashMap<String, String> deleteAllWorks() {
        return workService.deleteAllWorks() ? successAnswer : errorAnswer;
    }

    @PutMapping("/work")
    public HashMap<String, String> updateStudentWork(@RequestBody StudentWork studentWork) {
        return studentWorkService.updateStudentWork(studentWork) ? successAnswer : errorAnswer;
    }

    @DeleteMapping("/work/{id}")
    public HashMap<String, String> deleteStudentWork(@PathVariable Integer id) {
        return studentWorkService.deleteStudentWork(id) ? successAnswer : errorAnswer;
    }

    @GetMapping("/work/{id}")
    public StudentWork getStudentWorkById(@PathVariable Integer id) {
        return studentWorkService.getStudentWorkById(id);
    }

    @GetMapping("/work/task/{taskId}/user/{userId}")
    public List<StudentWork> getStudentWorksByTaskAndUser(@PathVariable Integer taskId, @PathVariable Integer userId) {
        return studentWorkService.getStudentWorksByTaskAndUser(taskId, userId);
    }

    @GetMapping("/work/task/{taskId}")
    public List<StudentWork> getStudentWorksByTask(@PathVariable Integer taskId) {
        return studentWorkService.getStudentWorkByTask(taskId);
    }

    @GetMapping("/work/user/{userId}")
    public List<StudentWork> getStudentWorksByUser(@PathVariable Integer userId) {
        return studentWorkService.getStudentWorksByUser(userId);
    }

    @GetMapping("/works")
    public List<StudentWork> getAllStudentWorks() {
        return studentWorkService.getAllStudentWorks();
    }

    //TODO: тестовый метод
    @GetMapping("/workss")
    public List<Work> getAllWorks() {
        return workService.getAllWorks();
    }

    @GetMapping("/work/download/{id}")
    public ResponseEntity<Object> downloadFile(@PathVariable Integer id) {
        String report;
        Work work = workService.getWorkByStudentWorkId(id);
        if (work == null)
            report = "The work is checking. Report will be available later";
        else
            report = workService.getWorkByStudentWorkId(id).getReport();
        if (report == null)
            report = "The work is checking. Report will be available later";

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(report.getBytes()));
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition","attachment; filename=\"report.txt\"");
        headers.add("Cache-Control","no-cache, no-store, must-revalidate");
        headers.add("Pragma","no-cache");
        headers.add("Expires","0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(report.length())
                .contentType(MediaType.parseMediaType("application/txt"))
                .body(resource);
    }
}
