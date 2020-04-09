package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.enums.CertificateType;

@Entity
@Table(name ="certificate")
public class Certificate implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //Ne moze string
	private Long id;
	
	@Column(nullable = false) 
	CertificateType certificateType;
	
	@Column(nullable = false) 
	boolean revoked;
	
	public Certificate() {
		
	}
	
	public Certificate(CertificateType type) {
		certificateType = type;
		revoked = false;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CertificateType getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(CertificateType certificateType) {
		this.certificateType = certificateType;
	}
	public boolean isRevoked() {
		return revoked;
	}
	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

}
