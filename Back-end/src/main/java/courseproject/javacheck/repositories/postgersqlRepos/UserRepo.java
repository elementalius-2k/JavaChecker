package courseproject.javacheck.repositories.postgersqlRepos;

import courseproject.javacheck.model.postgresqlModels.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
    Optional<User> findByLoginAndPassword(String login, String password);
    Optional<User> findByFullName(String fullName);
}
