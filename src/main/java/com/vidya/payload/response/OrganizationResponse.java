package com.vidya.payload.response;

public class OrganizationResponse {

	private Long id;

	private String name;

	private String logo;
	
	public OrganizationResponse(Long id, String name, String logo) {
		super();
		this.id = id;
		this.name = name;
		this.logo = logo;
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
