package com.vidya.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "semestermarks", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "student_id", "semester_id", "subject_id" }) })
public class SemesterMarks {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Integer total_marks;

	@NotNull
	private Double obtained_marks;

	@NotNull
	private Double percentage;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "semester_id")
	private Semester semester;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private User user;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subject_id")
	private Subject subject;

	public SemesterMarks() {

	}

	public SemesterMarks(Integer total_marks, Double obtained_marks, Double percentage) {
		super();
		this.total_marks = total_marks;
		this.obtained_marks = obtained_marks;
		this.percentage = percentage;
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

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}