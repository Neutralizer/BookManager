package com.books;


import com.books.controllers.BookController;
import com.books.model.Book;
import com.books.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("dev")
@AutoConfigureMockMvc
@SpringBootTest(classes = BookManagerApplication.class)
public class BookControllerTest {

    @MockBean
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAll() throws Exception {

        ArrayList<Book> allBooks = new ArrayList<>();
        allBooks.add(new Book("Book Title", "Book Author", "Book Summary 22", 2.2));
        allBooks.add(new Book("Book Title Second", "Book Author Second", "Book Summary 23 Second", 2.3));

        when(bookService.getAllBooks()).thenReturn(allBooks);
        String uri = UriComponentsBuilder.newInstance().path("/library/books").build().toUriString();

        mockMvc.perform(get(uri)).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().string(containsString("Title")))
                .andExpect(content().string(containsString("Author")))
                .andExpect(content().string(containsString("Summary")))
                .andExpect(content().string(containsString("Second")))
                .andExpect(content().string(containsString("23")));
    }

}
