package com.book.exchange.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.book.exchange.entity.Book;
import com.book.exchange.enums.AvailabilityStatus;
import com.book.exchange.enums.BookCondition;
import com.book.exchange.response.BookResponse;

@Component
public class BooksUtil {
	public List<BookResponse> mapEntityToModel(List<Book> books) {
		List<BookResponse> bookslist = new ArrayList<>();	
		if(null != books) {
			for(Book book:books) {
				BookResponse userBook = new BookResponse();
				userBook.setBookId(book.getBookId());
				userBook.setTitle(book.getTitle());
				userBook.setAuthor(book.getAuthor());
				userBook.setGenre(book.getGenre());
				userBook.setAvailabilityStatus(AvailabilityStatus.valueOf(book.getAvailabilityStatus()));
				userBook.setCondition(BookCondition.valueOf(book.getBookCondition()));
				userBook.setLocation(book.getLocation());
				bookslist.add(userBook);
			}
		}
		return bookslist;
		
	}
}
