package com.vidya.enums;

public enum RoleName {
	SUPER_ADMIN(1L, "SUPER ADMIN"), ADMIN(2L, "ADMIN"), TEACHER(3L, "TEACHER"), STUDENT(4L, "STUDENT"), STAFF(5L, "STAFF");

	Long index;
	String name;

	private RoleName(Long index, String name) {
		this.index = index;
		this.name = name;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
