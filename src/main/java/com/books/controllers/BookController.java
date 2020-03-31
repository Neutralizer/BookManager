package com.books.controllers;

import com.books.model.Book;
import com.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * Controller layer class for book app.
 */
@RestController
@RequestMapping("/library")
public class BookController {


    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retrieves all books from the repo.
     * @param titleContaining optional param for filtering books by part of the title name.
     * @return all books / all books with the given string contained in the title name (if param is provided).
     */
    @GetMapping(path = "/books", produces = "application/json")
    public ResponseEntity<List<Book>> getAllBooks(
            @RequestParam(required = false) String titleContaining,
            @RequestParam(required = false, defaultValue = "0") int pageNum,
            @RequestParam(required = false, defaultValue = "20") int entriesPerPage){
        List<Book> booksList;
        if(!isEmpty(titleContaining)){
            booksList = bookService.getBookByTitleContaining(titleContaining,pageNum,entriesPerPage);
        } else {
            booksList = bookService.getAllBooks(pageNum,entriesPerPage);
        }
        return new ResponseEntity<>(booksList,HttpStatus.OK);
    }

    /**
     * Retrieves a book by its id.
     * @param id the id of the book to be retrieved.
     * @return the book with the specified id.
     */
    @GetMapping(path = "/books/{id}")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable int id){
        Optional<Book> bookById = bookService.getBookById(id);
        return new ResponseEntity<>(bookById,HttpStatus.OK);
    }

    /**
     * Saves new book.
     * @param book the book to be persisted.
     * @return Status OK if successful.
     */
    @PostMapping(path = "/books", consumes = "application/json")
    public ResponseEntity addBook(@RequestBody Book book){
        bookService.saveBook(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes a book from the repo.
     * @param id the id of the book to be deleted.
     * @return Status OK if successful.
     */
    @DeleteMapping("/books/{id}")
    public ResponseEntity deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Increment the rating of a book
     * @param id the id of the book
     * @return Status OK if successful.
     */
    @PostMapping("/books/{id}/add_rating")
    public ResponseEntity incrementRating(@PathVariable int id){
        bookService.addRating(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Decrement the rating of a book
     * @param id the id of the book
     * @return Status OK if successful.
     */
    @PostMapping("/books/{id}/remove_rating")
    public ResponseEntity decrementRating(@PathVariable int id){
        bookService.removeRating(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

