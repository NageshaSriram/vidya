package com.vidya.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidya.payload.UserSummary;
import com.vidya.security.CurrentUser;
import com.vidya.security.UserPrincipal;

@RestController
@RequestMapping("/api")
public class UserController {
	
	 private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	    @GetMapping("/user/me")
	    @PreAuthorize("hasRole('USER')")
	    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
	    	logger.info("Fetching user summary");
	        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
	        return userSummary;
	    }
}
