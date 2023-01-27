package com.stripe.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.stripe.model.PaymentMethodCollection;

@Service
public class OutputFormatService {
	private List list = new ArrayList<>();
	
	public List paymentCardDetail(PaymentMethodCollection listPaymentAllMethodsCustomer) 
	{
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
		return list;
	}
}