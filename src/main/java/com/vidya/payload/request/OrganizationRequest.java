package com.vidya.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class OrganizationRequest {

	private Long id;

	@NotBlank
	@Size(max = 126)
	@Size(min = 3)
	private String name;

	@NotBlank
	@Size(max = 126)
	private String logo;

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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "OrganizationRequest [id=" + id + ", name=" + name + ", logo=" + logo + "]";
	}

}
