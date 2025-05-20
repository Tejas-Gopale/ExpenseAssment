package com.alephys.expensetracker.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSignUpDto {

	@NotBlank(message = "Name is Required")
	@NotEmpty(message = "Name is Required")
	@Size(max = 50 , min=2 , message = "Name of the User Must be in between 2 to 50 characters")
	private String name;
		
	@NotBlank(message = "Emial is Required")
	@Email(message = "Email Must be Valid Email")
	private String email;

	@NotBlank(message = "Employee is Required")
	@NotEmpty(message = "Employee is Required")
	@Size(max = 50 , min=2)
	private String password;
	
	
	
}
