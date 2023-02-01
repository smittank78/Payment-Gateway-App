package com.stripe.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		params = new HashMap<>();
		params.put("phone", dto.getPhone());
		params.put("name", dto.getName());
		params.put("email", dto.getEmail());
		params.put("description", dto.getDescription());

		customer = Customer.create(params);
		System.out.println("created---------------------------------------------------------------------");
		return customer;
	}

	public Customer retrive(String id) throws StripeException {
		Stripe.apiKey = stripe_key;

		customer = Customer.retrieve(id);
		System.out.println("retrive---------------------------------------------------------------------");

		return customer;
	}

	public Customer delete(String id) throws StripeException {
		Stripe.apiKey = stripe_key;

		customer = Customer.retrieve(id);

		Customer deletedCustomer = customer.delete();
		System.out.println("delete---------------------------------------------------------------------");

		return deletedCustomer;
	}

	public Customer update(String id) throws StripeException {
		Stripe.apiKey = stripe_key;

		customer = Customer.retrieve(id);

		Map<String, Object> metadata = new HashMap<>();
		metadata.put("order_id", "6735");
		params = new HashMap<>();
		params.put("metadata", metadata);

		Customer updatedCustomer = customer.update(params);
		System.out.println("update---------------------------------------------------------------------");

		return updatedCustomer;
	}

	public List<String> getAllCustomers() throws StripeException {
		Stripe.apiKey = stripe_key;
		Map<String, Object> params = new HashMap<>();
		List<String> listOfCustomers = new ArrayList<>();
		CustomerCollection customers = Customer.list(params);
		customers.getData().forEach(c -> {
			listOfCustomers.add(c.getName());
		});
		return listOfCustomers;
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