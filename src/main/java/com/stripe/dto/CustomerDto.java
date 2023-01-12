package com.stripe.dto;

public class CustomerDto
{
	private String id;
	private String name;
	private String email;
	private String phone;
	private String description;
	private String address_city;
	private String address_country;
	private String address_line1;
	private String address_line2;
	private String address_postal_code;
	private String address_state;
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddress_city() {
		return address_city;
	}
	public void setAddress_city(String address_city) {
		this.address_city = address_city;
	}
	public String getAddress_country() {
		return address_country;
	}
	public void setAddress_country(String address_country) {
		this.address_country = address_country;
	}
	public String getAddress_line1() {
		return address_line1;
	}
	public void setAddress_line1(String address_line1) {
		this.address_line1 = address_line1;
	}
	public String getAddress_line2() {
		return address_line2;
	}
	public void setAddress_line2(String address_line2) {
		this.address_line2 = address_line2;
	}
	public String getAddress_postal_code() {
		return address_postal_code;
	}
	public void setAddress_postal_code(String address_postal_code) {
		this.address_postal_code = address_postal_code;
	}
	public String getAddress_state() {
		return address_state;
	}
	public void setAddress_state(String address_state) {
		this.address_state = address_state;
	}
	@Override
	public String toString() {
		return "CustomerDto [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", description="
				+ description + ", address_city=" + address_city + ", address_country=" + address_country
				+ ", address_line1=" + address_line1 + ", address_line2=" + address_line2 + ", address_postal_code="
				+ address_postal_code + ", address_state=" + address_state + "]";
	}
}