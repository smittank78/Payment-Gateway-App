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
@Table(name = "user")
@Data
public class Product
{	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
		
	@Column(name = "product")
	private String product;
	
	@Column(name = "brand")
	private String brand;
		
	@Column(name = "model")
	private String model;
	
	@Column(name = "amount")
	private int amount;
	
	@Column(name = "qty")
	private int qty;
	
	@Column(name = "description")
	private String description;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "bill_id",referencedColumnName = "bill_id")
	private Billing billing;
		
}