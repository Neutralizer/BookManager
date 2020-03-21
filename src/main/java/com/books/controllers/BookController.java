package com.books.controllers;

import com.books.model.Book;
import com.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/library")
public class BookController {


    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "/hello")
    public ResponseEntity<String> greeting(){
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    @GetMapping(path = "/books")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> allBooks = bookService.getAllBooks();
        return new ResponseEntity<>(allBooks,HttpStatus.OK);
    }

    @GetMapping(path = "/books/{id}")
    public ResponseEntity<Optional<Book>> getBookById(int id){
        Optional<Book> bookById = bookService.getBookById(id);
        return new ResponseEntity<>(bookById,HttpStatus.OK);
    }

    @GetMapping(path = "/books/{title}")
    public ResponseEntity<List<Book>> getBookById(String title){
        List<Book> bookByTitle = bookService.getBookByTitle(title);
        return new ResponseEntity<>(bookByTitle, HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity addBook(@RequestParam Book book){
        bookService.saveBook(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity deleteBook(int id){
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

