package com.stripe.dto;

import java.util.List;

public class BillingDto
{
	private String name;
	private String email;
	private String phone;
	private String address;
	private List<PurchaseProductDto> purchase;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<PurchaseProductDto> getPurchase() {
		return purchase;
	}
	public void setPurchase(List<PurchaseProductDto> purchase) {
		this.purchase = purchase;
	}
	@Override
	public String toString() {
		return "BillingDto [name=" + name + ", email=" + email + ", phone=" + phone + ", address=" + address
				+ ", purchase=" + purchase + "]";
	}
}