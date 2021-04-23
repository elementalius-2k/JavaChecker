package courseproject.javacheck.service;

import courseproject.javacheck.domain.StudentWork;

import java.util.List;

public interface StudentWorkService {
    StudentWork createStudentWork(StudentWork studentWork);
    boolean updateStudentWork(StudentWork studentWork);
    boolean deleteStudentWork(Integer id);
    StudentWork getStudentWorkById(Integer id);
    List<StudentWork> getStudentWorksByTaskAndUser(Integer taskId, Integer userId);
    List<StudentWork> getStudentWorksByUser(Integer userId);
    List<StudentWork> getStudentWorkByTask(Integer taskId);
    List<StudentWork> getAllStudentWorks();
    String downloadGithubRepository(String url, String name);
    String checkProject(String path);
}
