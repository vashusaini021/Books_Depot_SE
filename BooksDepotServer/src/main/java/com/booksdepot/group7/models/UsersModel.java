package com.booksdepot.group7.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
		name="users_table",
		uniqueConstraints=
		@UniqueConstraint(columnNames={"username"})
		)
public class UsersModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;

	@Column(name="username",unique = true)
	@JsonProperty("username")
	String username;
	
	String firstName;
	
	String lastName;

	@JsonProperty(access = Access.WRITE_ONLY)
	String password;
	
//	
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="fk_cart_id")
//    private CartModel cart;
//    
//    
    

	public UsersModel(String username, String firstName, String lastName, String password) {
	super();
	this.username = username;
	this.firstName = firstName;
	this.lastName = lastName;
	this.password = password;
}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public UsersModel() {
		super();
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


//	public CartModel getCart() {
//		return cart;
//	}
//
//
//	public void setCart(CartModel cart) {
//		this.cart = cart;
//	}
	

}
