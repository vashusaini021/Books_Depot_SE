package com.booksdepot.group7.controllers;

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
import com.booksdepot.group7.models.UsersModel;
import com.booksdepot.group7.repositories.UsersRepo;

@RestController
@RequestMapping("/user")
public class UsersController {

	
	@Autowired
	 UsersRepo usersRepository;


	@GetMapping("/{id}")
	public ResponseEntity<UsersModel> getUserByUserById(@PathVariable("id") long id) {
		Optional<UsersModel> user = usersRepository.findById(id);
		if (user.isPresent()) {
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<UsersModel> createUser(@RequestBody UsersModel user) {
		try {
			

			UsersModel newUser = new UsersModel(user.getUsername(), user.getFirstName(), user.getLastName(), user.getPassword());
			usersRepository.save(newUser);
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UsersModel user) {
		try {
			
			String userName = user.getUsername();
			String password = user.getPassword();
			
			Optional<UsersModel> dbuser = usersRepository.findByUsernameAndPassword(userName, password);
			
			if (dbuser.isPresent()) {
		        return new ResponseEntity<>(dbuser.get(), HttpStatus.OK); 
			} else {
		        return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED); 
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>("User not found",HttpStatus.UNAUTHORIZED);
		}
	}
}

