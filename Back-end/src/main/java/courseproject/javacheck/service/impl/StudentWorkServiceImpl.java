package courseproject.javacheck.service.impl;

import courseproject.javacheck.domain.StudentWork;
import courseproject.javacheck.domain.Task;
import courseproject.javacheck.domain.User;
import courseproject.javacheck.repo.StudentWorkRepo;
import courseproject.javacheck.repo.TaskRepo;
import courseproject.javacheck.repo.UserRepo;
import courseproject.javacheck.service.StudentWorkService;
import courseproject.javacheck.utils.GithubDownloader;
import courseproject.javacheck.utils.JavaProjectParser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentWorkServiceImpl implements StudentWorkService {
    private final StudentWorkRepo studentWorkRepo;
    private final UserRepo userRepo;
    private final TaskRepo taskRepo;
    private final GithubDownloader githubDownloader;

    public StudentWorkServiceImpl(StudentWorkRepo studentWorkRepo, UserRepo userRepo, TaskRepo taskRepo,
                                  GithubDownloader githubDownloader) {
        this.studentWorkRepo = studentWorkRepo;
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
        this.githubDownloader = githubDownloader;
    }

    @Override
    public StudentWork createStudentWork(StudentWork studentWork) {
        try {
            studentWork.setTeacherReview(null);
            studentWork.setSystemReview(null);
            studentWork.setMark(null);
            studentWork.setDateTime(LocalDateTime.now());

            String workName = studentWork.getUser().getId() + "_" + studentWork.getTask().getId()
                    + "_" + studentWork.getDateTime().toString().replaceAll("[:.]","-");
            String localPath = downloadGithubRepository(studentWork.getPath(), workName);
            if (localPath == null)
                return null;
            studentWork.setLocalPath(localPath);
            String review = checkProject(localPath);
            if (review == null)
                return null;
            studentWork.setSystemReview(review);
            studentWork.setOriginality(null);

            studentWorkRepo.save(studentWork);
            return studentWork;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean updateStudentWork(StudentWork studentWork) {
        try {
            studentWorkRepo.findById(studentWork.getId()).ifPresent(studentWorkToChange -> {
                studentWorkToChange.setTask(studentWork.getTask());
                studentWorkToChange.setUser(studentWork.getUser());
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

    @Override
    public String downloadGithubRepository(String url, String name) {
        return githubDownloader.download(url, name);
    }

    @Override
    public String checkProject(String path) {
        return JavaProjectParser.startCheck(path);
    }
}
