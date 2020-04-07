package com.example.demo.dto;

public class NewCertificateDTO {

	private String commonName;
	private String country;
	private String surname;
	private String givenName;
	private String organization;
	private String organizationalUnit;
	private boolean selfSigned;
	private String issuer;
	private String email;
	private String serialNumber;
	private String certificateType;
	
	
	
	public NewCertificateDTO() {
		super();
	}

	public NewCertificateDTO(String commonName, String country, String surname, String givenName, String organization,
			String organizationalUnit, boolean selfSigned, String issuer, String email, String serialNumber,
			String certificateType) {
		super();
		this.commonName = commonName;
		this.country = country;
		this.surname = surname;
		this.givenName = givenName;
		this.organization = organization;
		this.organizationalUnit = organizationalUnit;
		this.selfSigned = selfSigned;
		this.issuer = issuer;
		this.email = email;
		this.serialNumber = serialNumber;
		this.certificateType = certificateType;
	}
	
	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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

	public boolean isSelfSigned() {
		return selfSigned;
	}

	public void setSelfSigned(boolean selfSigned) {
		this.selfSigned = selfSigned;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	@Override
	public String toString() {
		return "NewCertificateDTO [commonName=" + commonName + ", country=" + country + ", surname=" + surname
				+ ", givenName=" + givenName + ", organization=" + organization + ", organizationalUnit="
				+ organizationalUnit + ", selfSigned=" + selfSigned + ", issuer=" + issuer + ", email=" + email
				+ ", serialNumber=" + serialNumber + ", certificateType=" + certificateType + "]";
	}
}
