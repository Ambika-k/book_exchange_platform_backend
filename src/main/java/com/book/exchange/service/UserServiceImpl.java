package com.book.exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.exchange.entity.User;
import com.book.exchange.exception.InvalidPasswordException;
import com.book.exchange.exception.UserAlreadyExistsException;
import com.book.exchange.exception.UserNotFoundException;
import com.book.exchange.repository.UserRepository;
import com.book.exchange.request.LoginRequest;
import com.book.exchange.request.RegisterUserRequest;
import com.book.exchange.request.ResetPasswordRequest;
import com.book.exchange.response.CustomResponse;
import com.book.exchange.response.UserResponse;
import com.book.exchange.util.UserUtil;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public CustomResponse registerUser(RegisterUserRequest registerUser) {

		String encryptedPassword;
		User user = new User();
		CustomResponse customResponse = new CustomResponse();
		try {
			if (null != userRepository.findByEmail(registerUser.getEmail())) {
				throw new UserAlreadyExistsException("User already exists with this emailid");
			}
			encryptedPassword = UserUtil.encryptPassword(registerUser.getPassword());
			user.setName(registerUser.getName());
			user.setEmail(registerUser.getEmail());
			user.setPassword(encryptedPassword);
			user.setMobileNumber(registerUser.getMobileNumber());
			userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		customResponse.setMessage("User registered successfully");
		return customResponse;
	}

	@Override
	public UserResponse login(LoginRequest loginRequest) {
		User user = userRepository.findByEmail(loginRequest.getEmail());
		if (null == user) {
			throw new UserNotFoundException("No user exists with this email");
		}
		String encryptedPassword = user.getPassword();
		UserResponse userResponse = new UserResponse();
		String decryptedPassword = null;
		try {
			decryptedPassword = UserUtil.decryptPassword(encryptedPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (loginRequest.getPassword().equals(decryptedPassword)) {
			userResponse.setEmail(user.getEmail());
			userResponse.setName(user.getName());
			userResponse.setMobileNumber(user.getMobileNumber());
		} else {
			System.out.println("Throwing exception");
			throw new InvalidPasswordException("invalid password");
		}

		return userResponse;
	}

	@Override
	public CustomResponse resetPassword(String email, ResetPasswordRequest resetPasswordRequest) {
		User user = userRepository.findByEmail(email);
		if (null == user) {
			throw new UserNotFoundException("No user exists with this email");
		}

		String encryptedPassword = user.getPassword();
		try {
			String decryptedPassword = UserUtil.decryptPassword(encryptedPassword);
			if (!decryptedPassword. equals(resetPasswordRequest.getOldPassword())) {
				// throw exception;
				throw new InvalidPasswordException("Old password is invalid");
			} else {
				String newEncryptedPassword = UserUtil.encryptPassword(resetPasswordRequest.getNewPassword());
				user.setPassword(newEncryptedPassword);
				userRepository.save(user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new InvalidPasswordException("Old password is invalid");
		}
		CustomResponse customResponse = new CustomResponse("Reset the password successfully");
		return customResponse;
	}

}
