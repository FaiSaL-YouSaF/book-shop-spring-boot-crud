package com.faisalyousaf777.BookShop.Service;

import com.faisalyousaf777.BookShop.Entity.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookServiceImplement {
	
	Book getBookByBookTitle(String bookTitle);
	void addBook(Book book);
	Book getBookById(long bookId);
	List<Book> getAllBooks();
	
	void updateBook(long bookId, Book updatedBook);
	void deleteBookById(long bookId);
}
