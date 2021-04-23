package courseproject.javacheck.repo;

import courseproject.javacheck.domain.StudentWork;
import courseproject.javacheck.domain.Task;
import courseproject.javacheck.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentWorkRepo extends CrudRepository<StudentWork, Integer> {
    List<StudentWork> findAllByTaskAndUser(Task task, User user);
    List<StudentWork> findAllByUser(User user);
    List<StudentWork> findAllByTask(Task task);
}
