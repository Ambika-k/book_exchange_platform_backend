package com.book.exchange.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.book.exchange.request.AddBookRequest;
import com.book.exchange.request.EditBookRequest;
import com.book.exchange.response.BookResponse;
import com.book.exchange.response.CustomResponse;

@Service
public interface BookService {
	CustomResponse addBook(String email,AddBookRequest addBookRequest);
	List<BookResponse> getBooks(String email);
	Page<BookResponse> searchBookByCriteria(Optional<String> title,
            Optional<String> author,
            Optional<String> genre,
            Optional<String> location,
            Optional<List<String>> availabilitystatuses,Pageable pageable);
	CustomResponse editBook(EditBookRequest editBookRequest);
	CustomResponse deleteBook(String bookId);
}
