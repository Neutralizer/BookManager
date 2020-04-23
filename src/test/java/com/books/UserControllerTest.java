package com.books;


import com.books.controllers.UserController;
import com.books.model.Role;
import com.books.model.User;
import com.books.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = BookManagerApplication.class)
public class UserControllerTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void saveUser() throws Exception {

        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
        User user = new User("test", passwordEncoder.encode("test"), roles);

        String json = new Gson().toJson(user);
        String uri = UriComponentsBuilder.newInstance().path("/register")
                .build().toUriString();
        mockMvc.perform(post(uri).content(json).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isCreated());

    }

}
