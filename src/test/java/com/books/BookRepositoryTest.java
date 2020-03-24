package com.books;

import com.books.dao.BookRepository;
import com.books.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@DataJpaTest()
@ContextConfiguration(classes = BookManagerApplication.class)
public class BookRepositoryTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private BookRepository bookRepository;

	@Test
	public void whenFindAll_thenReturnBooks_AndCheckSize() {
		testEntityManager.persist(new Book("Book Title", "Book Author", "Book Summary 22",2.2));
		testEntityManager.persist(new Book("Second Book Title", "Second Book Author", "Second Book Summary 33",3.3));
		testEntityManager.flush();

		List<Book> found = (List<Book>) bookRepository.findAll();

		assertThat(found.size(), is(4));
	}


}
