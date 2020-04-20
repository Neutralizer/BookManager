package com.books;

import com.books.dao.UserRepository;
import com.books.model.Role;
import com.books.model.User;
import com.books.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = BookManagerApplication.class)
public class UserServiceTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    public void saveUserCheckPresent(){
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
        User user = new User("test", "test", roles);
        User saved = userService.save(user);

        verify(userRepository).save(saved);
    }

    @Test
    public void loadUserByUserDetailsCheck(){
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
        User user = new User("test", passwordEncoder.encode("test"), roles);

        when(userRepository.findByUsername("test")).thenReturn(user);

        UserDetails test = userService.loadUserByUsername("test");

        assertThat(user.getUsername(), containsString("test"));
    }

    @Test
    public void loadUserByUserDetails_ThrowsExceptionIfNotFound(){
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("notFound"));
    }


}
