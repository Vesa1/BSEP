package com.example.demo.dto;

import java.math.BigInteger;

public class CertificateDetailsDTO {

	private String issuedTo;
	private String issuedBy;
	private String validFrom;
	private int version;
	private BigInteger serialNumber;
	private String signatureAlgorithm;
	private String signatureHashAlgorithm;
	private String publicKey;
	
	public CertificateDetailsDTO() {}

	public CertificateDetailsDTO(String issuedTo, String issuedBy, String validFrom, int version,
			BigInteger serialNumber, String signatureAlgorithm, String signatureHashAlgorithm, String publicKey) {
		super();
		this.issuedTo = issuedTo;
		this.issuedBy = issuedBy;
		this.validFrom = validFrom;
		this.version = version;
		this.serialNumber = serialNumber;
		this.signatureAlgorithm = signatureAlgorithm;
		this.signatureHashAlgorithm = signatureHashAlgorithm;
		this.publicKey = publicKey;
	}

	public String getIssuedTo() {
		return issuedTo;
	}

	public void setIssuedTo(String issuedTo) {
		this.issuedTo = issuedTo;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public BigInteger getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(BigInteger serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSignatureAlgorithm() {
		return signatureAlgorithm;
	}

	public void setSignatureAlgorithm(String signatureAlgorithm) {
		this.signatureAlgorithm = signatureAlgorithm;
	}

	public String getSignatureHashAlgorithm() {
		return signatureHashAlgorithm;
	}

	public void setSignatureHashAlgorithm(String signatureHashAlgorithm) {
		this.signatureHashAlgorithm = signatureHashAlgorithm;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	@Override
	public String toString() {
		return "CertificateDetailsDTO [issuedTo=" + issuedTo + ", issuedBy=" + issuedBy + ", validFrom=" + validFrom
				+ ", version=" + version + ", serialNumber=" + serialNumber + ", signatureAlgorithm="
				+ signatureAlgorithm + ", signatureHashAlgorithm=" + signatureHashAlgorithm + ", publicKey=" + publicKey
				+ "]";
	}
}
