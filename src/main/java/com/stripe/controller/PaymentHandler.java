package com.stripe.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.dto.BillingDto;
import com.stripe.dto.PaymentDto;
import com.stripe.dto.PurchaseProductDto;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.service.CustomerService;
import com.stripe.service.OutputFormatService;
import com.stripe.service.PaymentService;

@RestController
@RequestMapping("/stripe/payment")
public class PaymentHandler
{
	@Autowired
	private PaymentService payService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OutputFormatService opService;
	
	@Value("${stripe.key}")
	private String stripe_key;

	/*
	 * this method creates payment method and attach it with customer, 
	 * by using that method user can make transaction
	 */
	@GetMapping("/createCard")
	public List createPaymentMethod(@RequestBody PaymentDto dto)
	{
		List list = new ArrayList<>();
		try {
			/*
			 * create payment method
			 */
			payService.createCard(dto);
			/*
			 * fetch customer to retrive all payment method associated with that customer
			 */
			Customer customer = customerService.retrive(dto.getCus_id());
			PaymentMethodCollection listPaymentAllMethodsCustomer = payService.listPaymentAllMethodsCustomer(customer);
			System.out.println("------------------------------------------------------------------");
			/*
			 * format payment details as we are going to send it to user
			 */
			list = opService.paymentCardDetail(listPaymentAllMethodsCustomer);
			System.out.println(listPaymentAllMethodsCustomer);
			
		} 
		catch (StripeException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	@GetMapping("/purchase")
	public BillingDto payment(@RequestBody BillingDto dto) throws StripeException //,@PathVariable("cus_id") String id) 
	{
		System.out.println("cus : " + dto.getCus_id());
		System.out.println("payment method : " + dto.getPayment_method_id());
		/*
		 * charge a customer
		 */
		dto = payService.chargeCard(dto);
		return dto;
	}
	/*
	 * retrive any payment transaction by transaction-id
	 */
	@GetMapping("/retrivePayment/{transaction-id}")
	public List<PurchaseProductDto> getBalance1(@PathVariable("transaction-id") String transaction_id) throws StripeException //,@PathVariable("cus_id") String id) 
	{
		System.out.println("transaction : " + transaction_id);

		payService.getTransaction(transaction_id);
		return null;
	}
}