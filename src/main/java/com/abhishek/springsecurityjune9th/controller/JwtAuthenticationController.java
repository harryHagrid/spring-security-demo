package com.abhishek.springsecurityjune9th.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abhishek.springsecurityjune9th.config.jwt.JwtRequest;
import com.abhishek.springsecurityjune9th.config.jwt.JwtResponse;
import com.abhishek.springsecurityjune9th.config.jwt.JwtUtils;
import com.abhishek.springsecurityjune9th.entity.Customer;
import com.abhishek.springsecurityjune9th.service.CustomerServiceImpl;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtTokenUtil;

	@Autowired
	private CustomerServiceImpl customerService;

	@PostMapping("/authenticate")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest request) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (DisabledException e) {
			throw new Exception("User disabled", e);
		} catch (BadCredentialsException e) {
			System.err.println(e.getMessage());
			throw new Exception("Bad credentials", e);
		}
		
		final UserDetails userDetails = this.customerService.loadUserByUsername(request.getUsername());
		
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new JwtResponse(token));
	}

}
