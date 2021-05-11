package courseproject.javacheck.services.impl;

import courseproject.javacheck.model.postgresqlModels.User;
import courseproject.javacheck.repositories.postgersqlRepos.UserRepo;
import courseproject.javacheck.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User createUser(User user) {
        try {
            userRepo.save(user);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateUser(User user) {
        try {
            userRepo.findById(user.getId()).ifPresent(userToChange -> {
                userToChange.setLogin(user.getLogin());
                userToChange.setPassword(user.getPassword());
                userToChange.setFullName(user.getFullName());
                userToChange.setCourse(user.getCourse());
                userToChange.setGroupNumber(user.getGroupNumber());
                userToChange.setTeacher(user.getTeacher());
                userRepo.save(userToChange);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(Integer id) {
        try {
            userRepo.findById(id).ifPresent(userRepo::delete);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUserById(Integer id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        return userRepo.findByLoginAndPassword(login, password).orElse(null);
    }

    @Override
    public User getUserByFullName(String fullName) {
        return userRepo.findByFullName(fullName).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepo.findAll();
    }
}
