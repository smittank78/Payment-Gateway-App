package com.stripe.dto;

public class PurchaseProductDto
{
	private String product;
	private String brand;
	private String model;
	private float amount;
	private int qty;
	private String description;
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "PurchaseProductDto [product=" + product + ", brand=" + brand + ", model=" + model + ", amount=" + amount
				+ ", qty=" + qty + ", description=" + description + "]";
	}
}