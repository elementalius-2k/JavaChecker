package courseproject.javacheck.repo;

import courseproject.javacheck.domain.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepo extends CrudRepository<Subject, Integer> {
}
