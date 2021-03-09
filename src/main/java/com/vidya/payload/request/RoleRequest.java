package com.vidya.payload.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;

public class RoleRequest {

	private Long id;

	@NaturalId
	@NotNull
	@NotEmpty
	private String name;

	public RoleRequest(Long id, @NotNull @NotEmpty String name) {
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
		return "Role [id=" + id + ", name=" + name  + "]";
	}

}