package com.vidya.payload.response;

public class BookResponse {

	private Long id;

	private String name;

	private String author;

	private String description;

	private Long subject_id;

	public BookResponse() {
	}

	public BookResponse(Long id, String name, String author, String description, Long subject_id) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.description = description;
		this.subject_id = subject_id;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(Long subject_id) {
		this.subject_id = subject_id;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author + ", description=" + description
				+ ", subject_id=" + subject_id + "]";
	}

}