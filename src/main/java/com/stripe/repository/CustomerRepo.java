package com.stripe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.stripe.entity.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> 
{
	@Query(value = "SELECT * FROM customer where user_id = ?1",nativeQuery = true)
    Customer findByUserId(int userId);
	
//	@Query(value = "SELECT user_id  FROM customer where cus_id = ?1",nativeQuery = true)
	Customer findByCustomerId(String cusId);
	
	@Modifying
	@Query(value = "update customer set active = 0 where user_id = :userId",nativeQuery = true)
	int deleteByUserId(@Param("userId") int userId);
	
	@Query(value = "select active from customer where user_id= ?1",nativeQuery = true)
	int findActiveByUserId(int userId);

}