package com.stripe.dto;

public class CardDto
{
	private String number;
	private int exp_month;
	private int exp_year;
	private int cvc;
	
	public CardDto() {
		
	}
	public CardDto(String number, int exp_month, int exp_year, int cvc) {
		super();
		this.number = number;
		this.exp_month = exp_month;
		this.exp_year = exp_year;
		this.cvc = cvc;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getExp_month() {
		return exp_month;
	}
	public void setExp_month(int exp_month) {
		this.exp_month = exp_month;
	}
	public int getExp_year() {
		return exp_year;
	}
	public void setExp_year(int exp_year) {
		this.exp_year = exp_year;
	}
	public int getCvc() {
		return cvc;
	}
	public void setCvc(int cvc) {
		this.cvc = cvc;
	}
	@Override
	public String toString() {
		return "CardDto [number=" + number + ", exp_month=" + exp_month + ", exp_year=" + exp_year + ", cvc=" + cvc
				+ "]";
	}	
}