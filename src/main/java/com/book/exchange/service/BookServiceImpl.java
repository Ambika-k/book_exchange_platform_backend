package com.book.exchange.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.book.exchange.entity.Book;
import com.book.exchange.entity.User;
import com.book.exchange.enums.AvailabilityStatus;
import com.book.exchange.enums.BookCondition;
import com.book.exchange.exception.UserNotFoundException;
import com.book.exchange.repository.BookRepository;
import com.book.exchange.repository.UserRepository;
import com.book.exchange.request.AddBookRequest;
import com.book.exchange.request.EditBookRequest;
import com.book.exchange.response.BookResponse;
import com.book.exchange.response.CustomResponse;
import com.book.exchange.util.BooksUtil;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	BooksUtil booksUtil;

	@Override
	public CustomResponse addBook(String email, AddBookRequest addBookRequest) {
		try {
			User user = userRepository.findByEmail(email);
			if (null == user) {
				throw new UserNotFoundException("No user exists with this email");
			}
			Book bookEntity = new Book();
			bookEntity.setAuthor(addBookRequest.getAuthor());
			bookEntity.setTitle(addBookRequest.getTitle());
			bookEntity.setGenre(addBookRequest.getGenre());
			bookEntity.setLocation(addBookRequest.getLocation());
			bookEntity.setBookCondition(addBookRequest.getCondition().toString());
			bookEntity.setAvailabilityStatus(addBookRequest.getAvailabilityStatus().toString());
			bookEntity.setUser(user);
			Book savedBook = bookRepository.save(bookEntity);
			String bookId = "u" + savedBook.getUser().getUserId() + "b" + savedBook.getId();
			savedBook.setBookId(bookId);
			bookRepository.save(savedBook);

		} catch (Exception e) {
			e.printStackTrace();
		}
		CustomResponse customResponse = new CustomResponse("Book added successfully");
		return customResponse;

	}

	@Override
	public List<BookResponse> getBooks(String email) {
		User user = userRepository.findByEmail(email);
		if (null == user) {
			throw new UserNotFoundException("No user exists with this email");
		}
		List<Book> bookslist = bookRepository.findByUser(user);
		System.out.println("Books got fecthed");
		return booksUtil.mapEntityToModel(bookslist);
	}

	@Override
	public Page<BookResponse> searchBookByCriteria(Optional<String> title, Optional<String> author,
			Optional<String> genre, Optional<String> location,
			Optional<List<String>> availabilityStatuses,Pageable pageable) {

		Page<Book> books = bookRepository.findByCriteria(title.orElse(null), author.orElse(null), genre.orElse(null),
				location.orElse(null), availabilityStatuses.orElse(null),pageable);
		return books.map(book -> {
	        BookResponse bookResponse = new BookResponse();
	        bookResponse.setAuthor(book.getAuthor());
	        bookResponse.setBookId(book.getBookId());
	        bookResponse.setTitle(book.getTitle());
	        bookResponse.setGenre(book.getGenre());
	        bookResponse.setLocation(book.getLocation());
	        bookResponse.setCondition(BookCondition.valueOf(book.getBookCondition()));
	        bookResponse.setAvailabilityStatus(AvailabilityStatus.valueOf( book.getAvailabilityStatus()));
	        return bookResponse;
	    });
	}

	@Override
	public CustomResponse editBook(EditBookRequest editBookRequest) {
		Book book = bookRepository.findByBookId(editBookRequest.getBookId());
		book.setTitle((null!= editBookRequest.getTitle())? editBookRequest.getTitle():book.getTitle());
		book.setAuthor((null != editBookRequest.getAuthor())?editBookRequest.getAuthor():book.getAuthor());
		book.setGenre((null != editBookRequest.getGenre())?editBookRequest.getGenre():book.getGenre());
		book.setAvailabilityStatus((null != editBookRequest.getAvailabilityStatus())?editBookRequest.getAvailabilityStatus().name():book.getAvailabilityStatus());
		book.setBookCondition((null != editBookRequest.getCondition())?editBookRequest.getCondition().name():book.getBookCondition());
		book.setLocation((null != editBookRequest.getLocation())?editBookRequest.getLocation():book.getLocation());
		bookRepository.save(book);
		CustomResponse customResponse = new CustomResponse("Edited book successfully");
		return customResponse;
	}

	@Override
	public CustomResponse deleteBook(String bookId) {
		Book book = bookRepository.findByBookId(bookId);
		bookRepository.delete(book);
		CustomResponse customResponse = new CustomResponse("Deleted book successfully");
		return customResponse;
	}

}
