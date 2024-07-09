package com.chatapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chatapp.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.full_name LIKE %:query% or u.email Like %:query%")
	List<User> searchUsers(@Param("query") String query);
}
