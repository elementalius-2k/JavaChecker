package courseproject.javacheck.controller;

import courseproject.javacheck.domain.StudentWork;
import courseproject.javacheck.service.impl.StudentWorkServiceImpl;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

@RestController
public class StudentWorkController {
    private final StudentWorkServiceImpl studentWorkService;

    private final HashMap<String, String> successAnswer = new HashMap<String, String>() {{ put("result", "success"); }};
    private final HashMap<String, String> errorAnswer = new HashMap<String, String>() {{ put("result", "error"); }};

    public StudentWorkController(StudentWorkServiceImpl studentWorkService) {
        this.studentWorkService = studentWorkService;
    }

    @PostMapping("/work")
    public StudentWork addStudentWork(@RequestBody StudentWork studentWork) {
        return studentWorkService.createStudentWork(studentWork);
    }

    @PutMapping("/work")
    public HashMap<String, String> updateStudentWork(@RequestBody StudentWork studentWork) {
        boolean updated = studentWorkService.updateStudentWork(studentWork);
        return updated ? successAnswer : errorAnswer;
    }

    @DeleteMapping("/work/{id}")
    public HashMap<String, String> deleteStudentWork(@PathVariable Integer id) {
        boolean deleted = studentWorkService.deleteStudentWork(id);
        return deleted ? successAnswer : errorAnswer;
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

    @GetMapping("/work/download/{id}")
    public ResponseEntity<Object> downloadFile(@PathVariable Integer id) {
        try {
            String localPath = studentWorkService.getStudentWorkById(id).getLocalPath();
            String fileName = localPath + "/report.txt";
            File file = new File(fileName);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();

            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            headers.add("Cache-Control","no-cache, no-store, must-revalidate");
            headers.add("Pragma","no-cache");
            headers.add("Expires","0");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/txt"))
                    .body(resource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
