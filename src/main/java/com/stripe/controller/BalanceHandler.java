package com.stripe.controller;

import java.util.HashMap;
import java.util.Map;

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
	public Map<String, Long> getBalance() {
		Stripe.apiKey = stripe_key;
		Map< String, Long> balance = new HashMap<>();
		
		try {
			Balance bal = service.getBalance();
			System.out.println("balance : " + bal);
			balance.put("available", bal.getAvailable().get(0).getAmount());
			balance.put("pending", bal.getPending().get(0).getAmount());

		} catch (StripeException e) {
			System.out.println(e.getMessage());
		}
		return balance;
		
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