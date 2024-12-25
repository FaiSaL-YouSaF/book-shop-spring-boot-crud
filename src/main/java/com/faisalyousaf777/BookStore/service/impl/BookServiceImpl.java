package com.faisalyousaf777.BookStore.service.impl;

import com.faisalyousaf777.BookStore.exceptions.BookAlreadyExistsException;
import com.faisalyousaf777.BookStore.exceptions.BookNotFoundException;
import com.faisalyousaf777.BookStore.exceptions.BookRecordsNotFoundException;
import com.faisalyousaf777.BookStore.entity.Book;
import com.faisalyousaf777.BookStore.repository.BookRepository;
import com.faisalyousaf777.BookStore.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

	@Autowired
	private final BookRepository bookRepository;
	
	
	@Override
	public Book getBookByTitle(final String title) {
		if (bookRepository.findByTitle(title).isEmpty()) {
			throw new BookNotFoundException("Invalid Title : Book with the title : " + title + " does not exists.");
		}
		return bookRepository.findByTitle(title).get();
	}
	
	@Override
	public void saveBook(final Book book) {
		if (bookRepository.findById(book.getBookId()).isPresent()) {
			throw new BookAlreadyExistsException("Invalid ID : Book with the ID " + book.getBookId() + " exists already.");
		}
		if (bookRepository.findByTitle(book.getTitle()).isPresent()) {
			throw new BookAlreadyExistsException("Invalid Title : Book with the title : " + book.getTitle() + " exists already.");
		}
		bookRepository.save(book);
	}
	
	@Override
	public Book getBookById(final long bookId) {
		if (bookRepository.findById(bookId).isEmpty()) {
			throw new BookNotFoundException("Invalid ID : Book with the ID " + bookId + " does not exist.");
		}
		return bookRepository.findById(bookId).get();
	}
	
	@Override
	public List<Book> getAllBooks() {
		if (bookRepository.findAll().isEmpty()) {
			throw new BookRecordsNotFoundException("No book records found in the database. Please add books to view the list.");
		}
		return bookRepository.findAll();
	}
	
	@Override
	public void updateBook(final long bookId, final Book book) {
		if (bookRepository.findById(bookId).isEmpty()) {
			throw new BookNotFoundException("Invalid ID : Book with the ID " + bookId + " does not exist.");
		}
		Book existingBook = bookRepository.findById(bookId).get();
		existingBook.setTitle(book.getTitle());
		existingBook.setSummary(book.getSummary());
		existingBook.setRating(book.getRating());
		bookRepository.save(existingBook);
	}
	
	@Override
	public void deleteBookById(final long bookId) {
		if (bookRepository.findById(bookId).isEmpty()) {
			throw new BookNotFoundException("Invalid ID : Book with the ID " + bookId + " does not exist.");
		}
		bookRepository.deleteById(bookId);
	}
}
