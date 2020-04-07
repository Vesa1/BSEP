package com.example.demo.dto;

public class IssuerCertificateDTO {
	
	private String SerialNumber;
	private String commonName;
	private String country;
	private String surname;
	private String givenName;
	private String organization;
	private String organizationalUnit;
	private String email;
	
	public IssuerCertificateDTO() {
		
	}
	
	public IssuerCertificateDTO(String serialNumber, String commonName, String country, String surname,
			String givenName, String organization, String organizationalUnit, String email) {
		super();
		SerialNumber = serialNumber;
		this.commonName = commonName;
		this.country = country;
		this.surname = surname;
		this.givenName = givenName;
		this.organization = organization;
		this.organizationalUnit = organizationalUnit;
		this.email = email;
	}



	public String getSerialNumber() {
		return SerialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		SerialNumber = serialNumber;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getOrganizationalUnit() {
		return organizationalUnit;
	}
	public void setOrganizationalUnit(String organizationalUnit) {
		this.organizationalUnit = organizationalUnit;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
