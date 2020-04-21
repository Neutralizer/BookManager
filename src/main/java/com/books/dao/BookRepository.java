package com.books.dao;

import com.books.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Repo layer with spring data jpa for storing the books.
 */
@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {

    List<Book> findByTitleContaining(String title, Pageable pageable);

    List<Book> findByAuthor(String author);

    List<Book> findAllByIdIn(Collection<Integer> ids, Pageable pageable);

}
