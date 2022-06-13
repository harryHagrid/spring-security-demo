package com.abhishek.springsecurityjune9th.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhishek.springsecurityjune9th.entity.Customer;
import com.abhishek.springsecurityjune9th.repository.CustomerRepository;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@PostMapping("/register")
	public String saveCustomer(@RequestBody Customer newCustomer) {
		
		this.customerRepo.save(newCustomer);
		
		return "Customer with email- " + newCustomer.getEmail() +" registered successfully";
		
	}
	
	@GetMapping("/all")
	public List<Customer> getAll() {
		return this.customerRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Customer getCustomerById(@PathVariable long id) {
		
		Customer customer = this.customerRepo.findById(id).get();
		
		return customer;
	}
	
	@DeleteMapping("/{id}")
	public String deleteCustomerById(@PathVariable long id) {
		
		this.customerRepo.deleteById(id);
		
		return "Customer deleted successfully";
	}

}
