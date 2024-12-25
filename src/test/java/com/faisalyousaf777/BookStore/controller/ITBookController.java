package com.faisalyousaf777.BookStore.controller;

import com.faisalyousaf777.BookStore.entity.Book;
import com.faisalyousaf777.BookStore.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ITBookController {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private BookRepository bookRepository;
	
	Book savedBook1;
	Book savedBook2;
	Book savedBook3;
	
	//Not Already saved in Database
	Book unSavedBook4 = new Book(4L, "Java", "Java for absolute beginners", 5);
	
	@BeforeEach
	void setUp() {
		savedBook1 = new Book(1L, "English", "Learn Basics of English", 4);
		savedBook2 = new Book(2L, "Math", "This is a Book of Math", 5);
		savedBook3 = new Book(3L, "Physics", "This is a Book of Physics", 5);
		bookRepository.save(savedBook1);
		bookRepository.save(savedBook2);
		bookRepository.save(savedBook3);
	}
	@AfterEach
	void tearDown() {
		bookRepository.deleteAll();
	}
	
	@Test
	@Order(1)
	@DisplayName("GetBookByTitle - Succeeds When Book Exists")
	void testGetBookByTitle_succeedsWhenBookExists() throws Exception {
		String title = savedBook1.getTitle();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/title/"+title))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.bookId").value(savedBook1.getBookId()))
				.andExpect(jsonPath("$.title").value(savedBook1.getTitle()))
				.andExpect(jsonPath("$.bookSummary").value(savedBook1.getSummary()))
				.andExpect(jsonPath("$.bookRating").value(savedBook1.getRating()));
	}
	
	@Test
	@Order(2)
	@DisplayName("GetBookByTitle - Fails When Book Does Not Exists")
	void testGetBookByTitle_failsWhenBookDoesNotExists() throws Exception {
		String title = "Java";
		
		mockMvc.perform(MockMvcRequestBuilders.get("/title/"+title))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Invalid Title : Book with the title : " + title + " does not exists."));
	}
	
	
	@Test
	@Order(3)
	@DisplayName("GetBookById - Succeeds When Book Exists")
	void testGetBookById_bookExists() throws Exception {
		long savedBookId = 1L;
		
		mockMvc.perform(MockMvcRequestBuilders.get(String.valueOf(savedBookId)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.bookId").value(savedBook1.getBookId()))
				.andExpect(jsonPath("$.title").value(savedBook1.getTitle()))
				.andExpect(jsonPath("$.bookSummary").value(savedBook1.getSummary()))
				.andExpect(jsonPath("$.bookRating").value(savedBook1.getRating()));
	}
	
	@Test
	@Order(4)
	@DisplayName("GetBookById - Fails When Book Does Not Exists")
	void testGetBookById_bookDoesNotExists() throws Exception {
		long unSavedBookId = unSavedBook4.getBookId();
		
		mockMvc.perform(MockMvcRequestBuilders.get(String.valueOf(unSavedBookId)))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Invalid ID : Book with the ID " + unSavedBookId + " does not exist."));
	}
	
	
	@Test
	@Order(5)
	@DisplayName("GetAllBooks - Succeeds When List of Books Exists")
	void testGetAllBooks_returnsListOfBooks() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].bookId").value(savedBook1.getBookId()))
				.andExpect(jsonPath("$[0].title").value(savedBook1.getTitle()))
				.andExpect(jsonPath("$[0].bookSummary").value(savedBook1.getSummary()))
				.andExpect(jsonPath("$[0].bookRating").value(savedBook1.getRating()))
				.andExpect(jsonPath("$[1].bookId").value(savedBook2.getBookId()))
				.andExpect(jsonPath("$[1].title").value(savedBook2.getTitle()))
				.andExpect(jsonPath("$[1].bookSummary").value(savedBook2.getSummary()))
				.andExpect(jsonPath("$[1].bookRating").value(savedBook2.getRating()))
				.andExpect(jsonPath("$[2].bookId").value(savedBook3.getBookId()))
				.andExpect(jsonPath("$[2].title").value(savedBook3.getTitle()))
				.andExpect(jsonPath("$[2].bookSummary").value(savedBook3.getSummary()))
				.andExpect(jsonPath("$[2].bookRating").value(savedBook3.getRating()));
	}
	
	@Test
	@Order(6)
	@DisplayName("GetAllBooks - Fails When List of Books is Empty")
	void testGetAllBooks_returnsNoContentWhenEmpty() throws Exception{
		// Need Empty Database to test this method
		bookRepository.deleteAll();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
						.andExpect(status().isNoContent())
						.andExpect(content().string("No book records found in the database. Please add books to view the list."));
	}
	
	
	@Test
	@Order(7)
	@DisplayName("AddBook - Succeeds When ID or Title are not Duplicate")
	void testAddBook_succeedsWhenIdOrTitleAreNotDuplicate() throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		String unSavedBookAsJson = objectMapper.writeValueAsString(unSavedBook4);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/add")
						.contentType(MediaType.APPLICATION_JSON)
						.content(unSavedBookAsJson)
				)
				.andExpect(status().isCreated())
				.andExpect(content().string("Book Added Successfully!"));
	}
	
	@Test
	@Order(8)
	@DisplayName("AddBook - Fails When ID is Duplicate")
	void testAddBook_failsWhenIdIsDuplicate() throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		String savedBookAsJson = objectMapper.writeValueAsString(savedBook1);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/add")
						.contentType(MediaType.APPLICATION_JSON)
						.content(savedBookAsJson)
				)
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Invalid ID : Book with the ID " + savedBook1.getBookId() +" exists already."));
	}
	
	@Test
	@Order(9)
	@DisplayName("AddBook - Fails When Title is Duplicate")
	void testAddBook_failsWhenTitleIsDuplicate() throws Exception{
		Book bookWithDuplicateTitle = new Book(unSavedBook4.getBookId(), savedBook1.getTitle(), savedBook1.getSummary(), savedBook1.getRating());
		ObjectMapper objectMapper = new ObjectMapper();
		String bookWithDuplicateTitleAsJson = objectMapper.writeValueAsString(bookWithDuplicateTitle);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/add")
						.contentType(MediaType.APPLICATION_JSON)
						.content(bookWithDuplicateTitleAsJson)
				)
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Invalid Title : Book with the title : " + savedBook1.getTitle() + " exists already."));
	}
	
	
	@Test
	@Order(10)
	@DisplayName("UpdateBookForm - Succeeds When Book Exists")
	void testUpdateBookForm_succeedsWhenBookExists() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/update-book-form/" + savedBook1.getBookId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.bookId").value(savedBook1.getBookId()))
				.andExpect(jsonPath("$.title").value(savedBook1.getTitle()))
				.andExpect(jsonPath("$.bookSummary").value(savedBook1.getSummary()))
				.andExpect(jsonPath("$.bookRating").value(savedBook1.getRating()));
	}
	
	@Test
	@Order(11)
	@DisplayName("UpdateBookForm - Fails When Book Does Not Exists")
	void testUpdateBookForm_failsWhenBookDoesNotExists() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/update-book-form/" + unSavedBook4.getBookId()))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Invalid ID : Book with the ID " + unSavedBook4.getBookId() + " does not exist."));
	}
	
	
	@Test
	@Order(12)
	@DisplayName("UpdateBook - Succeeds When Book Exists")
	void testUpdateBook_succeedsWhenBookExists() throws Exception {
		Book updatedBook = new Book(1L, "Updated Title", "Updated Summary of Book", 5);
		long bookId = updatedBook.getBookId();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String updatedBookJson = objectMapper.writeValueAsString(updatedBook);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/update/"+bookId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(updatedBookJson)
				)
				.andExpect(status().isOk())
				.andExpect(content().string("Book updated successfully!"));
	}
	
	@Test
	@Order(13)
	@DisplayName("UpdateBook - Fails When Book Does Not Exists")
	void testUpdateBook_failsWhenBookDoesNotExists() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String updatedBookJson = objectMapper.writeValueAsString(unSavedBook4);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/update/"+unSavedBook4.getBookId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(updatedBookJson)
				)
				.andExpect(status().isNotFound())
				.andExpect(content().string("Invalid ID : Book with the ID " + unSavedBook4.getBookId() + " does not exist."));
	}
	
	
	@Test
	@Order(14)
	@DisplayName("DeleteBook - Succeeds When Book Exists")
	void testDeleteBook_succeedsWhenBookExists() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/delete/"+savedBook1.getBookId()))
				.andExpect(status().isOk())
				.andExpect(content().string("Book deleted successfully!"));
	}
	
	@Test
	@Order(15)
	@DisplayName("DeleteBook - Fails When Book Does Not Exists")
	void testDeleteBook_failsWhenBookDoesNotExists() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/delete/"+unSavedBook4.getBookId()))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Invalid ID : Book with the ID " + unSavedBook4.getBookId() + " does not exist."));
	}
}