package com.stripe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Balance;
import com.stripe.model.BalanceTransactionCollection;
import com.stripe.service.BalanceService;

@RestController
@RequestMapping("/stripe/balance")
public class BalanceHandler {
	@Autowired
	private BalanceService service;

	@Value("${stripe.key}")
	private String stripe_key;

	@GetMapping("/getBalance")
	public void getBalance() {
		Stripe.apiKey = stripe_key;

		try {
			Balance balance = service.getBalance();
			System.out.println("balance : " + balance);
		} catch (StripeException e) {
			System.out.println(e.getMessage());
		}
	}

	@GetMapping("/transaction/getTransaction")
	public String getTransaction() {
		Stripe.apiKey = stripe_key;

		try {
			return service.getTransaction().toString();
		} catch (StripeException e) {
			System.out.println(e.getMessage());
		}
		return "error";
	}

	@GetMapping("/transaction/getAllBalanceTransaction")
	public BalanceTransactionCollection getAllBalanceTransaction() {
		Stripe.apiKey = stripe_key;

		try {
			return service.getAllTransaction();
		} catch (StripeException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}