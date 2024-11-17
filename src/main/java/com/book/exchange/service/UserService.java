package com.book.exchange.service;

import org.springframework.stereotype.Service;

import com.book.exchange.request.LoginRequest;
import com.book.exchange.request.RegisterUserRequest;
import com.book.exchange.request.ResetPasswordRequest;
import com.book.exchange.response.CustomResponse;
import com.book.exchange.response.UserResponse;

@Service
public interface UserService {
	CustomResponse registerUser(RegisterUserRequest registerUser);
	UserResponse login(LoginRequest loginRequest);
	CustomResponse resetPassword(String email,ResetPasswordRequest resetPasswordRequest);
}
