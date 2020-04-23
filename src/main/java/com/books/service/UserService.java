package com.books.service;

import com.books.dao.UserRepository;
import com.books.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for user.
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Saves user.
     * @param user the user to be saved.
     * @return the saved user.
     */
    public User save(User user) {
        User constructedUser = new User(user.getUsername(),
                passwordEncoder.encode(user.getPassword()), user.getRoles());
        userRepository.save(constructedUser);
        return constructedUser;
    }

    /**
     * Retrieve user by username.
     * @param username the username of user to be retrieved.
     * @return user with given username
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    /**
     * Retrieves all book ids, that are liked by a single user.
     * @param username the username of the user, whose liked book ids are to be retrieved.
     * @return all ids of the liked books by the user.
     */
    public List<Integer> getBookIdsLikedByUser(String username) {
        User user = userRepository.findByUsername(username);
        return user.getLikedBooksIds();
    }

    /**
     * Persists the id of the liked book in the user
     * @param username the user's username
     * @param bookId the id of the book
     */
    public void addLikedBookId(String username, int bookId) {
        User user = userRepository.findByUsername(username);
        user.getLikedBooksIds().add(bookId);
        userRepository.save(user);
    }

    /**
     * Removes the id of the previously liked book in the user
     * @param bookId the user's username
     * @param bookId the id of the book
     */
    public void removeLikedBookId(String username, int bookId) {
        User user = userRepository.findByUsername(username);
        user.getLikedBooksIds().remove(bookId);
        userRepository.save(user);
    }
}
