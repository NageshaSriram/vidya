package com.vidya.payload.request;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LessonRequest {

	private Long id;

	@NotEmpty
	@NotNull
	@Column(length = 60)
	private String name;

	@NotNull
	private Long book_id;

	@NotNull
	private Long user_id;

	public LessonRequest() {

	}

	public LessonRequest(Long id, @NotEmpty @NotNull String name, @NotNull Long book_id, @NotNull Long user_id) {
		super();
		this.id = id;
		this.name = name;
		this.book_id = book_id;
		this.user_id = user_id;
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

	public Long getBook_id() {
		return book_id;
	}

	public void setBook_id(Long book_id) {
		this.book_id = book_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "LessonRequest [id=" + id + ", name=" + name + ", book_id=" + book_id + ", user_id=" + user_id + "]";
	}

}