package com.vidya.payload.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class FeedbackRequest {

	private Long id;

	@NotNull
	private Long feedback_to;
	@NotNull
	private Long feedback_from;
	@NotNull
	@Max(value = 5)
	private Double rating;

	private String feedback;

	public FeedbackRequest() {

	}

	public FeedbackRequest(Long feedback_to, Long feedback_from, Double rating, String feedback) {
		super();
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

	public Long getFeedback_from() {
		return feedback_from;
	}

	public void setFeedback_from(Long feedback_from) {
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
		return "FeedbackRequest [id=" + id + ", feedback_to=" + feedback_to + ", feedback_from=" + feedback_from
				+ ", rating=" + rating + ", feedback=" + feedback + "]";
	}

}