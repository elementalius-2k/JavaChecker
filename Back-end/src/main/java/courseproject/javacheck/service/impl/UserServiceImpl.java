package courseproject.javacheck.service.impl;

import courseproject.javacheck.domain.User;
import courseproject.javacheck.repo.UserRepo;
import courseproject.javacheck.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User createUser(User user) {
        try {
            user.setCourse(null);
            user.setGroupNumber(null);
            user.setTeacher(false);
            userRepo.save(user);
            return user;
        } catch (Exception e) {
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
            return false;
        }
    }

    @Override
    public boolean deleteUser(Integer id) {
        try {
            Optional<User> curr = userRepo.findById(id);
            curr.ifPresent(userRepo::delete);
            return true;
        } catch (Exception e) {
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
