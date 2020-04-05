package com.example.demo.dto;

import java.math.BigInteger;

public class CertificateDTO {
	
	private BigInteger serialNumber;
	private String issuerOrganization;
	private String issuerOrganizationalUnit;
	private String subjectOrganization;
	private String subjectOrganizationalUnit;
	

	public CertificateDTO () {
		
	}

	public CertificateDTO(BigInteger serialNumber, String issuerOrganization,
			String issuerOrganizationalUnit,  String subjectOrganization,
			String subjectOrganizationalUnit) {
		super();
		this.serialNumber = serialNumber;
		
		this.issuerOrganization = issuerOrganization;
		this.issuerOrganizationalUnit = issuerOrganizationalUnit;
		this.subjectOrganization = subjectOrganization;
		this.subjectOrganizationalUnit = subjectOrganizationalUnit;
		
	}

	public BigInteger getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(BigInteger serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getIssuerOrganization() {
		return issuerOrganization;
	}

	public void setIssuerOrganization(String issuerOrganization) {
		this.issuerOrganization = issuerOrganization;
	}

	public String getIssuerOrganizationalUnit() {
		return issuerOrganizationalUnit;
	}

	public void setIssuerOrganizationalUnit(String issuerOrganizationalUnit) {
		this.issuerOrganizationalUnit = issuerOrganizationalUnit;
	}


	public String getSubjectOrganization() {
		return subjectOrganization;
	}

	public void setSubjectOrganization(String subjectOrganization) {
		this.subjectOrganization = subjectOrganization;
	}

	public String getSubjectOrganizationalUnit() {
		return subjectOrganizationalUnit;
	}

	public void setSubjectOrganizationalUnit(String subjectOrganizationalUnit) {
		this.subjectOrganizationalUnit = subjectOrganizationalUnit;
	}

	@Override
	public String toString() {
		return "CertificateDTO [serialNumber=" + serialNumber +  ", issuerOrganization=" + issuerOrganization + ", issuerOrganizationalUnit="
				+ issuerOrganizationalUnit +  ", subjectOrganization="
				+ subjectOrganization + ", subjectOrganizationalUnit=" + subjectOrganizationalUnit + "]";
	}
}
