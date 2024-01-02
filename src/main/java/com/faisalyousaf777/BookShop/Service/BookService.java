package com.faisalyousaf777.BookShop.Service;

import com.faisalyousaf777.BookShop.BookExceptions.BookAlreadyExistsException;
import com.faisalyousaf777.BookShop.BookExceptions.BookNotFoundException;
import com.faisalyousaf777.BookShop.BookExceptions.BookRecordsNotFoundException;
import com.faisalyousaf777.BookShop.Entity.Book;
import com.faisalyousaf777.BookShop.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements BookServiceImplement {
	
	private final BookRepository bookRepository;
	
	@Autowired
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	
	@Override
	public Book getBookByBookTitle(String bookTitle) {
		if (bookRepository.findByBookTitle(bookTitle).isEmpty()) {
			throw new BookNotFoundException("Book with the Title: " + bookTitle + " does not exists.");
		}
		return bookRepository.findByBookTitle(bookTitle).get();
	}
	
	@Override
	public void addBook(Book book) {
		if (bookRepository.findById(book.getBookId()).isPresent()) {
			throw new BookAlreadyExistsException("Book with the ID " + book.getBookId() + " exists already.");
		}
		if (bookRepository.findByBookTitle(book.getBookTitle()).isPresent()) {
			throw new BookAlreadyExistsException("Book with the Title: " + book.getBookTitle() + " exists already.");
		}
		bookRepository.save(book);
	}
	
	@Override
	public Book getBookById(long bookId) {
		if (bookRepository.findById(bookId).isEmpty()) {
			throw new BookNotFoundException("Book with the ID " + bookId + " does not exist.");
		}
		return bookRepository.findById(bookId).get();
	}
	
	@Override
	public List<Book> getAllBooks() {
		if (bookRepository.findAll().isEmpty()) {
			throw new BookRecordsNotFoundException("Book Records are not Found in Database!");
		}
		return bookRepository.findAll();
	}
	
	@Override
	public void updateBook(long bookId, Book updatedBook) {
		if (bookRepository.findById(bookId).isEmpty()) {
			throw new BookNotFoundException("Book with the ID " + bookId + " does not exist.");
		}
		Book existingBook = bookRepository.findById(bookId).get();
		existingBook.setBookTitle(updatedBook.getBookTitle());
		existingBook.setBookSummary(updatedBook.getBookSummary());
		existingBook.setBookRating(updatedBook.getBookRating());
		
		bookRepository.save(existingBook);
	}
	
	@Override
	public void deleteBookById(long bookId) {
		if (bookRepository.findById(bookId).isEmpty()) {
			throw new BookNotFoundException("Book with the ID " + bookId + " does not exist.");
		}
		bookRepository.deleteById(bookId);
	}
}
