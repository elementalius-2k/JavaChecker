package courseproject.javacheck;

import courseproject.javacheck.domain.StudentWork;
import courseproject.javacheck.domain.Subject;
import courseproject.javacheck.domain.Task;
import courseproject.javacheck.domain.User;
import courseproject.javacheck.service.impl.StudentWorkServiceImpl;
import courseproject.javacheck.service.impl.SubjectServiceImpl;
import courseproject.javacheck.service.impl.TaskServiceImpl;
import courseproject.javacheck.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class JavaCheckServicesTests {
    @Resource
    private StudentWorkServiceImpl studentWorkService;
    @Resource
    private SubjectServiceImpl subjectService;
    @Resource
    private TaskServiceImpl taskService;
    @Resource
    private UserServiceImpl userService;

    @Test
    void testUserService() {
        //Create
        User user = new User("sOmErAnDoMlOgIn","p123","Vasiliy",1,2,false);
        User createdUser = userService.createUser(user);
        assert (createdUser != null);
        assert (user.getId().equals(createdUser.getId()));

        //Find by id
        User userById = userService.getUserById(user.getId());
        assert (userById != null);
        assert (user.getId().equals(userById.getId()));
        //Find by login and password
        User userByLogin = userService.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
        assert (userByLogin != null);
        assert (user.getId().equals(userByLogin.getId()));
        //Find by full name
        User userByName = userService.getUserByFullName(user.getFullName());
        assert (userByName != null);
        assert (user.getId().equals(userByName.getId()));
        //All
        List<User> users = userService.getAllUsers();
        assert (users != null);
        assert (!users.isEmpty());
        assert (!users.contains(null));

        //Update
        user.setFullName("Not Vasiliy");
        boolean updated = userService.updateUser(user);
        assert (updated);
        assert (userService.getUserById(user.getId()).getFullName().equals("Not Vasiliy"));

        //Delete
        boolean deleted = userService.deleteUser(user.getId());
        User nullUser = userService.getUserById(user.getId());
        assert (deleted);
        assert (nullUser == null);
    }

    @Test
    void testSubjectService() {
        //Foreign key
        User user = new User("sOmErAnDoMlOgIn","p123","Vasiliy",1,2,true);
        userService.createUser(user);

        //Create
        Subject subject = new Subject(user,"subj","some description");
        Subject createdSubject = subjectService.createSubject(subject);
        assert (createdSubject != null);
        assert (subject.getId().equals(createdSubject.getId()));

        //Find by id
        Subject subjectById = subjectService.getSubjectById(subject.getId());
        assert (subjectById != null);
        assert (subject.getId().equals(subjectById.getId()));
        //Find by name
        Subject subjectByName = subjectService.getSubjectByName(subject.getName());
        assert (subjectByName != null);
        assert (subject.getId().equals(subjectByName.getId()));
        //Find by teacher id
        List<Subject> subjectsByTeacher = subjectService.getSubjectsByTeacher(user.getId());
        assert (subjectsByTeacher != null);
        assert (!subjectsByTeacher.isEmpty());
        assert (!subjectsByTeacher.contains(null));
        for (Subject curr : subjectsByTeacher)
            assert (user.getId().equals(curr.getTeacher().getId()));
        //All
        List<Subject> subjects = subjectService.getAllSubjects();
        assert (subjects != null);
        assert (!subjects.isEmpty());
        assert (!subjects.contains(null));

        //Update
        subject.setName("New name!");
        boolean updated = subjectService.updateSubject(subject);
        assert (updated);
        assert (subjectService.getSubjectById(subject.getId()).getName().equals("New name!"));

        //Delete
        boolean deleted = subjectService.deleteSubject(subject.getId());
        Subject nullSubject = subjectService.getSubjectById(subject.getId());
        assert (deleted);
        assert (nullSubject == null);

        //Delete foreign key
        userService.deleteUser(user.getId());
    }

    @Test
    void testTaskService() {
        //Foreign keys
        User user = new User("sOmErAnDoMlOgIn","p123","Vasiliy",1,2,true);
        userService.createUser(user);
        Subject subject = new Subject(user,"subj","some description");
        subjectService.createSubject(subject);

        //Create
        Task task = new Task(subject,"name","some description",100,5,10);
        Task createdTask = taskService.createTask(task);
        assert (createdTask != null);
        assert (task.getId().equals(createdTask.getId()));

        //Find by id
        Task taskById = taskService.getTaskById(task.getId());
        assert (taskById != null);
        assert (task.getId().equals(taskById.getId()));
        //Find by name
        Task taskByName = taskService.getTaskByName(task.getName());
        assert (taskByName != null);
        assert (task.getId().equals(taskByName.getId()));
        //Find by subject id
        List<Task> tasksBySubject = taskService.getTasksBySubject(subject.getId());
        assert (tasksBySubject != null);
        assert (!tasksBySubject.isEmpty());
        assert (!tasksBySubject.contains(null));
        for (Task curr : tasksBySubject)
            assert (subject.getId().equals(curr.getSubject().getId()));
        //All
        List<Task> tasks = taskService.getAllTasks();
        assert (tasks != null);
        assert (!tasks.isEmpty());
        assert (!tasks.contains(null));

        //Update
        task.setName("New name!");
        boolean updated = taskService.updateTask(task);
        assert (updated);
        assert (taskService.getTaskById(task.getId()).getName().equals("New name!"));

        //Delete
        boolean deleted = taskService.deleteTask(task.getId());
        Task nullTask = taskService.getTaskById(task.getId());
        assert (deleted);
        assert (nullTask == null);

        //Delete foreign keys
        subjectService.deleteSubject(subject.getId());
        userService.deleteUser(user.getId());
    }

    @Test
    void testStudentWorkService() {
        //Foreign keys
        User user = new User("sOmErAnDoMlOgIn","p123","Vasiliy",1,2,true);
        userService.createUser(user);
        Subject subject = new Subject(user,"subj","some description");
        subjectService.createSubject(subject);
        Task task = new Task(subject,"name","some description",100,5,10);
        taskService.createTask(task);

        //Create
        StudentWork studentWork = new StudentWork(task,user,"path","review","review",10,100, LocalDateTime.now(), "local path");
        StudentWork createdStudentWork = studentWorkService.createStudentWork(studentWork);
        assert (createdStudentWork != null);
        assert (studentWork.getId().equals(createdStudentWork.getId()));

        //Find by id
        StudentWork studentWorkById = studentWorkService.getStudentWorkById(studentWork.getId());
        assert (studentWorkById != null);
        assert (studentWork.getId().equals(studentWorkById.getId()));
        //Find by task id and user id
        List<StudentWork> studentWorksByTaskAndUser = studentWorkService.getStudentWorksByTaskAndUser(task.getId(), user.getId());
        assert (studentWorksByTaskAndUser != null);
        assert (!studentWorksByTaskAndUser.isEmpty());
        assert (!studentWorksByTaskAndUser.contains(null));
        for (StudentWork curr : studentWorksByTaskAndUser) {
            assert (studentWork.getUser().getId().equals(curr.getUser().getId()));
            assert (studentWork.getTask().getId().equals(curr.getTask().getId()));
        }
        //Find by task id
        List<StudentWork> studentWorksByTask = studentWorkService.getStudentWorkByTask(task.getId());
        assert (studentWorksByTask != null);
        assert (!studentWorksByTask.isEmpty());
        assert (!studentWorksByTask.contains(null));
        for (StudentWork curr : studentWorksByTask)
            assert (studentWork.getTask().getId().equals(curr.getTask().getId()));
        //Find by user id
        List<StudentWork> studentWorksByUser = studentWorkService.getStudentWorksByUser(user.getId());
        assert (studentWorksByUser != null);
        assert (!studentWorksByUser.isEmpty());
        assert (!studentWorksByUser.contains(null));
        for (StudentWork curr : studentWorksByUser)
            assert (studentWork.getUser().getId().equals(curr.getUser().getId()));
        //All
        List<StudentWork> studentWorks = studentWorkService.getAllStudentWorks();
        assert (studentWorks != null);
        assert (!studentWorks.isEmpty());
        assert (!studentWorks.contains(null));

        //Update
        studentWork.setSystemReview("New review!");
        boolean updated = studentWorkService.updateStudentWork(studentWork);
        assert (updated);
        assert (studentWorkService.getStudentWorkById(studentWork.getId()).getSystemReview().equals("New review!"));

        //Delete
        boolean deleted = studentWorkService.deleteStudentWork(studentWork.getId());
        StudentWork nullWork = studentWorkService.getStudentWorkById(studentWork.getId());
        assert (deleted);
        assert (nullWork == null);

        //Delete foreign keys
        taskService.deleteTask(task.getId());
        subjectService.deleteSubject(subject.getId());
        userService.deleteUser(user.getId());
    }
}
