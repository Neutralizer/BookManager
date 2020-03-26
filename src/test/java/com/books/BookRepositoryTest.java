package com.books;

import com.books.dao.BookRepository;
import com.books.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ActiveProfiles("dev")
@DataJpaTest()
@ContextConfiguration(classes = BookManagerApplication.class)
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void saveAnd_whenFindAll_thenReturnBooks_AndCheckSize() {
        testEntityManager.persist(new Book("Book Title", "Book Author", "Book Summary 22", 2.2));
        testEntityManager.persist(new Book("Second Book Title", "Second Book Author", "Second Book Summary 33", 3.3));
        testEntityManager.flush();

        List<Book> found = (List<Book>) bookRepository.findAll();

        assertThat(found.size(), is(4));
    }


    @Test
    public void whenFindAllByTitleNameContains_thenReturnBooks_AndCheckNameContains() {
        List<Book> found = bookRepository.findByTitleContaining("Hobbit");
        assertThat(found.get(0).getTitle(), containsString("Hobbit"));
    }

    @Test
    public void whenFindAllByTitleNameContains_thenReturnBooks_AndCheckMoreThanOneMatch() {
        List<Book> found = bookRepository.findByTitleContaining("The");
        assertThat(found.get(0).getTitle(), containsString("The"));
    }

    @Test
    public void whenGetById_thenCheckNotNull() {
        Optional<Book> byId = bookRepository.findById(0);
        assertThat(byId, notNullValue());
    }

    @Test
    public void whenGetByAuthor_thenCheckIfAuthorMatches() {
        List<Book> byAuthor = bookRepository.findByAuthor("Tolkien");
        assertThat(byAuthor.get(0).getAuthor(), containsString("Tolkien"));
    }

    @Test
    public void whenAdd_thenCheckIfTitleIsAdded() {
        testEntityManager.persist(new Book("Saved Title", "Saved Author", "Saved Summary 22", 2.2));
        testEntityManager.flush();

        List<Book> found = bookRepository.findByTitleContaining("Saved");

        assertThat(found.get(0).getTitle(), containsString("Saved"));
    }

    @Test
    public void whenAdd_thenUpdate_AndCheckIfTitleIsUpdated() {
        testEntityManager.persist(new Book("Saved Title", "Saved Author", "Saved Summary 22", 2.2));
        testEntityManager.flush();

        List<Book> found = bookRepository.findByTitleContaining("Saved");

        Book book = found.get(0);
        book.setTitle("Updated Title");

        testEntityManager.persist(book);
        testEntityManager.flush();

        Optional<Book> updated = bookRepository.findById(book.getId());

        assertThat(updated.orElseThrow(NoSuchElementException::new).getTitle(),
                containsString("Updated"));
    }

    @Test
    public void whenAdd_thenDelete_AndCheckIfDeleted() {
        testEntityManager.persist(new Book("Delete Title", "Delete Author", "Delete Summary 22", 2.2));
        testEntityManager.flush();

        List<Book> found = bookRepository.findByTitleContaining("Delete");

        bookRepository.deleteById(found.get(0).getId());

        List<Book> foundEmpty = bookRepository.findByTitleContaining("Delete");

        assertThat(foundEmpty.size(), is(0));
    }


}
