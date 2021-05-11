package courseproject.javacheck.repositories.postgersqlRepos;

import courseproject.javacheck.model.postgresqlModels.Subject;
import courseproject.javacheck.model.postgresqlModels.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepo extends CrudRepository<Subject, Integer> {
    Optional<Subject> findByName(String name);
    List<Subject> findAllByTeacher(User user);
}
