package courseproject.javacheck.repositories.postgersqlRepos;

import courseproject.javacheck.model.postgresqlModels.StudentWork;
import courseproject.javacheck.model.postgresqlModels.Task;
import courseproject.javacheck.model.postgresqlModels.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentWorkRepo extends CrudRepository<StudentWork, Integer> {
    List<StudentWork> findAllByTaskAndUser(Task task, User user);
    List<StudentWork> findAllByUser(User user);
    List<StudentWork> findAllByTask(Task task);
}
