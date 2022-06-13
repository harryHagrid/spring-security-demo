package com.abhishek.springsecurityjune9th.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abhishek.springsecurityjune9th.entity.Customer;
import com.abhishek.springsecurityjune9th.entity.CustomerDetail;
import com.abhishek.springsecurityjune9th.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements UserDetailsService {
	
	@Autowired
	CustomerRepository customerRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
			
			Customer customer = this.customerRepo.findByEmail(username).get();
			
			return new CustomerDetail(customer);
		} catch(NoSuchElementException e) {
			
			System.out.println("No User found with email - "+ username);
			throw new UsernameNotFoundException("No User found with email - "+ username);
		}
		
	}

}
