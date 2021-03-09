package com.vidya.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.vidya.model.audit.DateAudit;

@Entity
@Table(name = "classes", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "organization_id" }) })
public class Classes extends DateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id")
	private Organization organization;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "classes")
	private Set<User> users = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "classes")
	private Set<Subject> subjects = new HashSet<>();

	public Classes(@NotNull @NotEmpty String name) {
		super();
		this.name = name;
	}

	public Classes() {
		super();
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

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Set<User> getUsers() {
		return users;
	}

	@Override
	public String toString() {
		return "Classes [id=" + id + ", name=" + name + ", organization=" + organization + ", users=" + users + "]";
	}

}
