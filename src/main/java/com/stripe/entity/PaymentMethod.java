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
@Table(name = "payment_method")
@Data
public class PaymentMethod
{	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "pm_id")
	private String pmId;
	
	@OneToOne(cascade = CascadeType.ALL,fetch =  FetchType.EAGER)
	@JoinColumn(name = "cus_id" , referencedColumnName = "cus_id")
	private Customer customer;
	
}