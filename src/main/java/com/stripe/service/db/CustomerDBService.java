package com.stripe.service.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.stripe.dto.CustomerDto;
import com.stripe.entity.Customer;
import com.stripe.entity.User;
import com.stripe.repository.CustomerRepo;
import com.stripe.repository.UserRepo;
import com.stripe.service.conversion.EntityDtoService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerDBService 
{
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	EntityDtoService formatService;
	
	private boolean flag;

	public User addUser(CustomerDto dto) throws DataIntegrityViolationException,SQLException,Exception
	{
		User user = formatService.customerDtoToUser(dto);
		user.setActive(1);
		System.out.println(user.toString());
		//insert || update into db
		return userRepo.save(user);
	}
	public CustomerDto getUserByName(String name)
	{
		try {
		Optional<User> user = userRepo.findByName(name);
		if(user != null)
		{
			CustomerDto dto = formatService.userToCustomerDto(user.get());
			return dto;
		}
		else
		{
			return null;
		}
		}
		catch (Exception e) {
			System.out.println("user db exception : " + e.getMessage());
			return null;
		}
	}
	public CustomerDto getUser(int phone,String email)
	{
		try {
		Optional<User> user = userRepo.findByPhoneOrEmail(phone, email);
		if(user != null)
		{
			CustomerDto dto = formatService.userToCustomerDto(user.get());
			return dto;
		}
		else
		{
			return null;
		}
		}
		catch (Exception e) {
			System.out.println("user db exception : " + e.getMessage());
			return null;
		}
	}
	public CustomerDto getUserById(int userID)
	{
		try {
		Optional<User> user = userRepo.findById(userID);
		if(user != null)
		{
			CustomerDto dto = formatService.userToCustomerDto(user.get());
			return dto;
		}
		else
		{
			return null;
		}
		}
		catch (Exception e) {
			System.out.println("user db exception : " + e.getMessage());
			return null;
		}
	}
	public List<CustomerDto> getAllUser()
	{
		try {
		List<User> users = userRepo.findAll();
		List<CustomerDto> customers = new ArrayList<>();
		
		if(users.size() != 0)
		{
			users.forEach(
					user -> customers.add(formatService.userToCustomerDto(user))
					);
			return customers;
		}
		else
		{
			return null;
		}
		}
		catch (Exception e) {
			System.out.println("user db exception : " + e.getMessage());
			return null;
		}
	}
	public boolean deleteUser(int uderId)
	{
		try {
			userRepo.deleteById(uderId);
		}
		catch (Exception e) {
			System.out.println("user db exception : " + e.getMessage());
		}
		flag = (userRepo.findActiveById(uderId) == 0)?true:false;
		return flag;
	}
	public boolean deleteCustomerByUserId(int userId)
	{
		try {
		customerRepo.deleteByUserId(userId);
		}
		catch (Exception e) {
			System.out.println("user db exception : " + e.getCause());
		}
		flag = (customerRepo.findActiveByUserId(userId) == 0)?true:false;
		return flag;
	}
	public Customer createCustomer(CustomerDto dto,User user)
	{
		//convert from customer-dto to customer
		Customer customer = formatService.customerDtoToCustomer(dto,user);
		//check if user-id already exist then get customer and update customer-id else add new customer-id
		int id = 0;
		id = getCustomer(user.getId()).getId();
		if(id != 0)
			customer.setId(id);
		System.out.println("customer : " + customer.toString());
		return addCustomerId(customer);
	}
	
	public Customer addCustomerId(Customer customer)
	{
		try 
		{
			customer.setActive(1);
			customer = customerRepo.save(customer);
			return customer;
		}
		catch (Exception e) {
			System.out.println("customer db exception : " + e.getMessage());
		}
		return null;
	}
	public Customer getCustomer(int userId)
	{
		try {
		Customer customer = customerRepo.findByUserId(userId);
		if(customer != null)
			return customer;
		}
		catch (Exception e) {
			System.out.println("customer db exception : " + e.getMessage());
		}
		return null;
	}
	public Customer getCustomer(String customerId)
	{
		try {
		Customer customer = customerRepo.findByCustomerId(customerId);
		if(customer != null)
			return customer;
		}
		catch (Exception e) {
			System.out.println("customer db exception : " + e.getMessage());
		}
		return null;
	}
}