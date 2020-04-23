package com.books;

import com.books.config.BCryptEncoderConfig;
import com.books.dao.UserRepository;
import com.books.model.Role;
import com.books.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@DataJpaTest()
@ContextConfiguration(classes = {BookManagerApplication.class, BCryptEncoderConfig.class})
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveAnd_whenFindByUsername_thenReturnUser_AndCheckUsername() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
        User user = new User("test",
                passwordEncoder.encode("test"), roles);
        testEntityManager.persist(user);
        testEntityManager.flush();

        User foundUser = userRepository.findByUsername("test");

        assertThat(foundUser.getUsername(), containsString("test"));
    }

    @Test
    public void whenAdd_thenCheckIfPasswordMatches() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
        User user = new User("testUser",
                passwordEncoder.encode("testPassword"), roles);
        testEntityManager.persist(user);
        testEntityManager.flush();

        User foundUser = userRepository.findByUsername("testUser");

        assertThat(foundUser.getPassword(), containsString(user.getPassword()));
    }

    @Test
    public void saveAnd_whenFindByUsername_thenReturnUser_AndCheckLikedBooksIds() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
        User user = new User("test",
                passwordEncoder.encode("test"), roles);

        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        user.setLikedBooksIds(ids);

        testEntityManager.persist(user);
        testEntityManager.flush();

        User foundUser = userRepository.findByUsername("test");

        assertThat(foundUser.getLikedBooksIds(), hasSize(2));
    }

}
