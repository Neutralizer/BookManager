package com.books;

import com.books.dao.BookRepository;
import com.books.model.Book;
import com.books.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = BookManagerApplication.class)
public class BookServiceTest {

    @Autowired
    BookService bookService;

    @MockBean
    BookRepository bookRepository;


    @Test
    public void getAllBooksNotNull() {
        List<Book> allBooks = new ArrayList<>();
        allBooks.add(new Book("Title", "Author", "Summary 22", 2));
        allBooks.add(new Book("Title Second", "Author Second", "Summary 22 Second", 3 ));
        Page<Book> pBooks = new PageImpl<Book>(allBooks);

        Pageable pageable = PageRequest.of(0, 20);
        when(bookRepository.findAll(pageable)).thenReturn(pBooks);

        List<Book> found = bookService.getAllBooks(0,20);
        assertNotNull(found);
    }

    @Test
    public void saveBookCheckPresent(){
        Book book = new Book("Title", "Author", "Summary 22", 2 );
        bookService.saveBook(book);

        verify(bookRepository).save(book);

    }

    @Test
    public void getBookById(){
        Book book = new Book("Title", "Author", "Summary 22", 2 );

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        Optional<Book> found = bookService.getBookById(book.getId());

        assertThat(found.orElseThrow(NoSuchElementException::new).getTitle(), containsString("Title"));
    }

    @Test
    public void getBookByTitleContaining(){
        Book book = new Book("Title", "Author", "Summary 22", 2 );
        List<Book> books = new ArrayList<>();
        books.add(book);

        Pageable pageable = PageRequest.of(0, 20);
        when(bookRepository.findByTitleContaining(book.getTitle(), pageable)).thenReturn(books);

        List<Book> found = bookService.getBookByTitleContaining("Title",0,20);

        assertThat(found.get(0).getTitle(), containsString("Title"));
    }

    @Test
    public void deleteBook(){
        Book book = new Book("Title", "Author", "Summary 22", 2 );
        bookService.deleteBook(book.getId());

        verify(bookRepository).deleteById(book.getId());
    }

    @Test
    public void addRating(){
        Book book = new Book("Title", "Author", "Summary 22", 2 );

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        bookService.addRating(book.getId());
        verify(bookRepository).save(book);

        assertThat(book.getRating(), is(3));


    }

    @Test
    public void removeRating(){
        Book book = new Book("Title", "Author", "Summary 22", 2 );

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        bookService.removeRating(book.getId());
        verify(bookRepository).save(book);

        assertThat(book.getRating(), is(1));


    }

}
