package courseproject.javacheck.repo;

import courseproject.javacheck.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {
}
