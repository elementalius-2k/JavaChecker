package courseproject.javacheck.services.impl;

import courseproject.javacheck.model.postgresqlModels.StudentWork;
import courseproject.javacheck.model.postgresqlModels.Task;
import courseproject.javacheck.model.postgresqlModels.User;
import courseproject.javacheck.repositories.postgersqlRepos.StudentWorkRepo;
import courseproject.javacheck.repositories.postgersqlRepos.TaskRepo;
import courseproject.javacheck.repositories.postgersqlRepos.UserRepo;
import courseproject.javacheck.services.StudentWorkService;
import courseproject.javacheck.utils.GithubDownloader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentWorkServiceImpl implements StudentWorkService {
    private final StudentWorkRepo studentWorkRepo;
    private final UserRepo userRepo;
    private final TaskRepo taskRepo;

    public StudentWorkServiceImpl(StudentWorkRepo studentWorkRepo, UserRepo userRepo, TaskRepo taskRepo) {
        this.studentWorkRepo = studentWorkRepo;
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
    }

    @Override
    public StudentWork createStudentWork(StudentWork studentWork) {
        try {
            studentWork.setTeacherReview(null);
            studentWork.setMark(null);
            studentWork.setSystemReview("The work is checking. Report will be available later");
            studentWork.setOriginality(null);

            studentWorkRepo.save(studentWork);
            return studentWork;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateStudentWork(StudentWork studentWork) {
        try {
            studentWorkRepo.findById(studentWork.getId()).ifPresent(studentWorkToChange -> {
                studentWorkToChange.setPath(studentWork.getPath());
                studentWorkToChange.setSystemReview(studentWork.getSystemReview());
                studentWorkToChange.setTeacherReview(studentWork.getTeacherReview());
                studentWorkToChange.setOriginality(studentWork.getOriginality());
                studentWorkToChange.setMark(studentWork.getMark());
                studentWorkToChange.setDateTime(studentWork.getDateTime());
                studentWorkRepo.save(studentWorkToChange);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteStudentWork(Integer id) {
        try {
            Optional<StudentWork> curr = studentWorkRepo.findById(id);
            curr.ifPresent(studentWorkRepo::delete);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public StudentWork getStudentWorkById(Integer id) {
        return studentWorkRepo.findById(id).orElse(null);
    }

    @Override
    public List<StudentWork> getStudentWorksByTaskAndUser(Integer taskId, Integer userId) {
        User user = userRepo.findById(userId).orElse(null);
        Task task = taskRepo.findById(taskId).orElse(null);
        if (user != null && task != null)
            return studentWorkRepo.findAllByTaskAndUser(task, user);
        return new ArrayList<>();
    }

    @Override
    public List<StudentWork> getStudentWorksByUser(Integer userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (user != null)
            return studentWorkRepo.findAllByUser(user);
        return new ArrayList<>();
    }

    @Override
    public List<StudentWork> getStudentWorkByTask(Integer taskId) {
        Task task = taskRepo.findById(taskId).orElse(null);
        if (task != null)
            return studentWorkRepo.findAllByTask(task);
        return new ArrayList<>();
    }

    @Override
    public List<StudentWork> getAllStudentWorks() {
        return (List<StudentWork>) studentWorkRepo.findAll();
    }

    public String downloadGithubRepository(String url, String name) {
        return GithubDownloader.download(url, name);
    }
}
