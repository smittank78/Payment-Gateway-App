package com.stripe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "billing")
@Data
public class Billing
{	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bill_id")
	private int billId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "cus_id")
	private String cusId;
	
	@Column(name = "pm_id")
	private String pmId;
	
	@Column(name = "transaction_id")
	private String transactionId;
	
	@Column(name = "total_amount")
	private String totalAmount;
		
	@OneToOne(mappedBy = "billing" , fetch = FetchType.EAGER)
	private Product product;
}