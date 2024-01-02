package com.faisalyousaf777.BookShop.Service;

import com.faisalyousaf777.BookShop.BookExceptions.BookAlreadyExistsException;
import com.faisalyousaf777.BookShop.BookExceptions.BookNotFoundException;
import com.faisalyousaf777.BookShop.BookExceptions.BookRecordsNotFoundException;
import com.faisalyousaf777.BookShop.Entity.Book;
import com.faisalyousaf777.BookShop.Repository.BookRepository;
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
class BookServiceUnitTests {
	
	@Mock
	private BookRepository bookRepository;
	
	@InjectMocks
	private BookService bookService;
	
	@AfterEach
	void tearDown() {
		bookRepository.deleteAll();
	}
	
	Book mockedBook = new Book(1L,"Java", "Java for beginners", 5);
	long bookId = mockedBook.getBookId();
	
	@Test
	@DisplayName("GetBookByBookTitle- Succeeds When Book With Given Title Exists")
	void testGetBookByBookTitle_bookExists() {
		String bookTitle = mockedBook.getBookTitle();
		
		Mockito.when(bookRepository.findByBookTitle(bookTitle)).thenReturn(Optional.of(mockedBook));
		Book foundBook = bookService.getBookByBookTitle(bookTitle);
		
		assertNotNull(foundBook);
		assertEquals(mockedBook, foundBook);
	}
	
	@Test
	@DisplayName("GetBookByBookTitle- Fails When Book With Given Title Does Not Exists")
	void testGetBookByBookTitle_bookDoesNotExists() {
		String bookTitle = Mockito.anyString();
		
		assertThatThrownBy(() -> bookService.getBookByBookTitle(bookTitle))
				.isInstanceOf(BookNotFoundException.class)
				.hasMessageContaining("Book with the Title: "+bookTitle+" does not exists.");
	}
	
	@Test
	@DisplayName("AddBook - Succeeds When Valid Book is Provided")
	void testAddBook_succeedsWhenValidBookIsProvided() {
		bookService.addBook(mockedBook);
		
		Mockito.verify(bookRepository).save(mockedBook);
	}
	
	@Test
	@DisplayName("AddBook - Fails When Book ID Exists Already")
	void testAddBook_failsWhenBookIdExistsAlready() {
		Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockedBook));
		
		assertThatThrownBy(() -> bookService.addBook(mockedBook))
				.isInstanceOf(BookAlreadyExistsException.class)
				.hasMessageContaining("Book with the ID "+bookId+" exists already.");
		Mockito.verify(bookRepository, Mockito.never()).save(mockedBook);
	}
	
	@Test
	@DisplayName("AddBook - Fails When Book Title Exists Already")
	void testAddBook_failsWhenBookTitleExistsAlready() {
		String bookTitle = mockedBook.getBookTitle();
		
		Mockito.when(bookRepository.findByBookTitle(bookTitle)).thenReturn(Optional.of(mockedBook));
		
		assertThatThrownBy(() -> bookService.addBook(mockedBook))
				.isInstanceOf(BookAlreadyExistsException.class)
				.hasMessageContaining("Book with the Title: "+bookTitle+" exists already.");
		Mockito.verify(bookRepository, Mockito.never()).save(mockedBook);
	}
	
	@Test
	@DisplayName("GetBookById - Succeeds When Book Exists")
	void testGetBookById_bookExists() {
		Mockito.when(bookRepository.findById(mockedBook.getBookId())).thenReturn(Optional.of(mockedBook));
		
		assertEquals(mockedBook, bookService.getBookById(mockedBook.getBookId()));
	}
	
	@Test
	@DisplayName("GetBookById - Fails When Book Does Not Exists")
	void testGetBookById_bookDoesNotExists() throws BookNotFoundException {
		assertThatThrownBy(() -> bookService.getBookById(bookId))
				.isInstanceOf(BookNotFoundException.class)
				.hasMessageContaining("Book with the ID "+bookId+" does not exist.");
	}
	
	@Test
	void testGetAllBooks_booksExist() {
		List<Book> mockedBookList = List.of(
				new Book(1L,"Java", "Java for beginners", 5),
				new Book(2L,"Julia", "Julia for beginners", 4),
				new Book(3L,"Rust", "Rust for beginners", 3)
		);
		
		Mockito.when(bookRepository.findAll()).thenReturn(mockedBookList);
		List<Book> foundBookList = bookService.getAllBooks();
		
		assertNotNull(foundBookList);
		assertEquals(mockedBookList, foundBookList);
	}
	
	@Test
	void testGetAllBooks_booksDoesNotExist() {
//		Mockito.when(bookRepository.findAll()).thenReturn(Collections.emptyList());
		
		assertThatThrownBy(() -> bookService.getAllBooks())
				.isInstanceOf(BookRecordsNotFoundException.class)
				.hasMessageContaining("Book Records are not Found in Database!");
	}
	
	@Test
	@DisplayName("Update Book - Succeeds When Book With Given ID Exist")
	void testUpdateBook_bookExist() {
		Book updatedBook = new Book(bookId,"Python", "Automate the boring stuff with Python", 5);
		
		Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockedBook));
		bookService.updateBook(bookId, updatedBook);
		
		Mockito.verify(bookRepository).save(updatedBook);
	}
	
	@Test
	@DisplayName("Update Book - Fails When Book With Given ID Does Not Exist")
	void testUpdateBook_bookDoesNotExist() {
//		Book updatedBook = new Book("Java", "Java for beginners", 5);
		assertThatThrownBy(() -> bookService.updateBook(bookId, mockedBook))
				.isInstanceOf(BookNotFoundException.class)
				.hasMessageContaining("Book with the ID " +bookId+" does not exist.");
		Mockito.verify(bookRepository, Mockito.never()).save(mockedBook);
	}
	
	@Test
	void testDeleteBookById_bookDeleted() {
		Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockedBook));
		
		bookService.deleteBookById(bookId);
		Mockito.verify(bookRepository).deleteById(bookId);
	}
	
	@Test
	void testDeleteBookById_bookDoesNotExist() {
		assertThatThrownBy(() -> bookService.deleteBookById(bookId))
				.isInstanceOf(BookNotFoundException.class)
				.hasMessageContaining("Book with the ID " +bookId+" does not exist.");
		Mockito.verify(bookRepository, Mockito.never()).deleteById(bookId);
	}
}