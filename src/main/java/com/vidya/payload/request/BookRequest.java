package com.vidya.payload.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BookRequest {

	@NotEmpty
	@NotNull
	private String name;

	private String author;

	private String description;

	@NotNull
	private Long subject_id;

	public BookRequest() {

	}

	public BookRequest(@NotEmpty @NotNull String name, String author, String description) {
		super();
		this.name = name;
		this.author = author;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(Long subject_id) {
		this.subject_id = subject_id;
	}

}