package com.stripe.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.dto.CustomerDto;
import com.stripe.entity.User;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.service.CustomerService;
import com.stripe.service.db.CustomerDBService;

@RestController
@RequestMapping("/stripe/customer")
public class CustomerHandler 
{
	@Autowired
	private CustomerService service;
	
	@Autowired
	private CustomerDBService cusDbService;
	
	private Customer customer; 
	private boolean flag;
	@GetMapping("/")
	public String test()
	{
		return "test done";
	}
	/*
	 * Flow :
	 * create-customer -> new customer on stripe 
	 * check db user exist or not   
	 * if exist then update data else add new user
	 * check db user has customer-id or not
	 * if yes then update it with new customer-id else add new record
	 */
	@PostMapping("/createCustomer")
	public ResponseEntity createCustomer(@RequestBody CustomerDto dto)
	{			
		try 
		{
			/*
			 * data-base operations
			 */
			if(customer.getId() != null)
			{
				dto.setCusId(customer.getId());
				//get user by phone and email if not exist then create new user else update old 
				CustomerDto user2 = cusDbService.getUser(dto.getPhone(), dto.getEmail());
				/*
				 * if user exit then add old user-id to dto
				 * retrive customer-id from db and delete from stripe if exist
				 */
				if(user2 != null)
				{
					dto.setUserId(user2.getUserId());
					com.stripe.entity.Customer customer2 = cusDbService.getCustomer(user2.getUserId());
					service.delete(customer2.getCustomerId());
				}
				/* Stripe Operation :
				 * ================
				 * create customer on stripe		
				 */
				customer = service.create(dto);
				
				//create new user or update old
				User user = cusDbService.addUser(dto);
				dto.setUserId(user.getId());
				System.out.println("dto : " + dto.toString());
				//add customer-id into db
				com.stripe.entity.Customer customer1 = cusDbService.createCustomer(dto,user);
				
				System.out.println(customer1);
				return new ResponseEntity<CustomerDto>(dto,HttpStatus.OK);
				
			}
			else
				throw new Exception("customer not created");
		} 
		catch (DataIntegrityViolationException e) {
			System.err.println("data integritiy exception : " + e.getRootCause());
			return new ResponseEntity<String>("phone or email already used",HttpStatus.NOT_ACCEPTABLE);
		}
		catch (Exception e) {
			System.err.println("Exception while creating customer : " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/getCustomer/{cus_id}")
	public ResponseEntity<String> getCustomer(@PathVariable("cus_id") String cusId)
	{			
		System.out.println("retive : " + cusId);
		try 
		{
			/*
			 * retrive customer from stripe		
			 */
			customer = service.retrive(cusId);

			System.out.println(customer);
			return new ResponseEntity<String>(customer.toString(),HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			return new ResponseEntity<String>(e.getMessage().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/deleteCustomer/{user_id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("user_id") int userId)
	{
		System.out.println("delete : " + userId);
		try 
		{
			String cusId = cusDbService.getCustomer(userId).getCustomerId();
			/*
			 * delete customer from stripe		
			 */
			customer = service.delete(cusId);
			
			System.out.println("customer deleted from stripe : " + customer);
			/*
			 * in-active customer in db
			 */
			flag = cusDbService.deleteCustomerByUserId(userId);
			System.out.println("customer : " + cusId + " inactivated in db : " + flag);
			/*
			 * in-active user in db
			 */
			flag = cusDbService.deleteUser(userId);
			System.out.println("user : " + userId + " inactivated in db : " + flag);

			if(flag)
				return new ResponseEntity<String>(customer + " is deleted sucessfully",HttpStatus.OK);
			else
				return new ResponseEntity<String>("please try again",HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			System.err.println("Exception while delete customer : " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/updateCustomer")
	public ResponseEntity<String> updateCustomer(@RequestBody CustomerDto dto)
	{
		System.out.println("update : " + dto.getUserId());
		try 
		{
			if(dto.getUserId() !=  0)
			{
				/*
				 * update customer on stripe		
				 */
				String customerId = cusDbService.getCustomer(dto.getUserId()).getCustomerId();
				dto.setCusId(customerId);
 				customer = service.update(dto);
			/*
			 * update user in db
			 * get customer-id by user_id 
			 * get user by user-id
			 * set name,phone,email,user-id into dto from user
			 * user save
			 */
			CustomerDto user = cusDbService.getUserById(dto.getUserId());
			if(dto.getPhone() != 0)
				dto.setPhone(user.getPhone());
			if(dto.getName() == null)
				dto.setName(user.getName());
			if(dto.getEmail() == null)
				dto.setEmail(user.getEmail());
			cusDbService.addUser(dto);
			return new ResponseEntity<String>(dto.getName() + " is updated sucessfully",HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<String>("please add user id",HttpStatus.OK);
			}
		} 
		catch (Exception e) 
		{
			System.err.println("Exception while updating customer : " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllCustomer")
	public List<String> getAllCustomers()
	{
		try {
			/*
			 * retrive all customers from stripe		
			 */

			List<String> listOfCustomers = new ArrayList<>();
			CustomerCollection customers = service.getAllCustomers();
			customers.getData().forEach(c -> {
				listOfCustomers.add(c.getName());
			});
			return listOfCustomers;
		} 
		catch (Exception e) {
			return null;
		}
	}
	
	@GetMapping("/getCustomerId/{cus_name}")
	public String findCustomer(@PathVariable("cus_name") String name)
	{
		System.out.println("customer : " + name );
/*		try {
 * 			retrive customer by name from stripe
			return service.findCustomer(name);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
*/	
		CustomerDto userByName = cusDbService.getUserByName(name);
		String customerId = cusDbService.getCustomer(userByName.getUserId()).getCustomerId();
		return customerId;
	}
	@GetMapping("/deleteAllCustomer")
	public void deleteAllCustomer()
	{
		try {
			/*
			 * delete all customers from stripe		
			 */
			CustomerCollection customers = service.getAllCustomers();
			customers.getData().forEach(c->{
				try {
					service.delete(c.getId());
				} catch (StripeException e) {
					System.err.println("Exception while updating customer : " + e.getMessage());
				}
			});
		} 
		catch (Exception e) {
			System.err.println("Exception while updating customer : " + e.getMessage());
		}
	}
}