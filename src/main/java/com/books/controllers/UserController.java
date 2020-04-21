package com.books.controllers;

import com.books.model.Book;
import com.books.model.User;
import com.books.service.BookService;
import com.books.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User controller for logging in.
 */
@RestController
public class UserController {


    private UserService userService;

    private BookService bookService;

    @Autowired
    public UserController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
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

    @GetMapping("/myLikes")
    public ResponseEntity<List<Book>> getUserLikedBooks(@RequestParam(required = false, defaultValue = "0") int pageNum,
                                                        @RequestParam(required = false, defaultValue = "20") int entriesPerPage){

//        List<Integer> bookIdsLikedByUser = userService.getBookIdsLikedByUser();

        List<Integer> bookIdsLikedByUser = new ArrayList<>();//TODO
        bookIdsLikedByUser.add(1);
        bookIdsLikedByUser.add(2);
        List<Book> booksByIds = bookService.getBooksByIds(bookIdsLikedByUser, pageNum, entriesPerPage);
        return new ResponseEntity<>(booksByIds, HttpStatus.OK);
    }

}
