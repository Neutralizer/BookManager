package com.books;

import com.books.dao.BookRepository;
import com.books.model.Book;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class BookManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookManagerApplication.class, args);
	}



	@Profile("dev")
	@Bean
	CommandLineRunner initData(BookRepository bookRepository){
		return args -> {
			bookRepository.save(new Book("The Hobbit", "Tolkien", "Short sneaky guy gets taken on an adventure", 4));
			bookRepository.save(new Book("The Expanse", "James Corey", "Alien organism appears in the solar system", 0));
		};
	}
}
