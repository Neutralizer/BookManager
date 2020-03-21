package com.books.service;

import com.books.dao.BookRepository;
import com.books.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public void saveBook(Book book){
        repository.save(book);
    }

    public List<Book> getAllBooks(){
        return (List<Book>) repository.findAll();
    }

    public Optional<Book> getBookById(int id){//negative error check and null check
        return repository.findById(id);
    }

    public List<Book> getBookByTitle(String name){
        return repository.findByTitle(name);
    }

    public void deleteBook(int id){
        repository.deleteById(id);
    }


}
