package com.vidya.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ClassesPayload {

	private Long id;

	@NotEmpty
	@NotNull
	private String name;

	@NotNull
	private Long organization_id;

	public ClassesPayload(Long id, String name, Long organization_id) {
		super();
		this.id = id;
		this.name = name;
		this.organization_id = organization_id;
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

	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}

	@Override
	public String toString() {
		return "Classes [id=" + id + ", name=" + name + ", organization_id=" + organization_id + "]";
	}

}
