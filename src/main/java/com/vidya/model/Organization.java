package com.vidya.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.vidya.model.audit.DateAudit;
import com.vidya.payload.request.OrganizationRequest;

@Entity
@Table(name = "organizations", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
public class Organization extends DateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 126)
	private String name;

	@NotBlank
	@Size(max = 126)
	private String logo;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "organization")
	private Set<Classes> classes = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "organization")
	private Set<User> users = new HashSet<>();

	public Organization() {

	}

	public Organization(Long id, @NotBlank @Size(max = 126) String name) {
		this.id = id;
		this.name = name;
	}

	public Organization(@Valid OrganizationRequest organizationRequest) {
		this.id = organizationRequest.getId();
		this.name = organizationRequest.getName();
		this.logo = organizationRequest.getLogo();
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

}
