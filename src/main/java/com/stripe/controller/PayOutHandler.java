package com.stripe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.exception.StripeException;
import com.stripe.model.Payout;
import com.stripe.service.PayoutService;

@RestController
@RequestMapping("/stripe/payout")
public class PayOutHandler
{
	@Autowired
	private PayoutService payoutService;
	private Payout payOut;
	
	@GetMapping("/acc")
	public Payout getBalance1() 
	{
		try {
			payOut = payoutService.payOut();
			return payOut;		
		}
		catch (StripeException e) {
			System.out.println(e.getMessage());
			return null;			
		}
	}
}