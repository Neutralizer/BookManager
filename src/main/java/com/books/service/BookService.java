package com.books.service;

import com.books.dao.BookRepository;
import com.books.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


/**
 * Service layer of Book app.
 */
@Service
public class BookService {

    BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    /**
     * Persists a book object.
     *
     * @param book book to be saved.
     */
    public void saveBook(Book book) {
        repository.save(book);
    }

    /**
     * Retrieves all books in the current page with requested page size.
     *
     * @param pageNum Page number of request - zero based.
     * @param entriesPerPage Number of entries per page.
     * @return All books in the current page with requested page size.
     */
    public List<Book> getAllBooksByPage(int pageNum, int entriesPerPage) {
        Pageable pageable = PageRequest.of(pageNum, entriesPerPage);
        return repository.findAll(pageable).toList();
    }

    /**
     * Retrieves book by id.
     *
     * @param id the id of the book to be retrieved.
     * @return the book with the given id.
     */
    public Optional<Book> getBookById(int id) {//negative error check and null check
        return repository.findById(id);
    }

    /**
     * Retrieves all books, which contain a specific substring in it's title.
     *
     * @param titleContaining the title substring, on which books will be retrieved.
     * @return books with the matching title substring.
     */
    public List<Book> getBookByTitleContaining(String titleContaining,int pageNum, int entriesPerPage) {
        Pageable pageable = PageRequest.of(pageNum, entriesPerPage);
        return repository.findByTitleContaining(titleContaining,pageable);
    }

    /**
     * Deletes a book from the repository.
     *
     * @param id the id of the book to be deleted.
     */
    public void deleteBook(int id) {
        repository.deleteById(id);
    }

    /**
     * Adds rating to a book
     *
     * @param id the id of the book, whose rating will be increased
     */
    public void increaseRating(int id) {
        modifyRating(id, +1);
    }

    /**
     * Removes rating from a book
     *
     * @param id the id of the book, whose rating will be decreased
     */
    public void decreaseRating(int id) {
        modifyRating(id, -1);
    }

    private void modifyRating(int id, int modifyRating) {
        Optional<Book> byId = repository.findById(id);
        int rating = byId.orElseThrow(NoSuchElementException::new).getRating();
        byId.get().setRating(rating + modifyRating);
        repository.save(byId.get());
    }


}
