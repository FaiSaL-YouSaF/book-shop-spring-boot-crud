package com.faisalyousaf777.BookStore.service;

import com.faisalyousaf777.BookStore.exceptions.BookAlreadyExistsException;
import com.faisalyousaf777.BookStore.exceptions.BookNotFoundException;
import com.faisalyousaf777.BookStore.exceptions.BookRecordsNotFoundException;
import com.faisalyousaf777.BookStore.entity.Book;
import com.faisalyousaf777.BookStore.repository.BookRepository;
import com.faisalyousaf777.BookStore.service.impl.BookServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplUnitTests {
	
	@Mock
	private BookRepository bookRepository;
	
	@InjectMocks
	private BookServiceImpl bookServiceImpl;
	
	@AfterEach
	void tearDown() {
		bookRepository.deleteAll();
	}
	
	Book mockedBook = new Book(1L,"Java", "Java for beginners", 5);
	long bookId = mockedBook.getBookId();
	
	@Test
	@DisplayName("GetBookByTitle- Succeeds When Book With Given Title Exists")
	void testGetBookByTitle_bookExists() {
		String title = mockedBook.getTitle();
		
		Mockito.when(bookRepository.findByTitle(title)).thenReturn(Optional.of(mockedBook));
		Book foundBook = bookServiceImpl.getBookByTitle(title);
		
		assertNotNull(foundBook);
		assertEquals(mockedBook, foundBook);
	}
	
	@Test
	@DisplayName("GetBookByTitle- Fails When Book With Given Title Does Not Exists")
	void testGetBookByTitle_bookDoesNotExists() {
		String title = Mockito.anyString();
		
		assertThatThrownBy(() -> bookServiceImpl.getBookByTitle(title))
				.isInstanceOf(BookNotFoundException.class)
				.hasMessageContaining("Invalid Title : Book with the title : "+title+" does not exists.");
	}
	
	@Test
	@DisplayName("AddBook - Succeeds When Valid Book is Provided")
	void testSaveBook_succeedsWhenValidBookIsProvided() {
		bookServiceImpl.saveBook(mockedBook);
		
		Mockito.verify(bookRepository).save(mockedBook);
	}
	
	@Test
	@DisplayName("AddBook - Fails When Book ID Exists Already")
	void testSaveBook_failsWhenBookIdExistsAlready() {
		Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockedBook));
		
		assertThatThrownBy(() -> bookServiceImpl.saveBook(mockedBook))
				.isInstanceOf(BookAlreadyExistsException.class)
				.hasMessageContaining("Invalid ID : Book with the ID "+bookId+" exists already.");
		Mockito.verify(bookRepository, Mockito.never()).save(mockedBook);
	}
	
	@Test
	@DisplayName("AddBook - Fails When Book Title Exists Already")
	void testSaveBook_failsWhenTitleExistsAlready() {
		String title = mockedBook.getTitle();
		
		Mockito.when(bookRepository.findByTitle(title)).thenReturn(Optional.of(mockedBook));
		
		assertThatThrownBy(() -> bookServiceImpl.saveBook(mockedBook))
				.isInstanceOf(BookAlreadyExistsException.class)
				.hasMessageContaining("Invalid Title : Book with the title : "+title+" exists already.");
		Mockito.verify(bookRepository, Mockito.never()).save(mockedBook);
	}
	
	@Test
	@DisplayName("GetBookById - Succeeds When Book Exists")
	void testGetBookById_bookExists() {
		Mockito.when(bookRepository.findById(mockedBook.getBookId())).thenReturn(Optional.of(mockedBook));
		
		assertEquals(mockedBook, bookServiceImpl.getBookById(mockedBook.getBookId()));
	}
	
	@Test
	@DisplayName("GetBookById - Fails When Book Does Not Exists")
	void testGetBookById_bookDoesNotExists() throws BookNotFoundException {
		assertThatThrownBy(() -> bookServiceImpl.getBookById(bookId))
				.isInstanceOf(BookNotFoundException.class)
				.hasMessageContaining("Invalid ID : Book with the ID "+bookId+" does not exist.");
	}
	
	@Test
	void testGetAllBooks_booksExist() {
		List<Book> mockedBookList = List.of(
				new Book(1L,"Java", "Java for beginners", 5),
				new Book(2L,"Julia", "Julia for beginners", 4),
				new Book(3L,"Rust", "Rust for beginners", 3)
		);
		
		Mockito.when(bookRepository.findAll()).thenReturn(mockedBookList);
		List<Book> foundBookList = bookServiceImpl.getAllBooks();
		
		assertNotNull(foundBookList);
		assertEquals(mockedBookList, foundBookList);
	}
	
	@Test
	void testGetAllBooks_booksDoesNotExist() {
//		Mockito.when(bookRepository.findAll()).thenReturn(Collections.emptyList());
		
		assertThatThrownBy(() -> bookServiceImpl.getAllBooks())
				.isInstanceOf(BookRecordsNotFoundException.class)
				.hasMessageContaining("No book records found in the database. Please add books to view the list.");
	}
	
	@Test
	@DisplayName("Update Book - Succeeds When Book With Given ID Exist")
	void testUpdateBook_bookExist() {
		Book updatedBook = new Book(bookId,"Python", "Automate the boring stuff with Python", 5);
		
		Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockedBook));
		bookServiceImpl.updateBook(bookId, updatedBook);
		
		Mockito.verify(bookRepository).save(updatedBook);
	}
	
	@Test
	@DisplayName("Update Book - Fails When Book With Given ID Does Not Exist")
	void testUpdateBook_bookDoesNotExist() {
//		Book updatedBook = new Book("Java", "Java for beginners", 5);
		assertThatThrownBy(() -> bookServiceImpl.updateBook(bookId, mockedBook))
				.isInstanceOf(BookNotFoundException.class)
				.hasMessageContaining("Invalid ID : Book with the ID " +bookId+" does not exist.");
		Mockito.verify(bookRepository, Mockito.never()).save(mockedBook);
	}
	
	@Test
	void testDeleteBookById_bookDeleted() {
		Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockedBook));
		
		bookServiceImpl.deleteBookById(bookId);
		Mockito.verify(bookRepository).deleteById(bookId);
	}
	
	@Test
	void testDeleteBookById_bookDoesNotExist() {
		assertThatThrownBy(() -> bookServiceImpl.deleteBookById(bookId))
				.isInstanceOf(BookNotFoundException.class)
				.hasMessageContaining("Invalid ID : Book with the ID " +bookId+" does not exist.");
		Mockito.verify(bookRepository, Mockito.never()).deleteById(bookId);
	}
}