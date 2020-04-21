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

    public User save(User user) {
        User constructedUser = new User(user.getUsername(),
                passwordEncoder.encode(user.getPassword()), user.getRoles());
        userRepository.save(constructedUser);
        return constructedUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public List<Integer> getBookIdsLikedByUser(String username) {
        User user = userRepository.findByUsername(username);
        return user.getLikedBooksIds();
    }

    public void addLikedBookId(String username, int bookId) {
        User user = userRepository.findByUsername(username);
        user.getLikedBooksIds().add(bookId);
        userRepository.save(user);
    }

    public void removeLikedBookId(String username, int bookId) {
        User user = userRepository.findByUsername(username);
        user.getLikedBooksIds().remove(bookId);
        userRepository.save(user);
    }
}
