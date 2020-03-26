package com.books.dao;

import com.books.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repo layer with spring data jpa
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

    List<Book> findByTitleContaining(String title);

    List<Book> findByAuthor(String author);

}
