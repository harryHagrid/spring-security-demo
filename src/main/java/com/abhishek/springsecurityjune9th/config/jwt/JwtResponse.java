package com.abhishek.springsecurityjune9th.config.jwt;

public class JwtResponse {
	
	private String jwttoken;
	
	

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getJwttoken() {
		return jwttoken;
	}

	public void setJwttoken(String jwttoken) {
		this.jwttoken = jwttoken;
	}
	
	

}
