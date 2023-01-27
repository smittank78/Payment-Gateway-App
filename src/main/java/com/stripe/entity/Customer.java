package com.stripe.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "customer")
@Data
public class Customer
{	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "id")
	private int id;
	
	@Column(name = "cus_id")
	private String customerId;
	
	@OneToOne(cascade = CascadeType.ALL,fetch =  FetchType.EAGER)
	@JoinColumn(name = "user_id" , referencedColumnName = "id")
	private User user;
	
	@Column(name = "active")
	private int active;
	
	@OneToOne(mappedBy = "customer" , fetch = FetchType.EAGER)
	private PaymentMethod paymentMethod;
}