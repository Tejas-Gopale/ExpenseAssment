package com.alephys.expensetracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

	private String name;
	 private String email;
	 private String password;
	    
}
