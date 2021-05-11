package courseproject.javacheck;

import com.fasterxml.jackson.databind.ObjectMapper;
import courseproject.javacheck.controllers.UserController;
import courseproject.javacheck.model.postgresqlModels.User;
import courseproject.javacheck.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserServiceImpl userService;

    HashMap<String, String> success = new HashMap<String, String>() {{ put("result", "success"); }};

    @Test
    void givenUser_whenAdd_thenUserReturned() throws Exception {
        User user = createUser();
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(post("/user")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    void givenUser_whenUpdate_thenSuccessReturned() throws Exception {
        User user = createUser();
        Mockito.when(userService.updateUser(Mockito.any(User.class))).thenReturn(true);
        mockMvc.perform(put("/user")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(success)));
    }

    @Test
    void givenId_whenGetExistingUser_thenUserReturned() throws Exception {
        User user = createUser();
        Mockito.when(userService.getUserById(Mockito.anyInt())).thenReturn(user);
        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    void givenLoginAndPassword_whenGetExistingUser_thenUserReturned() throws Exception {
        User user = createUser();
        Mockito.when(userService.getUserByLoginAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(user);
        mockMvc.perform(get("/user/login/sOmErAnDoMlOgIn/password/p123"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    void givenFullName_whenGetExistingUser_thenUserReturned() throws Exception {
        User user = createUser();
        Mockito.when(userService.getUserByFullName(Mockito.anyString())).thenReturn(user);
        mockMvc.perform(get("/user/name/Vasiliy"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    void givenUsers_whenGetUsers_thenUserReturned() throws Exception {
        List<User> users = createTwoUsers();
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(users)));
    }

    @Test
    void givenUserId_whenDeleteUser_thenSuccessReturned() throws Exception {
        Mockito.when(userService.deleteUser(Mockito.anyInt())).thenReturn(true);
        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(success)));
    }

    private User createUser() {
        User user = new User("sOmErAnDoMlOgIn","p123","Vasiliy",1,2,false);
        user.setId(1);
        return user;
    }

    private List<User> createTwoUsers() {
        User user1 = new User("sOmErAnDoMlOgIn","p123","Vasiliy",1,2,false);
        user1.setId(1);
        User user2 = new User("rAnDoMlOgIn","p123","Vasiliy",1,2,false);
        user2.setId(2);
        return Arrays.asList(user1, user2);
    }
}
