package com.vidya.payload.response;

import java.util.List;

public class TeacherSubjectAssociationResponse {

	private UserResponse user;

	private List<SubjectResponse> subjects;

	public TeacherSubjectAssociationResponse() {

	}

	public TeacherSubjectAssociationResponse(UserResponse user, List<SubjectResponse> subjects) {
		super();
		this.user = user;
		this.subjects = subjects;
	}

	public UserResponse getUser() {
		return user;
	}

	public void setUser(UserResponse user) {
		this.user = user;
	}

	public List<SubjectResponse> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectResponse> subjects) {
		this.subjects = subjects;
	}

}