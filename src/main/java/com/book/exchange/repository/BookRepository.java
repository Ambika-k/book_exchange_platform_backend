package com.book.exchange.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.book.exchange.entity.Book;
import com.book.exchange.entity.User;


public interface BookRepository extends JpaRepository<Book, Long>{
	List<Book> findByUser(User user);
	@Query("SELECT b FROM Book b WHERE " +
		       "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
		       "(:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) AND " +
		       "(:genre IS NULL OR LOWER(b.genre) LIKE LOWER(CONCAT('%', :genre, '%'))) AND " +
		       "(:location IS NULL OR LOWER(b.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
		       "(COALESCE(:availabilityStatuses, NULL) IS NULL OR b.availabilityStatus IN (:availabilityStatuses))")
		Page<Book> findByCriteria(@Param("title") String title,
		                          @Param("author") String author,
		                          @Param("genre") String genre,
		                          @Param("location") String location,
		                          @Param("availabilityStatuses") List<String> availabilityStatuses,
		                          Pageable pageable);


	Book findByBookId(String bookId);
}
