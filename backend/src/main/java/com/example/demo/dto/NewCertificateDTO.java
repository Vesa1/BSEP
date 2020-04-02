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
	
	
	
	public NewCertificateDTO() {
		super();
	}

	public NewCertificateDTO(java.lang.String commonName, java.lang.String country, java.lang.String surname,
			java.lang.String givenName, java.lang.String organization, java.lang.String organizationalUnit,
			boolean string, java.lang.String issuer) {
		super();
		this.commonName = commonName;
		this.country = country;
		this.surname = surname;
		this.givenName = givenName;
		this.organization = organization;
		this.organizationalUnit = organizationalUnit;
		this.selfSigned = string;
		this.issuer = issuer;
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
				+ organizationalUnit + ", selfSigned=" + selfSigned + ", issuer=" + issuer + "]";
	}
	
	
	
}
