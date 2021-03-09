package com.vidya.payload.response;

import com.vidya.model.User;

public class FeedbackResponse {

	private Long id;

	private Long feedback_to;

	private UserResponse feedback_from;
	private Double rating;

	private String feedback;

	public FeedbackResponse() {

	}

	public FeedbackResponse(Long id, Long feedback_to, UserResponse feedback_from, Double rating, String feedback) {
		this.id = id;
		this.feedback_to = feedback_to;
		this.feedback_from = feedback_from;
		this.rating = rating;
		this.feedback = feedback;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFeedback_to() {
		return feedback_to;
	}

	public void setFeedback_to(Long feedback_to) {
		this.feedback_to = feedback_to;
	}

	public UserResponse getFeedback_from() {
		return feedback_from;
	}

	public void setFeedback_from(UserResponse feedback_from) {
		this.feedback_from = feedback_from;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Override
	public String toString() {
		return "FeedbackResponse [id=" + id + ", feedback_to=" + feedback_to + ", feedback_from=" + feedback_from
				+ ", rating=" + rating + ", feedback=" + feedback + "]";
	}

}