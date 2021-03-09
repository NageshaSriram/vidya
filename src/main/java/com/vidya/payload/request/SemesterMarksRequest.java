package com.vidya.payload.request;

import javax.validation.constraints.NotNull;

public class SemesterMarksRequest {
	private Long id;

	@NotNull
	private Integer total_marks;

	@NotNull
	private Double obtained_marks;

	@NotNull
	private Double percentage;

	@NotNull
	private Long semester_id;

	@NotNull
	private Long user_id;

	@NotNull
	private Long subject_id;

	public SemesterMarksRequest() {

	}

	public SemesterMarksRequest(Long id, Integer total_marks, Double obtained_marks, Double percentage,
			Long semester_id, Long user_id, Long subject_id) {
		super();
		this.id = id;
		this.total_marks = total_marks;
		this.obtained_marks = obtained_marks;
		this.percentage = percentage;
		this.semester_id = semester_id;
		this.user_id = user_id;
		this.subject_id = subject_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTotal_marks() {
		return total_marks;
	}

	public void setTotal_marks(Integer total_marks) {
		this.total_marks = total_marks;
	}

	public Double getObtained_marks() {
		return obtained_marks;
	}

	public void setObtained_marks(Double obtained_marks) {
		this.obtained_marks = obtained_marks;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Long getSemester_id() {
		return semester_id;
	}

	public void setSemester_id(Long semester_id) {
		this.semester_id = semester_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(Long subject_id) {
		this.subject_id = subject_id;
	}

}