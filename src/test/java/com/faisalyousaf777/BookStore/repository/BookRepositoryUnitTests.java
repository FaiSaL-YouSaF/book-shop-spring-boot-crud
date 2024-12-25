package com.faisalyousaf777.BookStore.repository;

import com.faisalyousaf777.BookStore.entity.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookRepositoryUnitTests {
	
	@Mock
	private BookRepository bookRepository;
	
	@AfterEach
	void tearDown() {
		bookRepository.deleteAll();
	}
	
	
	@Test
	void testFindByTitle_bookExists() {
		Book mockedBook = new Book(1L,"Java", "Java for beginners", 5);
		String title = mockedBook.getTitle();
		
		Mockito.when(bookRepository.findByTitle(title)).thenReturn(Optional.of(mockedBook));
		Optional<Book> foundBook = bookRepository.findByTitle(title);
		
		assertTrue(foundBook.isPresent());
		assertThat(bookRepository.findByTitle(title)).isEqualTo(Optional.of(mockedBook));
	}
	
	@Test
	void testFindByTitle_bookDoesNotExists() {
		String title = "Title of Book";
		
		Optional<Book> nonExistingBookOptional = bookRepository.findByTitle(title);
		
		assertTrue(nonExistingBookOptional.isEmpty());
	}
}