package com.vidya.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidya.model.AssignmentSubmission;
import com.vidya.payload.request.AssignmentSubmissionRequest;
import com.vidya.payload.response.ApiResponse;
import com.vidya.repository.AssignmentRepository;
import com.vidya.repository.AssignmentSumbissionRepository;
import com.vidya.repository.UserRepository;
import com.vidya.util.AppConstants;

@RestController
@RequestMapping("/assignement")
public class AssignmentController {

	private static final Logger logger = LoggerFactory.getLogger(AssignmentController.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	AssignmentSumbissionRepository assignmentSumbissionRepository;

	@Autowired
	AssignmentRepository assignmentRepository;

	@PostMapping("/submit")
	public ResponseEntity<?> sumbitAssignment(@Valid AssignmentSubmissionRequest assignmentSubmissionRequest) {
		try {
			if (!userRepository.existsById(assignmentSubmissionRequest.getUser_id())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "User doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}

			if (!assignmentRepository.existsById(assignmentSubmissionRequest.getAssignment_id())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Assignmet doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}
			AssignmentSubmission assignmentSubmission = new AssignmentSubmission(
					assignmentSubmissionRequest.getDocument(), null, null);
			assignmentSubmission.setUser(userRepository.findById(assignmentSubmissionRequest.getUser_id()).get());
			assignmentSubmission
					.setAssignment(assignmentRepository.findById(assignmentSubmissionRequest.getAssignment_id()).get());
			assignmentSumbissionRepository.save(assignmentSubmission);
			return new ResponseEntity<Object>(new ApiResponse(false, "Assignment submitted successfully"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}

	}

	@PutMapping("/update/{submissionId}/user/{userId}")
	public ResponseEntity<?> updateAssignment(@Valid @PathVariable("submissionId") @NotNull Long submissionId,
			@Valid @PathVariable("userId") @NotNull Long userId, @RequestBody String documentFilename) {
		try {
			if (!userRepository.existsById(userId)) {
				return new ResponseEntity<Object>(new ApiResponse(false, "User doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}

			if (!assignmentRepository.existsById(submissionId)) {
				return new ResponseEntity<Object>(new ApiResponse(false, "User doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}
			assignmentSumbissionRepository.updateDocumentById(documentFilename, submissionId);
			return new ResponseEntity<Object>(new ApiResponse(false, "Assignment submitted successfully"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}

	}
	
	@PutMapping("/correction/{submissionId}/user/{userId}")
	public ResponseEntity<?> assignmentCorrection(@Valid @PathVariable("submissionId") @NotNull Long submissionId,
			@Valid @PathVariable("userId") @NotNull Long userId, @RequestBody String feedback) {
		try {
			if (!userRepository.existsById(userId)) {
				return new ResponseEntity<Object>(new ApiResponse(false, "User doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}

			if (!assignmentRepository.existsById(submissionId)) {
				return new ResponseEntity<Object>(new ApiResponse(false, "User doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}
			assignmentSumbissionRepository.assignmentCorrectionById(feedback, submissionId);
			return new ResponseEntity<Object>(new ApiResponse(false, "Assignment submitted successfully"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}

	}

}
