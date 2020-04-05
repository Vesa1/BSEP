package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.demo.enums.CertificateType;

@Entity
public class Certificate implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //Ne moze string
	private Long id;
	
	CertificateType certificateType;
	boolean revoked;
	
	public Certificate() {
		
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
