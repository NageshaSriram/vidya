package com.vidya.payload.response;

public class AssignmentSubmissionResponse {

	private Long id;

	private String document;

	private Integer correctioStatus;

	private String feedback;

	private Long assignment_id;

	private Long user_id;

	public AssignmentSubmissionResponse() {

	}

	public AssignmentSubmissionResponse(Long id, String document, Integer correctioStatus, String feedback,
			Long assignment_id, Long user_id) {
		super();
		this.id = id;
		this.document = document;
		this.correctioStatus = correctioStatus;
		this.feedback = feedback;
		this.assignment_id = assignment_id;
		this.user_id = user_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Integer getCorrectioStatus() {
		return correctioStatus;
	}

	public void setCorrectioStatus(Integer correctioStatus) {
		this.correctioStatus = correctioStatus;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public Long getAssignment_id() {
		return assignment_id;
	}

	public void setAssignment_id(Long assignment_id) {
		this.assignment_id = assignment_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

}