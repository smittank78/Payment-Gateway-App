package com.stripe.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import com.stripe.dto.CustomerDto;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.CustomerSearchResult;
import com.stripe.param.CustomerSearchParams;

@Service
public class CustomerService {
	@Value("${stripe.key}")
	private String stripe_key;

	private Customer customer;

	private Map<String, Object> params;

	public Customer create(CustomerDto dto) throws StripeException {
		Stripe.apiKey = stripe_key;
		
		Map<String, String> address = new HashMap<>();
		address.put("line1",dto.getAddressLine1());
		address.put("line2",dto.getAddressLine2());
		address.put("city",dto.getAddressCity());
		address.put("state",dto.getAddressState());
		address.put("country",dto.getAddressCountry());
		address.put("postal_code",dto.getAddressPostalCode());
		
		params = new HashMap<>();
		params.put("phone", dto.getPhone());
		params.put("name", dto.getName());
		params.put("email", dto.getEmail());
		params.put("description", dto.getDescription());
		params.put("address", address);

		customer = Customer.create(params);
		System.out.println("created---------------------------------------------------------------------");
		return customer;
	}

	public Customer retrive(String cusId) throws StripeException {
		Stripe.apiKey = stripe_key;

		customer = Customer.retrieve(cusId);
		System.out.println("retrive---------------------------------------------------------------------");

		return customer;
	}

	public Customer delete(String cusId) throws StripeException {
		Stripe.apiKey = stripe_key;

		try {
		System.out.println("delete---------------------------------------------------------------------");
		customer = Customer.retrieve(cusId);
		customer = customer.delete();
		}
		catch (Exception e) {
			customer = null;
		}
		
		return customer;
	}

	public Customer update(CustomerDto dto) throws StripeException {
		Stripe.apiKey = stripe_key;

		customer = Customer.retrieve(dto.getCusId());

		Map<String, String> address = new HashMap<>();
		address.put("line1",dto.getAddressLine1());
		address.put("line2",dto.getAddressLine2());
		address.put("city",dto.getAddressCity());
		address.put("state",dto.getAddressState());
		address.put("country",dto.getAddressCountry());
		address.put("postal_code",dto.getAddressPostalCode());

		Map<String, Object> metadata = new HashMap<>();
		metadata.put("desc", "adress updated");

		params = new HashMap<>();
		params.put("address", address);
		params.put("metadata", metadata);

		Customer updatedCustomer = customer.update(params);
		System.out.println("update---------------------------------------------------------------------");

		return updatedCustomer;
	}

	public CustomerCollection getAllCustomers() throws StripeException {
		Stripe.apiKey = stripe_key;
		Map<String, Object> params = new HashMap<>();
		CustomerCollection customers = Customer.list(params);
		return customers;
	}

	public CustomerSearchResult findCustomer(String name) throws StripeException {
		Stripe.apiKey = stripe_key;
		CustomerSearchParams params = CustomerSearchParams.builder()
				.setQuery(("name:'" + name +"'").toString()).build();

		CustomerSearchResult customer = Customer.search(params);
		System.out.println("customer : " + customer);
		return customer;
	}
}