package com.booksdepot.group7.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booksdepot.group7.models.BooksModel;
import com.booksdepot.group7.repositories.BooksRepo;

@RestController
@RequestMapping("/books")
public class BooksController {

	@Autowired
	 BooksRepo booksRepository;

	@GetMapping("/allbooks")
	public ResponseEntity<List<BooksModel>> getAllBooks() {
		try {
			List<BooksModel> books = booksRepository.findAll();

			if (books.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(books, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "details/{id}")
	ResponseEntity<BooksModel> getBookDetaild(@PathVariable String id) {

		try {
			Optional<BooksModel> book = booksRepository.findById(Long.parseLong(id));

			if (book.isPresent()) {
				return new ResponseEntity<>(book.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/search")
    public ResponseEntity<?> searchBooksByTitle(@RequestParam("searchString") String searchString) {
        List<BooksModel> books = booksRepository.findByTitleContainingIgnoreCase(searchString);
        if (books.isEmpty()) {
            return new ResponseEntity<>("No books found with the given search string", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
	
	
	@GetMapping("/details/isbn/{isbn}")
	ResponseEntity<BooksModel> getBookDetailsbyISBN(@PathVariable String isbn) {

		try {
			Optional<BooksModel> book = booksRepository.findByIsbn(isbn);

			if (book.isPresent()) {
				return new ResponseEntity<>(book.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/bestseller")
	ResponseEntity<List<BooksModel>> getBestSellerBooks() {
		try {
			List<BooksModel> books  = booksRepository.findTop10ByIsBestSellerTrue();

			if (!books.isEmpty()) {
				return new ResponseEntity<>(books, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/featured")
	ResponseEntity<List<BooksModel>> getfeaturedBooks() {
		try {
			List<BooksModel> books  = booksRepository.findTop10ByIsFeaturedTrue();

			if (!books.isEmpty()) {
				return new ResponseEntity<>(books, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/details/author")
	public ResponseEntity<List<BooksModel>> getBookDetailsbyAuthors(@RequestParam(required = false) String author) {

		try {
			String[] authorArray = {author};
			List<BooksModel> books  = booksRepository.findByAuthorsIn(authorArray);

			if (!books.isEmpty()) {
				return new ResponseEntity<>(books, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	

}
