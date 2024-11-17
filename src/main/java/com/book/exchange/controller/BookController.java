package com.book.exchange.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.exchange.request.AddBookRequest;
import com.book.exchange.request.EditBookRequest;
import com.book.exchange.request.SearchCriteriaRequest;
import com.book.exchange.response.BookResponse;
import com.book.exchange.response.CustomResponse;
import com.book.exchange.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	@Autowired
	BookService bookService;

	@PostMapping("/add")
	public ResponseEntity<CustomResponse> addBook(@RequestParam String email,
			@RequestBody AddBookRequest addBookRequest) {
		CustomResponse customResponse =  bookService.addBook(email, addBookRequest);
		return new ResponseEntity<CustomResponse>(customResponse,HttpStatus.CREATED);
	}
	@PutMapping("/edit")
	public ResponseEntity<CustomResponse> editBook(@RequestBody EditBookRequest editBookRequest) {
		CustomResponse customResponse = bookService.editBook(editBookRequest);
		return new ResponseEntity<CustomResponse>(customResponse,HttpStatus.OK);
		
	}
	@DeleteMapping("/delete")
	public ResponseEntity<CustomResponse> deleteBook(@RequestParam String bookId) {
		CustomResponse customResponse = bookService.deleteBook(bookId);
		return new ResponseEntity<CustomResponse>(customResponse,HttpStatus.OK);
	}
	@GetMapping("/getbooks")
	public ResponseEntity<List<BookResponse>> getBooks(@RequestParam String email) {
		List<BookResponse> bookslist = bookService.getBooks(email);
		return new ResponseEntity<List<BookResponse>>(bookslist,HttpStatus.OK);
		
	}
	@PostMapping("/search")
	public ResponseEntity<Page<BookResponse>> searchBookByCriteria(@RequestBody SearchCriteriaRequest searchCriteriaRequest,@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {
		Page<BookResponse> bookResponses = bookService.searchBookByCriteria(Optional.ofNullable(searchCriteriaRequest.getTitle()),
                Optional.ofNullable(searchCriteriaRequest.getAuthor()),
                Optional.ofNullable(searchCriteriaRequest.getGenre()),
                Optional.ofNullable(searchCriteriaRequest.getLocation()),
                Optional.ofNullable(searchCriteriaRequest.getAvailabilityStatuses()),PageRequest.of(page, size));
		return new ResponseEntity<Page<BookResponse>>(bookResponses,HttpStatus.OK);
		
	}
	
}
