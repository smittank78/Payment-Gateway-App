package com.stripe.service.conversion;

import org.springframework.stereotype.Service;
import com.stripe.dto.CustomerDto;
import com.stripe.entity.Customer;
import com.stripe.entity.User;

@Service
public class EntityDtoService {
	
	public User customerDtoToUser(CustomerDto dto)
	{
		User user = new User();
		user.setName(dto.getName());
		user.setPhone(dto.getPhone());
		user.setEmail(dto.getEmail());
		user.setAddressCity(dto.getAddressCity());
		user.setAddressState(dto.getAddressState());
		user.setAddressCountry(dto.getAddressCountry());
		
		if(dto.getUserId() != 0)
		{
			user.setId(dto.getUserId());
		}
		return user;
	}	
	
	public CustomerDto userToCustomerDto(User user)
	{
		CustomerDto dto = new CustomerDto();
		dto.setName(user.getName());
		dto.setPhone(user.getPhone());
		dto.setEmail(user.getEmail());
		dto.setAddressCity(user.getAddressCity());
		dto.setAddressState(user.getAddressState());
		dto.setAddressCountry(user.getAddressCountry());
		dto.setUserId(user.getId());
		
		return dto;
	}	
	
	public Customer customerDtoToCustomer(CustomerDto dto,User user)
	{
		Customer customer = new Customer();
		customer.setCustomerId(dto.getCusId());
		customer.setUser(user);
		return customer;
	}	
}