package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.*;

public interface CertificateRepository extends JpaRepository<Certificate, Long>{
	List<Certificate> findAll();
}
