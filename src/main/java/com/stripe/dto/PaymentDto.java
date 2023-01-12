package com.stripe.dto;

public class PaymentDto
{
	private String cus_id;
	private String payment_id;
	private String type;
	private CardDto card;
	private BillingDto billing;
	
	
	public String getCus_id() {
		return cus_id;
	}
	public void setCus_id(String cus_id) {
		this.cus_id = cus_id;
	}
	public String getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public CardDto getCard() {
		return card;
	}
	public void setCard(CardDto card) {
		this.card = card;
	}
	public BillingDto getBilling() {
		return billing;
	}
	public void setBilling(BillingDto billing) {
		this.billing = billing;
	}
}