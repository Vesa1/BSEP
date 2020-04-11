package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.*;

public interface CertificateRepository extends JpaRepository<Certificate, Long>{
	
	public List<Certificate> findAll();
	
	@SuppressWarnings("unchecked")
	public Certificate save(Certificate c);
	
	public Certificate getById(Long id);
	public boolean getByRevoked(Long id);
}
