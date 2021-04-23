package courseproject.javacheck.repo;

import courseproject.javacheck.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
    Optional<User> findByLoginAndPassword(String login, String password);
    Optional<User> findByFullName(String fullName);
}
