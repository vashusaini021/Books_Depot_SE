package com.booksdepot.group7.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booksdepot.group7.models.UsersModel;

@Repository
public interface UsersRepo extends JpaRepository<UsersModel, Long> {
	Optional<UsersModel> findByUsername(String username);
    Optional<UsersModel> findByUsernameAndPassword(String username, String password);


}
