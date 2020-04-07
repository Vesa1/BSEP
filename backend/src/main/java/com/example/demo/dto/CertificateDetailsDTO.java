package com.example.demo.dto;

public class CertificateDetailsDTO {

	private String issuedTo;
	private String issuedBy;
	private String validFrom;
	private String version;
	private String serialNumber;
	private String signatureAlgorithm;
	private String signatureHashAlgorithm;
	private String publicKey;
	
	public CertificateDetailsDTO() {}

	public CertificateDetailsDTO(String issuedTo, String issuedBy, String validFrom, String version,
			String serialNumber, String signatureAlgorithm, String signatureHashAlgorithm, String publicKey) {
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
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
}
