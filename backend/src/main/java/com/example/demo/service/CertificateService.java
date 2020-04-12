package com.example.demo.service;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.CertificateType;
import com.example.demo.model.Certificate;
import com.example.demo.repository.CertificateRepository;

@Service
public class CertificateService {

	@Autowired
	private CertificateRepository certficateRepository;
	
	public CertificateType getType(BigInteger bi) {
		Long id = bi.longValue();
		Certificate cert = certficateRepository.getById(id);
		return cert.getCertificateType();
	}
	
	public boolean isRevoked(BigInteger bi) {
		Long id = bi.longValue();
		Certificate c = certficateRepository.getById(id);
		return c.isRevoked();
	}
	
	public Long saveCertificate(CertificateType type) {
		Certificate c = new Certificate(type);
		Certificate ret = certficateRepository.save(c);
		if(ret == null) {
			return null;
		} else {
			return ret.getId();
		}
	}
	
	public boolean isRevokedAndEndEntity(BigInteger bi) {
		Long id = bi.longValue();
		Certificate cert = certficateRepository.getById(id);
		if(cert != null && (cert.isRevoked() || cert.getCertificateType().equals(CertificateType.endEntity))) {
			return true;
		}
		return false;
	}
	
	public Certificate getCertificateById(Long id) {
		Certificate cert = certficateRepository.getById(id);
		
		if(cert != null) {
			return cert;
		}
		return null;
	}

	public boolean revokeCertificate(Long idCert) {
		Certificate cert = certficateRepository.getById(idCert);
		if(cert != null) {
			cert.setRevoked(true);
			this.certficateRepository.save(cert);
			return true;
		}
		
		return false;
	}

	public boolean checkValidity(Long idCert) {
		Certificate cert = certficateRepository.getById(idCert);
		if(cert != null) {
			return cert.isRevoked();
		}
		
		return false;
	}
}
