package com.vidya.payload.response;

import javax.validation.constraints.NotNull;

public class SemesterResponse {

	private Long id;

	private String name;

	private Long classes_id;

	public SemesterResponse() {

	}

	public SemesterResponse(Long id, String name, @NotNull Long classes_id) {
		super();
		this.id = id;
		this.name = name;
		this.classes_id = classes_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getClasses_id() {
		return classes_id;
	}

	public void setClasses_id(Long classes_id) {
		this.classes_id = classes_id;
	}

	@Override
	public String toString() {
		return "SubjectRequest [id=" + id + ", name=" + name + ", classes_id=" + classes_id + "]";
	}

}