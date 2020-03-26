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

@RestController
@RequestMapping("/library")
public class BookController {


    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "/books", produces = "application/json")
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String titleContaining){
        List<Book> booksList;
        if(!isEmpty(titleContaining)){
            booksList = bookService.getBookByTitleContaining(titleContaining);
        } else {
            booksList = bookService.getAllBooks();
        }
        return new ResponseEntity<>(booksList,HttpStatus.OK);
    }

    @GetMapping(path = "/books/{id}")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable int id){
        Optional<Book> bookById = bookService.getBookById(id);
        return new ResponseEntity<>(bookById,HttpStatus.OK);
    }

    @PostMapping(path = "/books", consumes = "application/json")
    public ResponseEntity addBook(@RequestBody Book book){
        bookService.saveBook(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

