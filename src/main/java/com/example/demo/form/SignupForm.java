package com.example.demo.form;

import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupForm {
	@Size(min = 1,max = 20)
	private String loginId;
	
	@Size(min = 1 ,max = 20)
	private String password;

}
