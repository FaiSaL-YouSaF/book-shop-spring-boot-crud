package com.faisalyousaf777.BookStore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book_table")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private long bookId;
	
	@NotBlank(message = "Book Title may not be Blank")
	@Column(name = "title")
	private String title;
	
	@NotBlank(message = "Book Summary may not be Blank")
	@Column(name = "summary")
	private String summary;
	
	@NotBlank(message = "Book Rating may not be Blank")
	@Column(name = "rating")
	private int rating;

	public Book(String title, String summary, int rating) {
		this.title = title.trim();
		this.summary = summary.trim();
		this.rating = rating;
	}

	public void setTitle(String title) {
		this.title = title.trim();
	}

	public void setSummary(String summary) {
		this.summary = summary.trim();
	}

	public String toString() {
		return "Book{" +
				"bookId=" + bookId +
				", title='" + title + '\'' +
				", summary='" + summary + '\'' +
				", rating=" + rating +
				'}';
	}
	
}
