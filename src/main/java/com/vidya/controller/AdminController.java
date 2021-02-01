package com.vidya.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidya.model.Organization;
import com.vidya.payload.request.OrganizationRequest;
import com.vidya.payload.response.ApiResponse;
import com.vidya.repository.OrganizationRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	OrganizationRepository organizationRepository;

	private static Logger logger = LoggerFactory.getLogger(AdminController.class);

	@PostMapping("/add/organization")
	public ResponseEntity<?> addOrganization(@Valid @RequestBody final OrganizationRequest organizationRequest)
			throws Exception {
		if (organizationRepository.existsByName(organizationRequest.getName())) {
			return new ResponseEntity<Object>(new ApiResponse(false, "Organization already exist!!"),
					HttpStatus.BAD_REQUEST);
		}
		logger.info("Adding organization: requested payload [{}]", organizationRequest);

		Organization organization = new Organization(organizationRequest);	
		organizationRepository.save(organization);

		return ResponseEntity.ok(new ApiResponse(true, "Organization added successfully!!"));

	}

}
