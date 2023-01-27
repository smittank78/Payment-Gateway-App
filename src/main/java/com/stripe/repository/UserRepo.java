package com.stripe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stripe.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> 
{

	Optional<User> findByName(String name);

	Optional<User> findByPhoneOrEmail(int phone,String email);
	
	@Modifying
	@Query(value = "update user set active = 0 where id= ?1",nativeQuery = true)
	int deleteById(int id);

	@Query(value = "select active from user where id= ?1",nativeQuery = true)
	int findActiveById(int userId);
}