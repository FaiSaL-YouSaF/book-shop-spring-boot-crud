package com.faisalyousaf777.BookShop.Repository;

import com.faisalyousaf777.BookShop.Entity.Book;
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
	void testFindByBookTitle_bookExists() {
		Book mockedBook = new Book(1L,"Java", "Java for beginners", 5);
		String bookTitle = mockedBook.getBookTitle();
		
		Mockito.when(bookRepository.findByBookTitle(bookTitle)).thenReturn(Optional.of(mockedBook));
		Optional<Book> foundBook = bookRepository.findByBookTitle(bookTitle);
		
		assertTrue(foundBook.isPresent());
		assertThat(bookRepository.findByBookTitle(bookTitle)).isEqualTo(Optional.of(mockedBook));
	}
	
	@Test
	void testFindByBookTitle_bookDoesNotExists() {
		String bookTitle = "Title of Book";
		
		Optional<Book> nonExistingBookOptional = bookRepository.findByBookTitle(bookTitle);
		
		assertTrue(nonExistingBookOptional.isEmpty());
	}
}