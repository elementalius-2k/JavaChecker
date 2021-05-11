package courseproject.javacheck.services.impl;

import courseproject.javacheck.model.postgresqlModels.Subject;
import courseproject.javacheck.model.postgresqlModels.Task;
import courseproject.javacheck.repositories.postgersqlRepos.SubjectRepo;
import courseproject.javacheck.repositories.postgersqlRepos.TaskRepo;
import courseproject.javacheck.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final SubjectRepo subjectRepo;

    public TaskServiceImpl(TaskRepo taskRepo, SubjectRepo subjectRepo) {
        this.taskRepo = taskRepo;
        this.subjectRepo = subjectRepo;
    }

    @Override
    public Task createTask(Task task) {
        try {
            taskRepo.save(task);
            return task;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean updateTask(Task task) {
        try {
            taskRepo.findById(task.getId()).ifPresent(taskToChange -> {
                taskToChange.setSubject(task.getSubject());
                taskToChange.setName(task.getName());
                taskToChange.setText(task.getText());
                taskToChange.setMaxMark(task.getMaxMark());
                taskToChange.setClassCount(task.getClassCount());
                taskToChange.setMethodCount(task.getMethodCount());
                taskRepo.save(taskToChange);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteTask(Integer id) {
        try {
            Optional<Task> curr = taskRepo.findById(id);
            curr.ifPresent(taskRepo::delete);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Task getTaskById(Integer id) {
        return taskRepo.findById(id).orElse(null);
    }

    @Override
    public List<Task> getTasksBySubject(Integer id) {
        Subject subject = subjectRepo.findById(id).orElse(null);
        if (subject != null)
            return taskRepo.findAllBySubject(subject);
        return new ArrayList<>();
    }

    @Override
    public Task getTaskByName(String name) {
        return taskRepo.findByName(name).orElse(null);
    }

    @Override
    public List<Task> getAllTasks() {
        return (List<Task>) taskRepo.findAll();
    }
}
