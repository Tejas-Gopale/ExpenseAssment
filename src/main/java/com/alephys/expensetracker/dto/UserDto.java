package com.alephys.expensetracker.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class UserDto {

	private long id;
		
	private String password;
	
	private String name;
	
	private String email;

	
}
