package com.example.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.NewCertificateDTO;


@RestController
@RequestMapping(value = "/certificate")
public class CertificateController {
	
	@RequestMapping(
			value = "/createNewCertificate",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity createNewCertificate(@RequestBody NewCertificateDTO newCertificateDTO) {
		System.out.println("---------------------------------");
		System.out.println("[CertificateController] -> createNewCertificate method");
		System.out.println("Post data: ");
		System.out.println(newCertificateDTO.toString());
		
		
		
		System.out.println("---------------------------------");
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/issuerList",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity issuerList() {
		System.out.println("Issuer List");
		return new ResponseEntity(HttpStatus.OK);
	}

}
