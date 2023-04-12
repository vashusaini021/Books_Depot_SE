package com.booksdepot.group7.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booksdepot.group7.models.CartModel;

@Repository
public interface CartRepo extends JpaRepository<CartModel, Long> {
	
	Optional<CartModel> findByUserId(Long cid);


}
