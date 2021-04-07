package courseproject.javacheck.repo;

import courseproject.javacheck.domain.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepo extends CrudRepository<Task, Integer> {
}
