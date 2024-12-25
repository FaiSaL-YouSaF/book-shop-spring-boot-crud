package com.faisalyousaf777.BookStore.service;

import com.faisalyousaf777.BookStore.entity.Book;

import java.util.List;

public interface BookService {
	
	void saveBook(final Book book);
	Book getBookById(final long bookId);
	Book getBookByTitle(final String title);
	List<Book> getAllBooks();
	void updateBook(final long bookId, final Book updatedBook);
	void deleteBookById(final long bookId);
}
