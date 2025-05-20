package com.alephys.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginDto {

	@NotBlank(message = "Employee Id is Required")
	@NotEmpty(message = "Employee Id is Required")
	private String email;
	
	@NotBlank(message = "Employee is Required")
	@NotEmpty(message = "Employee is Required")
	private String password;



}
