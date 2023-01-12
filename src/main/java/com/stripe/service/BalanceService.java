package com.stripe.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Balance;
import com.stripe.model.BalanceTransaction;
import com.stripe.model.BalanceTransactionCollection;

@Service
public class BalanceService {
	@Value("${stripe.key}")
	private String stripe_key;

	private Map<String, Object> params;

	public Balance getBalance() throws StripeException
	{
		Stripe.apiKey = stripe_key;
		
		return Balance.retrieve();
	}
	
	public BalanceTransaction getTransaction() throws StripeException
	{
		Stripe.apiKey = stripe_key;

		BalanceTransaction balanceTransaction = BalanceTransaction.retrieve("txn_1032HU2eZvKYlo2CEPtcnUvl");
		return balanceTransaction;
	}	
	public BalanceTransactionCollection getAllTransaction() throws StripeException
	{
		Stripe.apiKey = stripe_key;

		params = new HashMap<>();

		BalanceTransactionCollection balanceTransactions =  BalanceTransaction.list(params);
	
		return balanceTransactions;
	}	
}