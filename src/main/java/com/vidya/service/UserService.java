package com.vidya.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidya.model.User;
import com.vidya.repository.UserRepository;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepository userRepository;

	User findByEmail(String email) {
		logger.debug("find user by email [{}]", email);
		return userRepository.findByEmail(email).orElse(null);
	}

	User findByUsernameOrEmail(String username, String email) {
		logger.debug("find user by email [{}] username [{}]", email, username);
		return userRepository.findByUsernameOrEmail(username, email).orElse(null);
	}

	List<User> findByIdIn(List<Long> userIds) {
		logger.debug("find user by userIds [{}]", userIds);
		return userRepository.findByIdIn(userIds);
	}

	User findByUsername(String username) {
		logger.debug("find user by username [{}]", username);
		return userRepository.findByUsername(username).orElse(null);
	}

	Boolean existsByUsername(String username) {
		logger.debug("User exist by username [{}]", username);
		return userRepository.existsByUsername(username);
	}

	Boolean existsByEmail(String email) {
		logger.debug("User exist by email [{}]", email);
		return userRepository.existsByEmail(email);
	}
}
