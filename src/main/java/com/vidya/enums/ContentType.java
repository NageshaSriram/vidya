package com.vidya.enums;

public enum ContentType {
	TEXT(1, "Text"), DOCUMENT(2, "Document");

	int index;
	String name;

	ContentType(int index, String name) {
		this.index = index;
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
