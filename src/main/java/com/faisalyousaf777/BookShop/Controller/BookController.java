package com.faisalyousaf777.BookShop.Controller;

import com.faisalyousaf777.BookShop.BookExceptions.BookAlreadyExistsException;
import com.faisalyousaf777.BookShop.BookExceptions.BookNotFoundException;
import com.faisalyousaf777.BookShop.BookExceptions.BookRecordsNotFoundException;
import com.faisalyousaf777.BookShop.Entity.Book;
import com.faisalyousaf777.BookShop.Service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class BookController {
	
	private final BookService bookService;
	
	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@Operation(summary = "Retrieve a Book by its Title")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found The Book",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Book.class)
					)}),
			@ApiResponse(responseCode = "404", description = "Book Not Found",
					content = @Content
			)
	})
	@GetMapping("/get-by-book-title/{bookTitle}")
	public ResponseEntity<Object> getBookByBookTitle(@Parameter(description = "ID of the Book to be Searched")
																		 @PathVariable(name = "bookTitle") final String bookTitle) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookByBookTitle(bookTitle));
		} catch (BookNotFoundException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(summary = "Create and save a new Book by providing the required details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Book Added Successfully!"),
			@ApiResponse(responseCode = "400", description = "Invalid Book!",
					content = @Content(mediaType = "application/json")
			)
	})
	@PostMapping("/add-book")
	public ResponseEntity<Object> addBook(@RequestBody final Book book) {
		try {
			bookService.addBook(book);
			return new ResponseEntity<>("Book Added Successfully!", HttpStatus.CREATED);
		} catch (BookAlreadyExistsException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@Operation(summary = "Retrieve a Book by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found The Book",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Book.class)
					)}),
			@ApiResponse(responseCode = "404", description = "Book Not Found",
					content = @Content
			)
	})
	@GetMapping("/get-book-by-id/{bookId}")
	public ResponseEntity<Object> getBookById(@PathVariable(name = "bookId") final long bookId) {
		try {
			return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.OK);
		} catch (BookNotFoundException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(summary = "Retrieve All Available Books")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found The Books",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Book.class)
					)}),
			@ApiResponse(responseCode = "204", description = "No Content Available",
					content = @Content
			)
	})
	@GetMapping("/all-books")
	public ResponseEntity<Object> getAllBooks() {
		try {
			return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
		} catch (BookRecordsNotFoundException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NO_CONTENT);
		}
	}
	
	
	@Operation(summary = "Retrieve Book Details by ID for Update: Fetches and displays book information based on the provided ID, allowing the user to update the details")
	@GetMapping("/update-book-form/{bookId}")
	public ResponseEntity<Object> getAndUpdateBookForm(@PathVariable(name = "bookId") final long bookId) {
		try {
			return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.OK);
		} catch (BookNotFoundException ex) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(summary = "Update a Book in the database with the specified ID using the provided updatedBook details.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book Updated Successfully!",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Book.class)
					)}),
			@ApiResponse(responseCode = "404", description = "Book Not Found",
					content = @Content
			)
	})
	@PutMapping("/update-book/{bookId}")
	public ResponseEntity<Object> updateBook(@PathVariable(name = "bookId") final long bookId, @RequestBody final Book updatedBook) {
		try {
			bookService.updateBook(bookId, updatedBook);
			return ResponseEntity.status(HttpStatus.OK).body("Book Updated Successfully!");
		} catch (BookNotFoundException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	@Operation(summary = "Remove a Book based on its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book Deleted Successfully!"),
			@ApiResponse(responseCode = "404", description = "Book Not Found")
	})
	@DeleteMapping("/delete-book/{bookId}")
	public ResponseEntity<Object> deleteBook(@PathVariable(name = "bookId") final long bookId) {
		try {
			bookService.deleteBookById(bookId);
			return new ResponseEntity<>("Book Deleted Successfully!", HttpStatus.OK);
		} catch (BookNotFoundException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
