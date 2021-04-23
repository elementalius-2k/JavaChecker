package courseproject.javacheck.repo;

import courseproject.javacheck.domain.Subject;
import courseproject.javacheck.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepo extends CrudRepository<Subject, Integer> {
    Optional<Subject> findByName(String name);
    List<Subject> findAllByTeacher(User user);
}
