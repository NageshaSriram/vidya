package com.vidya.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SemesterRequest {

	private Long id;

	@NotBlank
	@Size(max = 100)
	private String name;

	@NotNull
	private Long classes_id;

	public SemesterRequest() {

	}

	public SemesterRequest(String name, Long classees_id) {
		this.name = name;
		this.classes_id = classees_id;
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