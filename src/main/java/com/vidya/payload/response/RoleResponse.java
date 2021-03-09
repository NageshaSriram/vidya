package com.vidya.payload.response;

public class RoleResponse {

	private Long id;

	private String name;

	public RoleResponse(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
	@Override
	public String toString() {
		return "RoleResponse [id=" + id + ", name=" + name + "]";
	}

}