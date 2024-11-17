package com.book.exchange.request;

import com.book.exchange.enums.AvailabilityStatus;
import com.book.exchange.enums.BookCondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditBookRequest {
	private String bookId;
	private String title;
	private String author;
	private String genre;
	private BookCondition condition;
	private AvailabilityStatus availabilityStatus;
	private String location;
}
