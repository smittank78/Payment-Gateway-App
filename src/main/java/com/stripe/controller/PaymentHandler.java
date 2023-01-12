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
import com.stripe.dto.CardDto;
import com.stripe.dto.PaymentDto;
import com.stripe.dto.PurchaseProductDto;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.service.CustomerService;
import com.stripe.service.PaymentService;

@RestController
@RequestMapping("/stripe/payment")
public class PaymentHandler
{
	@Autowired
	private PaymentService payService;
	
	@Autowired
	private CustomerService customerService;
	
	@Value("${stripe.key}")
	private String stripe_key;

	@GetMapping("/createCard")
	public List getBalance(@RequestBody PaymentDto dto) //,@PathVariable("cus_id") String id) 
	{
		List list = new ArrayList<>();
		try {
			payService.createCard(dto);
			Customer customer = customerService.retrive(dto.getCus_id());
			PaymentMethodCollection listPaymentAllMethodsCustomer = payService.listPaymentAllMethodsCustomer(customer);
			System.out.println("------------------------------------------------------------------");
			System.out.println(listPaymentAllMethodsCustomer);
			listPaymentAllMethodsCustomer.getData().forEach(a->{
				Map<String,Object> map = new HashMap<>();
				map.put("Payment_Type", a.getType());
				if(map.get("Payment_Type").equals("card"))
				{
					map.put("Card_Type", a.getCard().getFunding());
				}
				map.put("Brand", a.getCard().getBrand());
				map.put("Last4digit", a.getCard().getLast4());
				map.put("Exipre_Month", a.getCard().getExpMonth());
				map.put("Exipre_Year", a.getCard().getExpYear());
				map.put("Network", a.getCard().getNetworks().getAvailable());
				map.put("cvc", a.getCard().getChecks().getCvcCheck());
				map.put("Pyment_id", a.getId());
				list.add(map);
			});
		} 
		catch (StripeException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	@GetMapping("/purchase/{cus_id}/{payment_id}")
	public List<PurchaseProductDto> getBalance1(@PathVariable("cus_id") String cus_id,@PathVariable("payment_id") String payment_id,@RequestBody BillingDto dto) throws StripeException //,@PathVariable("cus_id") String id) 
	{
		System.out.println("cus : " + cus_id);
		System.out.println("payment : " + payment_id);

		List<PurchaseProductDto> list = payService.chargeCard(dto,cus_id,payment_id);
		return list;
	}
}