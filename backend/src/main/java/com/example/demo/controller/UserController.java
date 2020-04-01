package com.example.demo.controller;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginDataDTO;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	private final static String USERNAME_ADMIN = "admin";
	private final static String PASSWORD_ADMIN = "adminAdmin";

	
	
	 @RequestMapping(
	            value = "/login",
	            method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity logout(@RequestBody LoginDataDTO loginCredentials){
	       	
		 	if(loginCredentials != null) {
		 		String username = loginCredentials.getUsername();
		 		String password = loginCredentials.getPassword();
		 		if(!username.equals(USERNAME_ADMIN) && !password.equals(PASSWORD_ADMIN)) {
			        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		 		}
		 	}
	       	
	        return new ResponseEntity(loginCredentials, HttpStatus.OK);
	    }
}
