package com.stripe.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Payout;

@Service
public class PayoutService {
	@Value("${stripe.key}")
	private String stripe_key;

	private Map<String, Object> params;

	public Payout payOut() throws StripeException
	{
		Stripe.apiKey = stripe_key;

		Map<String, Object> metadata= new HashMap<>();
		
		metadata.put("item", "");
		metadata.put("metadata" ,"");
		metadata.put( "brand","");
	
		params = new HashMap<>();
		params.put("amount", 11000);
		params.put("currency", "INR");
		params.put("description", "Test Payout");
		params.put("currency", "INR");
		params.put("metadata", metadata);
		params.put("statement_descriptor", "test charged");
		
		Payout create = Payout.create(params);
		return create;
	}
}