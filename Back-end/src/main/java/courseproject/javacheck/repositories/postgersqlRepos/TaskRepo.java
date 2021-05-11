package courseproject.javacheck.repositories.postgersqlRepos;

import courseproject.javacheck.model.postgresqlModels.Subject;
import courseproject.javacheck.model.postgresqlModels.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends CrudRepository<Task, Integer> {
    Optional<Task> findByName(String name);
    List<Task> findAllBySubject(Subject subject);
}
