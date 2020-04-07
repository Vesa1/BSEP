package com.example.demo.service;

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
		if(cert.isRevoked() || cert.getCertificateType().equals(CertificateType.endEntity)) {
			return true;
		}
		return false;
	}
}
