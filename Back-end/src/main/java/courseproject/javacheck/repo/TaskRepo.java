package courseproject.javacheck.repo;

import courseproject.javacheck.domain.Subject;
import courseproject.javacheck.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends CrudRepository<Task, Integer> {
    Optional<Task> findByName(String name);
    List<Task> findAllBySubject(Subject subject);
}
