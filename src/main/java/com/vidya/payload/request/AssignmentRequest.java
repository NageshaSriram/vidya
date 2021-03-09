package com.vidya.payload.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AssignmentRequest {
	private Long id;

	@NotEmpty
	@NotNull
	private String name;

	@NotEmpty
	@NotNull
	private String content;

	@NotEmpty
	@NotNull
	private String content_type;

	@NotNull
	private Long lesson_id;

	@NotNull
	private Long user_id;

	public AssignmentRequest() {

	}

	public AssignmentRequest(Long id, @NotEmpty @NotNull String name, @NotEmpty @NotNull String content,
			@NotEmpty @NotNull String content_type, @NotNull Long lesson_id, Long user_id) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.content_type = content_type;
		this.lesson_id = lesson_id;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public Long getLesson_id() {
		return lesson_id;
	}

	public void setLesson_id(Long lesson_id) {
		this.lesson_id = lesson_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "NotesRequest [id=" + id + ", name=" + name + ", content=" + content + ", content_type=" + content_type
				+ ", lesson_id=" + lesson_id + ", user_id=" + user_id + "]";
	}

}