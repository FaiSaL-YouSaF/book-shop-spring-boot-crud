package com.faisalyousaf777.BookStore.controller;

import com.faisalyousaf777.BookStore.exceptions.BookAlreadyExistsException;
import com.faisalyousaf777.BookStore.exceptions.BookNotFoundException;
import com.faisalyousaf777.BookStore.exceptions.BookRecordsNotFoundException;
import com.faisalyousaf777.BookStore.entity.Book;
import com.faisalyousaf777.BookStore.service.impl.BookServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor
public class BookController {

	@Autowired
	private final BookServiceImpl bookServiceImpl;

	
	@Operation(summary = "Retrieve a Book by its Title")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the book",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Book.class)
					)}),
			@ApiResponse(responseCode = "404", description = "Book not found",
					content = @Content
			)
	})
	@GetMapping("title/{title}")
	public ResponseEntity<Object> getBookByTitle(@Parameter(description = "ID of the Book to be Searched")
																		 @PathVariable(name = "title") final String title) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(bookServiceImpl.getBookByTitle(title));
		} catch (BookNotFoundException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}


	@Operation(summary = "Create and save a new Book by providing the required details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Book Added Successfully!"),
			@ApiResponse(responseCode = "409", description = "Invalid Book!",
					content = @Content(mediaType = "application/json")
			)
	})
	@PostMapping("/add")
	public ResponseEntity<Object> addBook(@RequestBody final Book book) {
		try {
			bookServiceImpl.saveBook(book);
			return new ResponseEntity<>("Book Added Successfully!", HttpStatus.CREATED);
		} catch (BookAlreadyExistsException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
		}
	}


	@Operation(summary = "Retrieve a Book by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the book",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Book.class)
					)}),
			@ApiResponse(responseCode = "404", description = "Book not found",
					content = @Content
			)
	})
	@GetMapping("/{bookId}")
	public ResponseEntity<Object> getBookById(@PathVariable(name = "bookId") final long bookId) {
		try {
			return new ResponseEntity<>(bookServiceImpl.getBookById(bookId), HttpStatus.OK);
		} catch (BookNotFoundException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}


	@Operation(summary = "Retrieve All Available Books")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the books",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Book.class)
					)}),
			@ApiResponse(responseCode = "204", description = "No Content Available",
					content = @Content
			)
	})
	@GetMapping("/")
	public ResponseEntity<Object> getAllBooks() {
		try {
			return new ResponseEntity<>(bookServiceImpl.getAllBooks(), HttpStatus.OK);
		} catch (BookRecordsNotFoundException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NO_CONTENT);
		}
	}
	
	
	@Operation(summary = "Retrieve Book Details by ID for Update: Fetches and displays book information based on the provided ID, allowing the user to update the details")
	@GetMapping("/update-book-form/{bookId}")
	public ResponseEntity<Object> getAndUpdateBookForm(@PathVariable(name = "bookId") final long bookId) {
		try {
			return new ResponseEntity<>(bookServiceImpl.getBookById(bookId), HttpStatus.OK);
		} catch (BookNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}


	@Operation(summary = "Update a Book in the database with the specified ID using the provided updatedBook details.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book updated successfully!",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Book.class)
					)}),
			@ApiResponse(responseCode = "404", description = "Book not found",
					content = @Content
			)
	})
	@PutMapping("/update/{bookId}")
	public ResponseEntity<Object> updateBook(@PathVariable(name = "bookId") final long bookId, @RequestBody final Book updatedBook) {
		try {
			bookServiceImpl.updateBook(bookId, updatedBook);
			return ResponseEntity.status(HttpStatus.OK).body("Book updated successfully!");
		} catch (BookNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
	
	
	@Operation(summary = "Remove a Book based on its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book deleted successfully!"),
			@ApiResponse(responseCode = "404", description = "Book not found")
	})
	@DeleteMapping("/delete/{bookId}")
	public ResponseEntity<Object> deleteBook(@PathVariable(name = "bookId") final long bookId) {
		try {
			bookServiceImpl.deleteBookById(bookId);
			return new ResponseEntity<>("Book deleted successfully!", HttpStatus.OK);
		} catch (BookNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
}
