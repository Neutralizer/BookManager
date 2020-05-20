package com.books.controllers;

import com.books.model.Book;
import com.books.model.User;
import com.books.service.BookService;
import com.books.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    /**
     * Retrieves all books Favourite by the user.
     * @param principal current logged in user.
     * @param pageNum pagination number.
     * @param entriesPerPage entries per page for pagination.
     * @return all books Favourite by the user.
     */
    @GetMapping("/myFavourites")
    public ResponseEntity<List<Book>> getUserFavouriteBooks(Principal principal,
                                                            @RequestParam(required = false, defaultValue = "${books.default.pagenum}") int pageNum,
                                                            @RequestParam(required = false, defaultValue = "${books.default.entriesperpage}") int entriesPerPage){

        List<Integer> bookIdsFavouriteByUser = userService.getBookIdsFavouriteByUser(principal.getName());
        List<Book> booksFavouriteByIds = bookService.getBooksByIds(bookIdsFavouriteByUser, pageNum, entriesPerPage);

        return new ResponseEntity<>(booksFavouriteByIds, HttpStatus.OK);
    }

}
