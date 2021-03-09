package com.vidya.payload.request;

public class TeacherSubjectAssociationRequest {

	private Long user;

	private Long subject_id;

	public TeacherSubjectAssociationRequest() {

	}

	public TeacherSubjectAssociationRequest(Long user, Long subject_id) {
		super();
		this.user = user;
		this.subject_id = subject_id;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Long getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(Long subject_id) {
		this.subject_id = subject_id;
	}

}