package com.stripe.dto;

import java.util.List;

public class BillingDto {
	private String name;
	private String email;
	private String phone;
	private String address;
	private String cus_id;
	private String payment_method_id;
	private String transaction_id;
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

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getCus_id() {
		return cus_id;
	}

	public void setCus_id(String cus_id) {
		this.cus_id = cus_id;
	}

	public String getPayment_method_id() {
		return payment_method_id;
	}

	public void setPayment_method_id(String payment_method_id) {
		this.payment_method_id = payment_method_id;
	}

	@Override
	public String toString() {
		return "BillingDto [name=" + name + ", email=" + email + ", phone=" + phone + ", address=" + address
				+ ", cus_id=" + cus_id + ", payment_method_id=" + payment_method_id + ", transaction_id="
				+ transaction_id + ", purchase=" + purchase + "]";
	}
	
}