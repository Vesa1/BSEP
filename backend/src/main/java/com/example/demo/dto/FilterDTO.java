package com.example.demo.dto;

public class FilterDTO {

	private String type;
	private String commonName;
	
	public FilterDTO() {
		
	}

	public FilterDTO(String type, String commonName) {
		super();
		this.type = type;
		this.commonName = commonName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	@Override
	public String toString() {
		return "FilterDTO [type=" + type + ", commonName=" + commonName + "]";
	}
	
}
