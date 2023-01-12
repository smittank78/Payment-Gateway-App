package com.stripe.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.dto.BillingDto;
import com.stripe.dto.PaymentDto;
import com.stripe.dto.PurchaseProductDto;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;

@Service
public class PaymentService {
	
	@Value("${stripe.key}")
	private String stripe_key;

	private boolean flag;
	private Map<String, Object> params;
	private Map<String, Object> metadata;
	private Map<String, Object> card;
	private PaymentMethodCollection listPaymentMethods;
	private float total_amount = 0;
	private int counter =1;
	public void createCard(PaymentDto dto) throws StripeException 
	{
		Stripe.apiKey = stripe_key;
		
		//create payment method
		PaymentMethod createPayment = createPaymentMethod(dto);
		//attach method with payment
		attachCustomer(dto.getCus_id(), createPayment);	
	}
	public List<PurchaseProductDto> chargeCard(BillingDto dto,String cusId,String paymentId)  
	{
		
		Stripe.apiKey = stripe_key;
		
		List<Object> paymentMethodTypes = new ArrayList<>();
		paymentMethodTypes.add("card");
		
		Map<String, Object> metadata= new HashMap<>();
		
		dto.getPurchase().forEach(d -> {
		metadata.put("item no " + String.valueOf(counter) , "");
		metadata.put("metadata " + String.valueOf(counter), d.getProduct());
		metadata.put( "brand " + String.valueOf(counter),d.getBrand());
		metadata.put("model " + String.valueOf(counter), d.getModel());
		metadata.put("quantity " + String.valueOf(counter) ,String.valueOf(d.getQty()));
		metadata.put("amount " + String.valueOf(counter), String.valueOf(d.getAmount() * d.getQty()));
		metadata.put("purchase_date " + String.valueOf(counter),String.valueOf(new Date()));
		total_amount += (d.getAmount() * d.getQty());
		counter++;
		});
		
		Map<String, Object> params = new HashMap<>();
		params.put("amount", (int)total_amount * 100);
		params.put("currency", "INR");
		params.put( "payment_method_types",paymentMethodTypes);
		params.put("payment_method", paymentId);
		params.put("confirm", true);
		params.put("customer", cusId);
		params.put("metadata", metadata);
		params.put("receipt_email", "giantgujarat@gmail.com");
		params.put("description","metadata available for purchased items");
		
		System.out.println("bill created for purchased items : " + total_amount);
		try {
		PaymentIntent paymentIntent =  PaymentIntent.create(params);
		System.out.println(paymentIntent);
		flag = true;
		}
		catch (StripeException e) 
		{
			flag = false;
			System.out.println(e.getMessage());
			return null;
		}
			return dto.getPurchase();
	}

	public PaymentMethod createPaymentMethod(PaymentDto dto) throws StripeException {
		metadata = new HashMap<>();
		metadata.put("card", "this card used for testing purpose");

		card = new HashMap<>();

		card.put("number", dto.getCard().getNumber());
		card.put("exp_month", dto.getCard().getExp_month());
		card.put("exp_year", dto.getCard().getExp_year());
		card.put("cvc", dto.getCard().getCvc());

		Map<String, Object> params = new HashMap<>();
		params.put("type", dto.getType());
		params.put("card", card);

		System.out.println(">>>>>>>>>>payment method----------------");

		PaymentMethod paymentMethod = PaymentMethod.create(params);
		System.out.println(paymentMethod);

		return paymentMethod;
	}

	public boolean attachCustomer(String id, PaymentMethod paymentMethod) {
		try 
		{
			params = new HashMap<>();
			params.put("customer", id);

			PaymentMethod updatedPaymentMethod = paymentMethod.attach(params);
			System.out.println(">>>>>>>>>>>>payment method attached with customer");
			flag = true;
			if(updatedPaymentMethod == null)
			{
				System.out.println(">>>>>>>>>>>>payment method not attached with customer");
				flag = false;
			}
		}
		catch (StripeException e)
		{
			flag= false;
		}
		return flag;		
	}
	
	public boolean dettachCustomer(PaymentMethod paymentMethod) {
		try 
		{
			PaymentMethod updatedPaymentMethod = paymentMethod.detach();
			flag = true;
			System.out.println(">>>>>>>>>>>>>payment method dettached");
		}
		catch (StripeException e)
		{
			flag= false;
		}
		return flag;
	}

	public PaymentMethodCollection listPaymentAllMethodsCustomer(Customer customer) throws StripeException {
		listPaymentMethods = customer.listPaymentMethods();
		System.out.println("fetch all payment methods attached with customer");
		
		return listPaymentMethods;
	}
}