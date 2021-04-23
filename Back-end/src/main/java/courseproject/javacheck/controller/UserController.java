package courseproject.javacheck.controller;

import courseproject.javacheck.domain.User;
import courseproject.javacheck.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {
    private final UserServiceImpl userService;

    private final HashMap<String, String> successAnswer = new HashMap<String, String>() {{ put("result", "success"); }};
    private final HashMap<String, String> errorAnswer = new HashMap<String, String>() {{ put("result", "error"); }};

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/user")
    public HashMap<String, String> updateUser(@RequestBody User user) {
        boolean updated = userService.updateUser(user);
        return updated ?  successAnswer : errorAnswer;
    }

    @DeleteMapping("/user/{id}")
    public HashMap<String, String> deleteUser(@PathVariable Integer id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ?  successAnswer : errorAnswer;
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/user/login/{login}/password/{password}")
    public User getUserByLogin(@PathVariable String login, @PathVariable String password) {
        return userService.getUserByLoginAndPassword(login, password);
    }

    @GetMapping("/user/name/{name}")
    public User getUserByName(@PathVariable String name) {
        return userService.getUserByFullName(name);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
