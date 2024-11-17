package com.book.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.exchange.request.LoginRequest;
import com.book.exchange.request.RegisterUserRequest;
import com.book.exchange.request.ResetPasswordRequest;
import com.book.exchange.response.CustomResponse;
import com.book.exchange.response.UserResponse;
import com.book.exchange.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping("/register")
	public ResponseEntity<CustomResponse> registerUser(@RequestBody RegisterUserRequest registerUser) {
		CustomResponse customResponse = userService.registerUser(registerUser);
		return new ResponseEntity<CustomResponse>(customResponse,HttpStatus.CREATED);
	}
	@PostMapping("/login")
	public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest) {
		UserResponse userResponse= userService.login(loginRequest);
		return new ResponseEntity<UserResponse>(userResponse,HttpStatus.OK);
		
	}
	
	@PostMapping("/resetpassword")
	public ResponseEntity<CustomResponse> resetPassword(@RequestParam String email,@RequestBody ResetPasswordRequest resetPasswordRequest){
		CustomResponse customResponse = userService.resetPassword(email, resetPasswordRequest);
		return new ResponseEntity<CustomResponse>(customResponse,HttpStatus.OK);
		
	}
}
