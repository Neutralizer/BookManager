package com.books.controllers;

import com.books.model.User;
import com.books.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User controller for logging in.
 */
@RestController
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registering a new user and saving.
     * @param user the user to be saved.
     * @return HttpStatus.CREATED.
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user){

        userService.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
