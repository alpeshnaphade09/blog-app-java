package com.example.demo.payload;

import lombok.Data;

@Data
public class JwtAuthRequest {
	
	private String username;
	
	private String password;



}
