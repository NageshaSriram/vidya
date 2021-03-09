package com.vidya.payload.response;

public class LessonResponse {

	private Long id;

	private String name;

	private Long book_id;

	private Long user_id;

	public LessonResponse() {

	}

	public LessonResponse(Long id, String name, Long book_id, Long user_id) {
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