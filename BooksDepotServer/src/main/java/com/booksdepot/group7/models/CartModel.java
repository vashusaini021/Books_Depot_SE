package com.booksdepot.group7.models;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class CartModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	

	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="fk_user_id")
    @JsonProperty("user")
    private UsersModel user;
    
    
	
  @ManyToMany(mappedBy = "carts")
  @JsonProperty("books")
  private Set<BooksModel> books = new HashSet<>();
	
//		public CartModel() {
//			super();
//		}
		
		

		public CartModel(UsersModel user) {
			super();
			this.user = user;
		}



		public CartModel() {
			super();
		}



		public UsersModel getUser() {
			return user;
		}



		public void setUser(UsersModel user) {
			this.user = user;
		}

		public Set<BooksModel> getBooks() {
			return books;
		}



		public void setBooks(Set<BooksModel> books) {
			this.books = books;
		}



		public void addBooks(Set<BooksModel> booksList) {
			this.books.addAll(booksList);
			
		}
		
		public void removeBooks(Set<BooksModel> booksList) {
			this.books.removeAll(booksList);			
		}
	    
	    
	    
	    

}
