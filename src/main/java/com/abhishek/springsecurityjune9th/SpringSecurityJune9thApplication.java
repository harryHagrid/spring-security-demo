package com.abhishek.springsecurityjune9th;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.abhishek.springsecurityjune9th.entity.Authorities;
import com.abhishek.springsecurityjune9th.entity.Customer;
import com.abhishek.springsecurityjune9th.entity.Users;
import com.abhishek.springsecurityjune9th.repository.AuthoritiesRepository;
import com.abhishek.springsecurityjune9th.repository.CustomerRepository;
import com.abhishek.springsecurityjune9th.repository.UsersRepository;

@SpringBootApplication
public class SpringSecurityJune9thApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private UsersRepository usersRepo;
	
	@Autowired
	private AuthoritiesRepository authoritiesRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJune9thApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("invoking the run method");
		this.customerRepo.saveAll(this.loadCustomersData());
		
		this.usersRepo.saveAll(this.loadUsers());
		
		this.authoritiesRepo.saveAll(this.loadAuthorities());
		
		
	}
	
	private List<Users> loadUsers() {

		List<Users> users = new ArrayList<>();
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();

		Users u1 = new Users();

		u1.setUsername("happy");
		u1.setPassword(encoder.encode("12345"));
		u1.setEnabled(true);

		users.add(u1);

		u1 = new Users();

		u1.setUsername("mama");
		u1.setPassword(encoder.encode("12345"));
		u1.setEnabled(true);

		users.add(u1);
		return users;

	}

	private List<Authorities> loadAuthorities() {

		List<Authorities> authorities = new ArrayList<>();

		Users u1 = this.usersRepo.findById("happy").get();

		Authorities auth1 = new Authorities();
		auth1.setUsername(u1);
		auth1.setAuthority("admin");

		authorities.add(auth1);
		
		Users u2 = this.usersRepo.findById("mama").get();

		Authorities auth2 = new Authorities();
		auth2.setUsername(u2);
		auth2.setAuthority("user");

		authorities.add(auth2);

		return authorities;
	}
	
	
	private List<Customer> loadCustomersData() {
		
		List<Customer> customers = new ArrayList<>();
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();

		
		Customer customer1 = new Customer();
		customer1.setEmail("axess1@sc.com");
		customer1.setPassword(encoder.encode("12345"));
		customer1.setRole("admin");
		
		customers.add(customer1);
		
		Customer customer2 = new Customer();
		customer2.setEmail("axess2@sc.com");
		customer2.setPassword(encoder.encode("12345"));
		customer2.setRole("user");
		
		customers.add(customer2);
		
		Customer customer3 = new Customer();
		customer3.setEmail("axess3@sc.com");
		customer3.setPassword(encoder.encode("12345"));
		customer3.setRole("db_admin");
		
		customers.add(customer3);
		
		
		return customers;
	}

}
