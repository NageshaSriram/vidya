package com.vidya.payload.response;

public class UserResponse {

	private Long id;
	private String username;
	private String name;
	private String email;
	private Long organization_id;
	private long role_id;
	private Long class_id;

	public UserResponse(Long id, String username, String name, String email, Long organization_id, long role_id,
			Long class_id) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.email = email;
		this.organization_id = organization_id;
		this.role_id = role_id;
		this.class_id = class_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}

	public long getRole_id() {
		return role_id;
	}

	public void setRole_id(long role_id) {
		this.role_id = role_id;
	}

	public Long getClass_id() {
		return class_id;
	}

	public void setClass_id(Long class_id) {
		this.class_id = class_id;
	}

	@Override
	public String toString() {
		return "UserResponse [id=" + id + ", username=" + username + ", name=" + name + ", email=" + email
				+ ", organization_id=" + organization_id + ", role_id=" + role_id + ", class_id=" + class_id + "]";
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    return id == ((UserResponse) obj).id;
	}
	
	

}
