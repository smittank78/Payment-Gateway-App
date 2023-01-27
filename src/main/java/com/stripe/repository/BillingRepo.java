package com.stripe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.stripe.dto.BillingDto;
import com.stripe.entity.Billing;

@Repository
public interface BillingRepo extends JpaRepository<Billing, Integer> 
{

}