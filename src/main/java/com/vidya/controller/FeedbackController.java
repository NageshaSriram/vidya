package com.vidya.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidya.model.Feedback;
import com.vidya.model.User;
import com.vidya.payload.request.FeedbackRequest;
import com.vidya.payload.response.ApiResponse;
import com.vidya.payload.response.FeedbackResponse;
import com.vidya.payload.response.UserResponse;
import com.vidya.repository.FeedbackRepository;
import com.vidya.repository.UserRepository;
import com.vidya.util.AppConstants;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

	private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	FeedbackRepository feedbackRepository;

	@PostMapping("/submit")
	public ResponseEntity<?> sumbitAssignment(@Valid FeedbackRequest feedbackRequest) {
		try {
			if (!userRepository.existsById(feedbackRequest.getFeedback_from())) {
				return new ResponseEntity<Object>(
						new ApiResponse(false, "Userid: " + feedbackRequest.getFeedback_from() + " doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}

			if (!userRepository.existsById(feedbackRequest.getFeedback_to())) {
				return new ResponseEntity<Object>(
						new ApiResponse(false, "Userid: " + feedbackRequest.getFeedback_to() + " doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}

			if (null != feedbackRequest.getId() && feedbackRepository.existsById(feedbackRequest.getId())) {
				feedbackRepository.updateFeedbackById(feedbackRequest.getFeedback(), feedbackRequest.getRating(),
						feedbackRequest.getId());
			} else {
				Feedback feedback = new Feedback(feedbackRequest.getRating(), feedbackRequest.getFeedback());
				feedback.setUser(userRepository.findById(feedbackRequest.getFeedback_from()).get());
				feedback.setFeedbackTo(feedbackRequest.getFeedback_to());
				feedbackRepository.save(feedback);
			}
			return new ResponseEntity<Object>(new ApiResponse(true, "Feedback submitted successfully"), HttpStatus.OK);

		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}

	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<FeedbackResponse>> getFeedbacks(@Valid @PathVariable("userId") @NotNull Long userId) {
		try {
			if (!userRepository.existsById(userId)) {
				return ResponseEntity.badRequest().build();
			}
			List<FeedbackResponse> feedbackResponse = feedbackRepository.findByFeedbackTo(userId).stream()
					.map(feedback -> {
						User user = feedback.getUser();
						return new FeedbackResponse(feedback.getId(), feedback.getFeedbackTo(),
								new UserResponse(user.getId(), user.getUsername(), user.getName(), user.getEmail(),
										user.getOrganization().getId(), user.getRole().getId(), user.getClasses().getId()), feedback.getRating(), feedback.getFeedback());
					}).collect(Collectors.toList());

			return ResponseEntity.ok(feedbackResponse);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@DeleteMapping("{feedbackId}")
	public ResponseEntity<?> deleteFeedbacks(@Valid @PathVariable("feedbackId") @NotNull Long feedbackId) {
		try {
			if (!feedbackRepository.existsById(feedbackId)) {
				return new ResponseEntity<Object>(
						new ApiResponse(false, "FeedbackId: " + feedbackId + " doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}
			feedbackRepository.deleteById(feedbackId);

			return new ResponseEntity<Object>(new ApiResponse(true, "Feedback deleted successfully"), HttpStatus.OK);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

}
