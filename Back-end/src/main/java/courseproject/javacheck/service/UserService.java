package courseproject.javacheck.service;

import courseproject.javacheck.domain.User;

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
