package com.books;


import com.books.controllers.UserController;
import com.books.model.Book;
import com.books.model.Role;
import com.books.model.User;
import com.books.service.BookService;
import com.books.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = BookManagerApplication.class)
public class UserControllerTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserService userService;

    @MockBean
    private BookService bookService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void saveUser() throws Exception {

        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
        User user = new User("test", passwordEncoder.encode("test"), roles);

        String json = new Gson().toJson(user);
        String uri = UriComponentsBuilder.newInstance().path("/register")
                .build().toUriString();
        mockMvc.perform(post(uri).content(json).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isCreated());

    }

    @WithMockUser("USER")
    @Test
    public void getFavouriteBooks() throws Exception {

        ArrayList<Book> allBooks = new ArrayList<>();
        allBooks.add(new Book("Book Title", "Book Author", "Book Summary 22", 2 ));
        allBooks.add(new Book("Book Title Second", "Book Author Second", "Book Summary 23 Second", 2 ));

        ArrayList<Integer> bookIds = new ArrayList<>();
        bookIds.add(1);
        bookIds.add(2);

        when(userService.getBookIdsFavouriteByUser("USER")).thenReturn(bookIds);
        when(bookService.getBooksByIds(bookIds, 0, 20)).thenReturn(allBooks);

        String uri = UriComponentsBuilder.newInstance().path("/myFavourites").build().toUriString();

        mockMvc.perform(get(uri)).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Title")))
                .andExpect(content().string(containsString("Author")))
                .andExpect(content().string(containsString("Summary")))
                .andExpect(content().string(containsString("Second")))
                .andExpect(content().string(containsString("23")));
    }

}
