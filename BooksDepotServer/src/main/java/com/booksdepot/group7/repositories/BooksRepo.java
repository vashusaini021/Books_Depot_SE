package com.booksdepot.group7.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booksdepot.group7.models.BooksModel;


@Repository
public interface BooksRepo extends JpaRepository<BooksModel, Long> {
	
	Optional<BooksModel> findByIsbn(String isbn);
    List<BooksModel> findByAuthorsIn(String[] authors);
    List<BooksModel> findByPublishedDate(String date);
    List<BooksModel> findTop10ByIsBestSellerTrue();
    List<BooksModel> findTop10ByIsFeaturedTrue();
    List<BooksModel> findTop10ByOrderByPublishedDateAsc();
	List<BooksModel> findByTitleContainingIgnoreCase(String searchString);
}
