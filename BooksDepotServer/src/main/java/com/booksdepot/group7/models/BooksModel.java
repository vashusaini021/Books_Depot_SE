package com.booksdepot.group7.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import jakarta.persistence.Table;

@Entity
@Table(name="books_table")
public class BooksModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;

	@Column(name="book_title")
	String title;

	
	@Column(name="isbn")
	String isbn;

	@Column(name="pagecount")
	int pageCount;
	
	@Column(name="quantity")
	int quantity = 1;

	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	String publishedDate;
	String thumbnailUrl;
	
	@Column(name="shortDescription", length = 9999)
	String shortDescription;
	
	@Column(name="longDescription", length = 9999)
	String longDescription;
	String booksStatus;
	String[] authors;
	String[] categories;
	String price;
	boolean isFeatured = false;
	boolean isBestSeller = false;
	

	public boolean isFeatured() {
		return isFeatured;
	}

	public void setFeatured(boolean isFeatured) {
		this.isFeatured = isFeatured;
	}

	public boolean isBestSeller() {
		return isBestSeller;
	}

	public void setBestSeller(boolean isBestSeller) {
		this.isBestSeller = isBestSeller;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	@ManyToMany
	@JsonIgnore
    @JoinTable(
        name = "carts_for_book",
        joinColumns = @JoinColumn(name = "fk_cart_id"),
        inverseJoinColumns = @JoinColumn(name = "fk_book_id"
        
        )
    )
  private Set<CartModel> carts = new HashSet<>();
    
	
	public BooksModel() {
		super();
	}
	
    public BooksModel(String title, String isbn, int pageCount, String publishedDate, String thumbnailUrl,
			String shortDescription, String longDescription, String booksStatus, String[] authors, String[] categories,
			String price, boolean isFeatured, boolean isBestSeller) {
		super();
		this.title = title;
		this.isbn = isbn;
		this.pageCount = pageCount;
		this.publishedDate = publishedDate;
		this.thumbnailUrl = thumbnailUrl;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.booksStatus = booksStatus;
		this.authors = authors;
		this.categories = categories;
		this.price = price;
		this.isFeatured = isFeatured;
		this.isBestSeller = isBestSeller;
		this.carts = carts;
	}

	public Set<CartModel> getCarts() {
		return carts;
	}

	public void setCarts(Set<CartModel> carts) {
		this.carts = carts;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getBooksStatus() {
		return booksStatus;
	}

	public void setBooksStatus(String booksStatus) {
		this.booksStatus = booksStatus;
	}

	public String[] getAuthors() {
		return authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}
	public void addCart(CartModel newCart) {
		this.carts.add(newCart);
	}
	
	public void removeCart(CartModel removeCart) {
		this.carts.remove(removeCart);
	}
}
