package com.booksdepot.group7.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booksdepot.group7.models.BooksModel;
import com.booksdepot.group7.models.CartModel;
import com.booksdepot.group7.models.UsersModel;
import com.booksdepot.group7.repositories.BooksRepo;
import com.booksdepot.group7.repositories.CartRepo;
import com.booksdepot.group7.repositories.UsersRepo;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class AddToCartController {


	@Autowired
	public BooksRepo bookrepo;

	@Autowired
	public CartRepo cartrepo;

	@Autowired
	public UsersRepo userrepo;

	
	@PostMapping("/update")
	public ResponseEntity<CartModel> addPdroductToCart(@RequestBody CartModel requestCart) {
		
		
	    try {    		
	        Set<BooksModel> newBooks = new HashSet<>();
	        Optional<UsersModel> tempUsr = userrepo.findById(requestCart.getUser().getId());

	        if (!tempUsr.isPresent()) {
	            System.out.println("Book Depot Logs -->> No user found with user id: " + requestCart.getUser().getId());
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        } 

	        UsersModel user = tempUsr.get();
	        Optional<CartModel> optionalCart = cartrepo.findByUserId(user.getId());

	        CartModel myCart = optionalCart.isPresent() ? optionalCart.get() : new CartModel(user);

	        for (BooksModel book : requestCart.getBooks()) {
	            Optional<BooksModel> bookFromDb = bookrepo.findById(book.getId());

	            if (bookFromDb.isPresent()) {
	                BooksModel bookObj = bookFromDb.get();

	                // check if book already exists in cart
	                Optional<BooksModel> cartBookOptional = myCart.getBooks().stream()
	                        .filter(cartBook -> cartBook.getId() == (bookObj.getId())).findFirst();

	                if (cartBookOptional.isPresent()) {
	                    // book already exists in cart, increment quantity
	                	BooksModel cartBook = cartBookOptional.get();
	                    cartBook.setQuantity(cartBook.getQuantity() + 1);
	                } else {
	                    // book not present in cart, add new cart book with quantity 1
						bookObj.addCart(myCart);
						newBooks.add(bookFromDb.get());
	                		                }
	            } else {
	                System.out.println("Book Depot Logs -->> No book found with book id: " + book.getId());
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            }
	        }

	        if (optionalCart.isPresent()) {
	            myCart.addBooks(newBooks);
	            cartrepo.save(myCart);

	            System.out.println("Book Depot Logs -->> Old cart updated successfully:");
	        } else {
	            myCart.setBooks(newBooks);
	            cartrepo.save(myCart);
	            System.out.println("Book Depot Logs -->> New cart made and books added successfully:");
	        }

	        requestCart.setUser(user);
	        requestCart.setBooks(myCart.getBooks());

	        return new ResponseEntity<>(requestCart, HttpStatus.CREATED);

	    } catch (Exception e) {
	        System.out.println(e.getLocalizedMessage());
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@DeleteMapping("/remove")
	public ResponseEntity<CartModel> removeProductFromCart(@RequestBody CartModel requestCart) {
		

	    try {
	        Set<BooksModel> deletedBooks = new HashSet<>();
	        Optional<UsersModel> tempUsr = userrepo.findById(requestCart.getUser().getId());

	        if (!tempUsr.isPresent()) {
	            System.out.println("Book Depot Logs -->> No user found with user id: " + requestCart.getUser().getId());
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        UsersModel user = tempUsr.get();
	        Optional<CartModel> optionalCart = cartrepo.findByUserId(user.getId());

	        if (!optionalCart.isPresent()) {
	            System.out.println("Book Depot Logs -->> No cart found for user id: " + user.getId());
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        CartModel myCart = optionalCart.get();

	        for (BooksModel book : requestCart.getBooks()) {
	            Optional<BooksModel> bookFromDb = bookrepo.findById(book.getId());

	            if (bookFromDb.isPresent()) {
	                BooksModel bookObj = bookFromDb.get();

	                // check if book exists in cart
	                Optional<BooksModel> cartBookOptional = myCart.getBooks().stream()
	                        .filter(cartBook -> cartBook.getId() == (bookObj.getId())).findFirst();

	                if (cartBookOptional.isPresent()) {
	                    // book exists in cart
	                    BooksModel cartBook = cartBookOptional.get();

	                    if (cartBook.getQuantity() > 1) {
	                        // decrease quantity
	                        cartBook.setQuantity(cartBook.getQuantity() - 1);
	                    } else {
	                        // remove book from cart
	                        cartBook.removeCart(myCart);
	                        deletedBooks.add(cartBook);
	                    }
	                } else {
	                    System.out.println("Book Depot Logs -->> Book with id " + bookObj.getId() + " not found in cart");
	                }
	            } else {
	                System.out.println("Book Depot Logs -->> No book found with book id: " + book.getId());
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            }
	        }

	        myCart.removeBooks(deletedBooks);
	        cartrepo.save(myCart);

	        requestCart.setUser(user);
	        requestCart.setBooks(myCart.getBooks());

	        return new ResponseEntity<>(requestCart, HttpStatus.OK);

	    } catch (Exception e) {
	        System.out.println(e.getLocalizedMessage());
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	

	@GetMapping("/{userid}")
	public ResponseEntity<Set<BooksModel>> getMycart(@PathVariable("userid") long id) {

		try {    		
			Optional<UsersModel> tempUsr = userrepo.findById(id);

			if(!tempUsr.isPresent()) {
				System.out.println(" Book Depot Logs-->> No user found with user id: "+ id);
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} 
			UsersModel user = tempUsr.get();
			Optional<CartModel> optionalCart = cartrepo.findByUserId(user.getId());
			CartModel mycart = optionalCart.isPresent() ? optionalCart.get() :  new CartModel(user);

			return new ResponseEntity<>(mycart.getBooks(), HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@DeleteMapping("/update")
	public ResponseEntity<CartModel> deleteProductToCart(
			@RequestBody CartModel requestCart
			) {

		try {    		
			Optional<UsersModel> tempUsr = userrepo.findById(requestCart.getUser().getId());

			if(!tempUsr.isPresent()) {
				System.out.println(" Book Depot Logs-->> No user found with user id: "+ requestCart.getUser().getId());
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} 

			UsersModel user = tempUsr.get();
			requestCart.setUser(user);

			Optional<CartModel> optionalCart = cartrepo.findByUserId(user.getId());

			if (optionalCart.isPresent()) {
				CartModel mycart =  optionalCart.get() ;
				Set<BooksModel> allBooks =  mycart.getBooks();

				for (BooksModel book : requestCart.getBooks()) {
					Optional<BooksModel> bookFromDb = bookrepo.findById(book.getId());

					if (bookFromDb.isPresent()) {
						allBooks.removeIf(_book -> _book.getId() == bookFromDb.get().getId());
					} else {
						System.out.println(" Book Depot Logs-->> No book found with book id: "+ book.getId() );
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
					}
				}
				mycart.setBooks(allBooks);
				cartrepo.save(mycart);
				return new ResponseEntity<>(mycart, HttpStatus.NO_CONTENT);

			} else {
				Set<BooksModel> emptySet = new HashSet<>();
				requestCart.setBooks(emptySet);
				return new ResponseEntity<>( requestCart, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
