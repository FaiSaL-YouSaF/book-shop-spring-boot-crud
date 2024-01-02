package com.faisalyousaf777.BookShop.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "book_record")
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "book_id")
	private long bookId;
	
	@NotBlank(message = "Book Title may not be Blank")
	@Column(name = "book_title")
	private String bookTitle;
	
	@NotBlank(message = "Book Summary may not be Blank")
	@Column(name = "book_summary")
	private String bookSummary;
	
	@NotBlank(message = "Book Rating may not be Blank")
	@Column(name = "book_rating")
	private int bookRating;
	
	public Book(Book book) {
		this.bookId = book.getBookId();
		this.bookTitle = book.getBookTitle();
		this.bookSummary = book.getBookSummary();
		this.bookRating = book.getBookRating();
	}
	public Book(String bookTitle, String bookSummary, int bookRating) {
		this.bookTitle = bookTitle;
		this.bookSummary = bookSummary;
		this.bookRating = bookRating;
	}
	
}
