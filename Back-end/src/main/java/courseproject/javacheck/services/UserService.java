package courseproject.javacheck.services;

import courseproject.javacheck.model.postgresqlModels.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(Integer id);
    User getUserById(Integer id);
    User getUserByLoginAndPassword(String login, String password);
    User getUserByFullName(String fullName);
    List<User> getAllUsers();
}
