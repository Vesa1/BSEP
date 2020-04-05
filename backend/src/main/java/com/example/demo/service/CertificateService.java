package com.example.demo.service;

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
	
	
}
