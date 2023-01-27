package com.stripe.service.conversion;

import org.springframework.stereotype.Service;
import com.stripe.dto.BillingDto;
import com.stripe.dto.PurchaseProductDto;
import com.stripe.model.PaymentIntent;

@Service
public class ConvertionService {
	
	public BillingDto paymentIntentToBillingDto(PaymentIntent intent)
	{
		BillingDto dto = new BillingDto();
		PurchaseProductDto productDto = new PurchaseProductDto();
		dto.setCus_id(intent.getCustomer());
		dto.setPayment_method_id(intent.getPaymentMethod());
		dto.setTransaction_id(intent.getId());
		
		return null;
	}	
}