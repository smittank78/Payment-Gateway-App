package com.stripe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.dto.CustomerDto;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.service.CustomerService;

@RestController
@RequestMapping("/stripe/customer")
public class CustomerHandler 
{
	@Autowired
	private CustomerService service;
	
	private Customer customer; 
	
	@GetMapping("/")
	public String test()
	{
		return "test done";
	}
	@PostMapping("/createCustomer")
	public ResponseEntity<String> createCustomer(@RequestBody CustomerDto dto)
	{			
		try 
		{
			customer = service.create(dto);
			dto.setId(customer.getId());
			System.out.println(customer);
			return new ResponseEntity<String>(dto.toString(),HttpStatus.OK);
		} 
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/getCustomer/{cus_id}")
	public ResponseEntity<String> getCustomer(@PathVariable("cus_id") String id)
	{			
		System.out.println("retive : " + id);
		try 
		{
			customer = service.retrive(id);

			System.out.println(customer);
			return new ResponseEntity<String>(customer.toString(),HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			return new ResponseEntity<String>(e.getMessage().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/deleteCustomer/{cus_id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("cus_id") String id)
	{
		System.out.println("delete : " + id);
		try 
		{
			customer = service.delete(id);

			System.out.println(customer);
			return new ResponseEntity<String>(customer.getEmail() + "is deleted sucessfully",HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			return new ResponseEntity<String>(e.getMessage().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/updateCustomer/{cus_id}")
	public ResponseEntity<String> updateCustomer(@PathVariable("cus_id") String id)
	{
		System.out.println("update : " + id);
		try 
		{
			customer = service.update(id);

			System.out.println(customer);
			return new ResponseEntity<String>(customer.getEmail() + "is updated",HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			return new ResponseEntity<String>(e.getMessage().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/getAllCustomer")
	public List<String> getAllCustomers()
	{
		try {
			return service.getAllCustomers();
		} 
		catch (Exception e) {
			return null;
		}
	}
	
	@GetMapping("/getAllCustomer/{cus_name}")
	public CustomerSearchResult findCustomer(@PathVariable("cus_name") String name)
	{
		System.out.println("customer : " + name );
		try {
			return service.findCustomer(name);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}